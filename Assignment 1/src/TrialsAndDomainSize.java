
public class TrialsAndDomainSize {
	private int numberOfTrials;
	private int domainSize;
	public TrialsAndDomainSize(int numberOfTrials, int domainSize) {
		this.numberOfTrials = numberOfTrials;
		this.domainSize = domainSize;
	}
	public int getNumberOfTrials() {
		return numberOfTrials;
	}
	public int getDomainSize() {
		return domainSize;
	}
	@Override
	public boolean equals(Object other) {
		if (other instanceof TrialsAndDomainSize) {
			if (((TrialsAndDomainSize) other).numberOfTrials == this.numberOfTrials && ((TrialsAndDomainSize) other).domainSize == this.domainSize) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	@Override
	public int hashCode() {
		return 10000 * this.numberOfTrials + this.numberOfTrials;
	}
}
