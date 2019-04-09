package com.funtsui.updatelib.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.funtsui.updatelib.R;


public class WaveImageView extends View {
    //底图
    private Drawable mWaveDrawable;
    //灰色矩阵
    private static ColorFilter sGrayFilter = new ColorMatrixColorFilter(new float[]{
            0.264F, 0.472F, 0.088F, 0, 0,
            0.264F, 0.472F, 0.088F, 0, 0,
            0.264F, 0.472F, 0.088F, 0, 0,
            0, 0, 0, 1, 0
    });
    //移动
    private int mOffsetX;
    //路径
    private Path mPath;
    //水波纹操作的图片ID
    private int mWaveImgRes;
    //进度条
    private int mProgress = 50;
    //线性加速
    LinearInterpolator value = new LinearInterpolator();

    public WaveImageView(Context context) {
        this(context, null);
    }

    public WaveImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.WaveImageView);
            int resourceId = attributes.getResourceId(R.styleable.WaveImageView_src, -1);
            if (resourceId != -1) {
                mWaveDrawable = getDrawable(resourceId);
            }
            attributes.recycle();
        }
        mPath = new Path();
        //关闭硬件加速
        if (!isHardwareAccelerated()) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    /**
     * ImgRes 转 Drawable
     */
    private Drawable getDrawable(int imgRes) {
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = super.getContext().getDrawable(imgRes);
        } else {
            drawable = getResources().getDrawable(imgRes);
        }
        return drawable;
    }

    /**
     * 绘制
     */
    @Override
    protected void onDraw(Canvas canvas) {
        if (mWaveDrawable == null) {
            return;
        }
        int defaultSize = Math.min(getWidth(), getHeight());
        mWaveDrawable.setBounds(0, 0, defaultSize, defaultSize);
        mWaveDrawable.setColorFilter(sGrayFilter);
        mWaveDrawable.draw(canvas);
        int radius = defaultSize / 2;
        drawBezier(canvas, radius);
        if (mProgress > 0) {
            mWaveDrawable.setColorFilter(null);
            mWaveDrawable.draw(canvas);
        }
    }

    /**
     * 绘制贝塞尔
     */
    private void drawBezier(Canvas canvas, int radius) {
        mPath.reset();
        mPath.lineTo(0, 0);
        if (mProgress > 0 && mProgress < 99) {
            int wave = radius / 2;
            int waveHeight = radius / 4;
            int offsetY = (int) (radius * 2.0 - radius * 2 / 100.0 * mProgress);
            mPath.moveTo(mOffsetX, offsetY);
            mPath.rQuadTo(wave, waveHeight, wave * 2, 0);
            mPath.rQuadTo(wave, -waveHeight, wave * 2, 0);
            mPath.rQuadTo(wave, waveHeight, wave * 2, 0);
            mPath.rQuadTo(wave, -waveHeight, wave * 2, 0);
            mPath.lineTo(radius * 4, radius * 2);
            mPath.lineTo(0, radius * 2);
            mPath.close();
            canvas.clipPath(mPath);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int defaultWidth = mWaveDrawable.getIntrinsicWidth();
        int defaultHeight = mWaveDrawable.getIntrinsicHeight();
        int width = measureDimension(defaultWidth, widthMeasureSpec);
        int height = measureDimension(defaultHeight, heightMeasureSpec);
        int defaultSize = Math.min(width, height);
        setMeasuredDimension(defaultSize, defaultSize);
        //动画 动起来
        ValueAnimator animator = ValueAnimator.ofInt(0, defaultSize);
        animator.setDuration(1000);
        animator.setInterpolator(value);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffsetX = -(int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }

    protected int measureDimension(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize);
        }
        return result;
    }

    /**
     * 资源文件
     */
    public void setWaveImgRes(@DrawableRes int id) {
        this.mWaveImgRes = id;
        mWaveDrawable = getDrawable(mWaveImgRes);
    }

    /**
     * 进度条
     */
    public void setProgress(int progress) {
        if (mProgress < 0 || mProgress > 100) {
            return;
        }
        mProgress = progress;
    }
}
