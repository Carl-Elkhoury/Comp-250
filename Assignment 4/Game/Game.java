// package assignment4Game;

import java.io.*;

// import javax.crypto.Cipher;

// import jdk.nashorn.internal.runtime.Debug;

public class Game {
	
	public static int play(InputStreamReader input){
		BufferedReader keyboard = new BufferedReader(input);
		Configuration c = new Configuration();
		int columnPlayed = 3; int player;
		
		// first move for player 1 (played by computer) : in the middle of the grid
		c.addDisk(firstMovePlayer1(), 1);
		int nbTurn = 1;
		
		while (nbTurn < 42){ // maximum of turns allowed by the size of the grid
			player = nbTurn %2 + 1;
			if (player == 2){
				columnPlayed = getNextMove(keyboard, c, 2);
			}
			if (player == 1){
				columnPlayed = movePlayer1(columnPlayed, c);
			}
			System.out.println(columnPlayed);
			c.addDisk(columnPlayed, player);
			if (c.isWinning(columnPlayed, player)){
				c.print();
				System.out.println("Congrats to player " + player + " !");
				return(player);
			}
			nbTurn++;
		}
		return -1;
	}
	
	public static int getNextMove(BufferedReader keyboard, Configuration c, int player){
		int column =-1;
		c.print();
		while(true){ 
			try{
				column = Integer.parseInt(keyboard.readLine());
				// System.out.printf("the input is"+column);
			}catch(Exception e){
				continue;
			}
			if(column>6 || column< 0)
				continue;
			for(int i=0; i<6; i++){
				if(c.board[column][i] ==0){
					// System.out.print("returned");
					return column;
				}
			}

		}
		
	}
	
	public static int firstMovePlayer1 (){
		return 3;
	}
	
	public static int movePlayer1 (int columnPlayed2, Configuration c){
		// ADD YOUR CODE HERE
		int player =1;
		int winNextRound =c.canWinNextRound(player);
		int winTwoRound = c.canWinTwoTurns(player);
		if( winNextRound != -1)
			return winNextRound;
		
		if(winTwoRound != -1)
			return winTwoRound;
		int[] tries = {columnPlayed2, columnPlayed2-1,columnPlayed2+1,columnPlayed2-2,columnPlayed2+2,columnPlayed2-3,columnPlayed2+3,columnPlayed2-4,columnPlayed2+4,columnPlayed2-5,columnPlayed2+5, columnPlayed2-6 , columnPlayed2+6};
		for(int i=0 ; i<tries.length ; i++)
			if(canBePlayed(tries[i],c))
				return tries[i];

		return -1; // DON'T FORGET TO CHANGE THE RETURN
	}

	public static boolean canBePlayed(int column,Configuration c){
		if(column>6 || column< 0)
				return false;
		for(int i=0; i<6; i++){
			if(c.board[column][i] ==0)
				return true;
		}
		return false;
	}
}
