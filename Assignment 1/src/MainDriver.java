import java.text.DecimalFormat;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MainDriver {
	
	private DecimalFormat decimalFormat;

	public MainDriver() {
		this.decimalFormat = new DecimalFormat("0.0000");
	}
	
	public static void main(String[] args) {
		
		MainDriver mainDriver = new MainDriver();
		mainDriver.runBirthdayParadoxTests();

	}
	
	private void runBirthdayParadoxTests() {
		
		System.out.println("A: Trials till collision: " + BirthdayParadox.getTrialsTillCollision(BirthdayParadox.DEFAULT_DOMAIN_SIZE, new Random(System.currentTimeMillis())));
		Map<Integer, Double> plotValues = BirthdayParadox.getCumulativeDensityPlot(BirthdayParadox.NUMBER_OF_ITERATIONS);
		System.out.println("B: Cumulative Density Plot:\n" + getCumulativeDensityPlotRScript(plotValues));
		System.out.println("C: Expected Trials till Collision: " + this.decimalFormat.format(getExpectedTrialsToCollision(plotValues)));
	}

	/**
	 * Create R script for plotting the cumulative density plot
	 * @param plotValues
	 * @return
	 */
	private String getCumulativeDensityPlotRScript(Map<Integer, Double> plotValues) {
		
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
	
	private double getExpectedTrialsToCollision(Map<Integer, Double> plotValues) {
		
		int totalNumberOfTrials = 0;
		Set<Integer> numberOfTrails = plotValues.keySet();
		for (Integer trial : numberOfTrails) {
			totalNumberOfTrials += trial.intValue();
		}
		return (double) totalNumberOfTrials / numberOfTrails.size();
	}
	
}
