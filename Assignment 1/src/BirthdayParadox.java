import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class BirthdayParadox {
	
	public static final List<Integer> NUMBER_OF_TRIALS_SETTINGS = Arrays.asList(Integer.valueOf(300), Integer.valueOf(1000), Integer.valueOf(2000), Integer.valueOf(4000), Integer.valueOf(6000), Integer.valueOf(8000), Integer.valueOf(10000));
	public static final List<Integer> DOMAIN_SIZE_SETTINGS = Arrays.asList(Integer.valueOf(400), Integer.valueOf(100000), Integer.valueOf(200000), Integer.valueOf(400000), Integer.valueOf(600000), Integer.valueOf(800000), Integer.valueOf(1000000));
	
	public static final int DEFAULT_DOMAIN_SIZE = 4000;
	public static final int NUMBER_OF_ITERATIONS = 300;
	
	public static int getTrialsTillCollision(int domainSize, Random randomNumberGenerator) {
		
		int trialCounter = 0, randomIntegerGenerated = 0;
		Set<Integer> randomIntegersGeneratedSoFar = new HashSet<Integer>();
		
		//Repeat till you get a collision
		while(true) {
		
			randomIntegerGenerated = randomNumberGenerator.nextInt(domainSize);
			++trialCounter;
			if (randomIntegersGeneratedSoFar.contains(Integer.valueOf(randomIntegerGenerated))) {
				return trialCounter;
			} else {
				randomIntegersGeneratedSoFar.add(Integer.valueOf(randomIntegerGenerated));
			}
		
		}
		
	}
	
	public static Map<Integer, Double> getCumulativeDensityPlot(int numberOfIterations, int domainSize) {
		
		Map<Integer, Double> plotValues = new HashMap<Integer, Double>(NUMBER_OF_ITERATIONS);
		List<Integer> trialsTillCollision = new ArrayList<Integer>(NUMBER_OF_ITERATIONS);
		Random randomNumberGenerator = null;
		
		//Generate trial counts till collision 
		int iterationCounter = 0;
		while(iterationCounter++ < NUMBER_OF_ITERATIONS) {
			
			randomNumberGenerator = new Random(randomNumberGenerator != null ? randomNumberGenerator.nextLong() : System.currentTimeMillis());
			trialsTillCollision.add(Integer.valueOf(getTrialsTillCollision(domainSize, randomNumberGenerator)));

		}
		
		//Sort counts and compute fraction of experiments that succeeded in collision after count
		Collections.sort(trialsTillCollision);
		for (int trialIndexCounter = 0; trialIndexCounter < NUMBER_OF_ITERATIONS; ++trialIndexCounter) {
			
			plotValues.put(Integer.valueOf(trialsTillCollision.get(trialIndexCounter)), Double.valueOf( (double) trialIndexCounter / NUMBER_OF_ITERATIONS));	
			
		}
		
		return plotValues;
		
	}
	
	public static Map<TrialsAndDomainSize, Long> getRunTimes() {
		
		Map<TrialsAndDomainSize, Long> runTimes = new HashMap<TrialsAndDomainSize, Long>();
		long timeBeforeRun = 0, timeAfterRun = 0;
		for (Integer trials : NUMBER_OF_TRIALS_SETTINGS) {
			for (Integer domainSize : DOMAIN_SIZE_SETTINGS) {
		
				timeBeforeRun = System.currentTimeMillis();
				getCumulativeDensityPlot(trials.intValue(), domainSize.intValue());
				timeAfterRun = System.currentTimeMillis();
				runTimes.put(new TrialsAndDomainSize(trials.intValue(), domainSize.intValue()), timeAfterRun - timeBeforeRun);
		
			}
		}
		
		return runTimes;
		
	}

}
