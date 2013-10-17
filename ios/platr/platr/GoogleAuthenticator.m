//
//  GoogleAuthenticator.m
//  platr
//
//  Created by wa on 10/12/13.
//  Copyright (c) 2013 com.platr. All rights reserved.
//

#import "GoogleAuthenticator.h"

static NSString *AuthToken = nil;


@implementation GoogleAuthenticator

NSString *const authUrl = @"https://www.google.com/accounts/ClientLogin";
NSString *const authParams = @"accountType=HOSTED_OR_GOOGLE&service=lh2&source=gdata-Demo&Email=platr.upload@gmail.com&Passwd=foodimages";

- (NSString* ) authenticate
{
    NSData *postData = [authParams dataUsingEncoding: NSUTF8StringEncoding];
    NSMutableURLRequest *post = [[NSMutableURLRequest  alloc] initWithURL:[NSURL URLWithString:authUrl]];
    [post setHTTPMethod:@"POST"];
    [post setHTTPBody:postData];
    [post setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-type"];
    NSURLResponse *response;
    NSError *error;
    NSData *responseData = [NSURLConnection sendSynchronousRequest:post returningResponse:&response error: &error ];
    NSString* responseStr = [[NSString alloc] initWithData:responseData
                                              encoding:NSUTF8StringEncoding];
    
    NSLog(@"responseData: %@", responseStr);
    NSLog(@"responseData: %@", responseData);

    NSArray *lines = [responseStr componentsSeparatedByString:@"\n"];
    for(NSString *line in lines){
        if([line hasPrefix:@"Auth="]){
            NSString *authToken = [line substringFromIndex:5];
            AuthToken = authToken;
            return authToken;
        }
    }
    
    return nil;
}

@end
