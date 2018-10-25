
import javax.lang.model.util.ElementScanner6;


public class Warehouse{

	protected Shelf[] storage;
	protected int nbShelves;
	public Box toShip;
	public UrgentBox toShipUrgently;
	static String problem = "problem encountered while performing the operation";
	static String noProblem = "operation was successfully carried out";
	
	public Warehouse(int n, int[] heights, int[] lengths){
		this.nbShelves = n;
		this.storage = new Shelf[n];
		for (int i = 0; i < n; i++){
			this.storage[i]= new Shelf(heights[i], lengths[i]);
		}
		this.toShip = null;
		this.toShipUrgently = null;
	}
	
	public String printShipping(){
		Box b = toShip;
		String result = "not urgent : ";
		while(b != null){
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n" + "should be already gone : ";
		b = toShipUrgently;
		while(b != null){
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n";
		return result;
	}
	
 	public String print(){
 		String result = "";
		for (int i = 0; i < nbShelves; i++){
			result += i + "-th shelf " + storage[i].print();
		}
		return result;
	}
	
 	public void clear(){
 		toShip = null;
 		toShipUrgently = null;
 		for (int i = 0; i < nbShelves ; i++){
 			storage[i].clear();
 		}
 	}
 	
 	/**
 	 * initiate the merge sort algorithm
 	 */
	public void sort(){
		mergeSort(0, nbShelves -1);
	}
	
	/**
	 * performs the induction step of the merge sort algorithm
	 * @param start
	 * @param end
	 */
	protected void mergeSort(int start, int end){
		//ADD YOUR CODE HERE
		if(start < end){
			mergeSort(start,(start+end)/2);
			mergeSort((start+end)/2+1, end);
			merge(start,(start+end)/2,end);
		}
	}
	
	/**
	 * performs the merge part of the merge sort algorithm
	 * @param start
	 * @param mid
	 * @param end
	 */
	protected void merge(int start, int mid, int end){
		//ADD YOUR CODE HERE
		Shelf[] L = new Shelf[-start+mid+2];
		Shelf[] R = new Shelf[-mid+end+1];
		L[-start+mid+1] = new Shelf(2147483647,2147483647);
		R[-mid+end] = new Shelf(2147483647,2147483647);
		for(int n=0; n<-start+mid+1 ; n++)
			L[n] = storage[start+n];
		for(int n=0; n<end-mid; n++)
			R[n] = storage[mid+n+1];
		int q=0;
		int p=0;
		for(int k=start ; k<=end;k++){
			if(L[q].height<R[p].height){
				storage[k]=L[q];
				q++;
			}else{
				storage[k]=R[p];
				p++;
			}
		}
		
	}
	
	/**
	 * Adds a box is the smallest possible shelf where there is room available.
	 * Here we assume that there is at least one shelf (i.e. nbShelves >0)
	 * @param b
	 * @return problem or noProblem
	 */
	public String addBox (Box b){
		//ADD YOUR CODE HERE
		int k;
		boolean foundShelf = false;
		b.next = b.previous = null;
		for(k=0 ;k<nbShelves ;k++){
			if(b.height<=storage[k].height)
				if(b.length<=storage[k].availableLength){
					foundShelf = true;
					break;
				}
		}
		if(foundShelf){
			storage[k].addBox(b);
			return noProblem;
		}
		return problem;
	}
	
	/**
	 * Adds a box to its corresponding shipping list and updates all the fields
	 * @param b
	 * @return problem or noProblem
	 */
	public String addToShip (Box b){
		//ADD YOUR CODE HERE
		Box temp = new UrgentBox(1,1,"");
		Box temp2 = new Box(1,1,"");
		if(temp.getClass().equals(b.getClass())){
			UrgentBox b1 = (UrgentBox) b;
			if(toShipUrgently == null)
				toShipUrgently =  b1;
			else{
				toShipUrgently.previous = b1;
				b1.next = toShipUrgently;
				toShipUrgently =  b1;
			}
			return noProblem;
		}else{
			if(temp2.getClass().equals(b.getClass())){
				if(toShip == null){
					toShip = b ;
				}
				else{
					toShip.previous = b;
					b.next = toShip;
					toShip = b;
				}
			return noProblem;
			}else
				return problem;
		}
	}
	
	/**
	 * Find a box with the identifier (if it exists)
	 * Remove the box from its corresponding shelf
	 * Add it to its corresponding shipping list
	 * @param identifier
	 * @return problem or noProblem
	 */
	public String shipBox (String identifier){
		//ADD YOUR CODE HERE
		Box current = null;
		int n=0;
		for(n=0 ; n<storage.length; n++){
			current = storage[n].removeBox(identifier);
			if(current != null)
				break;
		}
		if(current != null){
			return addToShip(current);
		}
		else
			return problem;
	}
	
	/**
	 * if there is a better shelf for the box, moves the box to the optimal shelf.
	 * If there are none, do not do anything
	 * @param b
	 * @param position
	 */
	public void moveOneBox (Box b, int position){
		//ADD YOUR CODE HERE
		for(int n=0 ; n<position; n++ ){
			if(storage[n].height >= b.height)
				if(storage[n].availableLength >= b.length){
					storage[position].removeBox(b.id);
					storage[n].addBox(b);
					return;
				}
		}
	}
	
	/**
	 * reorganize the entire warehouse : start with smaller shelves and first box on each shelf.
	 */
	public void reorganize (){
		//ADD YOUR CODE HERE
		Box current = null;
		for(int n=1 ; n<nbShelves; n++){
			current = storage[n].firstBox;
			while(current!=null){
				Box next = current.next;
				moveOneBox(current, n);
				current=next;
			}
		}
	}
}