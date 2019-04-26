
public class Entity {
	
	//needed variables
	private int ID;
	private int R;
	private int G;
	private int B;
	private boolean hasDescription;
	private String description = "";
	
	
	//constructor 1
	public Entity(int ID, int R, int G, int B)
	{
		this.ID = ID;
		this.R = R;
		this.G = G;
		this.B = B;
	}
	
	
	//constructor 1, with description
	public Entity(int ID, int R, int G, int B, String description)
	{
		this.ID = ID;
		this.R = R;
		this.G = G;
		this.B = B;
		this.description = description;
	}
	
	
	//GETTERS
	public boolean hasDescription()
	{
		return hasDescription;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public int getID()
	{
		return ID;
	}
	
	public int getR()
	{
		return R;
	}
	
	public int getG()
	{
		return G;
	}
	
	public int getB()
	{
		return B;
	}
}
