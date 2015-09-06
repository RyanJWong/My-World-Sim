package myworldsim;

public class Location
{
	/*
	 * Class Instance Variables
	 * 
	 */
	
	private WorldLocation	location;				// Where it is: world location coordinates: x, y
	private LocationTypes	locationType;			// Initially, when a Location is created it is a World-type Location
	private String			locationDescription;	// Initially, when a Location is created it is described as a World-type Location
	private Exit[]			locationExits;			// One-way exits: inside direction is NOWHERE, such as Emergency Exits
	
	/*
	 * Class Constants
	 * 
	 */
	
	
	
	/*
	 * Consructor Methods
	 * 
	 */

	public Location()
	{
		// A Location is NOWHERE if its WorldLocation is null
		location 			= null;
		locationType		= LocationTypes.BLOCKED;
		locationDescription	= null;
		locationExits 	  	= null;
	}
	
	public Location(WorldLocation loc, WorldDimension dimension, String description, LocationTypes type)
	{
		location		  	= loc;
		locationDescription = description;
		locationType		= type;
		locationExits 	  	= new Exit[Direction.values().length];
		
		// Create the Exits
		for (int exit = 0; exit < Direction.values().length; exit++)
		{
			locationExits[exit] = new Exit();
		}
	}

	/*
	 * Getters
	 * 
	 */

	public WorldLocation getLocation()
	{
		return location;
	}

	public String getDescription()
	{
		return locationDescription;
	}

	public LocationTypes getType()
	{
		return locationType;
	}
	
	public Exit getExit(int exit)
	{
		return locationExits[exit];
	}
	public int getExit(String direction){
		
		int exitNumber=-1; //Default to unknown 
		if(direction.equalsIgnoreCase(Direction.NORTH.toString()) || direction.substring(0,1).equalsIgnoreCase("N")){
			exitNumber=0;

		}
		else if(direction.equalsIgnoreCase(Direction.SOUTH.toString()) || direction.substring(0,1).equalsIgnoreCase("S")){
			exitNumber=1;
		}else if(direction.equalsIgnoreCase(Direction.EAST.toString()) || direction.substring(0,1).equalsIgnoreCase("E")){
			exitNumber=2;

		}else if(direction.equalsIgnoreCase(Direction.WEST.toString()) || direction.substring(0,1).equalsIgnoreCase("W")){
			exitNumber=3;

		}else if(direction.equalsIgnoreCase(Direction.NORTHEAST.toString()) || direction.substring(0,1).equalsIgnoreCase("NE")){
			exitNumber=4;

		}else if(direction.equalsIgnoreCase(Direction.NORTHWEST.toString())|| direction.equalsIgnoreCase("NW")){
			exitNumber=5;

		}else if(direction.equalsIgnoreCase(Direction.SOUTHEAST.toString())|| direction.equalsIgnoreCase("SE")){
			exitNumber=6;

		}else if(direction.equalsIgnoreCase(Direction.SOUTHWEST.toString())|| direction.substring(0,1).equalsIgnoreCase("SW")){
			exitNumber=7;

		}else if(direction.equalsIgnoreCase(Direction.UP.toString())|| direction.substring(0,1).equalsIgnoreCase("U")){
			exitNumber=8;

		}else if(direction.equalsIgnoreCase(Direction.DOWN.toString())|| direction.substring(0,1).equalsIgnoreCase("D")){
			exitNumber=9;

		}
		return exitNumber;
	}
	
	/*
	 * Setters
	 * 
	 */
	
	public void setDescription(String description)
	{
		locationDescription = description;
	}

	public void setType(LocationTypes type)
	{
		locationType = type;
	}

	public void setExit(Direction direction, Location location)
	{
		locationExits[direction.ordinal()].setLocation(location);
	}
	
	/*
	 * Action Methods
	 * 
	 */
	
	public void build(LocationTypes type)
	{
		locationType = type;
	}
}
