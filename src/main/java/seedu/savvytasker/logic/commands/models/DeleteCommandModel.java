package seedu.savvytasker.logic.commands.models;

/**
 * Represents a model for use with the delete command.
 */
public class DeleteCommandModel extends CommandModel {
    
    private int[] targetIndex;
    
    public DeleteCommandModel(int[] targetIndex) {
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
