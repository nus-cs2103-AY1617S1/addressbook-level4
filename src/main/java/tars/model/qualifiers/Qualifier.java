package tars.model.qualifiers;

import tars.model.task.ReadOnlyTask;

public interface Qualifier {
    
        boolean run(ReadOnlyTask task);

        String toString();
}
