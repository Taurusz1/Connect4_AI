public class ConsoleView {
    private final String  SEPARATOR = "|";
    private final Board board;
    private final int[] size;
    private final int[][] state;
    public ConsoleView(Board board) {
        this.board = board;
        this.size = board.getSize();
        this.state = board.getState();
    }

    public void print(){
        System.out.println();
        //Print the first row
        for (int col = 0; col < size[1]; col++) {
            System.out.printf("%s%d", SEPARATOR, col);
        }
        System.out.println(SEPARATOR);

        //Print the state
        for (int row = 0; row < size[0]; row++) {
            for (int col = 0; col < this.size[1]; col++) {
                char marker = ' ';
                if (state[row][col] == 1) {
                    marker = 'X';
                } else if (state[row][col] == 2) {
                    marker = 'O';
                }
                System.out.printf("%s%c", SEPARATOR, marker);
            }
            System.out.println(SEPARATOR);
        }
        System.out.println();
    }
}
