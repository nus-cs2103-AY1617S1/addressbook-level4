package seedu.address.model.alias;

//@@author A0143756Y
/**
 * A read-only immutable interface for an Alias in the alias book.
 * Implementations should guarantee that 
 *
 */
public interface ReadOnlyAlias extends Comparable<ReadOnlyAlias> {

	public String getCommandAlias();
	public String getCommandPhrase();
	
    /**
     * Returns true if both have the same state. (Note that interfaces cannot override equals() method in Object class)
     */
    default boolean isSameStateAs(ReadOnlyAlias other) {
        return other == this // Short circuit if same object
                || (other != null // To avoid NullPointerException below
                && other.getCommandAlias().equals(this.getCommandAlias()) // State checks here onwards
                && other.getCommandPhrase().equals(this.getCommandPhrase()));
    }
    
    /**
     * Formats the alias as text, printing commandAlias and commandPhrase.
     */
    default String getAsText() {
    	final StringBuilder builder = new StringBuilder();
    	
    	builder.append("Command alias: " + getCommandAlias() + " ");
    	builder.append("Command phrase: " + getCommandPhrase());

        return builder.toString();
    }
    
    public int compareTo(ReadOnlyAlias other);
}
