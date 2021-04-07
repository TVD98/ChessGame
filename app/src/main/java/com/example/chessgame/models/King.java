package com.example.chessgame.models;

import com.example.chessgame.Point;

import java.util.ArrayList;
import java.util.List;

public class King extends ChessMan {

    public King(Point point, int id) {
        super("King", point, id);
    }

    @Override
    public List<Point> allAllowedPositionToMove() {
        List<Point> list = new ArrayList<>();
        list.add(this.position);
        if(this.position.leftCrossPoint(-1) != null)
            list.add(this.position.leftCrossPoint(-1));
        if(this.position.leftCrossPoint(1) != null)
            list.add(this.position.leftCrossPoint(1));

        if(this.position.rightCrossPoint(-1) != null)
            list.add(this.position.rightCrossPoint(-1));
        if(this.position.rightCrossPoint(1) != null)
            list.add(this.position.rightCrossPoint(1));

        if(this.position.verticalPoint(-1) != null)
            list.add(this.position.verticalPoint(-1));
        if(this.position.verticalPoint(1) != null)
            list.add(this.position.verticalPoint(1));

        if(this.position.horizontalPoint(-1) != null)
            list.add(this.position.horizontalPoint(-1));
        if(this.position.horizontalPoint(1) != null)
            list.add(this.position.horizontalPoint(1));
        return list;
    }
}
