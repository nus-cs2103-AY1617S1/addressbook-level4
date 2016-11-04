package tars.logic.parser;

/**
 * A prefix that marks the beginning of an argument
 * e.g. '/t' in 'add CS2103 Project Meeting /t meeting'
 * 
 * @@author A0139924W
 */
public class Prefix {
    public final String value;

    public Prefix(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Prefix)) {
            return false;
        }

        if (other == this) {
            return true;
        }

        Prefix otherPrefix = (Prefix) other;
        return otherPrefix.value.equals(this.value);
    }

    @Override
    public int hashCode() {
        return this.value == null ? 0 : this.value.hashCode();
    }
}
