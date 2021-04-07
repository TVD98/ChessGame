package com.example.chessgame.models;

import com.example.chessgame.Constraints;
import com.example.chessgame.Point;

import java.util.ArrayList;
import java.util.List;

public class Castle extends ChessMan{

    public Castle(Point point, int id){
        super("Rock", point, id);
    }
    @Override
    public List<Point> allAllowedPositionToMove() {
        List<Point> list = new ArrayList<>();
        for (int i = 0; i < Constraints.BOX_COUNT; i++) {
            list.add(new Point(i, position.getY()));
            if (i != position.getY())
                list.add(new Point(position.getX(), i));
        }

        return list;
    }
}
