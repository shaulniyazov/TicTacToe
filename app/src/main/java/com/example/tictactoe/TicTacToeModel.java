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
            grid[row][col] = turn.toString();
            currPlayer = (currPlayer.equals(WhoseTurn.X)) ? WhoseTurn.O : WhoseTurn.X;
        }
        isWin();
    }

    public boolean isWin() {
        if ((grid[0][0].equals(grid[1][0]) && grid[0][0].equals(grid[2][0]) && (!grid[0][0].equals(""))) //Left vertical
                || (grid[0][1].equals(grid[1][1]) && grid[0][0].equals(grid[2][1]) && (!grid[0][1].equals(""))) //Middle vertical
                || (grid[0][2].equals(grid[1][2]) && grid[0][2].equals(grid[2][2]) && (!grid[0][2].equals(""))) //Right vertical
                || (grid[0][0].equals(grid[0][1]) && grid[0][0].equals(grid[0][2]) && (!grid[0][0].equals(""))) //Top horizontal
                || (grid[1][0].equals(grid[1][1]) && grid[1][0].equals(grid[1][2]) && (!grid[1][0].equals(""))) //Middle horizontal
                || (grid[2][0].equals(grid[2][1]) && grid[2][0].equals(grid[2][2]) && (!grid[2][0].equals(""))) //Bottom horizontal
                || (grid[0][0].equals(grid[1][1]) && grid[0][0].equals(grid[2][2]) && (!grid[0][0].equals(""))) //Top-Left diagonal
                || (grid[0][2].equals(grid[1][1]) && grid[0][2].equals(grid[2][0]) && (!grid[0][2].equals(""))) //Top-Right diagonal
        )
            return true;
        return false;
    }

    public void restart(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = "";
            }
        }
    }

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
