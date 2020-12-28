package chapter03;

import java.util.Arrays;

public class Scoreboard {
    private int numEntries = 0; // number of actual entries
    private GameEntry[] board;

    public Scoreboard(int capacity) {
        board = new GameEntry[capacity];
    }

    public void add(GameEntry e) {
        int newScore = e.getScore();
        if (numEntries < board.length || newScore > board[numEntries - 1].getScore()) {
            if (numEntries < board.length) {
                numEntries++;
            }
            int j = numEntries - 1;
            while (j > 0 && board[j - 1].getScore() < newScore) {
                board[j] = board[j - 1];
                j--;
            }
            board[j] = e;
        }
    }

    public GameEntry remove(int i) throws IndexOutOfBoundsException {
        if (i < 0 || i >= numEntries) {
            throw new IndexOutOfBoundsException("Invalid index: " + i);
        }
        GameEntry temp = board[i];
        for (int j = i; j < numEntries - 1; j++) {
            board[j] = board[j + 1];
        }
        board[numEntries - 1] = null;
        numEntries--;
        return temp;
    }

    @Override
    public String toString() {
        StringBuilder bld = new StringBuilder();
        bld.append("-=* Scoreboard *=-\n");
        for (int i = 0; i < numEntries; i++) {
            bld.append("  ").append(i + 1).append(" ").append(board[i].toString()).append("\n");
        }
        return bld.toString();
    }
}
