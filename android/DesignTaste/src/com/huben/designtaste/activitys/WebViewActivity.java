package com.huben.designtaste.activitys;

import com.huben.designtaste.R;
import com.huben.designtaste.widget.JSBridgeWebView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

@ContentView(R.layout.activity_webview)
public class WebViewActivity extends Activity {

	public static final String HTML_URL = "html_url";
	
	@ViewInject(R.id.webview)
	private JSBridgeWebView webView;
	
	@ViewInject(R.id.webview_progress)
	private ProgressBar progressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		String htmlUrl = getIntent().getStringExtra(HTML_URL);
		webView.loadUrl(htmlUrl);
		webView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) {
					progressBar.setVisibility(View.GONE);
				} else {
					if (progressBar.getVisibility() == View.GONE) {
						progressBar.setVisibility(View.VISIBLE);
					}
					progressBar.setProgress(newProgress);
				}
				super.onProgressChanged(view, newProgress);
			}
			
		});
	}
}
