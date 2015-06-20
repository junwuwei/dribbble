//
//  HttpUtils.h
//  Dribbbles
//
//  Created by huben on 15/6/6.
//  Copyright (c) 2015年 huben. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "AFHTTPRequestOperationManager.h"

@interface HttpUtils : NSObject

+ (void) get: (NSString *) urlString params : (id) dic
     success:(void (^)(AFHTTPRequestOperation *operation, id responseObject))success
     failure:(void (^)(AFHTTPRequestOperation *operation, NSError *error))failure;


@end
