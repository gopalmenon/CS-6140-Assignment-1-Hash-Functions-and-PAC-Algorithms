import java.util.Random;

public class MainDriver {

	public static void main(String[] args) {
		
		MainDriver mainDriver = new MainDriver();
		mainDriver.runBirthdayParadoxTests();

	}
	
	private void runBirthdayParadoxTests() {
		
		System.out.println("A: Trials till collision: " + BirthdayParadox.getTrialsTillCollision(BirthdayParadox.DEFAULT_DOMAIN_SIZE, new Random(System.currentTimeMillis())));
		System.out.println("B: Cumulative Density Plot: " + BirthdayParadox.getCumulativeDensityPlot(BirthdayParadox.NUMBER_OF_ITERATIONS));
	}

}
