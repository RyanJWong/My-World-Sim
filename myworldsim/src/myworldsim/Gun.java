package myworldsim;

public class Gun extends Weapon
{
	/*
	 * Class Instance Variables
	 * 
	 */
	private int					type;
	private boolean				atomaticSetting;
	private	int					ammoCapacity;							// Number of rounds in magazine or belt
	private int					ammoLeft;
	private double				caliber;
	private int					recoil;
	private boolean				scope;
	private int					rpm;								// Only in automatic setting
	
	/*
	 * Class Constants
	 * 
	 */
	
	
	public static final int NO_SCOPE						= 0;
	public static final int SCOPE							= 1;
	public static final int MAUNAL							= 0;
	public static final int AUTOMATIC						= 1;
	
	
	/*
	 * Constructor Methods
	 * 
	 */
	
	
	

	public Gun(int type,int automatic, int range, int ammoCapacity, int caliber, int recoil, boolean scope, int RPM)
	{
		super(type, damage,weight,accuracy, range,mobility);
		
		atomaticSetting 				= automatic;
		this.ammoCapacity				= ammoCapacity;
		this.caliber					= caliber;
		this.recoil						= recoil;
		this.scope						= scope;
		this.rpm						= RPM;
	}

	public boolean isAtomaticSetting() {
		return atomaticSetting;
	}

	public int getAmmoCapacity() {
		return ammoCapacity;
	}

	public double getCaliber() {
		return caliber;
	}

	public int getRecoil() {
		return recoil;
	}

	public boolean hasScopeS() {
		return scope;
	}

	public int getRpm() {
		return rpm;
	}
	
	public void loadAmmo() {
		ammoLeft=ammoCapacity;
	}
	
	public boolean shoot(int rounds){
		
		boolean shot=false;
		for (int round = 1; round <= rounds; round++){
			if (ammoLeft - 1 > 0){
				
				ammoLeft--;
				shot=true;
			}
		}
			
		return shot;	
	}
	
	
}
