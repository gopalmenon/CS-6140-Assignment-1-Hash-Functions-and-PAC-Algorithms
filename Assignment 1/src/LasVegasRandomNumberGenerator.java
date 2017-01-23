import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Las Vegas algorithm implementation for random number generation
 *
 */
public class LasVegasRandomNumberGenerator {

	private int exponent;
	private Random randomNumberGenerator;
	
	public LasVegasRandomNumberGenerator(int maximumValue) {
		int intExponent = (int) (Math.log(maximumValue) / Math.log(2));
		double doubleExponent = Math.log(maximumValue) / Math.log(2);
		if (intExponent == doubleExponent) {
			this.exponent = intExponent;
		} else {
			this.exponent = intExponent + 1;
		}
		this.randomNumberGenerator = new Random(System.currentTimeMillis());
	}
	
	public int getNextInt() {
		
		List<Boolean> randomBinaryNumber = new ArrayList<Boolean>(this.exponent);
		
		//Repeat while not full
		while (randomBinaryNumber.size() < this.exponent) {
			
			randomBinaryNumber.add(rand_bit() == 0 ? Boolean.FALSE : Boolean.TRUE);
			
		}
		
		int returnValue = 1;
		for (int positionCounter = 0; positionCounter < this.exponent; ++positionCounter) {
			
			if (randomBinaryNumber.get(positionCounter).equals(Boolean.TRUE)) {
				returnValue += (int) Math.pow(2.0, positionCounter);
			}
			
		}
		
		return returnValue;
		
	}
	
	private int rand_bit() {
	
		return this.randomNumberGenerator.nextBoolean() ? 1 : 0;
	
	}
	
}
