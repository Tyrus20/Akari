package akari.model;

public class PuzzleImpl implements Puzzle {
    private final int[][] board;

    public PuzzleImpl(int[][] board) {
        this.board = board;
    }

    @Override
    public int getWidth() {
        int width = 0;
        for (int[] ints : board) {
            if (ints.length > width) {
                width = ints.length;
            }
        }
        return width;
    }

    @Override
    public int getHeight() {
        return board.length;
    }

    @Override
    public CellType getCellType(int r, int c) {
        if (r < 0 || r >= getHeight() || c < 0 || c >= getWidth()) {
            throw new IndexOutOfBoundsException();
        } else if (board[r][c] >= 0 && board[r][c] <= 4) {
            return CellType.CLUE;
        } else if (board[r][c] == 5) {
            return CellType.WALL;
        } else if (board[r][c] == 6) {
            return CellType.CORRIDOR;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public int getClue(int r, int c) {
        if (r < 0 || r >= getHeight() || c < 0 || c >= getWidth()) {
            throw new IndexOutOfBoundsException();
        } else if (getCellType(r, c) == CellType.CLUE) {
            return board[r][c];
        } else {
            throw new IllegalArgumentException();
        }
    }
}
