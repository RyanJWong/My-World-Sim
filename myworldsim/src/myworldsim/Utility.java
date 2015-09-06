package myworldsim;

import java.util.Random;

public abstract class Utility {
	
	
	public static int randomizer(int max) {
		int number = 0;

		Random randomNumber = new Random();

		number = randomNumber.nextInt(max) + 1;
		
		return number;
	}
}
