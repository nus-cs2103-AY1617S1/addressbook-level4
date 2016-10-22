package tars.logic.parser;

/**
 * A prefix that marks the beginning of an argument
 * e.g. '/t' in 'add CS2103 Project Meeting /t meeting'
 */
public class Prefix {
    public final String prefix;

    public Prefix(String prefix) {
        this.prefix = prefix;
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
        return otherPrefix.prefix.equals(this.prefix);
    }

    @Override
    public int hashCode() {
        return this.prefix == null ? 0 : this.prefix.hashCode();
    }
}
