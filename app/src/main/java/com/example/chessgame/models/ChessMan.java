package com.example.chessgame.models;

import com.example.chessgame.Point;

import java.util.List;

public abstract class ChessMan {
    protected String name;
    protected Point position;

    public ChessMan(String name, Point point){
        this.name = name;
        this.position = point;
    }

    public abstract List<Point> allAllowedPositionToMove();
}