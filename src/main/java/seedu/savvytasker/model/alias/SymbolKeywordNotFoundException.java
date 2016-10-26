//@@author A0139916U
package seedu.savvytasker.model.alias;

import seedu.savvytasker.commons.exceptions.IllegalValueException;

public class SymbolKeywordNotFoundException extends IllegalValueException {
    private static final long serialVersionUID = -5516547118656055929L;

    public SymbolKeywordNotFoundException() {
        super("Unable to find a symbol with the specified keyword.");
    }
}
