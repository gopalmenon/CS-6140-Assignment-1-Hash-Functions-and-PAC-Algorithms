import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CouponCollectors {

	public static final int DEFAULT_DOMAIN_SIZE = 200;
	
	
	public static int getNummberOfTrialsToFillDomain(int domainSize, Random randomNumberGenerator) {
		
		Set<Integer> randomGeneratedSet = new HashSet<Integer>(domainSize);
		int numberOfTrials = 0, generatedNumber = 0;
		
		while (randomGeneratedSet.size() < domainSize) {
			
			++numberOfTrials;
			generatedNumber = randomNumberGenerator.nextInt(domainSize);
			randomGeneratedSet.add(Integer.valueOf(generatedNumber));
			
		}
		
		return numberOfTrials;
		
	}
	
}
