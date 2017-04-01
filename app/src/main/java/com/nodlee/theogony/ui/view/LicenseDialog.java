package com.nodlee.theogony.ui.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.nodlee.theogony.R;

/**
 * 作者：nodlee
 * 时间：16/8/16
 * 说明：开源许可证对话框
 */
public class LicenseDialog {
    private static final String LICENSE_URL = "https://raw.githubusercontent.com/VernonLee/Theogony/master/LICENSE";
    private AlertDialog mDialog;
    private FrameLayout mContainer;
    private WebView webView;

    public LicenseDialog(Context context) {
        init(context);
    }

    private void init(Context context) {
        webView = new WebView(context);
        webView.loadUrl(LICENSE_URL);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        View rootView = LayoutInflater.from(context).inflate(R.layout.dialog_liscense, null);
        mContainer = (FrameLayout) rootView.findViewById(R.id.webview_container);
        mContainer.addView(webView);

        mDialog = new AlertDialog.Builder(context)
                  .setView(mContainer)
                  .setPositiveButton(R.string.okay_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dismiss();
                        }
                  }).create();
    }

    public void create() {
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mContainer.removeAllViews();
            webView.destroy();
        }
    }
}
