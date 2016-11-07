package w15c2.tusk.model;

//@@author A0139817U
/**
 * This class allows the instance of another class to be copied to return a new instance of that class.
 */
public interface Copiable<T> {
	// Makes an identical copy of the object 
	public T copy();
}
