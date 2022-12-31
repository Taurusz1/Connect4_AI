import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.min;

public class AIPlayer extends Player{
    private int[] size = null;
    private int[][] state;
    public AIPlayer(int token) {
        super(token);
    }

    public int step(Board board){
        setCurrentBoard(board);
        if(size ==null)
            size = getCurrentBoard().getSize();
        state = getCurrentBoard().getState();

        HashMap<Integer, Integer> topEmptyPos = findTopEmptyPos();
        //if we got here there is an empty spot on the board
        //MOHÓ
        //TODO Check every possible col, rate them at the same time
        return chooseStep(gradePos(topEmptyPos));
    }
    //TODO test
    private HashMap<Integer, Integer> findTopEmptyPos(){
        HashMap<Integer, Integer> topEmpty = null;
        for(int col = 0; col < size[1]; col++){
            for(int row = size[0]-1; row >= 0; row--){
                if(state[row][col] == 0){
                    topEmpty.put(col, row);
                }
            }
        }
        return topEmpty;
    }
    private HashMap<Integer, Integer> gradePos(HashMap<Integer, Integer> topEmptyPos){
        HashMap<Integer, Integer> grades = null;
        HashMap<String, Integer> bestDirOptions = null;

        for(Map.Entry<Integer, Integer> pos : topEmptyPos.entrySet()){
            int[] coord = {pos.getValue(),pos.getKey()};

            bestDirOptions.put("ROW",gradeRow(coord, state));
            bestDirOptions.put("COL",gradeCol(coord, state));
            bestDirOptions.put("DIAG",gradeDiag(coord, state));
            bestDirOptions.put("OTHERDIAG",gradeOtherDiag(coord, state));

            //TODO choose the best one, already got code for it, copy paste
        }
        return grades;
    }
    private int chooseStep(HashMap<Integer, Integer> options){
        Map.Entry<Integer, Integer> maxEntry = null;
        for(Map.Entry<Integer, Integer> entry : options.entrySet()){
            if(maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0){
             maxEntry = entry;
            }
        }

        return maxEntry.getKey();
    }







/*
    private int gradeRow(int[]size, int[][] state){
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
