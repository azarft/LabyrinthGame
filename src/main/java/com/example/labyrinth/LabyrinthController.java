package com.example.labyrinth;

import java.net.URL;
import java.nio.file.Paths;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LabyrinthController {
    @FXML
    private TextField gridSizeInput;

    @FXML
    private Label noSolutionLabel;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button createLabyrinthButton;

    @FXML
    private Button findSolutionButton;

    private int gridSize;
    private Rectangle startPoint;
    private Rectangle endPoint;
    private boolean isButtonClicked = false;


    private int[][] maze; // To store the maze structure

    @FXML
    protected void initialize() {
        playBackgroundMusic();
    }

    private void playBackgroundMusic() {
        try {
            // Load the MP3 file from resources
            URL resource = getClass().getResource("/background.mp3");
            if (resource == null) {
                System.err.println("Background music file not found");
                return;
            }

            Media media = new Media(resource.toString());
            // MediaPlayer for background music
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Error loading background music: " + e.getMessage());
            e.printStackTrace();
        }
    }



    @FXML
    protected void onStartButtonClick() {
        try {
            gridSize = Integer.parseInt(gridSizeInput.getText());
            if (gridSize < 5 || gridSize > 20) {
                gridSizeInput.setStyle("-fx-border-color: red;");
                return;
            }
            gridSizeInput.setStyle("-fx-border-color: black;");
            createGrid();
        } catch (NumberFormatException e) {
            gridSizeInput.setStyle("-fx-border-color: red;");
        }
        noSolutionLabel.setVisible(false);
        isButtonClicked = false;
    }

    @FXML
    protected void onCreateLabyrinthButtonClick() {
        generateMaze();
        createLabyrinthButton.setVisible(false);
        findSolutionButton.setVisible(true); // Make the "Find Solution" button visible
    }

    @FXML
    protected void onResetButtonClick() {
        gridPane.getChildren().clear();
        gridSizeInput.clear();
        gridSizeInput.setStyle("-fx-border-color: black;");
        startPoint = null;
        endPoint = null;
        createLabyrinthButton.setVisible(false);
        findSolutionButton.setVisible(false);
        noSolutionLabel.setVisible(false);
        isButtonClicked = false;
    }

    @FXML
    protected void onFindSolutionButtonClick() {
        char[][] labyrinth = GridUtils.convertMazeToCharArray(maze);
        Pair<Boolean, List<Character>> result = LabyrinthSolver.bfs(labyrinth);

        if (result.getKey()) {
            showSolution(result.getValue());
        } else {
            displayNoSolutionMessage();
        }
        findSolutionButton.setVisible(false);
        createLabyrinthButton.setVisible(false);
        isButtonClicked = true;
    }

    private void showSolution(List<Character> path) {
        int[] startCoords = GridUtils.getCellCoordinates(startPoint, gridPane);
        int[] endCoords = GridUtils.getCellCoordinates(endPoint, gridPane);
        int row = startCoords[0];
        int col = startCoords[1];

        for (char direction : path) {
            switch (direction) {
                case 'U':
                    row -= 1;
                    break;
                case 'D':
                    row += 1;
                    break;
                case 'L':
                    col -= 1;
                    break;
                case 'R':
                    col += 1;
                    break;
            }
            // Skip coloring the end point
            if (row == endCoords[0] && col == endCoords[1]) {
                continue;
            }
            // Get the cell at the new position and set its color to #eeb811
            Rectangle cell = (Rectangle) GridUtils.getNodeByRowColumnIndex(row, col, gridPane);
            if (cell != null) {
                cell.setFill(Color.web("#eeb811"));
            }
        }
    }

    private void createGrid() {
        gridPane.getChildren().clear();
        startPoint = null;
        endPoint = null;
        createLabyrinthButton.setVisible(false);
        findSolutionButton.setVisible(false);

        maze = new int[gridSize][gridSize]; // Initialize the maze array

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                double lenght = 450/gridSize;
                Rectangle cell = new Rectangle(lenght, lenght); // Adjust size as needed
                cell.setStroke(Color.BLACK);
                cell.setFill(Color.WHITE);
                final int row = i;
                final int col = j;
                cell.setOnMouseClicked(event -> handleCellClick(cell, row, col));
                gridPane.add(cell, j, i);
            }
        }
    }

    private void handleCellClick(Rectangle cell, int row, int col) {
        if (startPoint == null) {
            cell.setFill(Color.GREEN);
            cell.setAccessibleText("Start");// Start point
            startPoint = cell;
            maze[row][col] = 2; // Mark the start point in the maze
        } else if (endPoint == null && maze[row][col] != 2) {
            cell.setFill(Color.RED); // End point
            endPoint = cell;
            maze[row][col] = 3; // Mark the end point in the maze
            createLabyrinthButton.setVisible(true); // Make the "Create Labyrinth" button visible
        } else if (!isButtonClicked){
            if (maze[row][col] == 1 ) {
                cell.setFill(Color.WHITE); // End point
                maze[row][col] = 0; // Mark the end point in the maze
            } else if (maze[row][col] == 2) {
                maze[row][col] = 0;
                cell.setFill(Color.WHITE);
                startPoint = null;
            } else if (maze[row][col] == 3) {
                maze[row][col] = 0;
                cell.setFill(Color.WHITE);
                endPoint = null;
            } else {
                cell.setFill(Color.BLACK); // End point
                maze[row][col] = 1; // Mark the end point in the maze
                findSolutionButton.setVisible(true); // Make the "Create Labyrinth" button visible
            }

        }
    }

    private void generateMaze() {
        // Example: Simple placeholder maze generation
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (maze[i][j] == 0) { // Only change unmarked cells
                    if (Math.random() > 0.7) {
                        maze[i][j] = 1; // Mark as wall
                        ((Rectangle) GridUtils.getNodeByRowColumnIndex(i, j, gridPane)).setFill(Color.BLACK);

                    } else {
                        maze[i][j] = 0; // Mark as path
                    }
                }
            }
        }
        // Ensure start and end points are paths
        int[] startCoords = GridUtils.getCellCoordinates(startPoint, gridPane);
        int[] endCoords = GridUtils.getCellCoordinates(endPoint, gridPane);
        maze[startCoords[0]][startCoords[1]] = 2;
        maze[endCoords[0]][endCoords[1]] = 3;
    }

    private void displayNoSolutionMessage() {
        Label noSolutionLabel = (Label) gridPane.getParent().lookup("#noSolutionLabel");
        noSolutionLabel.setVisible(true);
    }
}
