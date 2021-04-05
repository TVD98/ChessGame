package com.example.chessgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard extends View {

    private Paint[] mBoxPaint = new Paint[2];
    private Paint highlightPain;
    private Rect rect = new Rect();
    private float mWidthBox;
    private final int BOX_COUNT = 8;
    private Point selectedBox = new Point();
    private List<Point> selectedBoxList = new ArrayList<>();
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

        highlightPain = new Paint(Paint.ANTI_ALIAS_FLAG);
        highlightPain.setStyle(Paint.Style.STROKE);
        highlightPain.setColor(Color.YELLOW);
        highlightPain.setStrokeCap(Paint.Cap.ROUND);
        highlightPain.setStrokeWidth(2 * getResources().getDisplayMetrics().density);
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
        // draw chessboard
        for (int i = 0; i < BOX_COUNT; i++) {
            for (int j = 0; j < BOX_COUNT; j++) {
                canvas.drawRect(j * mWidthBox, i * mWidthBox, (j + 1) * mWidthBox, (i + 1) * mWidthBox, mBoxPaint[((i + j) % 2)]);
            }
        }
        // highlight for selected box
        for (Point point : selectedBoxList
        ) {
            int x = point.getX();
            int y = point.getY();
            canvas.drawRect(x * mWidthBox, y * mWidthBox, (x + 1) * mWidthBox, (y + 1) * mWidthBox, highlightPain);
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
                handleBoardClicked(event.getX(), event.getY());
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
        }
        invalidate();
        return true;
    }

    private void handleBoardClicked(float x, float y) {
        int posX = (int) (x / mWidthBox);
        int posY = (int) (y / mWidthBox);
        onBoxClicked(posX, posY);
    }

    private void onBoxClicked(int x, int y) {
        sendPoint(x, y);
        changeSelectedBoxList(x, y);
    }

    private void changeSelectedBoxList(int x, int y) {
        // clear all selected box list
        selectedBoxList.clear();
        // check press selected box again
        if (selectedBox.compareTo(x, y)) {
            return;
        }
        // set selected box
        selectedBox.setX(x);
        selectedBox.setY(y);

        for (int i = 0; i < BOX_COUNT; i++) {
            // did not add first item again
            if (i != x)
                selectedBoxList.add(new Point(i, y));
            if (i != y)
                selectedBoxList.add(new Point(x, i));
        }

    }

    private void sendPoint(int x, int y) {
        if (listener != null) {
            listener.onBoardClick(new Point(x, y));
        }
    }

    public void addBoardClickListener(OnBoardClickListener listener) {
        this.listener = listener;
    }

    public interface OnBoardClickListener {
        void onBoardClick(Point point);
    }
}
