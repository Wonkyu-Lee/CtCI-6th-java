import java.util.Scanner;

public class Ex_07_08_Othello {
    enum Stone {
        Empty, Black, White
    }

    static class Board {
        private final int size;
        private final Stone[][] cells;
        private Stone turn = Stone.Black;
        private State state = State.Running;

        public enum State {
            Running,
            BlackWins,
            WhiteWins,
            Draws
        }

        public Board(int size) {
            this.size = size;
            cells = new Stone[size][size];
        }

        public void reset() {
            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {
                    cells[i][j] = Stone.Empty;
                }
            }
            turn = Stone.Black;
            state = State.Running;
        }

        public boolean isFinished() {
            return state != State.Running;
        }

        public Stone whoseTurn() {
            return turn;
        }

        public State getState() {
            return state;
        }

        public boolean put(Stone stone, int row, int col) {
            if (!canPut(stone, row, col))
                return false;

            update(stone, row, col);
            return true;
        }

        public boolean canPut(Stone stone, int row, int col) {
            if (isFinished())
                return false;

            if (whoseTurn() != stone)
                return false;

            if (!isValid(row, col))
                return false;

            if (cells[row][col] != Stone.Empty)
                return false;

            // TODO: 지금 돌이 상대 돌을 하나라도 감싸는지 리턴한다.




            return false;
        }

        private void update(Stone stone, int row, int col) {
            // TODO: 새로운 돌이 놓여졌을 때 셀들을 업데이트 한다. 게임 상태를 갱신한다. 턴을 갱신한다.


        }

        private boolean isValid(int row, int col) {
            return (0 <= row && row < size) && (0 <= col && col < size);
        }
    }

    interface Player {
        boolean waitTurn(Stone stone, Board board);
    }

    static class Othello {
        private final Board board;
        private final Player blackPlayer;
        private final Player whitePlayer;

        public Othello(int boardSize, Player blackPlayer, Player whitePlayer) {
            this.board = new Board(boardSize);
            this.blackPlayer = blackPlayer;
            this.whitePlayer = whitePlayer;
        }

        public void reset() {
            board.reset();
        }

        public void run() {
            board.reset();

            while (!board.isFinished()) {
                boolean turnFinished = false;
                while (!turnFinished) {
                    switch(board.whoseTurn()) {
                        case Black:
                            turnFinished = blackPlayer.waitTurn(Stone.Black, board);
                            break;
                        case White:
                            turnFinished = whitePlayer.waitTurn(Stone.White, board);
                            break;
                        default:
                            break;
                    }
                }
            }

            showResult();
        }

        private void showResult() {
            switch (board.getState()) {
                case Running:
                    break;
                case Draws:
                    System.out.println("Draw!");
                    break;
                case BlackWins:
                    System.out.println("Black wins!");
                    break;
                case WhiteWins:
                    System.out.println("White wins!");
                    break;
            }
        }
    }

    public static class KeyboardPlayer implements Player {
        @Override
        public boolean waitTurn(Stone stone, Board board) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter row and column: ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (!board.canPut(stone, row, col)) {
                System.out.println("Invalid position!");
                return false;
            } else {
                return true;
            }
        }
    }

    public static void main(String[] args) {
        // TODO: UI까지 있어야 만드는 의미 있다.
    }
}
