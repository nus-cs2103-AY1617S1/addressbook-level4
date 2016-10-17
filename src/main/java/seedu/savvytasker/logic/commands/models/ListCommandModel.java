package seedu.savvytasker.logic.commands.models;

import seedu.savvytasker.model.task.ListType;

/**
 * Represents a model for use with the list command.
 */
public class ListCommandModel extends CommandModel {
    
    private ListType listType;
    
    public ListCommandModel(ListType listType) {
        this.listType = listType;
    }
    
    public ListType getListType() {
        return listType;
    }

    public void setListType(ListType listType) {
        this.listType = listType;
    }
}
