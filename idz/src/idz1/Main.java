package idz1;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, user!");
        try {
            while (true) {
                if (newGame()) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong :(");
        }
    }

    public static void help() {
        System.out.println("""
                Reversi is played on an 8×8 board. Each player starts with two disks in the center.
                The objective is to have the majority of disks when the last playable square is filled.
                During their turn player must place a piece so that there is 1+ lines between the new piece and another
                one, with opponent's pieces between them.
                Pieces in the middle will belong to the player.
                                
                There is only 1 mode now:
                simple Player versus Computer.
                                
                You can always type /h for Help and /q to stop the current game or exit.
                                
                """);
    }

    public static boolean additionalOptions(String input) {
        if (Objects.equals(input, "/q")) {
            System.out.println("Are you sure you want to exit? Type y to confirm, type anything else to " +
                               "cancel.");
            return Objects.equals(input, "y");
        } else if (Objects.equals(input, "/h")) {
            help();
        }
        return false;
    }

    public static boolean newGame() {
        Scanner in = new Scanner(System.in);
        System.out.println("Starting a new game!");
        String input = "/h";
        while (!Objects.equals(input, "start") && !additionalOptions(input)) {
            if (!Objects.equals(input, "/h")) {
                System.out.println("Please use only correct commands during the game.");
            }
            System.out.println("""
                                    
                    Type start to play simple Player versus Computer
                                    
                    Type /q to exit, type /h for help""");
            input = in.nextLine();
        }
        if (!Objects.equals(input, "/q")) {
            GameLogic.startPlaying();
        }
        return true;
    }
}