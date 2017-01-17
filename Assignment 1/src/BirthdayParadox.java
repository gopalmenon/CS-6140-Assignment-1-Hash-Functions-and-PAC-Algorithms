import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BirthdayParadox {

	public static final int DEFAULT_DOMAIN_SIZE = 4000;
	
	private Random randomNumberGenerator;
	
	public BirthdayParadox() {
		this.randomNumberGenerator = new Random(0);
	}
	
	public int getTrialsTillCollision(int domainSize) {
		
		int trialCounter = 0, randomIntegerGenerated = 0;
		Set<Integer> randomIntegersGeneratedSoFar = new HashSet<Integer>();
		
		//Repeat till you get a collision
		while(true) {
		
			randomIntegerGenerated = this.randomNumberGenerator.nextInt(DEFAULT_DOMAIN_SIZE);
			++trialCounter;
			if (randomIntegersGeneratedSoFar.contains(Integer.valueOf(randomIntegerGenerated))) {
				return trialCounter;
			} else {
				randomIntegersGeneratedSoFar.add(Integer.valueOf(randomIntegerGenerated));
			}
		
		}
		
	}
	
}
