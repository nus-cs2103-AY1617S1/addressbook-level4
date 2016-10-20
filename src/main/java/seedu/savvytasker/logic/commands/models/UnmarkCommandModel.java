package seedu.savvytasker.logic.commands.models;

/**
 * Represents a model for use with the unmark command.
 */
public class UnmarkCommandModel extends CommandModel {
    
    private int[] targetIndex;
    
    public UnmarkCommandModel(int[] targetIndex) {
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
