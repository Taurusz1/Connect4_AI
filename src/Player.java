import java.util.Scanner;

public class Player {
    final int playerToken;
    public Player(int token){
        this.playerToken = token;
    }
    public int step(){
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
}
