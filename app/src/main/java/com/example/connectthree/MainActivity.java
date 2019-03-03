package com.example.connectthree;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity {

    int playerTurn = 0;
    int [] gameState = {2,2,2,2,2,2,2,2,2};
    int [][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    boolean gameOver = false;
    int turns = 9;
    boolean gameIsActive = true;


    public void dropIn(View view){
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.valueOf(counter.getTag().toString());
        Integer state = Integer.valueOf(gameState[tappedCounter]);
        String winner = "This game is a Draw";


        if (state == 2 && gameIsActive) {
            counter.setTranslationY(-1000f);

            if (playerTurn == 0) {
                playerTurn = 1;
                gameState[tappedCounter] = 0;
                counter.setImageResource(R.drawable.yellow);
                turns--;
            } else {
                playerTurn = 0;
                gameState[tappedCounter] = 1;
                counter.setImageResource(R.drawable.red);
                turns--;
            }

            counter.animate().translationYBy(1000f).setDuration(300);
            //if game card is full but no one has won.
            if (turns == 0){
                gameOver = true;
                gameIsActive = false;
            }


            //check for winning state
            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] != 2 && gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[0]] == gameState[winningPosition[2]]) {
                    gameOver = true;
                    gameIsActive = false;

                    //reverse the flop to get the winner
                    if (playerTurn == 1){
                        winner = "Yellow is the winner!";
                    } else if (playerTurn == 0){
                        winner = "Red is the winner!";
                    }
                }

            }

            if (gameOver){
                LinearLayout playAgainLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                playAgainLayout.setVisibility(View.VISIBLE);
                TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                winnerMessage.setText(winner);
                //reset state
            }


        }

    }

    public void replay(View view) {
        LinearLayout playAgainLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
        playAgainLayout.setVisibility(View.INVISIBLE);
        //reset game variables
        playerTurn = 0;
        turns = 9;
        gameIsActive = true;
        gameOver = false;

        for (int i = 0; i < 9; i++){
            gameState[i] = 2;
        }
////        reset board
        android.support.v7.widget.GridLayout gridLayout = (android.support.v7.widget.GridLayout) findViewById(R.id.gridLayout);
        Integer countChild = gridLayout.getChildCount();
            Log.i("Grid Children Count:", countChild.toString());
        for (int i = 0; i < gridLayout.getChildCount(); i++ ) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
