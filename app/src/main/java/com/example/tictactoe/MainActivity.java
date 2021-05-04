package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TicTacToeModel model = new TicTacToeModel();
    Button buttons[][] = new Button[3][3];
    String buttonText[][] = new String[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instantiateButtons();

        /*Button restartB = findViewById(R.id.btnRestart);
        restartB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        }); */

    }

    private void instantiateButtons() {
        buttons[0][0] = (Button)findViewById(R.id.button1);
        buttons[0][1] = (Button)findViewById(R.id.button2);
        buttons[0][2] = (Button)findViewById(R.id.button3);
        buttons[1][0] = (Button)findViewById(R.id.button4);
        buttons[1][1] = (Button)findViewById(R.id.button5);
        buttons[1][2] = (Button)findViewById(R.id.button6);
        buttons[2][0] = (Button)findViewById(R.id.button7);
        buttons[2][1] = (Button)findViewById(R.id.button8);
        buttons[2][2] = (Button)findViewById(R.id.button9);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttonText[i][j] = buttons[i][j].getText().toString();
                buttons[i][j].setTextSize(38);
                buttons[i][j].setTag(i + "," + j);
                buttons[i][j].setOnClickListener(this);
            }
        }
    }


    public void stop(Button buttons[][]) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setClickable(false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String tag = (String) button.getTag();
        int row = tag.charAt(0);
        int col = tag.charAt(2);

        if (model.currPlayer.equals(TicTacToeModel.WhoseTurn.X) && model.isValidClick(row, col)) {
            button.setText("X");
            model.cellClick(row,col, TicTacToeModel.WhoseTurn.X);
        } else if (model.currPlayer.equals(TicTacToeModel.WhoseTurn.O) && model.isValidClick(row, col)) {
            button.setText("O");
            model.cellClick(row,col, TicTacToeModel.WhoseTurn.O);

        }
        if (model.isWin()) {
            stop(buttons);
        }

    }
}