//@@author A0139916U
package seedu.savvytasker.model.alias;

import seedu.savvytasker.commons.exceptions.IllegalValueException;

public class DuplicateSymbolKeywordException extends IllegalValueException {
    private static final long serialVersionUID = -5516547118656055929L;

    public DuplicateSymbolKeywordException() {
        super("Operation will result in two symbols with the same keyword defined.");
    }
}
