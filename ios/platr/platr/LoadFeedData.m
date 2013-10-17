//
//  LoadFeedData.m
//  platr
//
//  Created by wa on 10/12/13.
//  Copyright (c) 2013 com.platr. All rights reserved.
//

#import "LoadFeedData.h"

@implementation LoadFeedData

- (void)loadData
{
    NSString* str = @"{\"id\": 1,\"name\": \"Foo\",\"price\": 123,\"tags\": [ \"Bar\", \"Eek\" ],\"stock\": {\"warehouse\": 300,\"retail\": 20}}";
    NSData *data = [str dataUsingEncoding:NSUTF8StringEncoding];
    NSError *error;
    NSDictionary *json = [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:&error];
    NSLog(@"dictionary data %@",json);
}


@end
