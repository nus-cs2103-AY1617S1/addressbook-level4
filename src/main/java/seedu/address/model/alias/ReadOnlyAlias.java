package seedu.address.model.alias;

//@@author A0143756Y
/**
 * A read-only immutable interface for an Alias in the alias book.
 * Implementations should guarantee that 
 *
 */
public interface ReadOnlyAlias extends Comparable<ReadOnlyAlias> {

	public String getAlias();
	public String getOriginalPhrase();
	
    /**
     * Returns true if both have the same state. (Note that interfaces cannot override equals() method in Object class)
     */
    default boolean isSameStateAs(ReadOnlyAlias other) {
        return other == this // Short circuit if same object
                || (other != null // To avoid NullPointerException below
                && other.getAlias().equals(this.getAlias()) // State checks here onwards
                && other.getOriginalPhrase().equals(this.getOriginalPhrase()));
    }
    
    /**
     * Formats the alias as text, printing commandAlias and commandPhrase.
     */
    default String getAsText() {
    	final StringBuilder builder = new StringBuilder();
    	
    	builder.append("Alias: " + getAlias() + ", ");
    	builder.append("Original phrase: " + getOriginalPhrase() + ".\n");

        return builder.toString();
    }
    
    public int compareTo(ReadOnlyAlias other);
}
