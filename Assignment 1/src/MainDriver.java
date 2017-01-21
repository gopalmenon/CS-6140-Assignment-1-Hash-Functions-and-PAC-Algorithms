import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MainDriver {

	public static final String BIRTHDAY_PARADOX_RUNTIMES_PLOT_TITLE = "Birthday Paradox Run Time Plot";
	public static final String COUPON_COLLECTORS_RUNTIMES_PLOT_TITLE = "Coupon Collectors Run Time Plot";
	public static final int NUMBER_OF_ANALYTICAL_TRIALS_FOR_COLLISION = 75;
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
		mainDriver.runAnalyticalComputations();

	}
	
	private void runBirthdayParadoxTests() {
		
		System.out.println("1A: Trials till collision: " + BirthdayParadox.getTrialsTillCollision(BirthdayParadox.DEFAULT_DOMAIN_SIZE, new Random(System.currentTimeMillis())));
		Map<Integer, Double> plotValues = BirthdayParadox.getCumulativeDensityPlot(BirthdayParadox.NUMBER_OF_ITERATIONS, BirthdayParadox.DEFAULT_DOMAIN_SIZE);
		System.out.println("1B: Cumulative Density Plot:\n" + getCumulativeDensityPlotRScript(plotValues));
		System.out.println("1C: Expected Trials till Collision: " + this.decimalFormat.format(getExpectedTrialsToCollision(plotValues)));
		System.out.println("1D: Run Times Script:\n" + getOctaveRunTimesPlotScript(BirthdayParadox.getRunTimes(), BIRTHDAY_PARADOX_RUNTIMES_PLOT_TITLE, BirthdayParadox.NUMBER_OF_TRIALS_SETTINGS, BirthdayParadox.DOMAIN_SIZE_SETTINGS));

	}
	
	private void runCouponCollectorTests() {
		
		System.out.println("2A: Number of trials for generating all numbers upto " + CouponCollectors.DEFAULT_DOMAIN_SIZE + " is " + CouponCollectors.getNummberOfTrialsToFillDomain(CouponCollectors.DEFAULT_DOMAIN_SIZE, new Random(System.currentTimeMillis())));
		Map<Integer, Double> plotValues = CouponCollectors.getCumulativeDensityPlot(CouponCollectors.NUMBER_OF_ITERATIONS, CouponCollectors.DEFAULT_DOMAIN_SIZE);
		System.out.println("2B: Cumulative Density Plot:\n" + getCumulativeDensityPlotRScript(plotValues));
		System.out.println("2C: Expected Trials till all values are generated: " + this.decimalFormat.format(getExpectedTrialsToCollision(plotValues)));
		//System.out.println("1D: Run Times Script:\n" + getOctaveRunTimesPlotScript(CouponCollectors.getRunTimes(), COUPON_COLLECTORS_RUNTIMES_PLOT_TITLE, CouponCollectors.NUMBER_OF_TRIALS_SETTINGS, CouponCollectors.DOMAIN_SIZE_SETTINGS));
		
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
	
	private String getOctaveRunTimesPlotScript(Map<TrialsAndDomainSize, Long> runTimes, String title, List<Integer> trialSettings, List<Integer> domainSizeSettings) {
		
		StringBuffer octaveRunTimesPlotScript = new StringBuffer();
		
		octaveRunTimesPlotScript.append("tx = [").append(trialSettings.get(0).intValue());
		for (int trialsIndex = 1; trialsIndex < trialSettings.size(); ++trialsIndex) {
			octaveRunTimesPlotScript.append(", ").append(trialSettings.get(trialsIndex).intValue());
		}
		octaveRunTimesPlotScript.append("];\n");
			
		octaveRunTimesPlotScript.append("ty = [").append(domainSizeSettings.get(0).intValue());
		for (int domainSizeIndex = 1; domainSizeIndex < domainSizeSettings.size(); ++domainSizeIndex) {
			octaveRunTimesPlotScript.append(", ").append(domainSizeSettings.get(domainSizeIndex).intValue());
		}
		octaveRunTimesPlotScript.append("];\n");
	
		octaveRunTimesPlotScript.append("[xx, yy] = meshgrid (tx, ty);\n");
		
		octaveRunTimesPlotScript.append("tz = [");
		boolean firstTime = true;
		for (int domainSizeIndex = 0; domainSizeIndex < domainSizeSettings.size(); ++domainSizeIndex) {
			firstTime = true;
			for (int trialsIndex = 0; trialsIndex < trialSettings.size(); ++trialsIndex) {
				if (firstTime) {
					firstTime = false;
				} else {
					octaveRunTimesPlotScript.append(", ");
				}
				octaveRunTimesPlotScript.append(runTimes.get(new TrialsAndDomainSize(trialSettings.get(trialsIndex), domainSizeSettings.get(domainSizeIndex))).longValue());
			}
			if (domainSizeIndex != domainSizeSettings.size() - 1) {
				octaveRunTimesPlotScript.append("; ");
			}
		}
		octaveRunTimesPlotScript.append("]\n");
		
		octaveRunTimesPlotScript.append("mesh (tx, ty, tz);\n");
		octaveRunTimesPlotScript.append("xlabel (\"Iterations\");\n");
		octaveRunTimesPlotScript.append("ylabel (\"Domain Size\");\n");
		octaveRunTimesPlotScript.append("zlabel (\"Run Time (ms)\");\n");
		octaveRunTimesPlotScript.append("title (\"").append(title).append("\");\n");
		
		return octaveRunTimesPlotScript.toString();
	
	}
	
	private void runAnalyticalComputations() {
		
		System.out.println("3A: With " + NUMBER_OF_ANALYTICAL_TRIALS_FOR_COLLISION + " trials for a domain size of " + BirthdayParadox.DEFAULT_DOMAIN_SIZE + ", the probability of a collision is: " + this.decimalFormat.format(BirthdayParadox.getAnalyticalCollisionProbability(BirthdayParadox.DEFAULT_DOMAIN_SIZE, NUMBER_OF_ANALYTICAL_TRIALS_FOR_COLLISION)));
		
	}
	
}
