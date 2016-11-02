package seedu.address.model.alias;

import java.util.Objects;

//@@author A0143756Y
public class Alias implements ReadOnlyAlias, Comparable<ReadOnlyAlias> {
	
	private String commandAlias;
	private String commandPhrase;
	
	/**
	 * Constructor for Alias class given commandAlias and commandPhrase Strings.
	 */
	
	public Alias(String commandAlias, String commandPhrase) {
		assert commandAlias != null;
		assert !commandAlias.isEmpty();
		assert commandPhrase != null;
		assert !commandPhrase.isEmpty();
		
		this.commandAlias = commandAlias;
		this.commandPhrase = commandPhrase;
	}
	
	
	public Alias(ReadOnlyAlias readOnlyAlias){
		this(readOnlyAlias.getCommandAlias(), readOnlyAlias.getCommandPhrase());
	}
	
	/**
	 * Constructor for Alias class given readOnlyAlias.
	 */

    public String getCommandAlias() {
        return commandAlias;
    }

    public void setCommandAlias(String commandAlias) {
        this.commandAlias = commandAlias;
    }
    
    public String getCommandPhrase() {
        return commandPhrase;
    }
    
    public void setCommandPhrase(String commandPhrase) {
        this.commandPhrase = commandPhrase;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // Short circuit if same object
                || (other instanceof ReadOnlyAlias // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyAlias) other));
    }

    @Override
    public int hashCode() {
        // Use this method for custom fields hashing instead of implementing your own
        return Objects.hash(commandAlias, commandPhrase);
    }

    @Override
    public String toString() {
        return getAsText();
    }

	@Override
	public int compareTo(ReadOnlyAlias other) {
		int statusCompare = this.getCommandPhrase().compareTo(other.getCommandPhrase());
		if (statusCompare != 0) {
			return statusCompare;
		}
		else {
			return this.getCommandAlias().compareTo(other.getCommandAlias());	
		}
	}
}
