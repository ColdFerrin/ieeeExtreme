// Don't place your source in a package
import java.util.*;
import java.lang.*;
import java.io.*;

// Please name your class Main
class Main {
    public static void main (String[] args) throws java.lang.Exception {
        Scanner in = new Scanner(System.in);
        int size = in.nextInt();
        int numPlayers = in.nextInt();
        int snakeNum = in.nextInt();
        HashMap<String, String> snakes = new HashMap<>();
        HashMap<String, String> ladders = new HashMap<>();
        ArrayList<Player> players = new ArrayList<>(numPlayers);

        for (int i = 0; i < numPlayers; i++) {
            players.add(i, new Player(size));
        }

        for (int i = 0; i < snakeNum; i++) {
            snakes.put(in.nextInt() + "," + in.nextInt(), in.nextInt() + "," + in.nextInt());
        }

        int ladderNum = in.nextInt();

        for (int i = 0; i < ladderNum; i++) {
            ladders.put(in.nextInt() + "," + in.nextInt(), in.nextInt() + "," + in.nextInt());
        }

        int rolls = in.nextInt();

        for (int i = 0, player = 0; i < rolls; i++, player = (player + 1) % numPlayers) {
            int num1 = in.nextInt();
            int num2 = in.nextInt();

            players.get(player).move(num1+num2);

            String moveEnd = players.get(player).getxPos() + "," + players.get(player).getyPos();
            String toMove = "";

            if(snakes.containsKey(moveEnd))
                toMove = snakes.get(moveEnd);

            if(ladders.containsKey(moveEnd))
                toMove = ladders.get(moveEnd);

            if(!toMove.isEmpty()) {
                String[] toMoveSplit = toMove.split(",");
                players.get(player).move(Integer.parseInt(toMoveSplit[0]), Integer.parseInt(toMoveSplit[1]));
            }
        }
        
        int i = 1;
        for (Player p: players) {
        	if (p.isWinner())
        		System.out.println(i + " winner");
        	i++;
        }	
    }
}

class Player {
    private int xPos;
    private int yPos;
    private int size;
    private boolean winner;

    public Player(int size) {
        this.xPos = 0;
        this.yPos = 1;
        this.size = size;
        winner = false;
    }
    
    int direction = 1;
    void move(int places){
    	places = Math.abs(places);
    	
    	xPos += places * direction;
    	if (direction > 0 && xPos > size - 1) {
    		direction = -1;
    		yPos--;
    		int overflow = xPos - size - 1;
    		xPos = size-1;
    		// move in the other direction
    		move(overflow);
    	}
    	else if (direction < 0 && xPos < 1) {
    		if (yPos != 0) {
    			direction = 1;
    			yPos--;
    			int overflow = 1 - xPos;
    			xPos = 1;
    			// move in the other direction
    			move(overflow);
    		}
    		else if (xPos <= 0){
    			winner = true;
    		}
    	}
    }

    void move(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }
}