package seedu.address.logic.commands.models;

/**
 * Represents a model for use with the list command.
 */
public class ListCommandModel extends CommandModel {
    
    private String listType;
    
    public ListCommandModel(String listType) {
        this.listType = listType;
    }
    
    public String getTargetIndex() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }
}
