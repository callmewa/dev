package android.widget.ext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.listview.R;

/**
 * Created by callmewa on 8/4/13.
 */
public class TextViewBordered extends TextView {

    public TextViewBordered(Context context) {
        super(context, null);
        initWithColor(null);
    }

    public TextViewBordered(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a;
        a = context.obtainStyledAttributes(attrs, R.styleable.TextViewBordered);
        String border_color = a.getString(R.styleable.TextViewBordered_border_color);
        a.recycle();
        initWithColor(border_color);
    }

    private void initWithColor(String color){
        if(color == null){
            setBackgroundResource(R.drawable.border);
        }else{
            GradientDrawable drawable = (GradientDrawable)getResources().getDrawable(R.drawable.border);
            drawable.setStroke(1, Color.parseColor(color));
            setBackground(drawable);
        }
    }
}
