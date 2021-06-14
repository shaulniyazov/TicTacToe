package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.example.tictactoe.TicTacToeModel.getGameFromJSON;
import static com.example.tictactoe.TicTacToeModel.getJSONFromGame;
import static com.example.tictactoe.Utils.showInfoDialog;


/*todo? https://stackoverflow.com/questions/30586252/how-to-change-git-repository-using-android-studio
    1) fix the bug where game crashes if its a draw in single player mode
    2) fix the button press if screen is rotated bug
    3)make the title screen look nicer
    4)(optional i guess) make back buttons
    5)(def. optional) statistics activity
    6)(insane) work on the AI
    7)start something else.

 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TicTacToeModel model;
    private Button buttons[][];
    private String buttonText[][];
    private boolean mUseAutoSave;
    private String mKeyUseAutoSave;
    private final String mKEY_GAME = "GAME";
    private static int countForComputer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpFields();
        instantiateButtons();
        instantiateRestartButton();

    }

    private void setUpFields() {
        model = new TicTacToeModel();
        buttons = new Button[3][3];
        buttonText = new String[3][3];
        mKeyUseAutoSave = getString(R.string.auto_save_key);
    }

    private void instantiateRestartButton() {
        Button restartB = findViewById(R.id.restart);
        restartB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //recreate just restarts the app while the code below sets everything back to the beginning.
                //recreate();
                model.restart();
                model.currPlayer = TicTacToeModel.WhoseTurn.X;
                countForComputer = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        buttons[i][j].setClickable(true);
                        buttons[i][j].setText("");
                    }
                }
            }
        });
    }

    private void instantiateButtons() {
        buttons[0][0] = findViewById(R.id.button1);
        buttons[0][1] = (Button) findViewById(R.id.button2);
        buttons[0][2] = (Button) findViewById(R.id.button3);
        buttons[1][0] = (Button) findViewById(R.id.button4);
        buttons[1][1] = (Button) findViewById(R.id.button5);
        buttons[1][2] = (Button) findViewById(R.id.button6);
        buttons[2][0] = (Button) findViewById(R.id.button7);
        buttons[2][1] = (Button) findViewById(R.id.button8);
        buttons[2][2] = (Button) findViewById(R.id.button9);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttonText[i][j] = buttons[i][j].getText().toString();
                buttons[i][j].setTextSize(38);
                buttons[i][j].setTag(i + "," + j);
                buttons[i][j].setOnClickListener(this);
            }
        }
    }


    public void disableBoard(Button buttons[][]) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setClickable(false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(TitleScreen.getMode() == 1)
            onClickWithComputer((Button) v);
        else if(TitleScreen.getMode() == 2)
            onClickTwoPlayers((Button) v);
    }

    private void onClickWithComputer(Button v) {
        Button button = v;
        String tag = (String) button.getTag();
        int row = tag.charAt(0) - 48;
        int col = tag.charAt(2) - 48;
//        Toast.makeText(getApplicationContext(),
//                model.currPlayer + " Turn!", Toast.LENGTH_SHORT).show();
        if (model.isValidClick(row, col)) {
            button.setText("X");
            model.cellClick(row, col, TicTacToeModel.WhoseTurn.X);
            if (isWin()) return;
            //todo if it is a draw then the game crashes because it keeps running computersTurn throws StackOverflowException

            if(computersTurn() && isWin()) return;
            else return;
        }

    }

    public void onClickTwoPlayers(View v) {
        Button button = (Button) v;
        String tag = (String) button.getTag();
        int row = tag.charAt(0) - 48;
        int col = tag.charAt(2) - 48;
        if (model.isValidClick(row, col)) {
            if (model.currPlayer.equals(TicTacToeModel.WhoseTurn.X)) {
                button.setText("X");
                model.cellClick(row, col, TicTacToeModel.WhoseTurn.X);

            } else if (model.currPlayer.equals(TicTacToeModel.WhoseTurn.O)) {
                button.setText("O");
                model.cellClick(row, col, TicTacToeModel.WhoseTurn.O);

            }
            isWin();
        }

    }

    private boolean isWin() {
        if (model.isWin()) {
            disableBoard(buttons);
            String winningPlayer = (!model.currPlayer.toString().equals(TicTacToeModel.WhoseTurn.X.toString())) ? "X" : "O";
            Toast.makeText(getApplicationContext(),
                    "Hooray! " + winningPlayer + " won!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public boolean computersTurn() {
        Random rand = new Random();
        int row = rand.nextInt(3), col = rand.nextInt(3);
        if(model.computersTurn(row,col)){
            buttons[row][col].setText("O");
            return true;
        }
        else{
            computersTurn();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings: {
                showSettings();
                return true;
            }
            case R.id.action_about: {
                showAbout();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveOrDeleteGameInSharedPrefs();
    }

    private void saveOrDeleteGameInSharedPrefs() {
        SharedPreferences defaultSharedPreferences = getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = defaultSharedPreferences.edit();

        // Save current game or remove any prior game to/from default shared preferences
        if (mUseAutoSave)
            editor.putString(mKEY_GAME, model.getJSONFromCurrentGame());
        else
            editor.remove(mKEY_GAME);

        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setAutoSaveFromPreferences();
        restoreGameIfAutoSaveOn();
        updateUI();
    }

    private void restoreGameIfAutoSaveOn() {
        SharedPreferences defaultSharedPreferences = getDefaultSharedPreferences(this);
        if (mUseAutoSave) {
            String gameString = defaultSharedPreferences.getString(mKEY_GAME, null);
            model = gameString != null ? getGameFromJSON(gameString) : model;
        }
    }

    private void setAutoSaveFromPreferences() {
        SharedPreferences sp = getDefaultSharedPreferences(this);
        mUseAutoSave = sp.getBoolean(mKeyUseAutoSave, true);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(mKEY_GAME, getJSONFromGame(model));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        model = getGameFromJSON(savedInstanceState.getString(mKEY_GAME));
        updateUI();
    }

    private void updateUI() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText(model.getText(i, j));
            }
        }
    }

    private void showSettings() {
        Intent intent = new Intent(getApplicationContext(), Settings.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            setAutoSaveFromPreferences();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void showAbout() {

        showInfoDialog(MainActivity.this, "About Tic Tac Toe",
                "Two Player version of Tic Tac Toe.\n" +
                        "\nMade by Joshua Horowitz, Sholom Abrahams, and Shaul Niyazov");
    }

}