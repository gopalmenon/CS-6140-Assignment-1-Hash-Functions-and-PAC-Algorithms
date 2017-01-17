import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class BirthdayParadox {

	public static final int DEFAULT_DOMAIN_SIZE = 4000;
	public static final int NUMBER_OF_ITERATIONS = 300;
	
	public static int getTrialsTillCollision(int domainSize, Random randomNumberGenerator) {
		
		int trialCounter = 0, randomIntegerGenerated = 0;
		Set<Integer> randomIntegersGeneratedSoFar = new HashSet<Integer>();
		
		//Repeat till you get a collision
		while(true) {
		
			randomIntegerGenerated = randomNumberGenerator.nextInt(DEFAULT_DOMAIN_SIZE);
			++trialCounter;
			if (randomIntegersGeneratedSoFar.contains(Integer.valueOf(randomIntegerGenerated))) {
				return trialCounter;
			} else {
				randomIntegersGeneratedSoFar.add(Integer.valueOf(randomIntegerGenerated));
			}
		
		}
		
	}
	
	public static String getCumulativeDensityPlot(int numberOfIterations) {
		
		Map<Integer, Double> plotValues = new HashMap<Integer, Double>(NUMBER_OF_ITERATIONS);
		List<Integer> trialsTillCollision = new ArrayList<Integer>(NUMBER_OF_ITERATIONS);
		Random randomNumberGenerator = null;
		
		//Generate trial counts till collision 
		int iterationCounter = 0;
		while(iterationCounter++ < NUMBER_OF_ITERATIONS) {
			
			randomNumberGenerator = new Random(randomNumberGenerator != null ? randomNumberGenerator.nextLong() : System.currentTimeMillis());
			trialsTillCollision.add(Integer.valueOf(getTrialsTillCollision(DEFAULT_DOMAIN_SIZE, randomNumberGenerator)));

		}
		
		//Sort counts and compute fraction of experiments that succeeded in collision after count
		Collections.sort(trialsTillCollision);
		for (int trialIndexCounter = 0; trialIndexCounter < NUMBER_OF_ITERATIONS; ++trialIndexCounter) {
			
			plotValues.put(Integer.valueOf(trialsTillCollision.get(trialIndexCounter)), Double.valueOf( (double) trialIndexCounter / NUMBER_OF_ITERATIONS));	
			
		}
		
		//Create R script for plotting the cumulative density plot
		StringBuffer xAxis = new StringBuffer(), yAxis = new StringBuffer();
		xAxis.append("trials = c(");
		yAxis.append("proportion = c(");
		
		boolean firstTime = true;
		Set<Integer> numberOfTrails = plotValues.keySet();
		for (Integer trial : numberOfTrails) {
			
			if (firstTime) {
				firstTime = false;
			} else {
				xAxis.append(", ");
				yAxis.append(", ");
			}
			
			xAxis.append(trial.toString());
			yAxis.append(plotValues.get(trial).toString());
			
		}
		
		return xAxis.append(")").append("\n").append(yAxis.append(")").toString()).toString();
		
	}
	
}
