package com.example.chessgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ChessBoard.OnBoardClickListener {
    LinearLayout contain;
    ChessBoard chessBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contain = findViewById(R.id.contain);
        chessBoard = findViewById(R.id.chess_board);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            contain.setOrientation(LinearLayout.HORIZONTAL);
        } else {
            // In portrait
        }

        chessBoard.addBoardClickListener(this);
    }

    @Override
    public void onBoardClick(Point point) {
        Toast.makeText(this, point.toString(), Toast.LENGTH_SHORT).show();
    }
}