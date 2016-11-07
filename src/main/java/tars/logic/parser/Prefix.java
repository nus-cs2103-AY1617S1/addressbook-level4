package tars.logic.parser;

// @@author A0139924W
/**
 * A prefix that marks the beginning of an argument e.g. '/t' in 'add CS2103 Project Meeting /t
 * meeting'
 */
public class Prefix {
    private static final int HASHCODE_NULL_VALUE = 0;
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
        return this.value == null ? HASHCODE_NULL_VALUE : this.value.hashCode();
    }

}
