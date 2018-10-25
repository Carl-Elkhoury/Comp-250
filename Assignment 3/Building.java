

public class Building {

	OneBuilding data;
	Building older;
	Building same;
	Building younger;
	
	public Building(OneBuilding data){
		this.data = data;
		this.older = null;
		this.same = null;
		this.younger = null;
	}
	
	public String toString(){
		String result = this.data.toString() + "\n";
		if (this.older != null){
			result += "older than " + this.data.toString() + " :\n";
			result += this.older.toString();
		}
		if (this.same != null){
			result += "same age as " + this.data.toString() + " :\n";
			result += this.same.toString();
		}
		if (this.younger != null){
			result += "younger than " + this.data.toString() + " :\n";
			result += this.younger.toString();
		}
		return result;
	}
	
	public Building addBuilding (OneBuilding b){


		if(data.yearOfConstruction == b.yearOfConstruction){
			if(data.height < b.height){
				Building insert = new Building(data);
				data = b;
				insert.same = same;
				same = insert;
				return this;
			}
			if(same == null){
				same = new Building(b);
				return this;
			}
			same.addBuilding(b);
			return this;
		}
		
		if(data.yearOfConstruction > b.yearOfConstruction){
			if(older == null){
				older = new Building(b);
				return this;
			}
			older.addBuilding(b);
			return this;
		}else
			if(younger == null){
				younger = new Building(b);
				return this;
			}
			else{
				younger.addBuilding(b);
				return this;
			}
	}
	
	public Building addBuildings (Building b){

		
		if(b.older != null)
			addBuildings(b.older);
		if(b.same != null)
			addBuildings(b.same);
		if(b.younger !=null)
			addBuildings(b.younger);

		addBuilding(b.data);
		
		return this; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
	}
	
	public Building removeBuilding (OneBuilding b){
		Building current =this;
		Building previous = null;
		int a = -2;
		while(current != null){
			if(current.data.equals(b))
				break;
			previous = current;
			if(current.data.yearOfConstruction == b.yearOfConstruction){
				current = current.same;
				//System.out.println("moving same");
				a=0;
				continue;
			}
			if(current.data.yearOfConstruction > b.yearOfConstruction){
				current = current.older;
				//System.out.println("moving older");
				a=-1;
				continue;
			}
			if(current.data.yearOfConstruction < b.yearOfConstruction){
				current = current.younger;
				a=1;
				continue;
			}

		}
		// System.out.print("");
		if(current == null)
			return this;
		
		if(previous == null){
			if(current.same != null){
				current.same.older = current.older;
				current.same.younger = current.younger;
				return current.same;
			}
			if(current.older != null ){
				current.older.addBuildings(current.younger);
				return current.older;
			}
			if(current.younger !=null){
			    return current.younger;
			}

		}
		
		switch(a){
			case 0:
				previous.same = current.same;
				return this;
			case -1:
				if(current.same != null){
					current.same.older = current.older;
					current.same.younger = current.younger;
					previous.older = current.same;
					return this;
				}
				if(current.older != null ){
					previous.older = current.older;
					previous.older.addBuildings(current.younger);
					return this;
				}
				if(current.younger !=null){
					previous.older = current.younger;
					return this;
				}
			case 1:
				if(current.same != null){
					current.same.older = current.older;
					current.same.younger = current.younger;
					previous.younger = current.same;
					return this;
				}
				if(current.older != null ){
					previous.younger = current.older;
					previous.younger.addBuildings(current.younger);
					return this;
				}
				if(current.younger !=null){
					previous.younger = current.younger;
					return this;
				}
		}
		return this;
		
		
	}
	
	public int oldest(){
		Building current = this;
		while(current.older != null)
			current = current.older;
		return current.data.yearOfConstruction;
	}
	
	public int highest(){
		int maxHeight = 0;
		maxHeight =data.height;
		int oldestHeight = 0;
		int youngestHeight =0;
		int sameHeight = 0;

		if(older != null){
			oldestHeight = older.highest();
			if(maxHeight < oldestHeight)
				maxHeight = oldestHeight;
		}
		if(younger != null){
			youngestHeight = younger.highest();
			if(maxHeight < youngestHeight)
				maxHeight = youngestHeight;
		}
		if(same != null){
			sameHeight = same.highest();
			if(maxHeight < sameHeight)
				maxHeight = sameHeight;
		}
		return maxHeight;
		
	}
	
	public OneBuilding highestFromYear (int year){
		OneBuilding maxHeight = null;
		if(data.yearOfConstruction == year){
			maxHeight = data;
		}
		if(older != null){
			OneBuilding nextHeight = older.highestFromYear(year);
			if(maxHeight == null || nextHeight !=null && nextHeight.height > maxHeight.height)
				maxHeight = nextHeight;
		}
		if(same != null){
			OneBuilding nextHeight = same.highestFromYear(year);
			if(maxHeight == null || nextHeight !=null && nextHeight.height > maxHeight.height )
				maxHeight = nextHeight;
		}
		if(younger != null){
			OneBuilding nextHeight = younger.highestFromYear(year);
			if(maxHeight == null || nextHeight !=null && nextHeight.height > maxHeight.height)
				maxHeight = nextHeight;
		}
		return maxHeight; 
	}
	
	public int numberFromYears (int yearMin, int yearMax){
		if(yearMin > yearMax)
			return 0;

		int totalNumber = 0;
		if(data.yearOfConstruction < yearMax+1 && data.yearOfConstruction > yearMin-1)
			totalNumber = 1;
		
		if(older != null){
			totalNumber += older.numberFromYears(yearMin, yearMax);
		}
		if(younger != null){
			totalNumber += younger.numberFromYears(yearMin, yearMax);
		}
		if(same != null){
			totalNumber += same.numberFromYears(yearMin, yearMax);
		}
		return totalNumber;
	}
	
	public int[] costPlanning (int nbYears){
		int[] result= new int[nbYears];

		if(data.yearForRepair >= 2018 && data.yearForRepair< 2018+nbYears )
			result[data.yearForRepair - 2018] += data.costForRepair;
		
		if(older != null){
			int[] nextResult = older.costPlanning(nbYears);
			for(int i=0 ; i<nbYears ; i++)
				result[i]+=nextResult[i];
		}
		if(younger != null){
			int[] nextResult = younger.costPlanning(nbYears);
			for(int i=0 ; i<nbYears ; i++)
				result[i]+=nextResult[i];
		}
		if(same != null){
			int[] nextResult = same.costPlanning(nbYears);
			for(int i=0 ; i<nbYears ; i++)
				result[i]+=nextResult[i];
		}
	return result;
	}
	
}
