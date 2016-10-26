package seedu.address.model.item;

//@@author A0139655U
public class Name {

    public String name;
    
    public Name(String name) {
        assert name != null;
        name = name.trim();
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && this.name.equals(((Name) other).name)); // state check
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public int compareTo(Name other) {
        return name.compareTo(other.name);
    }
}
