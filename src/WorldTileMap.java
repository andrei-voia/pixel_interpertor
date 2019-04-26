
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class WorldTileMap {
	
	
	//saves the end map, ready to be used in the game
	static void saveMap(int[][] map, String absolutePath)
	{
		try 
		{
			//create the output file
			PrintStream out_stream = new PrintStream(new FileOutputStream(absolutePath));
				
			//write the width and height of the map
			out_stream.println(map[0].length + " " + map.length);
			
			//go through the map and save it
			for(int y = 0; y < map.length; y ++)
			{
				for(int x = 0; x < map[0].length; x ++)
				{
					out_stream.print(map[x][y] + " ");
				}	
				out_stream.println();
			}	
			//must close it
			out_stream.close();								
		} 
		
		catch(IOException e) 
		{
				System.out.println("Error to SAVE file operation.");
				System.exit(-1);
		}		
	}
	
	
	//saves the error possibilities, helping the user understand and change the possible errors locations
	private static void saveErrorPossibility(Map<Integer, Integer> countIDs, String absolutePath)
	{
		try 
		{
			//create the output file
			PrintStream out_stream = new PrintStream(new FileOutputStream(absolutePath));
				
			//write the title of the text
			out_stream.println("POSSIBLE ERROR LOCATIONS:");
			//write a quick note to help the user understand what this file does to help him
			out_stream.println("Note: This usually happens where there are 2 ore more(n) different tiles around "
					+ "the central tile, that will have to change in one of those n tiles around it, resulting in "
					+ "some incorrect transformations.");
			out_stream.println();
			
			//write every possible error reflecting on the map that you can see
			for (Map.Entry<Integer, Integer> entry : countIDs.entrySet())
			{
				out_stream.println("- on coordinate X: " + entry.getKey() + " / Y: " + entry.getValue());
			}
			
			//print the number of the possible errors
			out_stream.println();
			out_stream.println("TOTAL POSSIBLE ERRORS: " + countIDs.size());
			
			//must close it
			out_stream.close();								
		} 
		
		catch(IOException e) 
		{
				System.out.println("Error to SAVE error possiblities.");
				System.exit(-121);
		}		
	}
	
	
	//saves the error possibilities, helping the user understand and change the possible errors locations
	private static void saveErrorPossibility(Map<Integer, Integer> countIDs, List<ParticularTile> tileParticular, String absolutePath)
	{
		try 
		{
			//create the output file
			PrintStream out_stream = new PrintStream(new FileOutputStream(absolutePath));
				
			//write the title of the text
			out_stream.println("POSSIBLE ERROR LOCATIONS:");
			//write a quick note to help the user understand what this file does to help him
			out_stream.println("Note: This usually happens where there are 2 ore more(n) different tiles around "
					+ "the central tile, that will have to change in one of those n tiles around it, resulting in "
					+ "some incorrect transformations.");
			out_stream.println("*Legend:  <X> = <case mplemented>");
			out_stream.println();
			
			//write every possible error reflecting on the map that you can see
			for (Map.Entry<Integer, Integer> entry : countIDs.entrySet())
			{
				out_stream.print("- on coordinate X: " + entry.getKey() + " / Y: " + entry.getValue());
				
				for(ParticularTile particulars: tileParticular)
					if(particulars.getX() == entry.getKey() && particulars.getY() == entry.getValue())
					{
						out_stream.print("    <X>  <implemented>");
						break;
					}
						
				out_stream.println();
			}
			
			//print the number of the possible errors
			out_stream.println();
			out_stream.println("TOTAL POSSIBLE ERRORS: " + countIDs.size());
			
			//must close it
			out_stream.close();								
		} 
		
		catch(IOException e) 
		{
				System.out.println("Error to SAVE error possiblities.");
				System.exit(-121);
		}		
	}

	
	//this counts the pixel around the central pixel, that are different and only when the central pixel is the one that's
	//changing regarding to the near pixels
	private static int countNearIdenticalIDs(int[][] map, int x, int y, List<TileIntersect> tileIntersect)
	{
		List<Integer> differentTiles = new LinkedList<Integer>();
		
		differentTiles.add(map[x][y]);
		
		//go through every intersection and check every pixel for possible combinations
		for(TileIntersect intersections: tileIntersect)
		{
			//int auxiliray_counter = convert_to_intersection(map, x, y, intersections);
			//if(auxiliray_counter != -1) 
			//	if(differentTiles.contains(auxiliray_counter) == false) differentTiles.add(auxiliray_counter);
			
			if(intersections.existsID(map[x][y], map[x+1][y]) && intersections.isFirst(map[x][y]))
				if(differentTiles.contains(map[x+1][y]) == false) differentTiles.add(map[x+1][y]);
			
			if(intersections.existsID(map[x][y], map[x-1][y]) && intersections.isFirst(map[x][y]))
				if(differentTiles.contains(map[x-1][y]) == false) differentTiles.add(map[x-1][y]);
			
			if(intersections.existsID(map[x][y], map[x][y+1]) && intersections.isFirst(map[x][y]))
				if(differentTiles.contains(map[x][y+1]) == false) differentTiles.add(map[x][y+1]);
			
			if(intersections.existsID(map[x][y], map[x][y-1]) && intersections.isFirst(map[x][y]))
				if(differentTiles.contains(map[x][y-1]) == false) differentTiles.add(map[x][y-1]);
			
			if(intersections.existsID(map[x][y], map[x+1][y+1]) && intersections.isFirst(map[x][y]))
				if(differentTiles.contains(map[x+1][y+1]) == false) differentTiles.add(map[x+1][y+1]);
			
			if(intersections.existsID(map[x][y], map[x-1][y+1]) && intersections.isFirst(map[x][y]))
				if(differentTiles.contains(map[x-1][y+1]) == false) differentTiles.add(map[x-1][y+1]);
			
			if(intersections.existsID(map[x][y], map[x+1][y-1]) && intersections.isFirst(map[x][y]))
				if(differentTiles.contains(map[x+1][y-1]) == false) differentTiles.add(map[x+1][y-1]);
			
			if(intersections.existsID(map[x][y], map[x-1][y-1]) && intersections.isFirst(map[x][y]))
				if(differentTiles.contains(map[x-1][y-1]) == false) differentTiles.add(map[x-1][y-1]);
		}

		return differentTiles.size();
	}
	
	
	//makes test ready for w_possible_errors
	private static Map<Integer, Integer> calculateErrorPossibility(int[][] map, List<TileIntersect> tileIntersect)
	{
		Map<Integer, Integer> countIDs = new HashMap<Integer, Integer>();
		
		//go through the map without the margins
		for(int y = 0; y < map.length; y ++)
			for(int x = 0; x < map[0].length; x ++)
				//check how many different pixels are around the current point in a range of 1
				if(x>0 && y>0 && x<map[0].length-1 && y<map.length-1)
				{
					int tileNeighbours = countNearIdenticalIDs(map, x, y, tileIntersect);
					if(tileNeighbours > 2) countIDs.put(x, y);
				}
		
		return countIDs;
	}
	
	
	//calculate the intersections of the tile map between 2 IDs that have an intersection, saved in the list
	private static int[][] calculateSmooth(int[][] map, List<TileIntersect> tileIntersect)
	{
		int[][] newMap = new int[map[0].length][map.length];

		//make a copy of the original matrix
		for(int y = 0; y < newMap.length; y ++)
			for(int x = 0; x < newMap[0].length; x ++)
				newMap[x][y] = map[x][y];

		//MAYBE FIND A BETTER MORE OO implementation in the future here ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//go through the map without the margins
		for(int y = 0; y < newMap.length; y ++)
		{
			for(int x = 0; x < newMap[0].length; x ++)
			{
				//go through every tile intersection
				for(TileIntersect intersections: tileIntersect)
				{
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					//if the current pixel is at the edge of the matrix, then check specially for them
					if(x==0||y==0||x==newMap[0].length-1||y==newMap.length-1)
					{
						//UP
						if(y==0 && x>0 && x<newMap[0].length-1)
						{
							//check up
							if(intersections.existsID(map[x][y], map[x][y+1]))
								if(intersections.isFirst(map[x][y])) 
									if(map[x][y] == intersections.ID1 && map[x][y+1] == intersections.ID2)
									{
										newMap[x][y] = intersections.up;
										continue;
									}
							
							//check UP RIGHT INDOOR CORNER
							if(intersections.existsID(map[x][y], map[x-1][y+1]))
								if(intersections.isFirst(map[x][y])) 
									if(map[x-1][y] == intersections.ID1 && map[x][y+1] == intersections.ID1 && map[x-1][y+1] == intersections.ID2)
									{
										newMap[x][y] = intersections.upInRight;
										continue;
									}
							
							//check UP LEFT INDOOR CORNER
							if(intersections.existsID(map[x][y], map[x+1][y+1]))
								if(intersections.isFirst(map[x][y])) 
									if(map[x+1][y] == intersections.ID1 && map[x][y+1] == intersections.ID1 && map[x+1][y+1] == intersections.ID2)
									{
										newMap[x][y] = intersections.upInLeft;
										continue;
									}
						}
							
						//DOWN
						if(y==newMap.length-1 && x>0 && x<newMap[0].length-1)
						{
							//check DOWN
							if(intersections.existsID(map[x][y-1], map[x][y]))
								if(intersections.isFirst(map[x][y])) 
									if(map[x][y-1] == intersections.ID2 && map[x][y] == intersections.ID1)
									{
										newMap[x][y] = intersections.down;
										continue;
									}
							
							//check DOWN LEFT INDOOR CORNER
							if(intersections.existsID(map[x][y], map[x+1][y-1]))
								if(intersections.isFirst(map[x][y]))
									if(map[x+1][y] == intersections.ID1 && map[x][y-1] == intersections.ID1 && map[x+1][y-1] == intersections.ID2)
									{
										newMap[x][y] = intersections.downInLeft;
										continue;
									}
							
							//check DOWN RIGHT INDOOR CORNER
							if(intersections.existsID(map[x][y], map[x-1][y-1]))
								if(intersections.isFirst(map[x][y]))
									if(map[x-1][y] == intersections.ID1 && map[x][y-1] == intersections.ID1 && map[x-1][y-1] == intersections.ID2)
									{
										newMap[x][y] = intersections.downInRight;
										continue;
									}
						}
						
						//UP AND DOWN
						if((y==0 || y==newMap.length-1) && x>0 && x<newMap[0].length-1)
						{
							//check LEFT
							if(intersections.existsID(map[x-1][y], map[x+1][y]))
								if(intersections.isFirst(map[x][y])) 
									if(map[x-1][y] == intersections.ID1 && map[x+1][y] == intersections.ID2)
									{
										newMap[x][y] = intersections.left;
										continue;
									}
							
							//check RIGHT
							if(intersections.existsID(map[x-1][y], map[x+1][y]))
								if(intersections.isFirst(map[x][y])) 
									if(map[x-1][y] == intersections.ID2 && map[x+1][y] == intersections.ID1)
									{
										newMap[x][y] = intersections.right;
										continue;
									}
						}
						
						//LEFT
						if(x==0 && y>0 && y<newMap.length-1)
						{
							//check LEFT
							if(intersections.existsID(map[x][y], map[x+1][y]))
								if(intersections.isFirst(map[x][y])) 
									if(map[x][y] == intersections.ID1 && map[x+1][y] == intersections.ID2)
									{
										newMap[x][y] = intersections.left;
										continue;
									}
							
							//check DOWN LEFT INDOOR CORNER
							if(intersections.existsID(map[x][y], map[x+1][y-1]))
								if(intersections.isFirst(map[x][y]))
									if(map[x+1][y] == intersections.ID1 && map[x][y-1] == intersections.ID1 && map[x+1][y-1] == intersections.ID2)
									{
										newMap[x][y] = intersections.downInLeft;
										continue;
									}
							
							//check UP LEFT INDOOR CORNER
							if(intersections.existsID(map[x][y], map[x+1][y+1]))
								if(intersections.isFirst(map[x][y])) 
									if(map[x+1][y] == intersections.ID1 && map[x][y+1] == intersections.ID1 && map[x+1][y+1] == intersections.ID2)
									{
										newMap[x][y] = intersections.upInLeft;
										continue;
									}
						}
						
						//RIGHT
						if(x==newMap[0].length-1 && y>0 && y<newMap.length-1)
						{
							//check RIGHT
							if(intersections.existsID(map[x-1][y], map[x][y]))
								if(intersections.isFirst(map[x][y])) 
									if(map[x-1][y] == intersections.ID2 && map[x][y] == intersections.ID1)
									{
										newMap[x][y] = intersections.right;
										continue;
									}
							
							//check UP RIGHT INDOOR CORNER
							if(intersections.existsID(map[x][y], map[x-1][y+1]))
								if(intersections.isFirst(map[x][y])) 
									if(map[x-1][y] == intersections.ID1 && map[x][y+1] == intersections.ID1 && map[x-1][y+1] == intersections.ID2)
									{
										newMap[x][y] = intersections.upInRight;
										continue;
									}
							
							//check DOWN RIGHT INDOOR CORNER
							if(intersections.existsID(map[x][y], map[x-1][y-1]))
								if(intersections.isFirst(map[x][y]))
									if(map[x-1][y] == intersections.ID1 && map[x][y-1] == intersections.ID1 && map[x-1][y-1] == intersections.ID2)
									{
										newMap[x][y] = intersections.downInRight;
										continue;
									}
						}
						
						//LEFT AND RIGHT
						if((x==0 || x==newMap[0].length-1) && y>0 && y<newMap.length-1)
						{
							//check UP
							if(intersections.existsID(map[x][y-1], map[x][y+1]))
								if(intersections.isFirst(map[x][y])) 
									if(map[x][y-1] == intersections.ID1 && map[x][y+1] == intersections.ID2)
									{
										newMap[x][y] = intersections.up;
										continue;
									}
							
							//check DOWN
							if(intersections.existsID(map[x][y-1], map[x][y+1]))
								if(intersections.isFirst(map[x][y])) 
									if(map[x][y-1] == intersections.ID2 && map[x][y+1] == intersections.ID1)
									{
										newMap[x][y] = intersections.down;
										continue;
									}
						}
						
						continue;
					}
					
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					//now we check all the pixels on the rest of the map, basically 99% of the smoothing
					int aux_new_map = convert_to_intersection(map, x, y, intersections);
					//if it returns -1 then it means it doesn't have to be changed, else change it to the return value
					if(aux_new_map != -1) newMap[x][y] = aux_new_map;
				}
			}	
		}

		return newMap;
	}
	
	
	//sets the tile to the respective transformation for specific intersection with a tile
	private static int convert_to_intersection(int[][] map, int x, int y, TileIntersect intersections)
	{
		//check UP LEFT OUTDOOR CORNER
		if(intersections.existsID(map[x-1][y], map[x+1][y]) && intersections.existsID(map[x][y-1], map[x][y+1]))
			if(intersections.isFirst(map[x][y]))
				if(map[x+1][y] == intersections.ID1 && map[x][y+1] == intersections.ID1 && map[x][y-1] == intersections.ID2 && map[x-1][y] == intersections.ID2)
				{
					return intersections.upOutLeft;
				}
		
		//check UP RIGHT OUTDOOR CORNER
		if(intersections.existsID(map[x-1][y], map[x+1][y]) && intersections.existsID(map[x][y-1], map[x][y+1]))
			if(intersections.isFirst(map[x][y]))
				if(map[x-1][y] == intersections.ID1 && map[x][y+1] == intersections.ID1 && map[x][y-1] == intersections.ID2 && map[x+1][y] == intersections.ID2)
				{
					return intersections.upOutRight;
				}
		
		//check DOWN LEFT OUTDOOR CORNER
		if(intersections.existsID(map[x-1][y], map[x+1][y]) && intersections.existsID(map[x][y-1], map[x][y+1]))
			if(intersections.isFirst(map[x][y]))
				if(map[x+1][y] == intersections.ID1 && map[x][y-1] == intersections.ID1 && map[x][y+1] == intersections.ID2 && map[x-1][y] == intersections.ID2)
				{
					return intersections.downOutLeft;
				}
		
		//check DOWN RIGHT OUTDOOR CORNER
		if(intersections.existsID(map[x-1][y], map[x+1][y]) && intersections.existsID(map[x][y-1], map[x][y+1]))
			if(intersections.isFirst(map[x][y]))
				if(map[x-1][y] == intersections.ID1 && map[x][y-1] == intersections.ID1 && map[x][y+1] == intersections.ID2 && map[x+1][y] == intersections.ID2)
				{
					return intersections.downOutRight;
				}
		
		//check UP
		if(intersections.existsID(map[x][y-1], map[x][y+1]))
			if(intersections.isFirst(map[x][y])) 
				if(map[x][y-1] == intersections.ID1 && map[x][y+1] == intersections.ID2)
				{
					return intersections.up;
				}
		
		//check DOWN
		if(intersections.existsID(map[x][y-1], map[x][y+1]))
			if(intersections.isFirst(map[x][y])) 
				if(map[x][y-1] == intersections.ID2 && map[x][y+1] == intersections.ID1)
				{
					return intersections.down;
				}
		
		//check LEFT
		if(intersections.existsID(map[x-1][y], map[x+1][y]))
			if(intersections.isFirst(map[x][y])) 
				if(map[x-1][y] == intersections.ID1 && map[x+1][y] == intersections.ID2)
				{
					return intersections.left;
				}
		
		//check RIGHT
		if(intersections.existsID(map[x-1][y], map[x+1][y]))
			if(intersections.isFirst(map[x][y])) 
				if(map[x-1][y] == intersections.ID2 && map[x+1][y] == intersections.ID1)
				{
					return intersections.right;
				}
		
		//check UP LEFT INDOOR CORNER
		if(intersections.existsID(map[x][y], map[x+1][y+1]))
			if(intersections.isFirst(map[x][y])) 
				if(map[x+1][y] == intersections.ID1 && map[x][y+1] == intersections.ID1 && map[x+1][y+1] == intersections.ID2)
				{
					return intersections.upInLeft;
				}
		
		//check UP RIGHT INDOOR CORNER
		if(intersections.existsID(map[x][y], map[x-1][y+1]))
			if(intersections.isFirst(map[x][y])) 
				if(map[x-1][y] == intersections.ID1 && map[x][y+1] == intersections.ID1 && map[x-1][y+1] == intersections.ID2)
				{
					return intersections.upInRight;
				}
		
		//check DOWN LEFT INDOOR CORNER
		if(intersections.existsID(map[x][y], map[x+1][y-1]))
			if(intersections.isFirst(map[x][y]))
				if(map[x+1][y] == intersections.ID1 && map[x][y-1] == intersections.ID1 && map[x+1][y-1] == intersections.ID2)
				{
					return intersections.downInLeft;
				}
		
		//check DOWN RIGHT INDOOR CORNER
		if(intersections.existsID(map[x][y], map[x-1][y-1]))
			if(intersections.isFirst(map[x][y]))
				if(map[x-1][y] == intersections.ID1 && map[x][y-1] == intersections.ID1 && map[x-1][y-1] == intersections.ID2)
				{
					return intersections.downInRight;
				}
		
		//tile doesn't have to be changed
		return -1;
	}
	
	
	//initialize the particular tiles that need to be changed in a specific location
	private static List<ParticularTile> initializeParticulars(String relativePath)
	{
		int countLines = 0;
		List<ParticularTile> particular = new LinkedList<ParticularTile>();
		
		//read the input text
		try(BufferedReader br = new BufferedReader(new FileReader(relativePath))) 
		{
			//go through line by line
		    for(String line; (line = br.readLine()) != null; ) 
		    {
		    	countLines ++;
		    	
		    	//skip the first line
		    	if(countLines == 1) continue;
		    	
		    	//separate the line by " " (space)
		    	String[] separated = line.split(" ");
		    	//check for errors
		    	if(separated.length < 3)
		    	{
		    		System.out.println("Error initializing particulars: line " + countLines + " has not enough arguments.");
		    		System.exit(-27);
		    	}
		    	
		    	//create new Tile and add it to the list, ignoring everything that is written after the first 3 values
		    	if(separated.length >= 3)
		    		particular.add(new ParticularTile(Integer.parseInt(separated[0]), Integer.parseInt(separated[1]), 
			    			Integer.parseInt(separated[2])));
		    }
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			e.printStackTrace();
			System.out.println("ERROR: PARTICULARS file not found in the specified path.");
			System.exit(-399);
		}
		
		return particular;
	}
	
	
	//initialize the intersections between 2 tiles and save them into a list
	private static List<TileIntersect> initializeIntersections(String relativePath)
	{
		int countLines = 0;
		List<TileIntersect> tileIntersect = new LinkedList<TileIntersect>();
		
		//read the input text
		try(BufferedReader br = new BufferedReader(new FileReader(relativePath))) 
		{
			//go through line by line
		    for(String line; (line = br.readLine()) != null; ) 
		    {
		    	countLines ++;
		    	
		    	//skip the first line
		    	if(countLines == 1) continue;	
		    	
		    	//separate the line by " " (space)
		    	String[] separated = line.split(" ");
		    	//check for errors
		    	if(separated.length < 3)
		    	{
		    		System.out.println("Error initializing intersections: line " + countLines + " has not enough arguments.");
		    		System.exit(-20);
		    	}
		    	
		    	//create new Tile and add it to the list, ignoring everything that is written after the first 3 values
		    	if(separated.length >= 3)
		    		tileIntersect.add(new TileIntersect(Integer.parseInt(separated[0]), Integer.parseInt(separated[1]), 
			    			Integer.parseInt(separated[2])));
		    }
		    
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			e.printStackTrace();
			System.out.println("ERROR: intersections file not found in the specified path.");
			System.exit(-310);
		}
		
		return tileIntersect;
	}
	
	
	//initializes the tiles that should exist in the map and their respective RGB codes
	static List<Entity> initializeTiles(String relativePath)
	{
		int countLines = 0;
		List<Entity> tileList = new LinkedList<Entity>();
		
		//read the input text
		try(BufferedReader br = new BufferedReader(new FileReader(relativePath))) 
		{
			//go through line by line
		    for(String line; (line = br.readLine()) != null; ) 
		    {
		    	//if the line is empty(then skip the empty line)
		    	if(line.length() == 0) continue;
		    	countLines ++;
		    	
		    	//skip the first line
		    	if(countLines == 1) continue;	
		    	
		    	//separate the line by " " (space)
		    	String[] separated = line.split(" ");
		    	//check for errors
		    	if(separated.length < 4)
		    	{
		    		System.out.println("Error initializing tiles: line " + countLines + " has not enough arguments.");
		    		System.exit(-22);
		    	}
		    	
		    	//create new Tile and add it to the list
		    	if(separated.length == 4)
			    	tileList.add(new Entity(Integer.parseInt(separated[0]), Integer.parseInt(separated[1]), 
			    			Integer.parseInt(separated[2]), Integer.parseInt(separated[3])));
		    	
		    	//create tile with description, even though we don't need a description now (future proof)
		    	else if(separated.length > 4)
		    	{
		    		String text = "";
		    		for(int i = 4; i < separated.length; i++) text += (separated[i] + " ");
		    		
		    		tileList.add(new Entity(Integer.parseInt(separated[0]), Integer.parseInt(separated[1]), 
			    			Integer.parseInt(separated[2]), Integer.parseInt(separated[3]), text));
		    	}
		    }
		    
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.out.println("ERROR: tile file not found in the specified path.");
			System.exit(-301);
		}
		
		return tileList;
	}
	
	
	//gets a tile that exists in the tile list, if it exists
	private static int getTile(List<Entity> tileList, int red, int green, int blue)
	{
		for(Entity i: tileList)
		{
			if(red == i.getR() && green == i.getG() && blue == i.getB()) return i.getID();
		}
		
		//error, no tile found
		return -1;	
	}
	
	
	//builds up the map, transforming the RGB codes into game IDs
	static int[][] constructMap(BufferedImage image, List<Entity> tileList)
	{
		
		int width = image.getWidth();
		int height = image.getHeight();
		int map[][] = new int[width][height];

		//go through the map
		for(int y = 0; y < height; y ++)
		{
			for(int x = 0; x < width; x ++)
			{
				int pixel = image.getRGB(x, y);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
			
				//build the current ID / pixel
				map[x][y] = getTile(tileList, red, green, blue);
			}	
		}
		
		return map;
	}
	
	
	//loads the image from the specified path
	static BufferedImage loadImage(String path)
	{
		BufferedImage image = null;
		
		try 
		{
			image = ImageIO.read(WorldTileMap.class.getResource(path));
		} 
		
		catch (IOException e) 
		{
			System.out.println("\n ~~~~ ERROR LOADING THE IMAGE ~~~~");
			e.printStackTrace();
			System.exit(-72);
		}
		
		return image;
	}
	
	
	//this returns the current path of the application / class you are running
	static String getCurrentPath()
	{
		Path currentRelativePath = Paths.get("");
		String path = currentRelativePath.toAbsolutePath().toString() + "\\";
		//System.out.println("Current relative path is: " + path);
		
		return path;
	}
	
	
	//fine tuning means that the map will be modified according to the input, correcting unwanted errors that occurred
	private static int[][] fine_tuning(int[][] map, List<ParticularTile> tileParticular)
	{
		for(ParticularTile particulars: tileParticular)
		{
			//check if the tile is not out of the bounds of the map
			if(particulars.getX() < 0 || particulars.getX() > map[0].length)
			{
				System.out.println("ERROR: paricular tile out of bounds of the map: X=" + particulars.getX());
				System.exit(-1211);
			}
			if(particulars.getY() < 0 || particulars.getY() > map.length)
			{
				System.out.println("ERROR: paricular tile out of bounds of the map: Y= "+particulars.getY());
				System.exit(-1213);
			}
			
			map[particulars.getX()][particulars.getY()] = particulars.getID();
		}
		
		return map;
	}
	
	
	//checks the existence of a file in a specific path
	private static boolean check_existence(String absolutePath)
	{
		File f = new File(absolutePath);
		//System.out.println(f.exists());
		return f.exists();
	}
	
	
	//make all the required operations to create a tile map
	public static void run() 
	{	
		System.out.println("*TileMap starting...");
		//load the image you want to transform in the LVL text
		BufferedImage image = loadImage("map.png");

		//get the current path and initialize all the existing and needed files that are found there
		String path = getCurrentPath();
		List<Entity> tileList = initializeTiles(path + "tile_code.txt");
		List<TileIntersect> tileIntersect = null;
		List<ParticularTile> tileParticular = null;
		//initialize the intersections file
		if(check_existence(path + "tile_intersection.txt"))
			tileIntersect =	initializeIntersections(path + "tile_intersection.txt");
		//initialize the particular file
		if(check_existence(path + "tile_particular.txt"))
			tileParticular = initializeParticulars(path + "tile_particular.txt");
		
		//construct the basic map from the image given as an argument
		int[][] map = constructMap(image, tileList);
		int[][] finalMap = null;
		Map<Integer, Integer> errorPossibilities = null;
		
		//if there is a file for intersections, then calculate the smooth new map
		if(tileIntersect != null) finalMap = calculateSmooth(map, tileIntersect);
		else finalMap = map;
		//if there is a file for particular tiles, then calculate the new map with the specific tiles changed
		if(tileParticular != null) finalMap = fine_tuning(finalMap, tileParticular);	
		//if the file exists, then calculate and save the error possibilities and locations
		if(tileIntersect != null) errorPossibilities = calculateErrorPossibility(map, tileIntersect);
		
		//save the error possibilities and check box if the tile case is already treated
		if(tileIntersect != null && tileParticular != null) 
		//else if there is no tile particular then just save the error possibilities
			saveErrorPossibility(errorPossibilities, tileParticular, path + "world_possible_errors.txt");
		else if(tileIntersect != null)
			saveErrorPossibility(errorPossibilities, path + "world_possible_errors.txt");
		
		//save the final map, with or without all those extra options
		saveMap(finalMap, path + "world1.lvl");
		System.out.println("*TileMap ended...");
	}
}
