
public class Shelf {
	
	protected int height;
	protected int availableLength;
	protected int totalLength;
	protected Box firstBox;
	protected Box lastBox;

	public Shelf(int height, int totalLength){
		this.height = height;
		this.availableLength = totalLength;
		this.totalLength = totalLength;
		this.firstBox = null;
		this.lastBox = null;
	}
	
	protected void clear(){
		availableLength = totalLength;
		firstBox = null;
		lastBox = null;
	}
	
	public String print(){
		String result = "( " + height + " - " + availableLength + " ) : ";
		Box b = firstBox;
		while(b != null){
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n";
		return result;
	}
	
	/**
	 * Adds a box on the shelf. Here we assume that the box fits in height and length on the shelf.
	 * @param b
	 */
	public void addBox(Box b){
		//ADD YOUR CODE HERE
		b.next=b.previous=null;
		if(firstBox == null){
			firstBox = lastBox = b;
		}else{
			lastBox.next = b;
			b.previous = lastBox;
			lastBox = b;
		}
		availableLength -= b.length;
	}
	
	/**
	 * If the box with the identifier is on the shelf, remove the box from the shelf and return that box.
	 * If not, do not do anything to the Shelf and return null.
	 * @param identifier
	 * @return
	 */
	public Box removeBox(String identifier){
		Box currentBox = firstBox;
		while(currentBox != null){
			if(currentBox.id.equals(identifier)){
				if(currentBox.previous != null){
					(currentBox.previous).next=currentBox.next;
				}else{
					firstBox = currentBox.next;
				}
				if(currentBox.next !=null){
					(currentBox.next).previous = currentBox.previous;
				}else{
					lastBox = currentBox.previous;
				}
				currentBox.next = currentBox.previous = null;
				availableLength += currentBox.length;
				return currentBox;
			}else{
				currentBox = currentBox.next;
			}
		}
		return null;
	}
	
}
