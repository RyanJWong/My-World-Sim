package myworldsim;

import java.util.List;

public class WolfTree extends TreeModel
{
	/*
	 * Class Instance Variables
	 * 
	 */
	
	private List<Room>			rooms;
	private List<Wolf>			wolves;
	private List<MachineGun> 	machineGuns;
	private List<Missile> 		missles;
	
	/*
	 * Class Constants
	 * 
	 */
	private final int	MAX_NUMBER_OF_ROOMS				= 30; 
	private final int	MAX_NUMBER_OF_WOLVES			= 150; // 5 wolves per room
	private final int	MAX_NUMBER_OF_MACHINE_GUNS		= 150; // 1 MG per wolf
	private final int	DEFAULT_NUMBER_OF_ROOMS			= 10;  // 3 wolves per room
	private final int	DEFAULT_NUMBER_OF_WOLVES		= 30;  
	private final int	DEFAULT_NUMBER_OF_MACHINE_GUNS	= 30; // 1 MG per room

	
	/*
	 * Constructor Method
	 * 
	 */
	
	public WolfTree()
	{
		rooms		= DEFAULT_NUMBER_OF_ROOMS;
		wolves		= DEFAULT_NUMBER_OF_WOLVES;
		machineGuns = DEFAULT_NUMBER_OF_MACHINE_GUNS;
	}
	
	public WolfTree(int number, int branches, int roots, int rooms, int wolves)
	{
		super(number, branches, roots);
		
		rooms 	 	= rooms;
		wolves 	 	= wolves;
		machineGuns = NUMBER_OF_MACHINEGUNS_PER_ROOM * rooms;
	}
}
