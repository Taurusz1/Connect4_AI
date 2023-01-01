import java.util.Scanner;

public class Player {
    final int playerToken;
    Board currentBoard;
    public Player(int token){
        this.playerToken = token;
    }
    public int step(Board board){
        System.out.println("Type in the column, " + getName() + ": " );
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }
    String getName(){
        return "X";
    }
    public int getToken() {
        return playerToken;
    }
    void setCurrentBoard(Board board){
        this.currentBoard = board;
    }
    Board getCurrentBoard(){
        return currentBoard;
    }
}
