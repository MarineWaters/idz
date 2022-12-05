package idz1;

import java.util.Arrays;
import java.util.Scanner;

public class GameLogic {
    public static void startPlaying() {
        boolean previousTurnWasComputers = true;
        MakingTheBoard.firstPosition();
        Scanner in = new Scanner(System.in);
        String square;
        for (int turn = 0; turn < 60; turn++) {
            if (!MakingTheBoard.thereArePossibleMoves(true, true) && !MakingTheBoard.
                    thereArePossibleMoves(false, false)) {
                break;
            }
            if ((previousTurnWasComputers || !MakingTheBoard.thereArePossibleMoves(false, false))
                && MakingTheBoard.thereArePossibleMoves(true, false)) {
                System.out.println("Player's turn");
                while (true) {
                    System.out.println("Please choose one of the available squares by entering a row and a " +
                                       "column (ex. 1 4)");
                    square = in.nextLine();
                    try {
                        if (MakingTheBoard.list.contains(new int[]{(int) square.charAt(0), (int) square.charAt(2)})) {
                            MakingTheBoard.disksPlacement[(int) square.charAt(0)] = MakingTheBoard.disksPlacement
                                                                                            [square.charAt(0)].substring
                                    (0, square.charAt(2)) + '⚫' + MakingTheBoard.disksPlacement
                                                                                            [square.
                                    charAt(0)].substring((int) square.charAt(2) + 1);
                        }
                        MakingTheBoard.placeTheDisk(true, new int[]{(int) square.charAt(0), (int) square.charAt(2)});
                        break;
                    } catch (Exception e) {
                        System.out.println("Incorrect format.");
                    }
                }
                previousTurnWasComputers = false;
            } else {
                System.out.println("Computer's turn");
                MakingTheBoard.thereArePossibleMoves(false, false);
                square = chooseTheSquare();
                if (MakingTheBoard.list.contains(new int[]{(int) square.charAt(0), (int) square.charAt(2)})) {
                    MakingTheBoard.disksPlacement[(int) square.charAt(0)] = MakingTheBoard.disksPlacement
                                                                                    [square.charAt(0)].substring
                            (0, square.charAt(2)) + '⚪' + MakingTheBoard.disksPlacement
                                                                                    [square.
                            charAt(0)].substring((int) square.charAt(2) + 1);
                }
                MakingTheBoard.placeTheDisk(true, new int[]{(int) square.charAt(0), (int) square.charAt(2)});
                previousTurnWasComputers = true;
            }
        }
        System.out.println("No available squares. The game ended.");
        int playersDisks = countTheDisks(true);
        int computersDisks = countTheDisks(false);
        if (playersDisks == computersDisks) {
            System.out.println("That's a draw! Both has " + playersDisks + " each.");
        } else if (playersDisks > computersDisks) {
            System.out.println("You won! You've got " + playersDisks + " disks.");
        } else {
            System.out.println("You lost! Your opponent has " + computersDisks + " disks while you have " +
                               playersDisks + ".");
        }
    }

    private static String chooseTheSquare() {
        int bestValue = 0;
        int[] bestPosition = MakingTheBoard.list.get(0);
        for (int[] position : MakingTheBoard.list) {
            int value = 0;
            for (int i = 1; i < 9; i++) {
                for (int j = 1; j < 9; j++) {
                    if (MakingTheBoard.disksPlacement[i].charAt(j) == '⚪' && (i == position[0] || j == position[1] ||
                                                                              Math.abs(i - position[0]) == Math.abs(j - position[1]))
                        && !Arrays.equals(position, new int[]{i, j})) {
                        int max1 = Math.max(i, position[0]) + 1;
                        int max2 = Math.max(j, position[1]);
                        int min1 = Math.min(i, position[0]) + 1;
                        int min2 = Math.max(i, position[0]);
                        boolean check = false;
                        for (; min1 < max1; min1++) {
                            for (; min2 < max2; min2++) {
                                if (min1 == '⚫') {
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
                            for (min1 = Math.min(i, position[0]); min1 < max1; min1++) {
                                for (min2 = Math.max(i, position[0]); min2 < max2; min2++) {
                                    if (min1 == 1 || min2 == 1 || min1 == 8 || min2 == 8) {
                                        value += 2;
                                    } else {
                                        value += 1;
                                    }
                                }
                            }
                            if (i == 1 || i == 8 || j == 1 || j == 8) {
                                if ((i == 1 || i == 8) && (j == 1 || j == 8))
                                    value += 0.8;
                                else
                                    value += 0.4;
                            }
                        }
                    }
                }
            }
            if (value > bestValue) {
                bestValue = value;
                bestPosition = position;
            }
        }
        return bestPosition[0] + " " + bestPosition[1];
    }

    public static int countTheDisks(boolean isBlack) {
        if (isBlack) {
            return MakingTheBoard.blackDisks;
        } else {
            return MakingTheBoard.whiteDisks;
        }
    }
}
