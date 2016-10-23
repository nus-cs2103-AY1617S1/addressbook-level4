package seedu.address.model.task;

public class Recurrence {

	public String recurFreq;
	
	public Recurrence(String recurFreq) {
		this.recurFreq = recurFreq;
	}

	public String getRecurFreq() {
		return recurFreq;
	}

	public void setRecurFreq(String recurFreq) {
		this.recurFreq = recurFreq;
	}
	
	@Override
    public String toString() {
        return recurFreq;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Recurrence // instanceof handles nulls
                && this.recurFreq.equals(((Recurrence) other).recurFreq)); // state check
    }

    @Override
    public int hashCode() {
        return recurFreq.hashCode();
    }
}
