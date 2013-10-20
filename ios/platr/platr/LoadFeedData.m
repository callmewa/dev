//
//  LoadFeedData.m
//  platr
//
//  Created by wa on 10/12/13.
//  Copyright (c) 2013 com.platr. All rights reserved.
//

#import "LoadFeedData.h"
#import "GoogleAuthenticator.h"

@implementation LoadFeedData

- (void)loadData
{
    NSString* str = @"{\"id\": 1,\"name\": \"Foo\",\"price\": 123,\"tags\": [ \"Bar\", \"Eek\" ],\"stock\": {\"warehouse\": 300,\"retail\": 20}}";
    NSData *data = [str dataUsingEncoding:NSUTF8StringEncoding];
    NSError *error;
    NSDictionary *json = [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:&error];
    NSLog(@"dictionary data %@",json);
}


- (void)setAuthHeader:(NSMutableURLRequest *)req with:(NSString *)authToken
{
    [req setValue: [NSString stringWithFormat:@"GoogleLogin auth=%@", authToken] forHTTPHeaderField:@"Authorization"] ;
}

- (void)loadFeed
{
    NSString* const url = @"https://picasaweb.google.com/data/feed/api/user/platr.upload?kind=album&fields=entry(id,title,summary,media:group(media:content))&alt=json";
    
    NSString* authToken = [GoogleAuthenticator getAuthToken];
    
    
    NSMutableURLRequest *req = [[NSMutableURLRequest  alloc] initWithURL:[NSURL URLWithString:url]];
    [self setAuthHeader:req with:authToken];
    NSURLResponse *response;
    NSError *error;
    NSData *responseData = [NSURLConnection sendSynchronousRequest:req returningResponse:&response error: &error ];
    NSString* responseStr = [[NSString alloc] initWithData:responseData
                                                  encoding:NSUTF8StringEncoding];
    
    NSLog(@"responseData: %@", responseStr);
    
    
}




@end
