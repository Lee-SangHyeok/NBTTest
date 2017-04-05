package com.nbt.oopssang.nbttest.view;

import android.content.Context;
import android.util.AttributeSet;

public class TimesTitleView extends android.support.v7.widget.AppCompatTextView {

    private String mUrl;

    public TimesTitleView(Context context) {
        super(context);
    }

    public TimesTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimesTitleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setUrl(String url){
        mUrl = url;
    }

    public String getUrl(){
        return mUrl;
    }
}
