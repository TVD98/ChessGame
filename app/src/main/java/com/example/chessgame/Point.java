package com.example.chessgame;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        this.x = -1;
        this.y = -1;
    }

    public boolean isHidden() {
        return this.x == -1 || this.y == -1;
    }

    public void isDefaultValue(boolean result) {
        if (result) {
            this.x = -1;
            this.y = -1;
        }
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Point otherPoint = (Point) obj;
        return this.x == otherPoint.x && this.y == otherPoint.y;
    }

    public Point leftCrossPoint(int increase) {
        int x = this.x + increase;
        int y = this.y + increase;
        if (ChessBoard.isOn(x, y))
            return new Point(x, y);
        return null;
    }

    public Point verticalPoint(int increase) {
        int y = this.y + increase;
        if (ChessBoard.isOn(this.x, y))
            return new Point(this.x, y);
        return null;
    }

    public Point horizontalPoint(int increase) {
        int x = this.x + increase;
        if (ChessBoard.isOn(x, this.y))
            return new Point(x, this.y);
        return null;
    }

    public Point rightCrossPoint(int increase) {
        int x = this.x + increase;
        int y = this.y - increase;
        if (ChessBoard.isOn(x, y))
            return new Point(x, y);
        return null;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
