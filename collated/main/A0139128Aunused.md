# A0139128Aunused
###### \java\seedu\whatnow\logic\commands\UndoAndRedo.java
``` java
package seedu.whatnow.logic.commands;

import seedu.whatnow.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.whatnow.model.task.UniqueTaskList.TaskNotFoundException;

public abstract class UndoAndRedo extends Command {
    public abstract CommandResult undo() throws DuplicateTaskException, TaskNotFoundException;

    public abstract CommandResult redo() throws TaskNotFoundException;
}
```
###### \java\seedu\whatnow\logic\parser\Parser.java
``` java
    /**
     * Forward slashes are reserved for delimiter prefixes variable number of
     * tags
     */
    private static final Pattern TASK_DATA_ARGS_FORMAT = Pattern
            .compile("(?<name>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)");

    /**
     * This arguments is for e.g. add task on today, add task on 18/10/2016
     */
    private static final Pattern TASK_MODIFIED_WITH_DATE_ARGS_FORMAT = Pattern.compile("(?<name>[^/]+)\\s"
            + "(.*?\\bon|by\\b.*?\\s)??" + "(?<dateArguments>([0-3]??[0-9][//][0-1]??[0-9][//][0-9]{4})??)"
            + "(?<tagArguments>(?: t/[^/]+)*)");

```
