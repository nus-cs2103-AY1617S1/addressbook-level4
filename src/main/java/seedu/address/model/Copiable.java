package seedu.address.model;

public interface Copiable<T> {
	/**
	 * This class allows the instance of another class to be copied to return a new instance of that class.
	 */
	 public T copy();
}
