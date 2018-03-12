package com.bfurns.utility;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Mahesh on 14/06/16.
 */
public class MyRecyclerView extends RecyclerView {

    Context context;

    public MyRecyclerView(Context context) {
        super(context);
        this.context = context;
    }

    public MyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {

        velocityX *= 0.7;
        // velocityX *= 0.7; for Horizontal recycler view. comment velocityY line not require for Horizontal Mode.

        return super.fling(velocityX, velocityY);
    }

}