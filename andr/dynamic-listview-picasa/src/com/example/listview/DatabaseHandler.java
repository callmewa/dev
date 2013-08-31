package com.example.listview;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
 
public class DatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "platr";
 
    // Contacts table name
    private static final String TABLE_CONTACTS = "places";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH = "phone";
    private static final String KEY_ADD = "address";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LON = "lon";

    public final Context context;
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_ADD + " TEXT,"
                + KEY_LAT + " REAL,"
                + KEY_LON + " REAL,"
                + KEY_PH + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        //insert statement
        SQLiteStatement insert = db.compileStatement("INSERT INTO " + TABLE_CONTACTS
                + "(" + KEY_NAME +"," + KEY_ADD +"," + KEY_LAT +","+ KEY_LON+","+ KEY_PH +")"
                + "VALUES (?,?,?,?,?)");

        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open("restaurant_info.csv");
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            Iterable<CSVRecord> parser = CSVFormat.DEFAULT.withHeader().parse(in);
            for (CSVRecord record : parser) {
                insert.bindString(1, record.get(KEY_NAME));
                insert.bindString(2, record.get(KEY_ADD));
                insert.bindDouble(3, Double.parseDouble(record.get(KEY_LAT)));
                insert.bindDouble(4, Double.parseDouble(record.get(KEY_LON)));
                insert.bindString(5, record.get(KEY_PH));
                insert.executeInsert();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new contact
    void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // Contact Name
        values.put(KEY_PH, contact.getPhone()); // Contact Phone
 
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }
 
    // Getting single contact
    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                KEY_NAME, KEY_PH}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }
     
    // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT " + KEY_ID +"," + KEY_NAME +"," + KEY_LAT +"," + KEY_LON +"," + KEY_PH + " FROM " + TABLE_CONTACTS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setLat(cursor.getDouble(2));
                contact.setLon(cursor.getDouble(3));
                contact.setPhone(cursor.getString(4));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return contactList;
    }
 
    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH, contact.getPhone());
 
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }
 
    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }
 
 
    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
 
}