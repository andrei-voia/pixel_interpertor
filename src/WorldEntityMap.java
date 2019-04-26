import java.awt.image.BufferedImage;
import java.util.List;

public class WorldEntityMap {

	
	//initializes the tiles that should exist in the map and their respective RGB codes
	private static List<Entity> initializeCreatures(String relativePath)
	{
		return WorldTileMap.initializeTiles(relativePath);
	}
	
	
	private static int[][] constructCreatureMap(BufferedImage image, List<Entity> tileList)
	{
		return WorldTileMap.constructMap(image, tileList);
	}
	
	
	public static void run()
	{
		System.out.println("*EntityMap starting...");
		//load the image you want to transform in the LVL text
		BufferedImage image = WorldTileMap.loadImage("map.png");

		//get the current path and initialize all the existing and needed files that are found there
		String path = WorldTileMap.getCurrentPath();
		List<Entity> tileList = initializeCreatures(path + "entity_code.txt");
		
		int creatureMap[][] = constructCreatureMap(image, tileList);
		WorldTileMap.saveMap(creatureMap, path + "entitiesMap1.lvl");
		System.out.println("*EntityMap ended...");
	}
}
