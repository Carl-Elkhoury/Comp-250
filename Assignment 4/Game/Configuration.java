
// package assignment4Game;

import javax.lang.model.util.ElementScanner6;



public class Configuration {
	
	public int[][] board;
	public int[] available;
	boolean spaceLeft;
	
	public Configuration(){
		board = new int[7][6];
		available = new int[7];
		spaceLeft = true;
	}
	
	public void print(){
		System.out.println("| 0 | 1 | 2 | 3 | 4 | 5 | 6 |");
		System.out.println("+---+---+---+---+---+---+---+");
		for (int i = 0; i < 6; i++){
			System.out.print("|");
			for (int j = 0; j < 7; j++){
				if (board[j][5-i] == 0){
					System.out.print("   |");
				}
				else{
					System.out.print(" "+ board[j][5-i]+" |");
				}
			}
			System.out.println();
		}
	}
	
	public void addDisk (int index, int player){
		// ADD YOUR CODE HERE
		for(int n=0; n<6 ; n++){
			if(board[index][n] == 0){
				board[index][n]=player;
				available[index]++;
				break;
			}
		}
		boolean passed = false;
		for(int n=0 ; n<6 ;n++)
			if(available[n] !=6){
				passed = true;
				break;
			}
		spaceLeft = passed;
			

	}
	
	public boolean isWinning (int lastColumnPlayed, int player){
		// ADD YOUR CODE HERE
		int n;
		for(n=5; n>-2 ; n-- ){
			if(n == -1)
				return false;
			if(board[lastColumnPlayed][n] == player){
				break;
			}
		}
		int count = 0;
		for(int i=n-3; i<n+4; i++){
			if(i<0 || i>5){
				continue;
			}
			if(board[lastColumnPlayed][i] == player)
				count++;
			else
				count = 0;

			if(count == 4)
				return true;
		}
		count = 0;
		for(int i=lastColumnPlayed-3 ; i<lastColumnPlayed+4 ; i++){
			if(i<0 || i>6){
				continue;
			}
			if(board[i][n] == player)
				count++;
			else
				count = 0;

			if(count == 4)
				return true;
		}
		count=0;
		int count2 = 0;
		for(int i=-3; i<4 ; i++){
			if(lastColumnPlayed+i >-1 && n+i >-1 && lastColumnPlayed+i<7 && n+i<6 )
				if(board[lastColumnPlayed+i][n+i] == player)
					count++;
				else
					count=0;
			
			if(lastColumnPlayed+i> -1 && n-i>-1 && lastColumnPlayed+i<7 && n-i<6)
				if(board[lastColumnPlayed+i][n-i] == player)
					count2++;
				else
					count2=0;
			if(count2==4 || count==4)
				return true;
			 
		}


		return false; // DON'T FORGET TO CHANGE THE RETURN
	}
	
	public int canWinNextRound (int player){
		for(int column=0; column<7; column++){
			int row;
			for(row=0 ; row<6 ; row++){
				if(board[column][row]==0)
					break;
			}
			if(row == 6)
				continue;

			addDisk(column, player);
			if(isWinning(column, player)){
				board[column][row]=0;
				available[column]--;
				return column;
			}
			board[column][row]=0;
			available[column]--;
		}
		return -1; // DON'T FORGET TO CHANGE THE RETURN
	}
	
	public int canWinTwoTurns (int player){
		int oponent;
		if(player == 1)
			oponent = 2;
		else
			oponent = 1;
		
		for(int column=0; column<7; column++){
			int row;
			for(row=0 ; row<6 ; row++){
				if(board[column][row]==0)
					break;
			}
			if(row == 6)
				continue;

			addDisk(column, player);
			if(canWinNextRound(oponent) != -1){
				board[column][row]=0;
				available[column]--;
				continue;
			}
			boolean passed = true;
			for(int index=0; index<7; index++){
				int row2;
				for(row2=0 ; row2<6 ; row2++){
					if(board[index][row2]==0)
						break;
				}
				if(row2 == 6)
					continue;
				addDisk(index, oponent);
				if(canWinNextRound(player) == -1){
					board[index][row2]=0;
					available[index]--;
					passed=false;
					break;
				}
				board[index][row2]=0;
				available[index]--;
			}
				board[column][row]=0;
				available[column]--;
				if(passed)
					return column;
		}
		return -1; // DON'T FORGET TO CHANGE THE RETURN
	}
	
}
