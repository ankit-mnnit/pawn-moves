import pawntour.Coordinate;
import pawntour.PawnTour;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        if (args.length < 2) {
            showUsage();
            System.exit(1);
        }
        int boardSize = Integer.parseInt(args[0]);
        if (boardSize > 0) {
            int[][] moves = {{-3, 0}, {-2, 2}, {0, 3}, {2, 2}, {3, 0}, {2, -2}, {0, -3}, {-2, -2}};
            List<Coordinate> tours = new ArrayList<>();
            List<Coordinate> closedTours = new ArrayList<>();
            PawnTour pawnTour = new PawnTour(boardSize);


            for (int i = 0; i < boardSize; ++i) {
                for (int j = 0; j < boardSize; ++j) {
                    pawnTour.resetBoard();
                    Coordinate coordinate = new Coordinate(i, j);
                    boolean hasTour = pawnTour.hasTour(moves, coordinate);
                    if (Boolean.parseBoolean(args[1]) && hasTour) {
                        System.out.println("--------------------------------------------");
                        pawnTour.printBoard();
                    }
                    pawnTour.resetBoard();
                    boolean hasClosedTour = pawnTour.hasClosedTour(moves, coordinate);
                    if (hasTour) {
                        tours.add(coordinate);
                    }

                    if (hasClosedTour) closedTours.add(coordinate);
                }
            }

            System.out.println(String.format("%02d tours found starting with coordinates %s", tours.size(), tours));
            System.out.println(String.format("%02d closed tours found starting with coordinates %s", closedTours.size(), closedTours));
        } else {
            System.out.println("board size should be more than 0");
        }

    }

    private static void showUsage() {
        System.out.println("Usage: java Solution <length> <printBoard>");
        System.out.println("printBoard is boolean, i.e. either 'true' or 'false' (without quotes)");
        System.out.println("length is the length of square");
    }
}
