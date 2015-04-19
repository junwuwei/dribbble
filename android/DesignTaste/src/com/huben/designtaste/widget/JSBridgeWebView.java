/*
 * @(#)JSBridgeWebView.java		       Project:Community
 * Date:2013-11-14
 *
 * Copyright (c) 2013 DONGXINWANGTAI 
 * All rights reserved.
 *
 */
package com.huben.designtaste.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

@SuppressWarnings("deprecation")
@SuppressLint("SetJavaScriptEnabled")
public class JSBridgeWebView extends WebView {
	private ProgressBar progressBar;
	private Context mContext;


	public JSBridgeWebView(Context context) {
		this(context, null);
	}

	public JSBridgeWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	private void init() {
		if (!this.isInEditMode()) {
			initWebView();
			initProgressBar();
		}
	}

	private void initProgressBar() {
		progressBar = new ProgressBar(getContext(), null,
				android.R.attr.progressBarStyleHorizontal);
		progressBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				5, 0, 0));
		addView(progressBar);
	}

	private void initWebView() {
		WebSettings ws = this.getSettings();
		ws.setJavaScriptEnabled(true);
		ws.setAllowFileAccess(true);
		ws.setBuiltInZoomControls(false);
		ws.setSupportZoom(true);
		ws.setDomStorageEnabled(true);
		ws.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);

		this.setWebViewClient(new WebViewClient() {
			/*
			 * 覆盖loadUrl方法，默认以浏览器打开URL
			 * 
			 * @see
			 * android.webkit.WebViewClient#shouldOverrideUrlLoading(android
			 * .webkit.WebView, java.lang.String)
			 */
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.startsWith("http://") || url.startsWith("https://")) {
					((JSBridgeWebView) view).superLoadUrl(url);
					return true;
				}
				return super.shouldOverrideUrlLoading(view, url);
			}

			/*
			 * 加载失败回调
			 * 
			 * @see
			 * android.webkit.WebViewClient#onReceivedError(android.webkit.WebView
			 * , int, java.lang.String, java.lang.String)
			 */
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				view.loadUrl("file:///android_asset/error.html");
			}

			/*
                 *
                 */
			@Override
			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error) {
				handler.proceed();
			}

		});
		this.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) {
					progressBar.setVisibility(GONE);
				} else {
					if (progressBar.getVisibility() == GONE) {
						progressBar.setVisibility(VISIBLE);
					}
					progressBar.setProgress(newProgress);
				}
				super.onProgressChanged(view, newProgress);
			}

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					final JsResult result) {

				return true;
			}

			@Override
			public boolean onJsConfirm(WebView view, String url,
					final String message, final JsResult result) {

				return true;
			}

			@Override
			public boolean onJsPrompt(WebView view, String url, String message,
					final String defaultValue, final JsPromptResult result) {
				if (!TextUtils.isEmpty(message)) {

				}
				return true;
			}
		});
		this.addJavascriptInterface(new JSAndroidBridge(this), "android");
	}

	public void superLoadUrl(String url) {
		super.loadUrl(url);
	}

	/**
	 * 执行JS方法，可通过参数向JS当前页面传值
	 * 
	 * @param methodName
	 *            JS方法名称
	 * @param param
	 *            传递的参数，以字符串传递，多种值建议是JSON字串传递
	 */
	public void executeJSMethod(String methodName, String param) {
		this.loadUrl("javascript:" + methodName + "('" + param + "')");
	}

	public final class JSAndroidBridge {
		private JSBridgeWebView webView;

		public JSAndroidBridge(JSBridgeWebView webView) {
			super();
			this.webView = webView;
		}

		@JavascriptInterface
		public void goWebBrowser(String url) {
			if (!TextUtils.isEmpty(url)) {
				mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri
						.parse(url)));
			}
		}

	}

}
