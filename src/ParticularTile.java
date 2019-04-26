

public class ParticularTile {
	
	//needed variables
	private int ID;
	private int x;
	private int y;
	
	public ParticularTile(int ID, int x, int y)
	{
		this.ID = ID;
		this.x = x;
		this.y = y;
	}
	
	//getters
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getID()
	{
		return ID;
	}
}
