import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.TST;

import java.util.Collections;
import java.util.HashSet;

public class BoggleSolver {

    private final TST<Boolean> dict = new TST<>();

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        for (String word : dictionary) {
            dict.put(word, Boolean.TRUE);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        int rows = board.rows();
        int cols = board.cols();
        TST<Boolean> boardDict = new TST<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char c = board.getLetter(i, j);
                Sequence w = new Sequence();
                w.add(i, j, c);
                w.move();
            }
        }


        return Collections.EMPTY_LIST;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        throw new RuntimeException("Not implemented");
    }

    class Sequence {
        StringBuilder chars = new StringBuilder();
        int row;
        int col;
        int rowCnt;
        int colCnt;
        byte pointer;
        HashSet<Integer> rows;
        HashSet<Integer> cols;
        int pos[][] = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1}, {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };

        void add(int r, int c, char ch) {
            row = r;
            col = c;
            rows.add(r);
            cols.add(c);
            chars.append(ch);
            pointer = 0;
        }

        void move() {
            int[] nextPos = calcRowCol();
        }

        int[] calcRowCol() {
            int[] shift = null;
            do {
                shift = pos[pointer++];
                if (shift[0] + row >= 0 && shift[1] + col >= 0 && shift[0] + row < rowCnt && shift[1] + col < colCnt) {
                    return shift;
                }
            } while (pointer < pos.length);
            return null;
        }
    }
}