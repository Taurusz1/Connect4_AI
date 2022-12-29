import static java.lang.Math.max;
import static java.lang.Math.min;

public class GameLogic {
    private static GameLogic gameLogicInstance = null;
    private final Board board;
    private final ConsoleView view;
    private static Player human;
    private static Player ai;
    private Player currentPlayer;
    private int lastplayerToken = -1;
    private int[] lastToken = {-1,-1};
    private int winnerToken = -1;

    private GameLogic() {
        this.board = new Board();
        this.view = new ConsoleView(board);
        this.human = new Player(1);
        this.ai = new AIPlayer(2);
        this.currentPlayer = this.ai;
        this.Run();
    }
    public static GameLogic getInstance() {
        if (gameLogicInstance == null)
            gameLogicInstance = new GameLogic();

        return gameLogicInstance;
    }
    private void Run(){
        while (true){
            view.print();
            int  col = currentPlayer.step();
            while(!isStepValid(col)){
                System.out.println("invalid input");
                col = currentPlayer.step();
            }
            step(currentPlayer.getToken(),col);
            //TODO check victory in the good way
            if(checkVictory()) {
                view.print();
                EndGame(lastplayerToken);
                break;
            }
            switchCurrentPlayer();
        }
    }
    private void switchCurrentPlayer(){
        currentPlayer = currentPlayer == human ? ai: human;
    }
    public void step(int playerIndex, int col) {
        //nSteps++;
        lastplayerToken = playerIndex;
        //Top row is 0, faster to check this way
        for (int row = board.getSize()[0] - 1; row >= 0; row--) {
            if (board.getState()[row][col] == 0) {
                board.getState()[row][col] = playerIndex;
                lastToken = new int[]{row,col};
                return;
            }
        }
    }
    private boolean isStepValid(int col){
        if(col >= board.getSize()[1]){
            return false;
        }
        else if(board.getState()[0][col] != 0){
            return false;
        }
        return true;
    }
    private boolean checkVictory(){
        int[] size = board.getSize();
        int[][] state = board.getState();
        if(boardFull(size, state) || inRow(size, state) || inCol(size, state) ){
            return true;
        }
        return false;
    }

    // |0|1|2|3|4|5|6|
    // | | | | | | | |
    // | | | | | | | |
    // |X| | | | | | |
    // |X| | | | | | |
    // |X| | | | | | |
    // |X| | | | | | |
    //Only checks if the last placed token completes the game
    private boolean inRow(int[]size, int[][] state){
        int rowCount = 0;
        int toConnect = board.getToConnect();
        int startrow = lastToken[0];
        int startcol = lastToken[1] - toConnect < 0 ? 0 : lastToken[1] - toConnect;
            for (int col = startcol; col < size[1]; col++) {
                if(state[startrow][col] == lastplayerToken){
                    rowCount++;
                    if(rowCount == toConnect){
                        winnerToken = lastplayerToken;
                        return true;
                    }
                }else{
                    rowCount = 0;
                }
            }
        return false;
    }
    private boolean inCol(int[]size, int[][] state){
        int colCount = 0;
        int toConnect = board.getToConnect();
        int startrow = lastToken[0];
        int startcol = lastToken[1];
            for (int row = startrow; row < size[0]; row++) {
                if(state[row][startcol] == lastplayerToken){
                    colCount++;
                    if(colCount == toConnect){
                        winnerToken = lastplayerToken;
                        return true;
                    }
                }else{
                    colCount = 0;
                }
            }
        return false;
    }
    private boolean inDiag(int[]size, int[][] state){
        int diagCount = 0;
        for (int diag = 1; diag <= (size[0] + size[1] - 1); diag++) {
            int start_col = max(0, diag - size[0]);
            int count = min(diag, min((size[1] - start_col), size[0]));
            for (int j = 0; j < count; j++){
                //System.out.print(state[min(size[0], diag) - j- 1][start_col + j] + " ");
                if(state[min(size[0], diag) - j- 1][start_col + j] == lastplayerToken){
                    diagCount++;
                    if(diagCount == board.getToConnect()){
                        winnerToken = lastplayerToken;
                        return true;
                    }
                }else{
                    diagCount = 0;
                }
            }
            diagCount = 0;
            System.out.println();
        }
        return false;
    }
    private boolean inOtherDiag(int[]size, int[][] state){
        int otherdiagCount = 0;
        for (int diag = 1; diag <= (size[0] + size[1] - 1); diag++) {
            int start_col;
            if(diag <= size[0]){
                start_col = diag-1;
            }else{
                start_col = size[0]-1;
            }
            int count = min(diag, min((size[1] - start_col), size[0]));
            for (int j = 0; j < count; j++){
                System.out.print(state[min(size[0], diag) - j- 1][start_col + j] + " ");
                if(state[min(size[0], diag) - j- 1][start_col + j] == lastplayerToken){
                    otherdiagCount++;
                    if(otherdiagCount == board.getToConnect()){
                        winnerToken = lastplayerToken;
                        return true;
                    }
                }else{
                    otherdiagCount = 0;
                }
            }
            otherdiagCount = 0;
            System.out.println();
        }
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
        System.out.println("Game ended");
        if(lastplayerToken == ai.getToken()){
            System.out.println("AI wins");
        }else{
            System.out.println("Human wins");
        }
    }
}
