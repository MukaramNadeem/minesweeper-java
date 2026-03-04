package com.example.minesweeper;

public class Board {
    //The Board class will have a 2D array of Cell objects. (grid)
    //It's responsible for creating them, placing mines and then calculating the adjacent mine numbers for each cell.

    private int rows;
    private int cols;
    private int numMines;

    public Cell[][] grid; //!




    //constructor!!!
    public Board(int rows, int cols, int numberOfMines) {
        this.rows = rows;
        this.cols = cols;
        
        this.numMines = numberOfMines; 
        grid = new Cell[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Cell();
            }
        }

        placeMines(numberOfMines);
        calculateAdjacentMines();

    }





//    public void printBoard() {
//
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//
//                if (grid[i][j].isFlagged) {
//                    System.out.print(" F ");
//                } else if (grid[i][j].isRevealed == false) {
//                    System.out.print(" . ");
//                } else if (grid[i][j].isMine) {
//                    System.out.print(" X ");
//                } else {
//                    System.out.print(" " + grid[i][j].adjacentMines + " ");
//                }
//            }
//            System.out.println();
//        }
//    }

    private void placeMines(int totalMines) {
        int numberOfMinesPlaced = 0;

        //int randomNumber = (int) (Math.random() * max); // (0 to max-1)
        while (numberOfMinesPlaced < totalMines) {
            int rRowIndex = (int) (Math.random() * rows);
            int rColIndex = (int) (Math.random() * cols);

            //check!!!
            if (grid[rRowIndex][rColIndex].isMine != true) {

                grid[rRowIndex][rColIndex].isMine = true;

                numberOfMinesPlaced++;
            }
        }
    }
    //-------------calculate int adjacentMines-------------

    //check if the cell is valid (IndexOutOfBoundsException)
    private boolean isValid(int r, int c) {
        if (r < rows && r >= 0 && c < cols && c >= 0) {
            return true;
        } else {
            return false;
        }
    }

    //look at all the 8 neighbours of the cell and return the mines found
    private int countMinesAround(int r, int c) {
        int count = 0;

        if (isValid(r - 1, c - 1) && grid[r - 1][c - 1].isMine == true) {
            count++;
        }
        if (isValid(r - 1, c) && grid[r - 1][c].isMine == true) {
            count++;
        }
        if (isValid(r - 1, c + 1) && grid[r - 1][c + 1].isMine == true) {
            count++;
        }
        if (isValid(r, c - 1) && grid[r][c - 1].isMine == true) {
            count++;
        }
        //?
        if (isValid(r, c + 1) && grid[r][c + 1].isMine == true) {
            count++;
        }
        if (isValid(r + 1, c - 1) && grid[r + 1][c - 1].isMine == true) {
            count++;
        }
        if (isValid(r + 1, c) && grid[r + 1][c].isMine == true) {
            count++;
        }
        if (isValid(r + 1, c + 1) && grid[r + 1][c + 1].isMine == true) {
            count++;
        }
        return count;
    }

    //final step
    private void calculateAdjacentMines() {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j].adjacentMines = countMinesAround(i, j);
            }
        }
    }
    //.......................................//

    private void openZeroCells(int r, int c) {

        if (!isValid(r, c)) {
            return;
        }
        if (grid[r][c].isRevealed) {
            return;
        }
        if (grid[r][c].isFlagged) {
            return;
        }

        grid[r][c].reveal();

        //recursion
        if (grid[r][c].adjacentMines == 0) {
            openZeroCells(r - 1, c - 1);
            openZeroCells(r - 1, c);
            openZeroCells(r - 1, c + 1);
            openZeroCells(r, c - 1);
            openZeroCells(r, c + 1);
            openZeroCells(r + 1, c - 1);
            openZeroCells(r + 1, c);
            openZeroCells(r + 1, c + 1);
        }

    }

    //get the cell to reveal
    public Cell revealCell(int r, int c) {
        if (!isValid(r, c)) {
            return null;
        }
        if (grid[r][c].isMine) {

            grid[r][c].reveal();
            return grid[r][c]; //it would return and then game over is going to handle it.
        }

        openZeroCells(r, c);

        return grid[r][c];
    }



    public Cell getCell(int r, int c) {
        if (isValid(r, c)) {
            return grid[r][c];
        }
        return null;
    }

    //if a player win
     public boolean checkWin() {
        int revealCount = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j].isRevealed) {
                    revealCount++;
                }
            }
        }
        if (revealCount == (rows * cols) - numMines) {
            return true;
        } else {
            return false;
        }
    }
}
