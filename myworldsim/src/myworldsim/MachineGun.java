package myworldsim;

public class MachineGun extends Gun
{
	/*
	 * Class Instance Variables
	 * 
	 */
	
	private int				model;
	
	//Class Constants
	
	public static final int MK48		= 1;
	public static final int M2		    = 2;

	
	//M2 Browning
	
	public static final double[] M2_MACHINE_GUN				= { MK48,
																AUTOMATIC,
																100,//ammo cap
																100,//ammo left
																12.7, // caliber
																485, //rpm
																NO_SCOPE //no scope
																};
	//Mk48	
	
	public static final double[] MK48_MACHINE_GUN			= { M2,
																AUTOMATIC,
																250,//ammo cap
																250,//ammo left
																7.62, // caliber
																710, //rpm
																NO_SCOPE //no scope
																};
						
	
	/*
	 * Constructor Method
	 * 
	 */
	
	public MachineGun(int type, int mount)
	{
		super();
		
		
	}
}
