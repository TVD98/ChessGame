package com.example.chessgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.chessgame.models.ChessMan;
import com.example.chessgame.models.King;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard extends View {

    private Paint[] mBoxPaint = new Paint[2];
    private Paint highlightPain;
    private int mWidthBox;
    private List<Point> selectedBoxList = new ArrayList<>();
    private List<ChessMan> chessManList = new ArrayList<>();
    private Drawable[] drawableChessMans = new Drawable[12];
    private OnBoardClickListener listener;
    private Rect[] rectList = new Rect[32];
    private int selectedChessman = -1;
    public Point selectedBox = new Point();
    int _xDelta;
    int _yDelta;
    boolean isMoving = false;

    public ChessBoard(Context context) {
        this(context, null);
    }

    public ChessBoard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initChessMan(context);
    }

    private void initChessMan(Context context) {
        // get all drawable chessman
        drawableChessMans[0] = context.getResources().getDrawable(R.drawable.bk);
        drawableChessMans[1] = context.getResources().getDrawable(R.drawable.bq);
        drawableChessMans[2] = context.getResources().getDrawable(R.drawable.br);
        drawableChessMans[3] = context.getResources().getDrawable(R.drawable.bn);
        drawableChessMans[4] = context.getResources().getDrawable(R.drawable.bb);
        drawableChessMans[5] = context.getResources().getDrawable(R.drawable.bp);
        drawableChessMans[6] = context.getResources().getDrawable(R.drawable.wk);
        drawableChessMans[7] = context.getResources().getDrawable(R.drawable.wq);
        drawableChessMans[8] = context.getResources().getDrawable(R.drawable.wr);
        drawableChessMans[9] = context.getResources().getDrawable(R.drawable.wn);
        drawableChessMans[10] = context.getResources().getDrawable(R.drawable.wb);
        drawableChessMans[11] = context.getResources().getDrawable(R.drawable.wp);

        // add black chessman
        chessManList.add(new King(new Point(0, 0), 2));
        chessManList.add(new King(new Point(1, 0), 3));
        chessManList.add(new King(new Point(2, 0), 4));
        chessManList.add(new King(new Point(3, 0), 1));
        chessManList.add(new King(new Point(4, 0), 0));
        chessManList.add(new King(new Point(5, 0), 4));
        chessManList.add(new King(new Point(6, 0), 3));
        chessManList.add(new King(new Point(7, 0), 2));
        chessManList.add(new King(new Point(0, 1), 5));
        chessManList.add(new King(new Point(1, 1), 5));
        chessManList.add(new King(new Point(2, 1), 5));
        chessManList.add(new King(new Point(3, 1), 5));
        chessManList.add(new King(new Point(4, 1), 5));
        chessManList.add(new King(new Point(5, 1), 5));
        chessManList.add(new King(new Point(6, 1), 5));
        chessManList.add(new King(new Point(7, 1), 5));

        // add white chessman
        chessManList.add(new King(new Point(0, 7), 8));
        chessManList.add(new King(new Point(1, 7), 9));
        chessManList.add(new King(new Point(2, 7), 10));
        chessManList.add(new King(new Point(3, 7), 6));
        chessManList.add(new King(new Point(4, 7), 7));
        chessManList.add(new King(new Point(5, 7), 10));
        chessManList.add(new King(new Point(6, 7), 9));
        chessManList.add(new King(new Point(7, 7), 8));
        chessManList.add(new King(new Point(0, 6), 11));
        chessManList.add(new King(new Point(1, 6), 11));
        chessManList.add(new King(new Point(2, 6), 11));
        chessManList.add(new King(new Point(3, 6), 11));
        chessManList.add(new King(new Point(4, 6), 11));
        chessManList.add(new King(new Point(5, 6), 11));
        chessManList.add(new King(new Point(6, 6), 11));
        chessManList.add(new King(new Point(7, 6), 11));


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
        highlightPain.setColor(Color.BLUE);
        highlightPain.setStrokeCap(Paint.Cap.SQUARE);
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
        mWidthBox = w / Constraints.BOX_COUNT;

        //change rect of chessman
        for (int i = 0; i < chessManList.size(); i++) {
            Point point = chessManList.get(i).getPosition();
            rectList[i] = new Rect(point.getX() * mWidthBox, point.getY() * mWidthBox,
                    (point.getX() + 1) * mWidthBox, (point.getY() + 1) * mWidthBox);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Rect rect;
        // draw chessboard
        for (int i = 0; i < Constraints.BOX_COUNT; i++) {
            for (int j = 0; j < Constraints.BOX_COUNT; j++) {
                rect = new Rect(j * mWidthBox, i * mWidthBox, (j + 1) * mWidthBox, (i + 1) * mWidthBox);
                canvas.drawRect(rect, mBoxPaint[((i + j) % 2)]);
            }
        }
        // highlight for selected box
        for (Point point : selectedBoxList
        ) {
            int x = point.getX();
            int y = point.getY();
            canvas.drawRect(x * mWidthBox, y * mWidthBox, (x + 1) * mWidthBox, (y + 1) * mWidthBox, highlightPain);
        }

        // draw all chessman exception moving chessman
        for (int i = 0; i < chessManList.size(); i++) {
            if (selectedChessman == -1 || i != selectedChessman) {
                ChessMan chessMan = chessManList.get(i);
                Point point = chessMan.getPosition();
                rect = rectList[i];
                Drawable drawable = drawableChessMans[chessMan.getId()];
                drawable.setBounds(rect);
                drawable.draw(canvas);
            }
        }

        // draw moving chessman
        if(selectedChessman != -1){
            ChessMan chessMan = chessManList.get(selectedChessman);
            Point point = chessMan.getPosition();
            rect = rectList[selectedChessman];
            Drawable drawable = drawableChessMans[chessMan.getId()];
            drawable.setBounds(rect);
            drawable.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // get masked (not specific to a pointer) action
        int maskedAction = event.getActionMasked();
        final int X = (int) event.getX();
        final int Y = (int) event.getY();
        if (!isMoving) {
            changeSelectedChessman(X, Y);
            isMoving = !isMoving;
        }
        switch (maskedAction) {
            case MotionEvent.ACTION_DOWN:
                handleBoardClicked(X, Y);
                _xDelta = X - rectList[selectedChessman].left;
                _yDelta = Y - rectList[selectedChessman].top;
                break;
            case MotionEvent.ACTION_POINTER_DOWN: {
                // TODO use data
                break;
            }
            case MotionEvent.ACTION_MOVE:
                rectList[selectedChessman].left = X - _xDelta;
                rectList[selectedChessman].right = X - _xDelta + mWidthBox;
                rectList[selectedChessman].top = Y - _yDelta;
                rectList[selectedChessman].bottom = Y - _yDelta + mWidthBox;
                break;
            case MotionEvent.ACTION_UP:
                int posX = (int) ((rectList[selectedChessman].right - mWidthBox / 2) / mWidthBox);
                int posY = (int) ((rectList[selectedChessman].bottom - mWidthBox / 2) / mWidthBox);
                rectList[selectedChessman].left = posX * mWidthBox;
                rectList[selectedChessman].right = (posX + 1) * mWidthBox;
                rectList[selectedChessman].top = posY * mWidthBox;
                rectList[selectedChessman].bottom = (posY + 1) * mWidthBox;
                chessManList.get(selectedChessman).getPosition().setX(posX);
                chessManList.get(selectedChessman).getPosition().setY(posY);
                isMoving = !isMoving;
                selectedBoxList.clear();
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
        }
        invalidate();
        return true;
    }

    private void changeSelectedChessman(int x, int y) {
        int posX = (int) (x / mWidthBox);
        int posY = (int) (y / mWidthBox);
        for (int i = 0; i < chessManList.size(); i++) {
            if (chessManList.get(i).getPosition().getX() == posX
                    && chessManList.get(i).getPosition().getY() == posY) {
                selectedChessman = i;
            }
        }
    }

    private void handleBoardClicked(float x, float y) {
        int posX = (int) (x / mWidthBox);
        int posY = (int) (y / mWidthBox);
        onBoxClicked(posX, posY);
    }

    private void onBoxClicked(int x, int y) {
        Point point = new Point(x, y);
        boolean repeated = false;
        // check press box again
        if (selectedBox.equals(point)) {
            repeated = true;
            // set default value for selected box
            selectedBox.isDefaultValue(true);
            // clear all highlight box
            selectedBoxList.clear();
            invalidate();
        } else {
            // set selected box
            selectedBox = point;
        }
        sendPoint(point, repeated);
    }

    public static boolean isOn(int x, int y) {
        return !(x < 0 || x >= Constraints.BOX_COUNT || y < 0 || y >= Constraints.BOX_COUNT);
    }

    public void setSelectedBoxList(List<Point> points) {
        selectedBoxList = points;
        invalidate();
    }

    public void setChessManList(List<ChessMan> chessMans) {
        chessManList = chessMans;
        invalidate();
    }

    private void sendPoint(Point point, boolean repeated) {
        if (listener != null) {
            listener.onBoardClick(point, repeated);
        }
    }

    public void addBoardClickListener(OnBoardClickListener listener) {
        this.listener = listener;
    }

    public interface OnBoardClickListener {
        void onBoardClick(Point point, boolean repeated);
    }
}
