package com.example.labyrinth;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class GridUtils {

    public static Node getNodeByRowColumnIndex(int row, int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == row &&
                    GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) == column) {
                return node;
            }
        }
        return null;
    }

    public static int[] getCellCoordinates(Rectangle cell, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (node == cell) {
                int row = GridPane.getRowIndex(node);
                int col = GridPane.getColumnIndex(node);
                return new int[]{row, col};
            }
        }
        return null;
    }

    public static char[][] convertMazeToCharArray(int[][] maze) {
        int gridSize = maze.length;
        char[][] labyrinth = new char[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                switch (maze[i][j]) {
                    case 2:
                        labyrinth[i][j] = 'A'; // Start point
                        break;
                    case 3:
                        labyrinth[i][j] = 'B'; // End point
                        break;
                    case 1:
                        labyrinth[i][j] = '#'; // Wall
                        break;
                    case 0:
                    default:
                        labyrinth[i][j] = '.'; // Free space
                        break;
                }
            }
        }
        return labyrinth;
    }
}
