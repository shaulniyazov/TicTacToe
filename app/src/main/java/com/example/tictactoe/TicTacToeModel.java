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

    public boolean isWin() {
        /*for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

            }
        } */
        //TODO even the empty buttons are equal to each other
        if (grid[0][0].equals(grid[1][0]) && grid[0][0].equals(grid[2][0])
                || grid[0][1].equals(grid[1][1]) && grid[0][0].equals(grid[2][1])
                || grid[0][2].equals(grid[1][2]) && grid[0][2].equals(grid[2][2])
                || grid[0][0].equals(grid[0][1]) && grid[0][0].equals(grid[0][2])
                || grid[1][0].equals(grid[1][1]) && grid[1][0].equals(grid[1][2])
                || grid[2][0].equals(grid[2][1]) && grid[2][0].equals(grid[2][2])
                || grid[0][0].equals(grid[1][1]) && grid[0][0].equals(grid[2][2])
                || grid[0][2].equals(grid[1][1]) && grid[0][2].equals(grid[2][0]))
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
