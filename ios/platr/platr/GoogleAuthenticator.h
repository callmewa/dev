//
//  GoogleAuthenticator.h
//  platr
//
//  Created by wa on 10/12/13.
//  Copyright (c) 2013 com.platr. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface GoogleAuthenticator : NSObject

- (NSString *) authenticate;
    
+ (NSString *) getAuthToken;

@end
