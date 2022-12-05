package idz1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MakingTheBoard {
    public static String[] disksPlacement = new String[10];
    public static List<int[]> list = new ArrayList<>();
    public static int blackDisks = 2, whiteDisks = 2;

    public static void firstPosition() {
        disksPlacement[0] = "         ";
        disksPlacement[1] = "⁸⬜⬜⬜⬜⬜⬜⬜⬜ ";
        disksPlacement[2] = "⁷⬜⬜⬜⬜⬜⬜⬜⬜ ";
        disksPlacement[3] = "⁶⬜⬜⬜⬜⬜⬜⬜⬜ ";
        disksPlacement[4] = "⁵⬜⬜⬜⚫⚪⬜⬜⬜ ";
        disksPlacement[5] = "⁴⬜⬜⬜⚪⚫⬜⬜⬜ ";
        disksPlacement[6] = "³⬜⬜⬜⬜⬜⬜⬜⬜ ";
        disksPlacement[7] = "²⬜⬜⬜⬜⬜⬜⬜⬜ ";
        disksPlacement[8] = "¹⬜⬜⬜⬜⬜⬜⬜⬜ ";
        disksPlacement[9] = "  ¹ ² ³ ⁴ ⁵  ⁶ ⁷ ⁸";
        printTheBoard(disksPlacement);
    }

    public static void placeTheDisk(boolean isBlack, int[] position) {
        char circle, opponentsCircle;
        var newPlacement = disksPlacement;
        if (isBlack) {
            circle = '⚫';
            opponentsCircle = '⚪';
        } else {
            circle = '⚪';
            opponentsCircle = '⚫';
        }
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                if (disksPlacement[i].charAt(j) == circle && (i == position[0] || j == position[1] ||
                                                              Math.abs(i - position[0]) == Math.abs(j - position[1]))
                    && !Arrays.equals(position, new int[]{i, j})) {
                    int max1 = Math.max(i, position[0]) + 1;
                    int max2 = Math.max(j, position[1]);
                    int min1 = Math.min(i, position[0]) + 1;
                    int min2 = Math.max(i, position[0]);
                    boolean check = false;
                    for (; min1 < max1; min1++) {
                        for (; min2 < max2; min2++) {
                            if (min1 == opponentsCircle) {
                                check = true;
                            } else {
                                check = false;
                                break;
                            }
                        }
                        if (!check)
                            break;
                    }
                    if (check) {
                        for (min1 = Math.min(i, position[0]); min1 <= max1; min1++) {
                            for (min2 = Math.max(i, position[0]); min2 <= max2; min2++) {
                                newPlacement[min1] = newPlacement[min1].substring(0, min2) + circle +
                                                     newPlacement[min1].substring(min2 + 1);
                            }
                        }
                    }
                }
            }
        }

        disksPlacement = newPlacement;
    }

    public static boolean thereArePossibleMoves(boolean isBlack, boolean shouldPrint) {
        char currentDisks, opponentsDisks;
        String availableSquare;
        boolean thereAreAvailableSquares = false;
        list.clear();
        if (isBlack) {
            currentDisks = '⚫';
            opponentsDisks = '⚪';
            availableSquare = "🟩";
        } else {
            currentDisks = '⚪';
            opponentsDisks = '⚫';
            availableSquare = "🟪";
        }
        var currentAndPossible = disksPlacement;
        for (int row = 1; row < 9; row++) {
            for (int column = 1; column < 9; column++) {
                if (currentAndPossible[row].charAt(column) == currentDisks) {
                    int[][] arr = {{row - 1, column - 1}, {row - 1, column + 1}, {row - 1, column}, {row, column - 1},
                            {row, column + 1},
                            {row + 1, column - 1}, {row + 1, column + 1}, {row + 1, column}};
                    for (var pair : arr) {
                        if (currentAndPossible[pair[0]].charAt(pair[1]) == opponentsDisks) {
                            int currRow = pair[0], currColumn = pair[1];
                            while (currentAndPossible[currRow + (row - pair[0])].charAt(currColumn + (column -
                                                                                                      pair[1])) ==
                                   opponentsDisks) {
                                currRow += (row - pair[0]);
                                currColumn += (column - pair[1]);
                            }
                            currRow += (row - pair[0]);
                            currColumn += (column - pair[1]);
                            if (currentAndPossible[currRow].charAt(currColumn) == '⬜') {
                                currentAndPossible[currRow] = currentAndPossible[currRow].substring(0, currColumn) +
                                                              availableSquare + currentAndPossible[currRow].substring
                                        (currColumn + 1);
                                thereAreAvailableSquares = true;
                                list.add(new int[]{currRow, currColumn});
                            }
                        }
                    }
                }
            }
        }
        if (shouldPrint) {
            printTheBoard(currentAndPossible);
        }
        return thereAreAvailableSquares;
    }

    public static void printTheBoard(String[] disks) {
        for (var disk : disks) {
            System.out.println(disk);
        }
    }
}
