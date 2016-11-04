package seedu.address.model.alias;

import java.util.Objects;

//@@author A0143756Y-reused
public class Alias implements ReadOnlyAlias, Comparable<ReadOnlyAlias> {
	
	private String alias;
	private String originalPhrase;
	
	/**
	 * Constructor for Alias class given alias and originalPhrase Strings.
	 */
	
	public Alias(String alias, String originalPhrase) {
		assert alias != null;
		assert !alias.isEmpty();
		assert originalPhrase != null;
		assert !originalPhrase.isEmpty();
		
		this.alias = alias;
		this.originalPhrase = originalPhrase;
	}
	
	
	public Alias(ReadOnlyAlias readOnlyAlias){
		this(readOnlyAlias.getAlias(), readOnlyAlias.getOriginalPhrase());
	}
	
	/**
	 * Constructor for Alias class given readOnlyAlias.
	 */

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
    
    public String getOriginalPhrase() {
        return originalPhrase;
    }
    
    public void setOriginalPhrase(String originalPhrase) {
        this.originalPhrase = originalPhrase;
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
        return Objects.hash(alias, originalPhrase);
    }

    @Override
    public String toString() {
        return getAsText();
    }

	@Override
	public int compareTo(ReadOnlyAlias other) {
		int statusCompare = this.getOriginalPhrase().compareTo(other.getOriginalPhrase());
		if (statusCompare != 0) {
			return statusCompare;
		}
		else {
			return this.getAlias().compareTo(other.getAlias());	
		}
	}
}
