package com.quantum.apps.simplynotes.MarkdownView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.widget.Toast;

import org.markdownj.MarkdownProcessor;

/**
 * Created by mgclarke on 6/12/17.
 */

public class MarkdownView extends WebView {
    private static final String TAG = "MarkdownView";
    private GestureDetector mGestureDetector;

    public MarkdownView(Context context) {
        super(context);
        init();
    }

    public MarkdownView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public void loadMarkdown(String txt, String cssFileUrl) {
        loadMarkdownToView(txt, cssFileUrl);
    }

    public void loadMarkdown(String txt) {
        loadMarkdown(txt, null);
    }

    private void loadMarkdownToView(String txt, String cssFileUrl) {
        MarkdownProcessor markdownProcessor = new MarkdownProcessor();
        String html = markdownProcessor.markdown(txt);
        if (cssFileUrl != null) {
            html = 	"<link rel='stylesheet' type='text/css' href='"+ cssFileUrl +"' />" + html;
        }
        loadDataWithBaseURL("fake://", html, "text/html", "UTF-8", null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return mGestureDetector.onTouchEvent(e);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent motionEvent) {
            Toast.makeText(getContext(), "Double Tap", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    private void init() {
        mGestureDetector = new GestureDetector(MarkdownView.this.getContext(), new GestureListener());
    }

}
