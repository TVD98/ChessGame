package com.example.chessgame.models;

import com.example.chessgame.Point;

import java.util.List;

public abstract class ChessMan {
    protected String name;
    protected Point position;
    protected int id;

    public ChessMan(String name, Point point, int id){
        this.name = name;
        this.position = point;
        this.id = id;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public abstract List<Point> allAllowedPositionToMove();
}