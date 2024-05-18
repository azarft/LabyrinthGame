package com.example.labyrinth;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

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

    public static ArrayList<String> getLabyrinthAsList(int[][] maze, int gridSize) {
        ArrayList<String> labyrinthList = new ArrayList<>();
        for (int i = 0; i < gridSize; i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < gridSize; j++) {
                char cellChar;
                switch (maze[i][j]) {
                    case 2:
                        cellChar = 'A'; // Start point
                        break;
                    case 3:
                        cellChar = 'B'; // End point
                        break;
                    case 1:
                        cellChar = '#'; // Wall
                        break;
                    case 0:
                    default:
                        cellChar = '.'; // Free space
                        break;
                }
                row.append(cellChar);
            }
            labyrinthList.add(row.toString());
        }
        return labyrinthList;
    }

    public static char[][] convertListTo2DArray(ArrayList<String> list) {
        char[][] array = new char[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i).toCharArray();
        }
        return array;
    }
}
