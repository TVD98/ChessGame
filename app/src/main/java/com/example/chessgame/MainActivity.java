package com.example.chessgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.chessgame.models.Castle;
import com.example.chessgame.models.King;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ChessBoard.OnBoardClickListener {
    LinearLayout contain;
    ChessBoard chessBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contain = findViewById(R.id.contain);
        chessBoard = findViewById(R.id.chess_board);

        if(savedInstanceState != null){
            int x = savedInstanceState.getInt("x");
            int y = savedInstanceState.getInt("y");
            Point point = new Point(x, y);
            King king = new King(point);
            chessBoard.selectedBox = point; 
            chessBoard.setSelectedBoxList(king.allAllowedPositionToMove());
        }

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            contain.setOrientation(LinearLayout.HORIZONTAL);
        } else {
            // In portrait
        }

        chessBoard.addBoardClickListener(this);
    }

    @Override
    public void onBoardClick(Point point, boolean repeated) {
        if (!repeated) {
            King king = new King(point);
            chessBoard.setSelectedBoxList(king.allAllowedPositionToMove());
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("x", chessBoard.selectedBox.getX());
        outState.putInt("y", chessBoard.selectedBox.getY());
    }
}