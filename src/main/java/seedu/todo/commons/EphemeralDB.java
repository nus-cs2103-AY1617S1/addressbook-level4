package seedu.todo.commons;

/**
 * A bit like Redis, essentially a data store for things that should not be
 * persisted to disk, but should be shared between all modules.
 * 
 * All variables should be public. In-place modifications of variables are
 * encouraged.
 * 
 * @author louietyj
 */
public class EphemeralDB {

    private static EphemeralDB instance = null;

    protected EphemeralDB() {
        // Prevent instantiation.
    }

    public static EphemeralDB getInstance() {
        if (instance == null) {
            instance = new EphemeralDB();
        }
        return instance;
    }

}
