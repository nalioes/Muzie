package com.syncsource.org.muzie.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by nls on 9/27/16.
 */

public class Title extends TextView {
    private static Typeface mmTypeFace;

    public Title(Context context) {
        super(context);
        mmTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/zawgyi.ttf");
        setMMFont(this);
    }

    public Title(Context context, AttributeSet attrs) {
        super(context, attrs);
        mmTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/zawgyi.ttf");
        setMMFont(this);
    }

    public Title(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mmTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/zawgyi.ttf");
        setMMFont(this);
    }

    public Title(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mmTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/zawgyi.ttf");
        setMMFont(this);
    }

    public static void setMMFont(TextView view) {
        view.setTypeface(mmTypeFace);
    }

}
