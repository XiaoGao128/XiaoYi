package com.example.xiaoyi_test_2.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.xiaoyi_test_2.R;

public class NiceImageView extends AppCompatImageView {
    private Bitmap mBitmap;
    private Path mPath;

    public NiceImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.xingy);
        mPath = new Path();
        mPath.addCircle(mBitmap.getWidth() / 2, mBitmap.getHeight() / 2, mBitmap.getWidth() / 2, Path.Direction.CCW);
        canvas.clipPath(mPath);
        canvas.drawBitmap(mBitmap, 0, 0, paint);
    }
}
