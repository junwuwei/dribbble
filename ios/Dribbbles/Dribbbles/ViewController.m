//
//  ViewController.m
//  Dribbbles
//
//  Created by huben on 15/5/31.
//  Copyright (c) 2015年 huben. All rights reserved.
//

#import "ViewController.h"
#import "HttpUtils.h"
#import "UIImageView+WebCache.h"
#import "AFHTTPRequestOperationManager.h"
#import "MBProgressHUD.h"
#import "MJRefresh.h"
#import "ShotDetailsController.h"


@interface ViewController ()

@end

@implementation ViewController
UICollectionView *collection;
NSMutableArray *shots;
//NSMutableDictionary *dic;
NSString *identifier = @"cellectionCell";
NSInteger currentPage = 0;
NSInteger pageSize = 20;
- (void)viewDidLoad {
    [super viewDidLoad];
    collection = [[UICollectionView alloc] initWithFrame:[[UIScreen mainScreen] bounds] collectionViewLayout:[[UICollectionViewFlowLayout alloc]init]];
    collection.delegate = self;
    collection.dataSource = self;
    collection.backgroundColor = [UIColor grayColor];
    [collection registerClass:[UICollectionViewCell class] forCellWithReuseIdentifier:identifier];
    //shots = [[NSMutableArray alloc] init];
    [collection addLegendHeaderWithRefreshingBlock:^{
        //[weakSelf.collection.header endRefreshing];
        NSMutableArray *temp = [[NSMutableArray alloc] init];
        shots = temp;
        currentPage = 0;
        [self getShotsPage:currentPage per_page:pageSize];

    }];
    [collection.header beginRefreshing];
    //[MBProgressHUD showHUDAddedTo:self.view animated:YES];
    
    // 上拉刷新
    [collection addLegendFooterWithRefreshingBlock:^{
        currentPage ++;
        [collection.footer beginRefreshing];
        [self getShotsPage:currentPage per_page:pageSize];
    }];
    // 默认先隐藏footer
    collection.footer.hidden = YES;
    [self.view addSubview:collection];
}

- (void)getShotsPage:(NSInteger) page per_page:(NSInteger) per_page
{
    NSMutableDictionary *params = [[NSMutableDictionary alloc] init];
    [params setValue:@"e505fc9e31dea35dd4e9989e58ced2dab71ab783ae864a6a75f33883d49bc863" forKey:@"access_token"];
    //    [dic setValue:@"playoffs" forKey:@"list"];
    //    [dic setValue:@"week" forKey:@"timeframe"];
    //    [dic setValue:@"views" forKey:@"sort"];
    [params setValue:@"attachments" forKey:@"list"];
    [params setValue:@"month" forKey:@"timeframe"];
    [params setValue:@"views" forKey:@"sort"];
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    NSString *baseUrl = @"https://api.dribbble.com/v1/shots/?page=%d&per_page=%d";
    NSString *url = [[NSString alloc] initWithFormat: baseUrl, page , per_page, nil];
    [manager GET:url
      parameters:(id)params
         success:^(AFHTTPRequestOperation *operation, id responseObject) {
              NSLog(@"%@ +[接口调用成功]: %@", url, responseObject);
             //[MBProgressHUD hideHUDForView:self.view animated:YES];
             [collection.header endRefreshing];
             [collection.footer endRefreshing];
             collection.footer.hidden = NO;
             if (shots.count == 0) {
                 shots = responseObject;
             }else {
                 NSMutableArray *temp = [[NSMutableArray alloc] init];
                 [temp addObjectsFromArray:shots];
                 [temp addObjectsFromArray:responseObject];
                 shots = temp;
             }
             [collection reloadData];
         } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
             //[MBProgressHUD hideHUDForView:self.view animated:YES];
             NSLog(@"%@ +[接口调用失败]: %@", url, error);
             [collection.header endRefreshing];
             [collection.footer endRefreshing];
         }];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];

}

- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section
{
//    return 10;
    return shots.count;
}

- (void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath
{
    ShotDetailsController *shotsDetailVC = [[ShotDetailsController alloc] init];
    shotsDetailVC.shotDetilsDic = [shots objectAtIndex:indexPath.row];
    UIBarButtonItem *backItem = [[UIBarButtonItem alloc] init];
    backItem.title = @"返回";
    self.navigationItem.backBarButtonItem = backItem;
    [self.navigationController pushViewController:shotsDetailVC animated:YES];
}

- (UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath
{
    UICollectionViewCell *cell = (UICollectionViewCell *)[collectionView dequeueReusableCellWithReuseIdentifier:identifier forIndexPath:indexPath];
    cell.backgroundColor = [UIColor whiteColor];
    UIImageView *image = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, cell.frame.size.width, cell.frame.size.height)];
    image.contentMode = UIViewContentModeScaleToFill;
    NSDictionary *dic = [shots objectAtIndex:indexPath.row];
    [image sd_setImageWithURL: [[dic objectForKey:@"images"] objectForKey:@"teaser"]];
    [cell addSubview:image];
    return cell;
}

- (NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView
{
    return 1;
}
//每个cell的大小
- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath *)indexPath
{
    float width = [[UIScreen mainScreen] bounds].size.width / 2 - 15;
    return CGSizeMake(width, width);
}

-(UIEdgeInsets)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout insetForSectionAtIndex:(NSInteger)section
{
    return UIEdgeInsetsMake(10, 10, 10, 10);//分别为上、左、下、右
}

@end
