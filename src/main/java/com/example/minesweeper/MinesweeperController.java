package com.example.minesweeper;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class MinesweeperController {

    @FXML
    private GridPane gridPane;
    @FXML
    private Label statusLabel;

    private Board board;

    private final int ROWS = 10;
    private final int COLS = 10;
    private final int MINES = 20;

    private boolean gameOver = false;

    private int flagsPlaced = 0;

    public void initialize() {
        startNewGame();
    }

    @FXML
    void resetGame() {
        startNewGame();
    }

    private void startNewGame() {
        board = new Board(ROWS, COLS, MINES);
        gameOver = false;


        flagsPlaced = 0;
        updateStatusLabel();

        gridPane.getChildren().clear();

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                Cell cell = board.getCell(r, c);
                CellButton button = new CellButton(r, c, cell);
                button.setOnMouseClicked(e -> handleMouseClick(button, e));
                gridPane.add(button, c, r);
            }
        }
    }


    private void updateStatusLabel() {
        if (gameOver) return;

        int remaining = MINES - flagsPlaced;
        statusLabel.setText("Mines: " + remaining);


    }

    private void handleMouseClick(CellButton btn, MouseEvent e) {
        if (gameOver) return;


        if (e.getButton() == MouseButton.SECONDARY) {


            if (!btn.getCell().isRevealed) {


                if (btn.getCell().isFlagged) {

                    flagsPlaced--;
                } else {

                    flagsPlaced++;
                }

                btn.getCell().toggleFlag();
                updateButton(btn);


                updateStatusLabel();
            }
        }

        else if (e.getButton() == MouseButton.PRIMARY) {
            if (btn.getCell().isFlagged) return;

            board.revealCell(btn.getRow(), btn.getCol());

            if (btn.getCell().isMine) {
                gameOver = true;
                statusLabel.setText("GAME OVER!");
                statusLabel.setTextFill(Color.RED);
                revealAll();
            } else if (board.checkWin()) {
                gameOver = true;
                statusLabel.setText("YOU WIN!");
                statusLabel.setTextFill(Color.GREEN);
                revealAll();
            } else {
                updateBoardUI();
            }
        }
    }

    private void updateBoardUI() {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (node instanceof CellButton) {
                updateButton((CellButton) node);
            }
        }
    }

    private void updateButton(CellButton btn) {
        Cell cell = btn.getCell();

        if (cell.isRevealed) {
            btn.setDisable(true);
            if (cell.isMine) {
                btn.setText("☢︎");
                btn.setStyle("-fx-background-color: #ff5555; -fx-opacity: 1;");
            } else {
                if (cell.adjacentMines > 0) {
                    btn.setText(String.valueOf(cell.adjacentMines));

                    switch(cell.adjacentMines) {
                        case 1: btn.setTextFill(Color.BLUE); break;
                        case 2: btn.setTextFill(Color.GREEN); break;
                        case 3: btn.setTextFill(Color.RED); break;
                        case 4: btn.setTextFill(Color.DARKBLUE); break;
                        default: btn.setTextFill(Color.BLACK);
                    }
                } else {
                    btn.setText("");
                }
                btn.setStyle("-fx-background-color: #dddddd; -fx-opacity: 1;");
            }
        } else {

            btn.setDisable(false);
            if (cell.isFlagged) {
                btn.setText("⚐");
                btn.setTextFill(Color.RED);
                btn.setStyle("-fx-background-color: #ffff99;");
            } else {
                btn.setText("");
                btn.setStyle(null);
            }
        }
    }

    private void revealAll() {
        for (int r=0; r<ROWS; r++) {
            for (int c=0; c<COLS; c++) {
                board.getCell(r, c).isRevealed = true;
            }
        }
        updateBoardUI();
    }
}