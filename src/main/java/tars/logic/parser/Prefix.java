package tars.logic.parser;

public class Prefix {
    public static final String NAME = "/n";
    public static final String PRIORITY = "/p";
    public static final String DATETIME = "/dt";
    public static final String TAG = "/t";
    public static final String ADDTAG = "/ta";
    public static final String REMOVETAG = "/tr";
    public static final String DONE = "/do";
    public static final String UNDONE = "/ud";
    public static final String LIST = "/ls";

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
