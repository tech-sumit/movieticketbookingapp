package com.webtechdevelopers.sumit.movieticketbookingapp.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.webkit.WebView;

import com.webtechdevelopers.sumit.movieticketbookingapp.R;

public class DialogAboutUs extends Dialog {
    @NonNull
    private final Context context;
    public DialogAboutUs(@NonNull Context context) {
        super(context,R.style.AppTheme_NoActionBar_Dark);
        this.context=context;
        View view=View.inflate(context,R.layout.layout_about_us,null);
        setContentView(view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView=findViewById(R.id.web_about_us);
        webView.loadUrl(context.getString(R.string.about_us_url));
        findViewById(R.id.backFab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

}
