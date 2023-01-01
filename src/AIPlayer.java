import java.util.HashMap;
import java.util.Map;

public class AIPlayer extends Player{
    private int[] size = null;
    private int[][] state;
    public AIPlayer(int token) {
        super(token);
    }

    public int step(Board board){
        setCurrentBoard(board);
        if(size == null)
            size = currentBoard.getSize();
        state = currentBoard.getState();

        //MOHÓ
        //if we got here there is an empty spot on the board
        //      col       row
        HashMap<Integer, Integer> topEmptyPos = findTopEmptyPos();
        System.out.println("topPos: " + topEmptyPos);
        HashMap<Integer, Integer> grades = gradePos(topEmptyPos);
        System.out.println("grades:"+ grades);
        int bestStep = chooseStep(grades);
        return bestStep;

        //TODO Check every possible col, rate them at the same time
    }
    //We find the place that our token can land in every column
    //Key is col index
    //Value is row index
    private HashMap<Integer, Integer> findTopEmptyPos(){
        HashMap<Integer, Integer> topEmpty = new HashMap<>();
        for(int col = 0; col < size[1]; col++){
            for(int row = size[0]-1; row >= 0; row--){
                if(state[row][col] == 0){
                    topEmpty.put(col, row);
                    break;
                }
            }
        }
        return topEmpty;
    }
    //We grade every possible place we can put our token
    //Key is col index
    //Value is row index
    private HashMap<Integer, Integer> gradePos(HashMap<Integer, Integer> topEmptyPos){
        HashMap<Integer, Integer> grades = new HashMap<>();
        //Key is Dir, Value is grade
        HashMap<String, Integer> bestDirOptions = new HashMap<>();

        for(Map.Entry<Integer, Integer> pos : topEmptyPos.entrySet()){
            int[] coord = {pos.getValue(),pos.getKey()};
            bestDirOptions.put("ROW",gradeRow(coord, state));
            //bestDirOptions.put("COL",gradeCol(coord, state));
            //bestDirOptions.put("DIAG",gradeDiag(coord, state));
            //bestDirOptions.put("OTHERDIAG",gradeOtherDiag(coord, state));
            grades.put(pos.getKey(),chooseBestDir(bestDirOptions));
            bestDirOptions.clear();
        }
        return grades;
    }
    private int chooseBestDir(HashMap<String, Integer> options){
        int maxEntry = 0;
        for(Map.Entry<String, Integer> entry : options.entrySet()){
            if(entry.getValue().compareTo(maxEntry) > 0){
                maxEntry = entry.getValue();
            }
        }
        return maxEntry;
    }
    private int chooseStep(HashMap<Integer, Integer> options){
        int[] maxEntry = {0,0};
        for(Map.Entry<Integer, Integer> entry : options.entrySet()){
            if(entry.getValue().compareTo(maxEntry[1]) > 0){
             maxEntry[0] = entry.getKey();
             maxEntry[1] = entry.getValue();
            }
        }
        return maxEntry[0];
    }

//Let grade be how much already placed tokens there are
    private int gradeRow(int[] lastToken, int[][] state){
        int rowCount = 0;
        int toConnect = currentBoard.getToConnect();
        int startrow = lastToken[0];
        int startcol = Math.max(lastToken[1] - toConnect-1, 0);
        System.out.println(lastToken[0] + "; " + lastToken[1]);
        //TODO ha a sor elején Enemy van, offolja a sort |X| | | | | sor kuka
        //TODO az kellene hogy első saját és számított oszlop között ne legyen |O| |X|O| |, csak ez legyen rossz a fenti ne
        for (int col = startcol; col < size[1]; col++) {
            if(state[startrow][col] == playerToken || startrow == lastToken[0] && col == lastToken[1]){//if its ours, or its the spot we want to place
                rowCount++;
            }else if(state[startrow][col] != playerToken && state[startrow][col] != 0 && col < lastToken[1]){//This means spot is Enemy Token
                System.out.println("Enemy in the way");
                return 0;
            }
        }
        System.out.println("rowcount: " + rowCount);
        return rowCount;
    }
    /*
    private int gradeCol(int[]size, int[][] state){
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
    private int gradeDiag(int[]size, int[][] state){
        int diagCount = 0;
        int toConnect = board.getToConnect();

        //max 3, vagy amennyi lehet fölötte, vagy tőle balra
        int stepLeftUp = min(toConnect - 1, min(lastToken[0], lastToken[1]));
        //ugyan ez csak jobbra lefelé nézve
        int stepRightDown = min(toConnect - 1, min(size[0] - lastToken[0], size[1] - lastToken[1]));
        //ha ki se jöhet a sor
        if ((stepLeftUp + stepRightDown) < toConnect)
            return false;

        for (int diagonalStep = -stepLeftUp; diagonalStep < stepRightDown; diagonalStep++) {
            if (state[lastToken[0] + diagonalStep][lastToken[1] + diagonalStep] == lastplayerToken) {
                diagCount++;
                if(diagCount == toConnect){
                    winnerToken = lastplayerToken;
                    return true;
                }
            }else{
                diagCount = 0;
            }
        }
        return false;
    }
    private int gradeOtherDiag(int[]size, int[][] state){
        int otherdiagCount = 0;
        int toConnect = board.getToConnect();

        int stepLeftDown = min(toConnect - 1, min(size[0] - lastToken[0] - 1, lastToken[1]));
        int stepRightUp = min(toConnect, min(lastToken[0]+1, size[1] - lastToken[1]));
        //ha ki se jöhet a sor
        if ((stepRightUp + stepLeftDown) < toConnect)
            return false;
        for (int diagonalStep = -stepLeftDown; diagonalStep < stepRightUp; diagonalStep++) {

            if (state[lastToken[0] - diagonalStep][lastToken[1] + diagonalStep] == lastplayerToken) {
                otherdiagCount++;
                if(otherdiagCount == toConnect){
                    winnerToken = lastplayerToken;
                    return true;
                }
            }else{
                otherdiagCount = 0;
            }
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
*/
    String getName(){
        return "O";
    }
}
