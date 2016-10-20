package seedu.savvytasker.logic.commands.models;

/**
 * Represents a model for use with the select command.
 */
public class SelectCommandModel extends CommandModel {
    
    private int[] targetIndex;
    
    public SelectCommandModel(int[] targetIndex) {
        assert(targetIndex != null);
        this.targetIndex = targetIndex;
    }
    
    public int[] getTargetIndex() {
        return targetIndex;
    }

    public void setTargetIndex(int[] targetIndex) {
        assert(targetIndex != null);
        this.targetIndex = targetIndex;
    }
}
