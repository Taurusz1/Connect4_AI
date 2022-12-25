public class GameLogic {
    private final Board board;
    private final ConsoleView view;
    private int lastplayerToken = -1;
    private int winner = -1;
    private boolean victory;

    public GameLogic(int[] size) {
        this.board = new Board(size);
        this.view = new ConsoleView(board);
        this.victory = false;


        this.view.print();
        this.Run();
    }

    private void Run(){
        if(!victory){
            System.out.println("go on");
        }
        checkVictory();
    }

    private void checkVictory(){
        int[] size = board.getSize();
        int[][] state = board.getState();
        if(boardFull(size, state) || inCol(size, state) || inRow(size, state) ){
            victory = true;
            System.out.println("game ended");
        }
    }

    private boolean inCol(int[]size, int[][] state){
        int colCount = 0;
        for (int row = 0; row < size[0]; row++) {
            for (int col = 0; col < size[1]; col++) {
                if(state[row][col] == lastplayerToken){
                    colCount++;
                    if(colCount == board.getToConnect()){
                        winner = lastplayerToken;
                        return true;
                    }
                }else{
                    colCount = 0;
                }
            }
        }
        return false;
    }
    private boolean inRow(int[]size, int[][] state){
        int rowCount = 0;
        for (int col = 0; col < size[1]; col++) {
            for (int row = 0; row < size[0]; row++) {
                if(state[row][col] == lastplayerToken){
                    rowCount++;
                    if(rowCount == board.getToConnect()){
                        winner = lastplayerToken;
                        return true;
                    }
                }else{
                    rowCount = 0;
                }
            }
        }
        return false;
    }

    private boolean inDiag(){
        return false;
    }
    private boolean inOtherDiag(){
        return false;
    }
    private boolean boardFull(int[]size, int[][] state){
        for (int row = 0; row < size[0]; row++) {
            for (int col = 0; col < size[1]; col++) {
                if(state[row][col] == 0)
                    return false;
            }
        }
        return true;
    }
    private void EndGame(int lastplayerToken) {
        //TODO some shit
    }
}
