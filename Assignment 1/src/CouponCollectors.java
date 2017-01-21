import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class CouponCollectors {

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
	
}
