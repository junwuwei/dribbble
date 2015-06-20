//
//  HttpUtils.m
//  Dribbbles
//
//  Created by huben on 15/6/6.
//  Copyright (c) 2015年 huben. All rights reserved.
//

#import "HttpUtils.h"


@implementation HttpUtils

+ (void) get:(NSString *)urlString
     parameters:(id)parameters
     success:(void (^)(AFHTTPRequestOperation *operation, id responseObject))success
     failure:(void (^)(AFHTTPRequestOperation *operation, NSError *error))failure
{
//    NSLog(@"%@ +[接口调用成功]: %@", urlString, responseObject);
//     NSLog(@"%@ +[接口调用失败]: %@", urlString, error);
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    [manager GET:urlString parameters:parameters success:success failure:failure];

}

@end
