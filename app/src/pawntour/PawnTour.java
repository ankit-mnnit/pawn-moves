package pawntour;

public class PawnTour {

    // all legal moves for the pawn

    private int size;
    private int[] board;
    private int numDigits;
    private Coordinate invalid = new Coordinate(-1, -1);

    public PawnTour(int size) {
        this.size = size;
        this.board = new int[size * size];
        this.numDigits = getNumDigits();
        initBoard();

    }

    // this will be used only for formatting of board cell values.
    private int getNumDigits() {
        int square = size * size;
        int count = 0;
        while (square > 0) {
            count += 1;
            square = square / 10;
        }

        return count;
    }

    // initialize the board
    private void initBoard() {
        for (int i = 0; i < size * size; ++i) {
            board[i] = -1;
        }
    }

    // checks whether the input coordinate is within matrix bounds
    private boolean limits(Coordinate coordinate) {
        return (coordinate.getRow() >= 0 && coordinate.getRow() < size) && (coordinate.getCol() >= 0 && coordinate.getCol() < size);
    }

    // checks whether the input coordinate is within matrix bounds and the cell is visitable
    private boolean isEmpty(Coordinate coordinate) {
        return limits(coordinate) && (board[coordinate.getCol() * size + coordinate.getRow()] < 0);
    }

    // For given coordinate, calculate number of places the pawn can go.
    private int outDegreeOf(Coordinate coordinate, int[][] moves) {
        int count = 0;
        for (int[] move: moves) {
            Coordinate newCoordinate = new Coordinate(coordinate.getRow() + move[0], coordinate.getCol() + move[1]);
            if (isEmpty(newCoordinate)) count += 1;
        }

        return count;
    }

    // calculate next move. For the given coordinate we check all legal and visitable cells. we return the coordinates
    // of the adjacent cell which has minimum out Degree.
    private Coordinate nextMove(Coordinate coordinate, int[][] moves) {
        int minDegIdx = -1;
        int c;
        // setting to very large value
        int minDeg = (size + 1);
        Coordinate newCoordinate;

        for (int i = 0; i < moves.length; ++i) {
            newCoordinate = new Coordinate(coordinate.getRow() + moves[i][0], coordinate.getCol() + moves[i][1]);
            // check for valid move and update minimum degree of adjacent cells
            if (isEmpty(newCoordinate) && (c = outDegreeOf(newCoordinate, moves)) < minDeg) {
                minDegIdx = i;
                minDeg = c;
            }
        }

        // no minimum degree index found
        if (minDegIdx == -1) return invalid;

        // calculate new coordinate
        newCoordinate = new Coordinate(coordinate.getRow() + moves[minDegIdx][0], coordinate.getCol() + moves[minDegIdx][1]);

        // set value of this coordinate to previous coordinate's value + 1
        board[newCoordinate.getCol() * size + newCoordinate.getRow()] = board[coordinate.getCol() * size + coordinate.getRow()] + 1;

        // return new coordinate
        return newCoordinate;
    }

    // checks whether there exists a closed tour starting at given coordinate
    public boolean hasClosedTour(int[][] moves, Coordinate coordinate) {
        Coordinate coordinateCopy = new Coordinate(coordinate);
        coordinateCopy = findTour(coordinateCopy, moves);

        return !coordinateCopy.equals(invalid) && neighbour(coordinateCopy, coordinate, moves);

    }

    // checks whether the two coordinates are one move away.
    private boolean neighbour(Coordinate c1, Coordinate c2, int[][] moves) {
        for (int[] move: moves) {
            if ((c1.getRow() + move[0] == c2.getRow()) && (c1.getCol() + move[1] == c2.getCol())) return true;
        }
        return false;
    }

    // checks whether there exists a tour from the given coordinate
    public boolean hasTour(int[][] moves, Coordinate coordinate) {
        return !findTour(coordinate, moves).equals(invalid);
    }

    // Method to compute tours from a given coordinate.
    private Coordinate findTour(Coordinate coordinate, int[][] moves) {

        Coordinate coordinateCopy = new Coordinate(coordinate);
        // first move
        board[coordinateCopy.getCol() * size + coordinateCopy.getRow()] = 1;
        for (int i = 0; i < size * size - 1; ++i) {
            // make next move
            coordinateCopy = nextMove(coordinateCopy, moves);
            if (coordinateCopy.equals(invalid)) {
                return invalid;
            }

        }

        return coordinateCopy;
    }

    public void printBoard() {
        String format = "%0" + numDigits + "d ";
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                System.out.print(String.format(format, board[j * size + i]));
            }
            System.out.println();
        }
        System.out.println();
    }

    // resets the board.
    public void resetBoard() {
        initBoard();
    }
}
