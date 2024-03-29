import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class BirthdayParadox {
	
	public static final List<Integer> NUMBER_OF_TRIALS_SETTINGS = Arrays.asList(Integer.valueOf(300), Integer.valueOf(1000), Integer.valueOf(2000), Integer.valueOf(3000), Integer.valueOf(4000), Integer.valueOf(5000), Integer.valueOf(6000), Integer.valueOf(7000), Integer.valueOf(8000), Integer.valueOf(9000), Integer.valueOf(10000));
	public static final List<Integer> DOMAIN_SIZE_SETTINGS = Arrays.asList(Integer.valueOf(400), Integer.valueOf(1000), Integer.valueOf(4000), Integer.valueOf(10000), Integer.valueOf(50000), Integer.valueOf(100000), Integer.valueOf(200000), Integer.valueOf(300000), Integer.valueOf(400000), Integer.valueOf(500000), Integer.valueOf(600000), Integer.valueOf(700000), Integer.valueOf(800000), Integer.valueOf(900000), Integer.valueOf(1000000));
	
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
		
		SortedMap<Integer, Double> plotValues = new TreeMap<Integer, Double>();
		List<Integer> trialsTillCollision = new ArrayList<Integer>(numberOfIterations);
		Random randomNumberGenerator = null;
		
		//Generate trial counts till collision 
		int iterationCounter = 0;
		while(iterationCounter++ < numberOfIterations) {
			
			randomNumberGenerator = new Random(randomNumberGenerator != null ? randomNumberGenerator.nextLong() : System.currentTimeMillis());
			trialsTillCollision.add(Integer.valueOf(getTrialsTillCollision(domainSize, randomNumberGenerator)));

		}
		
		//Sort and compute fraction of experiments that succeeded in collision after count
		Collections.sort(trialsTillCollision);
		for (int trialIndexCounter = 0; trialIndexCounter < numberOfIterations; ++trialIndexCounter) {
			
			plotValues.put(Integer.valueOf(trialsTillCollision.get(trialIndexCounter)), Double.valueOf( (double) trialIndexCounter / numberOfIterations));	
			
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
	
	public static double getAnalyticalNonCollisionProbability(int domainSize, int numberOfTrials) {
		
		double analyticalNonCollisionProbability = 1.0;
		
		for (int trial = 1; trial < numberOfTrials; ++trial) {
			
			analyticalNonCollisionProbability *= (double) (domainSize - trial) / domainSize;
		}
				
		return analyticalNonCollisionProbability;
		
	}

}
