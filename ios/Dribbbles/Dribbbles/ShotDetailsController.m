//
//  ShotDetailsController.m
//  Dribbbles
//
//  Created by huben on 15/6/7.
//  Copyright (c) 2015年 huben. All rights reserved.
//

#import "ShotDetailsController.h"
#import "UIImageView+WebCache.h"

@interface ShotDetailsController ()

@end

@implementation ShotDetailsController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self.navigationItem setTitle:@"Details"];
    
    UIScrollView *scrollView = [[UIScrollView alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    scrollView.contentSize = CGSizeMake(0, [[UIScreen mainScreen] bounds].size.height);
    UIBarButtonItem *rightButton = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemAdd  target:self action:@selector(selectRightAction:)];
    //self.navigationItem.rightBarButtonItem = rightButton;

    self.view.backgroundColor = [UIColor whiteColor];
    CGFloat padding = 10;
    CGRect screenRect =[[UIScreen mainScreen] bounds];
    CGSize screenSize = screenRect.size;
    CGFloat screenWidth = screenSize.width;
    
    UIImageView *userIcon = [[UIImageView alloc] initWithFrame: CGRectMake(10, 10, 50, 50)];
    CGFloat nameX = CGRectGetMaxX(userIcon.frame) + padding;
    UILabel *name = [[UILabel alloc] initWithFrame:CGRectMake(nameX, 5, 200, 30)];
    CGFloat tiemY = CGRectGetMaxY(name.frame);
    UILabel *time = [[UILabel alloc] initWithFrame:CGRectMake(nameX, tiemY, 250, 30)];
    
    
    [userIcon sd_setImageWithURL:[[self.shotDetilsDic objectForKey:@"user"] objectForKey:@"avatar_url"]];
    name.text = [[self.shotDetilsDic objectForKey:@"user"] objectForKey:@"username"];
    time.text = [self.shotDetilsDic objectForKey:@"updated_at"];
    
    CGFloat hidpiViewY =CGRectGetMaxY(userIcon.frame)+padding;
    UIImageView *hidpiView = [[UIImageView alloc] initWithFrame:CGRectMake(10, hidpiViewY, screenWidth - 20, 240)];
    hidpiView.contentMode = UIViewContentModeScaleAspectFit;
    [hidpiView sd_setImageWithURL:[[self.shotDetilsDic objectForKey:@"images"] objectForKey:@"hidpi"]];
    
    CGFloat contentY = CGRectGetMaxY(hidpiView.frame)+padding;
    UILabel *content = [[UILabel alloc] initWithFrame:CGRectMake(10, contentY, screenWidth - 20, 200)];
    content.numberOfLines = 0;
    NSString *des =[self.shotDetilsDic objectForKey:@"description"];
    content.attributedText = [[NSAttributedString alloc] initWithString:des];
    
    
    [scrollView addSubview:userIcon];
    [scrollView addSubview:name];
    [scrollView addSubview:time];
    [scrollView addSubview:hidpiView];
    [scrollView addSubview:content];
    [self.view addSubview:scrollView];

}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void) selectRightAction:(id)rightButton
{

}


@end
