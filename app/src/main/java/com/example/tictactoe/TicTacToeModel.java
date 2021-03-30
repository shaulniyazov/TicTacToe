package com.example.tictactoe;

public class TicTacToeModel {

    private String grid[][] = new String[3][3];
    //boolean whoseTurn = true;
    WhoseTurn currPlayer = WhoseTurn.X;
    TicTacToeModel(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = WhoseTurn.None.toString();
            }
        }
    }

    public boolean isValidClick(int row, int col){
        if(grid[row][col].equals("")){
            return true;
        }

        return false;
    }

    public void cellClick(int row, int col, WhoseTurn turn){
        if(isValidClick(row,col)){
            grid[row][col].replace("", turn.toString());
            currPlayer = (currPlayer.equals(WhoseTurn.X)) ? WhoseTurn.O : WhoseTurn.X;
        }
        if(isWin()){
            //insert shrug emoji here
            System.out.println("Horray! you won");
        }
    }

    private boolean isWin() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

            }
        }
        if(grid[0][0].equals(grid[1][0]) && grid[0][0].equals(grid[2][0]))
            return true;
        //if(grid[1][0])
        return false;
    }

    public void restart(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = "";
            }
        }
    }

    //TODO win method

    enum WhoseTurn{
        None, X, O;

        @Override
        public String toString() {
            if(this == WhoseTurn.X)
                return "X";
            if(this == WhoseTurn.O)
                return "O";
            return "";
        }
    }
}
