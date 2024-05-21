package com.example.labyrinth;

import javafx.util.Pair;

import java.util.*;

public class LabyrinthSolver {

    public static Pair<Boolean, List<Character>> bfs(char[][] labyrinth) {
        int n = labyrinth.length;
        int m = labyrinth[0].length;
        int startX = -1, startY = -1, endX = -1, endY = -1;

        // Find the start and end points
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (labyrinth[i][j] == 'A') {
                    startX = i;
                    startY = j;
                } else if (labyrinth[i][j] == 'B') {
                    endX = i;
                    endY = j;
                }
            }
        }

        if (startX == -1 || startY == -1 || endX == -1 || endY == -1) {
            return new Pair<>(false, new ArrayList<>());
        }

        // Define possible directions to move: up, down, left, right
        char[] directions = {'U', 'D', 'L', 'R'};
        int[][] move = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        // Initialize visited array
        boolean[][] visited = new boolean[n][m];

        // Initialize queue for BFS
        Queue<Pair<Pair<Integer, Integer>, List<Character>>> queue = new LinkedList<>();

        // Mark starting point as visited
        visited[startX][startY] = true;
        queue.add(new Pair<>(new Pair<>(startX, startY), new ArrayList<>()));

        // Perform BFS
        while (!queue.isEmpty()) {
            Pair<Integer, Integer> curr = queue.peek().getKey();
            List<Character> path = queue.peek().getValue();
            queue.poll();

            if (curr.getKey() == endX && curr.getValue() == endY) {
                return new Pair<>(true, path);
            }

            for (int i = 0; i < directions.length; i++) {
                int newX = curr.getKey() + move[i][0];
                int newY = curr.getValue() + move[i][1];

                if (isValid(newX, newY, n, m, labyrinth, visited)) {
                    visited[newX][newY] = true;
                    List<Character> newPath = new ArrayList<>(path);
                    newPath.add(directions[i]);
                    queue.add(new Pair<>(new Pair<>(newX, newY), newPath));
                }
            }
        }

        return new Pair<>(false, new ArrayList<>());
    }

    private static boolean isValid(int x, int y, int n, int m, char[][] labyrinth, boolean[][] visited) {
        return x >= 0 && x < n && y >= 0 && y < m && labyrinth[x][y] != '#' && !visited[x][y];
    }
}
