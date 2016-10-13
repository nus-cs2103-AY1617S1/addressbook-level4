package seedu.savvytasker.logic.commands.models;

/**
 * Represents a model for use with the mark command.
 */
public class MarkCommandModel extends CommandModel {
    
    private int[] targetIndex;
    
    public MarkCommandModel(int[] targetIndex) {
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
