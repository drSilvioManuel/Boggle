import java.util.HashSet;
import java.util.Set;


public class BoggleSolver {

    private final Trie dict = new Trie();

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        throwExceptionIfNull(dictionary);

        for (String word : dictionary) {
            dict.put(word);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        throwExceptionIfNull(board);

        int rows = board.rows();
        int cols = board.cols();
        Set<String> words = new HashSet<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Sequence w = new Sequence(rows, cols, board);
                w.fillIn(i, j, words);
            }
        }

        return words;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        StringBuilder str = new StringBuilder();
        int wordLength = word.length();
        if (wordLength < 3) return 0;
        for (int i = 0; i < wordLength; i++) {
            char c = word.charAt(i);
            str.append(c);
        }
        Trie.Node x = dict.get(str);
        if (null == x || !x.isLeaf) return 0;

        if (wordLength <= 4) return 1;
        else if (wordLength <= 5) return 2;
        else if (wordLength <= 6) return 3;
        else if (wordLength <= 7) return 5;
        else return 11;
    }

    class Sequence {

        final int rowCnt;
        final int colCnt;
        final boolean[][] visited;
        final BoggleBoard board;
        int pos[][] = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1}, {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };

        Sequence(int r, int c, BoggleBoard b) {
            rowCnt = r;
            colCnt = c;
            visited = new boolean[r][c];
            board = b;
        }

        void fillIn(int r, int c, Set<String> words) {

            StringBuilder str = new StringBuilder();
            str.append(board.getLetter(r, c));
            visited[r][c] = true;

            moveToNextChar(r, c, str, words);
        }

        void moveToNextChar(int row, int col, StringBuilder str, Set<String> words) {
            int pointer = 0;
            do {
                int[] shift = pos[pointer++];
                int r = shift[0] + row;
                int c = shift[1] + col;
                if (r >= 0 && c >= 0 && r < rowCnt && c < colCnt && !visited[r][c]) {
                    visited[r][c] = true;
                    char ch = board.getLetter(r, c);
                    str.append(ch);
                    if (ch == 'Q') str.append('U');
                    Trie.Node node = dict.get(str);

                    if (null != node) {
                        if (node.isLeaf && str.length() > 2) words.add(str.toString());
                        moveToNextChar(r, c, str, words);
                    }
                    str.deleteCharAt(str.length() - 1);
                    if (ch == 'Q') str.deleteCharAt(str.length() - 1);
                    visited[r][c] = false;
                }
            } while (pointer < pos.length);
        }
    }

    private static class Trie {

        private static final int R = 26;
        private static final char SHIFT = 'A';
        private Node root;
        private int n;

        private static class Node {
            private Node[] next = new Node[R];
            private boolean isLeaf;
        }

        Node get(StringBuilder key) {
            throwExceptionIfNull(key);

            Node node = get(root, key, 0);

            return node;
        }

        private Node get(Node node, StringBuilder key, int d) {
            if (null == node) return null;
            if (d == key.length()) return node;

            int index = key.charAt(d) - SHIFT;

            return get(node.next[index], key, d + 1);
        }

        void put(String key) {
            throwExceptionIfNull(key);

            root = put(root, key, 0);
        }

        private Node put(Node node, String key, int d) {
            if (null == node) node = new Node();
            if (d == key.length()) {
                if (!node.isLeaf) n++;
                node.isLeaf = true;
                return node;
            }
            int index = key.charAt(d) - SHIFT;
            node.next[index] = put(node.next[index], key, d + 1);
            return node;
        }
    }

    private static void throwExceptionIfNull(Object... args) {
        for (Object arg : args)
            if (arg == null) throw new IllegalArgumentException();
    }
}