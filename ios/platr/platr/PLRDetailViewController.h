//
//  PLRDetailViewController.h
//  platr
//
//  Created by wa on 10/11/13.
//  Copyright (c) 2013 com.platr. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface PLRDetailViewController : UIViewController

@property (strong, nonatomic) id detailItem;

@property (weak, nonatomic) IBOutlet UILabel *detailDescriptionLabel;
@end
