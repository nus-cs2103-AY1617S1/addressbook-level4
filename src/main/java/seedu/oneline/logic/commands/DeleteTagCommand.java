package seedu.oneline.logic.commands;

import seedu.oneline.commons.core.Messages;
import seedu.oneline.commons.core.UnmodifiableObservableList;
import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.logic.parser.Parser;
import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.model.task.UniqueTaskList.TaskNotFoundException;

public class DeleteTagCommand extends DeleteCommand {

    public DeleteTagCommand(int targetIndex) {
        super(targetIndex);
    }
    
    public static DeleteTagCommand createFromArgs(String args) throws IllegalValueException {

        return null; //stub
    }
    
    @Override
    public CommandResult execute() {
        return null; //stub
    }
    
}
