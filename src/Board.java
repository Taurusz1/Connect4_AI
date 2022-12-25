public class Board {
    private final int size[];
    private final int state[][];
    private final int toConnect = 4;


    public Board(int[] size) {
        this.size = size;
        this.state = new int[size[0]][size[1]];
    }

    public int[] getSize() {
        return size;
    }

    public int[][] getState() {
        return state;
    }

    public int getToConnect() {
        return toConnect;
    }
}
