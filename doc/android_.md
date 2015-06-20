##基本介绍
简单功能的Dribbble客户端，网络接口参考见
[Dribbble Api](http://developer.dribbble.com/v1/)，
首页加载列表数据，点击查看详细及相关评论，单击设计图片查看高清图片，可以保存到本地文件夹，和分享到相关社交工具。

* 第三组件下拉刷新，上拉加载[XListView](https://github.com/Maxwin-z/XListView-Android)，相关场景实现IXListViewListener接口，重载相关方法：

		@Override
		public void onRefresh() {
			LogUtils.d("下拉刷新。。。");
			list.clear();
			ApiConnecter.shareInstance().getShots(0, 20, callback);
		}
	
		@Override
		public void onLoadMore() {
			LogUtils.d("加载更多。。。");
			// ToastUtils.show("暂不支持上拉加载更多数据");
			currentPage++;
			ApiConnecter.shareInstance().getShots(currentPage++, 20, callback);
		}

* [支持手势photoview项目地址](https://github.com/chrisbanes/PhotoView)， [靠谱的源码分析](http://codekk.com/open-source-project-analysis/detail/Android/dkmeteor/PhotoView%20%E6%BA%90%E7%A0%81%E8%A7%A3%E6%9E%90)

* [快速开发框架xutils](https://github.com/wyouflf/xUtils)， [靠谱的源码分析](http://codekk.com/open-source-project-analysis/detail/Android/Caij/xUtils%20%E6%BA%90%E7%A0%81%E8%A7%A3%E6%9E%90)

	

		//下载图片到本地文件夹	
		new HttpUtils().download(hidpiImgUrl,Constants.SAVE_FILE_PATH +"/"+ System.currentTimeMillis()+".png", new RequestCallBack<File>() {

			@Override
			public void onSuccess(ResponseInfo<File> response) {
				LogUtils.d("下载完成");
				ToastUtils.show("文件保存成功,详情见Dribbble文件夹");
			}

			@Override
			public void onFailure(HttpException e, String arg1) {
				e.printStackTrace();
			}
		});

* 不集成SDK分享功能

		public class AndroidShare {
    	public static final int SHARE_BY_SMS = 0xf0; // 短信
    	public static final int SHARE_BY_WEIXIN = 0xf1; // 微信
   		public static final int SHARE_BY_WEIXIN_MOMENTS = 0xf2; // 朋友圈
    	public static final int SHARE_BY_QQ = 0xf3;// QQ
    	public static final int SHARE_BY_QQ_QZONE = 0xf4;// QQ空间
    	public static final int SHARE_BY_SINA_WEIBO = 0xf5;// 新浪微博
    	public static final int SHARE_BY_TENCENT_WEIBO = 0xf6; // 腾讯微博


    	private static boolean isAvilible(Context context, String packageName) {
        	PackageManager pm = context.getPackageManager();
        	List<PackageInfo> pis = pm.getInstalledPackages(0);
        	for (PackageInfo i : pis) {
            	if (i.packageName.equalsIgnoreCase(packageName)) {
               		return true;
            	}
        	}
        	return false;
    	}

    	/**
     	* 调用系统分享功能
     	* @param context
     	* @param channel
     	* @param title
     	* @param msgText
     	* @param imgPath
     	* @param resultCode
     	* @return false未安装相关的应用,true正确启动相关应用
     	*/
    	public static boolean share(Context context, int channel, String title,
            String msgText, String imgPath, int resultCode) {
	        try {
	            if (channel == SHARE_BY_SMS) { // 短信分享
	                Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"));
	                i.putExtra("sms_body", msgText);
	                if (context instanceof Activity) {
	                    ((Activity) context).startActivityForResult(i, resultCode);
	                }
	                return true;
	            } else {
	                String packageName = getPackageNameByChannel(channel);
	                if (isAvilible(context, packageName)) {
	                    Intent i = new Intent(Intent.ACTION_SEND);
	                    if (TextUtils.isEmpty(imgPath)) {
	                        i.setType("text/plain");
	                    } else {
	                        File f = new File(imgPath);
	                        if (f != null && f.exists() && f.isFile()) {
	                            i.setType("image/png");
	                            Uri u = Uri.fromFile(f);
	                            i.putExtra(Intent.EXTRA_STREAM, u);
	                        }
	                    }
	                    i.putExtra(Intent.EXTRA_SUBJECT, title);
	                    i.putExtra(Intent.EXTRA_TEXT, msgText);
	                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                    i.setComponent(getComponetNameByChannel(channel));
	                    if (context instanceof Activity) {
	                        ((Activity) context).startActivityForResult(i, resultCode);
	                    }
	                    return true;
	                } else {
	                    return false;
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
    	}

	    	private static String getPackageNameByChannel(int channel) {
	        switch (channel) {
	            case SHARE_BY_WEIXIN:
	                return "com.tencent.mm";
	            case SHARE_BY_WEIXIN_MOMENTS:
	                return "com.tencent.mm";
	            case SHARE_BY_QQ:
	                return "com.tencent.mobileqq";
	            case SHARE_BY_QQ_QZONE:
	                return "com.qzone";
	            case SHARE_BY_SINA_WEIBO:
	                return "com.sina.weibo";
	            case SHARE_BY_TENCENT_WEIBO:
	                return "com.tencent.WBlog";
	            default:
	                return null;
        	}
    	}

    	private static ComponentName getComponetNameByChannel(int channel) {
        String packageName = getPackageNameByChannel(channel);
        String activityName = null;
        switch (channel) {
            case SHARE_BY_WEIXIN:
                activityName = "com.tencent.mm.ui.tools.ShareImgUI";
                break;
            case SHARE_BY_WEIXIN_MOMENTS:
                activityName = "com.tencent.mm.ui.tools.ShareToTimeLineUI";
                break;
            case SHARE_BY_QQ:
                activityName = "com.tencent.mobileqq.activity.JumpActivity";
                break;
            case SHARE_BY_QQ_QZONE:
                activityName = "com.qzone.ui.operation.QZonePublishMoodActivity";
                break;
            case SHARE_BY_SINA_WEIBO:
                activityName = "com.sina.weibo.EditActivity";
                break;
            case SHARE_BY_TENCENT_WEIBO:
                activityName = "com.tencent.WBlog.intentproxy.TencentWeiboIntent";
                break;
            default:
                break;
        }
        if (!TextUtils.isEmpty(packageName) && !TextUtils.isEmpty(activityName)) {
            return new ComponentName(packageName, activityName);
        } else {
            return null;
        }
    	}
		}

* [使用Umeng统计](http://www.umeng.com/)
		