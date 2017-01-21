import java.text.DecimalFormat;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MainDriver {

	private DecimalFormat decimalFormat;

	/**
	 * Constructor
	 */
	public MainDriver() {
		this.decimalFormat = new DecimalFormat("0.0000");
	}
	
	public static void main(String[] args) {
		
		MainDriver mainDriver = new MainDriver();
		mainDriver.runBirthdayParadoxTests();
		mainDriver.runCouponCollectorTests();

	}
	
	private void runBirthdayParadoxTests() {
		
		System.out.println("1A: Trials till collision: " + BirthdayParadox.getTrialsTillCollision(BirthdayParadox.DEFAULT_DOMAIN_SIZE, new Random(System.currentTimeMillis())));
		Map<Integer, Double> plotValues = BirthdayParadox.getCumulativeDensityPlot(BirthdayParadox.NUMBER_OF_ITERATIONS, BirthdayParadox.DEFAULT_DOMAIN_SIZE);
		System.out.println("1B: Cumulative Density Plot:\n" + getCumulativeDensityPlotRScript(plotValues));
		System.out.println("1C: Expected Trials till Collision: " + this.decimalFormat.format(getExpectedTrialsToCollision(plotValues)));
		System.out.println("1D: Run Times Script:\n" + getOctaveRunTimesPlotScript());

	}
	
	private void runCouponCollectorTests() {
		
		System.out.println("2A: Number of trials for generating all numbers upto " + CouponCollectors.DEFAULT_DOMAIN_SIZE + " is " + CouponCollectors.getNummberOfTrialsToFillDomain(CouponCollectors.DEFAULT_DOMAIN_SIZE, new Random(System.currentTimeMillis())));
		Map<Integer, Double> plotValues = CouponCollectors.getCumulativeDensityPlot(CouponCollectors.NUMBER_OF_ITERATIONS, CouponCollectors.DEFAULT_DOMAIN_SIZE);
		System.out.println("2B: Cumulative Density Plot:\n" + getCumulativeDensityPlotRScript(plotValues));
		
		
		
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
	
	/**
	 * @param plotValues
	 * @return expected number of trials till collision
	 */
	private double getExpectedTrialsToCollision(Map<Integer, Double> plotValues) {
		
		int totalNumberOfTrials = 0;
		Set<Integer> numberOfTrails = plotValues.keySet();
		for (Integer trial : numberOfTrails) {
			totalNumberOfTrials += trial.intValue();
		}
		return (double) totalNumberOfTrials / numberOfTrails.size();
	}
	
	private String getOctaveRunTimesPlotScript() {
		
		StringBuffer octaveRunTimesPlotScript = new StringBuffer();
		
		Map<TrialsAndDomainSize, Long> runTimes = BirthdayParadox.getRunTimes();
		
		octaveRunTimesPlotScript.append("tx = [").append(BirthdayParadox.NUMBER_OF_TRIALS_SETTINGS.get(0).intValue());
		for (int trialsIndex = 1; trialsIndex < BirthdayParadox.NUMBER_OF_TRIALS_SETTINGS.size(); ++trialsIndex) {
			octaveRunTimesPlotScript.append(", ").append(BirthdayParadox.NUMBER_OF_TRIALS_SETTINGS.get(trialsIndex).intValue());
		}
		octaveRunTimesPlotScript.append("];\n");
			
		octaveRunTimesPlotScript.append("ty = [").append(BirthdayParadox.DOMAIN_SIZE_SETTINGS.get(0).intValue());
		for (int domainSizeIndex = 1; domainSizeIndex < BirthdayParadox.DOMAIN_SIZE_SETTINGS.size(); ++domainSizeIndex) {
			octaveRunTimesPlotScript.append(", ").append(BirthdayParadox.DOMAIN_SIZE_SETTINGS.get(domainSizeIndex).intValue());
		}
		octaveRunTimesPlotScript.append("];\n");
	
		octaveRunTimesPlotScript.append("[xx, yy] = meshgrid (tx, ty);\n");
		
		octaveRunTimesPlotScript.append("tz = [");
		boolean firstTime = true;
		for (int domainSizeIndex = 0; domainSizeIndex < BirthdayParadox.DOMAIN_SIZE_SETTINGS.size(); ++domainSizeIndex) {
			firstTime = true;
			for (int trialsIndex = 0; trialsIndex < BirthdayParadox.NUMBER_OF_TRIALS_SETTINGS.size(); ++trialsIndex) {
				if (firstTime) {
					firstTime = false;
				} else {
					octaveRunTimesPlotScript.append(", ");
				}
				octaveRunTimesPlotScript.append(runTimes.get(new TrialsAndDomainSize(BirthdayParadox.NUMBER_OF_TRIALS_SETTINGS.get(trialsIndex), BirthdayParadox.DOMAIN_SIZE_SETTINGS.get(domainSizeIndex))).longValue());
			}
			if (domainSizeIndex != BirthdayParadox.DOMAIN_SIZE_SETTINGS.size() - 1) {
				octaveRunTimesPlotScript.append("; ");
			}
		}
		octaveRunTimesPlotScript.append("]\n");
		
		octaveRunTimesPlotScript.append("mesh (tx, ty, tz);");
		
		return octaveRunTimesPlotScript.toString();
	
	}
	
}
