import edu.princeton.cs.algs4.In;

public class BoggleBoard {

    private final int rows;
    private final int cols;
    private final char[][] board;
    // Initializes a random 4-by-4 Boggle board.
    // (by rolling the Hasbro dice)
    public BoggleBoard() {
        this(4, 4);
    }

    // Initializes a random m-by-n Boggle board.
    // (using the frequency of letters in the English language)
    public BoggleBoard(int m, int n) {
        rows = m;
        cols = n;
        board = new char[rows][cols];
    }

    // Initializes a Boggle board from the specified filename.
    public BoggleBoard(String filename) {
        throwExceptionIfNull(filename);

        In input = new In(filename);
        String line  = input.readLine();
        throwExceptionIfNull(line);

        String[] dims = line.trim().split(" ");
        rows = Integer.parseInt(dims[0]);
        cols = Integer.parseInt(dims[1]);
        board = new char[rows][cols];
        int i = 0;
        while (input.hasNextLine()) {
            line = input.readLine();
            int j = 0;
            for (int n = 0; n < line.length(); n++) {
                char c = line.charAt(n);
                if (c == ' ') continue;

                board[i][j++] = c;

                if (c == 'Q') n++;
            }
            i++;
        }
    }

    // Initializes a Boggle board from the 2d char array.
    // (with 'Q' representing the two-letter sequence "Qu")
    public BoggleBoard(char[][] a) {
        board = a;
        rows = a.length;
        cols = a[0].length;
    }

    // Returns the number of rows.
    public int rows() {
        return rows;
    }

    // Returns the number of columns.
    public int cols() {
        return cols;
    }

    // Returns the letter in rows i and column j.
    // (with 'Q' representing the two-letter sequence "Qu")
    public char getLetter(int i, int j) {
        return board[i][j];
    }

    // Returns a string representation of the board.
    public String toString() {
        throw new RuntimeException("Not implemented");
    }

    private static void throwExceptionIfNull(Object... args) {
        for (Object arg : args)
            if (arg == null) throw new IllegalArgumentException();
    }
}
