package w15c2.tusk.testutil;

import w15c2.tusk.model.Alias;

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
