

public class TileIntersect {

	//needed variables
	public int ID1;
	public int ID2;
	public int intervalStart;
	//directional variables
	public int up;
	public int down;
	public int left;
	public int right;
	public int upInLeft;
	public int upInRight;
	public int downInLeft;
	public int downInRight;
	public int upOutLeft;
	public int upOutRight;
	public int downOutLeft;
	public int downOutRight;
	
	
	public TileIntersect(int IDTile1, int IDTile2, int intervalStart)
	{
		this.intervalStart = intervalStart;
		this.ID1 = IDTile1;
		this.ID2 = IDTile2;

		this.up = intervalStart ++;
		this.down = intervalStart ++;
		this.left = intervalStart ++;
		this.right = intervalStart ++;
		this.upInLeft = intervalStart ++;
		this.upInRight = intervalStart ++;
		this.downInLeft = intervalStart ++;
		this.downInRight = intervalStart ++;
		this.upOutLeft = intervalStart ++;
		this.upOutRight = intervalStart ++;
		this.downOutLeft = intervalStart ++;
		this.downOutRight = intervalStart;
	}
	
	
	//check if the two IDs are the ones in this intersection object
	public boolean existsID(int ID1, int ID2)
	{
		 if(ID1 == this.ID1 && ID2 == this.ID2) return true;
		 if(ID2 == this.ID1 && ID1 == this.ID2) return true;
		 return false;
	}
	
	
	//check if the specific ID is the first parameter ID in this intersection object
	public boolean isFirst(int ID)
	{
		if(ID == this.ID1) return true;
		return false;
	}
}
