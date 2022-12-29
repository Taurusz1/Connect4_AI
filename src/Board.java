public class Board {
    private final int size[] = {6,7};
    private final int state[][];
    private final int toConnect = 4;
    public Board() {
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
