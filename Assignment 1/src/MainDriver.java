
public class MainDriver {

	public static void main(String[] args) {
		
		MainDriver mainDriver = new MainDriver();
		mainDriver.runBirthdayParadoxTests();

	}
	
	private void runBirthdayParadoxTests() {
		
		BirthdayParadox birthdayParadox = new BirthdayParadox();
		System.out.println("Trials till collision: " + birthdayParadox.getTrialsTillCollision(BirthdayParadox.DEFAULT_DOMAIN_SIZE));
		
	}

}
