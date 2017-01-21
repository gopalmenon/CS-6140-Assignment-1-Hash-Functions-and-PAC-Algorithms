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

public class CouponCollectors {
	
	public static final List<Integer> NUMBER_OF_TRIALS_SETTINGS = Arrays.asList(Integer.valueOf(300), Integer.valueOf(500), Integer.valueOf(1000), Integer.valueOf(1500), Integer.valueOf(2000), Integer.valueOf(2500), Integer.valueOf(3000), Integer.valueOf(3500), Integer.valueOf(4000), Integer.valueOf(4500), Integer.valueOf(5000));
	public static final List<Integer> DOMAIN_SIZE_SETTINGS = Arrays.asList(Integer.valueOf(200), Integer.valueOf(1000), Integer.valueOf(3000), Integer.valueOf(5000), Integer.valueOf(7000), Integer.valueOf(9000), Integer.valueOf(11000), Integer.valueOf(13000), Integer.valueOf(15000), Integer.valueOf(17000), Integer.valueOf(19000), Integer.valueOf(20000));

	public static final int DEFAULT_DOMAIN_SIZE = 200;
	public static final int NUMBER_OF_ITERATIONS = 300;
	
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
	
	public static Map<Integer, Double> getCumulativeDensityPlot(int numberOfIterations, int domainSize) {
		
		SortedMap<Integer, Double> plotValues = new TreeMap<Integer, Double>();
		List<Integer> trialsToFillDomain = new ArrayList<Integer>(NUMBER_OF_ITERATIONS);
		Random randomNumberGenerator = null;
		
		//Generate trial counts till all random numbers are generated 
		int iterationCounter = 0;
		while(iterationCounter++ < NUMBER_OF_ITERATIONS) {
			
			randomNumberGenerator = new Random(randomNumberGenerator != null ? randomNumberGenerator.nextLong() : System.currentTimeMillis());
			trialsToFillDomain.add(Integer.valueOf(getNummberOfTrialsToFillDomain(domainSize, randomNumberGenerator)));

		}
		
		//Sort and compute fraction of experiments that succeeded in collision after count
		Collections.sort(trialsToFillDomain);
		for (int trialIndexCounter = 0; trialIndexCounter < NUMBER_OF_ITERATIONS; ++trialIndexCounter) {
			
			plotValues.put(Integer.valueOf(trialsToFillDomain.get(trialIndexCounter)), Double.valueOf( (double) trialIndexCounter / NUMBER_OF_ITERATIONS));	
			
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
	
	
	public static int getAnalyticalTrialsForAllCoupons(int domainSize) {
		
		double analyticalTrialsForAllCoupons = 0.0;
		
		for (int counter = 1; counter <= domainSize; ++counter) {
			
			analyticalTrialsForAllCoupons +=  1.0 / counter;
		}
				
		return (int) (domainSize * analyticalTrialsForAllCoupons);
		
	}

	
}
