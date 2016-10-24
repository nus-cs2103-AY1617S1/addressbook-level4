package seedu.oneline.model.task;

import seedu.oneline.logic.commands.CommandConstants;

public enum TaskField {
    NAME(null),
    START_TIME(CommandConstants.KEYWORD_START_TIME),
    END_TIME(CommandConstants.KEYWORD_END_TIME),
    DEADLINE(CommandConstants.KEYWORD_DEADLINE),
    RECURRENCE(CommandConstants.KEYWORD_RECURRENCE),
    TAG(CommandConstants.KEYWORD_PREFIX);
    
    String keyword;
    
    private TaskField(String keyword) {
        this.keyword = keyword;
    }
    
    public String getKeyword() {
        return keyword;
    }
}
