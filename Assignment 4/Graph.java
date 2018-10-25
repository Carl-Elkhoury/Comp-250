package assignment4Graph;

public class Graph {
	
	boolean[][] adjacency;
	int nbNodes;
	
	public Graph (int nb){
		this.nbNodes = nb;
		this.adjacency = new boolean [nb][nb];
		for (int i = 0; i < nb; i++){
			for (int j = 0; j < nb; j++){
				this.adjacency[i][j] = false;
			}
		}
	}
	
	public void addEdge (int i, int j){
		adjacency[i][j] = adjacency[j][i] = true;
	}
	
	public void removeEdge (int i, int j){
		adjacency[i][j] = adjacency[j][i] = false;
	}
	
	public int nbEdges(){
		int edges =0;
		int edgesToDevide = 0;
		for(int i =0 ; i<adjacency.length ; i++)
			for(int j = 0; j<adjacency.length; j++)
				if(adjacency[i][j] == true)
					if(i==j){
						edges++;
					}else{
						edgesToDevide++;
					}				
		return edges+ edgesToDevide/2; // DON'T FORGET TO CHANGE THE RETURN
	}
	
	public boolean cycle(int start){
		// boolean[][] checkList = new boolean[nbNodes][nbNodes];
		// for(int j = 0 ; j<nbNodes ;j++)
		// 	checkList[j][j]=true;

		// return cycleRec(start, checkList, start, 0); // DON'T FORGET TO CHANGE THE RETURN
		if(recursivePath(start, start,0,new boolean[nbNodes],start)>nbNodes)
			return false;
		return true;
	}
	
	// private boolean cycleRec(int row, boolean[][] checkList , int start,int layer){
	// 	if(adjacency[row][start] && layer>2 && !checkList[row][start])
	// 		return true;

	// 	for(int j = 0 ; j<nbNodes; j++){
	// 		if(checkList[row][j] == false){
	// 			checkList[row][j] = true;	
	// 			if(adjacency[row][j] ==true)
	// 				if(cycleRec(j,checkList,start,++layer))
	// 					return true;
	// 		}
	// 	}
	// 	return false; // DON'T FORGET TO CHANGE THE RETURN
	// }

	public int shortestPath(int start, int end){
		// ADD YOUR CODE HERE
		boolean[] checklist =new boolean[nbNodes];
		if(start != end)
			checklist[start] =true;
		else{
			if(adjacency[start][start])
				return 1;
		}
			return recursivePath(start, end,0, checklist,start);
		// }else{
		// 	return selfRecursivePath(start, end, 0, new boolean[nbNodes][nbNodes], start);
		// }
	}
	
	public int recursivePath(int start, int end, int layer, boolean[] checklist , int beginning){

		int result;
		if(start == end && layer != 0){
			result = layer;
			return result;
		}else
			result = nbNodes +1;
		for(int j=0; j<nbNodes ; j++){
			if(j!=start && adjacency[start][j] && !checklist[j]){
				 if(layer < 2 && j== beginning)
				 	continue;
				checklist[j] = true;
				int nextResult = recursivePath(j, end, layer+1, checklist.clone(), beginning);
				if(nextResult<result )
					result = nextResult;
			}
		}
		return result;
	}
	public int selfRecursivePath(int start, int end, int layer, boolean[][] checklist , int beginning){
		int result;
			if(start == end && layer != 0){
				result = layer;
				return result;
			}else
				result = nbNodes +1;
			for(int j=0; j<nbNodes ; j++){
				if(j!=start && adjacency[start][j] && !checklist[start][j]){
					checklist[start][j] = true;
					checklist[j][start] = true;
					int nextResult = selfRecursivePath(j, end, layer+1, checklist.clone(), beginning);
					if(nextResult<result )
						result = nextResult;
			}
		}
		return result;
	}
}

