package com.example.minesweeper;

public class Cell {

    public boolean isFlagged;

    public boolean isMine;

    public boolean isRevealed;

    public int adjacentMines;

    public Cell() {
        isFlagged = false;
        isMine = false;
        isRevealed = false;
        adjacentMines = 0;
    }

    public void reveal() {
        this.isRevealed = true;
    }

     public void toggleFlag() {

        isFlagged = !isFlagged;
     }

}