package com.example.chessgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class ChessBoard extends View {

    private Paint[] mBoxPaint = new Paint[2];
    private Rect rect = new Rect();
    private float mWidthBox;
    private final int BOX_COUNT = 8;
    private OnBoardClickListener listener;

    public ChessBoard(Context context) {
        this(context, null);
    }

    public ChessBoard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChessBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaints();
    }

    private void initPaints() {
        Paint mWhiteBoxPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWhiteBoxPaint.setStyle(Paint.Style.FILL);
        mWhiteBoxPaint.setColor(Color.WHITE);

        mBoxPaint[0] = mWhiteBoxPaint;

        Paint mGrayBoxPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGrayBoxPaint.setStyle(Paint.Style.FILL);
        mGrayBoxPaint.setColor(Color.GRAY);

        mBoxPaint[1] = mGrayBoxPaint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);

        int size = Math.min(w, h);
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidthBox = w / BOX_COUNT;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < BOX_COUNT; i++) {
            for (int j = 0; j < BOX_COUNT; j++) {
                canvas.drawRect(j * mWidthBox, i * mWidthBox, (j + 1) * mWidthBox, (i + 1) * mWidthBox, mBoxPaint[((i + j) % 2)]);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // get masked (not specific to a pointer) action
        int maskedAction = event.getActionMasked();

        switch (maskedAction) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                // TODO use data
                break;
            }
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                sendPoint(event.getX(), event.getY());
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
        }
        invalidate();
        return true;
    }

    private void sendPoint(float x, float y){
        int posX = (int) (x / mWidthBox);
        int posY = (int) (y/ mWidthBox);
        if (listener != null){
            listener.onBoardClick(new Point(posX, posY));
        }
    }

    public void addBoardClickListener(OnBoardClickListener listener){
        this.listener = listener;
    }

    public interface OnBoardClickListener{
        void onBoardClick(Point point);
    }
}
