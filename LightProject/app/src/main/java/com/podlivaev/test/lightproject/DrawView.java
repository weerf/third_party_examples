package com.podlivaev.test.lightproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import java.text.AttributedCharacterIterator;

/**
 * Created by kirill on 09.12.15.
 */
public class DrawView extends View {

    public DrawView(Context context){
        super(context);
    }

    public DrawView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }

    @Override
    public void onDraw(Canvas canvas){
        canvas.drawColor(Color.argb(90, 0, 0xff, 0));

    }




}
