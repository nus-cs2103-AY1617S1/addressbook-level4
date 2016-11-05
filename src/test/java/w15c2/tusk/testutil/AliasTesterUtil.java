package w15c2.tusk.testutil;

import w15c2.tusk.model.Alias;
import w15c2.tusk.model.task.DeadlineTask;
import w15c2.tusk.model.task.EventTask;
import w15c2.tusk.model.task.Task;

//@@author A0138978E
/*
 * Utility functions that help with tests that use Aliases
 */
public class AliasTesterUtil {
    public static String getAddAliasCommandFromAlias(Alias alias) {
        StringBuilder command = new StringBuilder("alias " + alias.getShortcut() + " " + alias.getSentence());
        return command.toString();
    }
}
