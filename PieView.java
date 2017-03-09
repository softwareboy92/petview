package com.lvndk.barnner.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.lvndk.barnner.R;

/**
 * create by mac 2017.03.09
 */
public class PieView extends View {

    //A模块占据的百分比颜色
    private int colorA;
    //B模块占据的百分比颜色
    private int colorB;
    //C模块占据的百分比颜色
    private int colorC;
    //D模块占据的百分比颜色
    private int colorD;

    //扫过的角度
    private float arcA;
    private float arcB;
    private float arcC;
    private float arcD;
    //画笔宽度
    private int strokeWidth;
    //文字颜色
    private int textColor;
    //文字大小
    private int textSize;
    //直径
    private int diameter;
    //画笔
    private Paint paint;
    private TextPaint textPaint;

    //扇形区域
    private RectF rectF;
    //零时变量
    private float tmpArcA;
    private float tmpArcB;
    private float tmpArcC;
    private float tmpArcD;
    //文字区域
    private Rect rect;
    //中间百分数
    private String text;
    //文字区域
    private Rect rectPercent;
    //百分号%
    private String textPercent;
    //百分号大小
    private int textPercentSize;
    //提示文字
    private String textTips;

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PieView, defStyle, 0);
        int count = array.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.PieView_color_a:
                    colorA = array.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.PieView_color_b:
                    colorB = array.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.PieView_color_c:
                    colorC = array.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.PieView_color_d:
                    colorD = array.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.PieView_stroke_width:
                    strokeWidth = array.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.PieView_text_color:
                    textColor = array.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.PieView_text_size:
                    textSize = array.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.PieView_text:
                    text = array.getString(attr);
                    break;
                case R.styleable.PieView_percent_arc_a:
                    //这个属性是只是为了预览视图
                    tmpArcA = percentToDegree(array.getFloat(attr, 0f));
                    break;
                case R.styleable.PieView_text_percent_size:
                    textPercentSize = array.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics()));
                    break;
            }
        }
        array.recycle();

        paint = new Paint();
        paint.setAntiAlias(true);
        //直径取屏幕的一半
        diameter = getResources().getDisplayMetrics().widthPixels / 2;
        //半径
        int radius = diameter / 2;
        rectF = new RectF(-radius, -radius, radius, radius);
        rectPercent = new Rect();
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        rect = new Rect();
        textPercent = "%";
        textTips = "默认值";
    }

    /**
     * 在Measure中计算下所占屏幕的宽度和高度
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthNode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightNode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;
        if (widthNode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = diameter * 2;
        }

        if (heightNode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = diameter * 2 - diameter / 2;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initCanvas(canvas);
        drawArcA(canvas);
        drawArcB(canvas);
        drawArcC(canvas);
        drawArcD(canvas);
        drawText(canvas);
    }

    /**
     * 初始化画板
     * 移动画板的坐标原点
     *
     * @param canvas
     */
    private void initCanvas(Canvas canvas) {
        //将坐标移动到中间位置
        canvas.translate(getWidth() / 2, getHeight() / 2);
        //设置画笔的样式
        paint.setStyle(Paint.Style.FILL);
        //设置画笔的颜色
        paint.setColor(Color.RED);
        //原点
        canvas.drawCircle(0, 0, 3, paint);
    }


    /**
     * 设置A画笔的颜色
     *
     * @param colorA
     */
    public void setColorA(int colorA) {
        this.colorA = colorA;
    }

    /**
     * 设置B画笔的颜色
     *
     * @param colorB
     */
    public void setColorB(int colorB) {
        this.colorB = colorB;
    }

    /**
     * 设置C画笔的颜色
     *
     * @param colorC
     */
    public void setColorC(int colorC) {
        this.colorC = colorC;
    }

    /**
     * 设置D画笔的颜色
     *
     * @param colorD
     */
    public void setColorD(int colorD) {
        this.colorD = colorD;
    }

    /**
     * 绘制A模块
     *
     * @param canvas
     */
    private void drawArcA(Canvas canvas) {
        //设置样式
        paint.setStyle(Paint.Style.STROKE);
        //设置颜色
        paint.setColor(colorA);
        //设置画笔的宽度
        paint.setStrokeWidth(strokeWidth);
        canvas.drawArc(rectF, 0, tmpArcA, false, paint);
    }

    /**
     * 绘制B模块
     *
     * @param canvas
     */
    private void drawArcB(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(colorB);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawArc(rectF, arcA, tmpArcB, false, paint);
    }

    /**
     * 绘制C模块
     *
     * @param canvas
     */
    private void drawArcC(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(colorC);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawArc(rectF, arcA + arcB, tmpArcC, false, paint);
    }

    /**
     * 绘制D模块
     *
     * @param canvas
     */
    private void drawArcD(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(colorD);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawArc(rectF, arcA + arcB + arcC, tmpArcD, false, paint);
    }

    /**
     * 绘制文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        //设置画笔的颜色
        textPaint.setColor(textColor);
        //设置画笔的大小
        textPaint.setTextSize(textSize);
        //设置画笔的弧度
        textPaint.getTextBounds(text, 0, text.length(), rect);
        canvas.drawText(text, 0 - rect.width() / 1.5f, rect.height() / 4, textPaint);
        //绘制百分号
        textPaint.setTextSize(textPercentSize);
        textPaint.getTextBounds(textPercent, 0, textPercent.length(), rectPercent);
        canvas.drawText(textPercent, rect.width() / 4 + rectPercent.width(), rect.height() / 10 + rectPercent.height() / 3, textPaint);
        //绘制优秀度
        textPaint.setColor(Color.parseColor("#999999"));
        textPaint.setTextSize(textPercentSize);
        textPaint.getTextBounds(textTips, 0, textTips.length(), rectPercent);
        canvas.drawText(textTips, -rectPercent.width() / 2, rect.height() + rectPercent.height(), textPaint);
    }


    /**
     * 设置底部文字的大小
     *
     * @param textPercentSize
     */
    public void setTextPercentSize(int textPercentSize) {
        this.textPercentSize = textPercentSize;
    }

    /**
     * 设置中心文字的含义
     *
     * @param textTips
     */
    public void setTextTips(String textTips) {
        this.textTips = textTips;
    }

    /**
     * 百分比转换为度数
     *
     * @param percent
     * @return
     */
    private float percentToDegree(float percent) {
        return 360 * percent / 100;
    }

    /**
     * 设置中心文字的大小
     *
     * @param size
     */
    public void setTextSize(int size) {
        this.textSize = size;

    }

    /**
     * 设置A模块所占的百分比
     *
     * @param percent
     */
    public void setArcA(float percent) {
        this.arcA = percentToDegree(percent);
    }

    /**
     * 设置B模块所占的百分比
     *
     * @param percent
     */
    public void setArcB(float percent) {
        this.arcB = percentToDegree(percent);
    }

    /**
     * 设置C模块所占的百分比
     *
     * @param percent
     */
    public void setArcC(float percent) {
        this.arcC = percentToDegree(percent);
    }

    /**
     * 设置D模块所占的百分比
     *
     * @param percent
     */
    public void setArcD(float percent) {
        this.arcD = percentToDegree(percent);
    }

    /**
     * 设置中心显示的文字
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 启动动画
     */
    public void startAnim() {
        ValueAnimator valueAnimatorA = ValueAnimator.ofFloat(0f, arcA);
        valueAnimatorA.setInterpolator(new LinearInterpolator());
        valueAnimatorA.setDuration(1000);
        valueAnimatorA.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tmpArcA = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimatorA.start();

        ValueAnimator valueAnimatorB = ValueAnimator.ofFloat(0f, arcB);
        valueAnimatorB.setInterpolator(new LinearInterpolator());
        valueAnimatorB.setDuration(1000);
        valueAnimatorB.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tmpArcB = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimatorB.start();

        ValueAnimator valueAnimatorC = ValueAnimator.ofFloat(0f, arcC);
        valueAnimatorC.setInterpolator(new LinearInterpolator());
        valueAnimatorC.setDuration(1000);
        valueAnimatorC.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tmpArcC = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimatorC.start();

        ValueAnimator valueAnimatorD = ValueAnimator.ofFloat(0f, arcD);
        valueAnimatorD.setInterpolator(new LinearInterpolator());
        valueAnimatorD.setDuration(1000);
        valueAnimatorD.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tmpArcD = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimatorD.start();

    }
}