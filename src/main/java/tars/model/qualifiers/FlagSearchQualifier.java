package tars.model.qualifiers;

//@@author A0124333U

import tars.commons.util.DateTimeUtil;
import tars.commons.util.StringUtil;
import tars.model.task.ReadOnlyTask;
import tars.model.task.TaskQuery;

public class FlagSearchQualifier implements Qualifier {

    private TaskQuery taskQuery;

    public FlagSearchQualifier(TaskQuery taskQuery) {
        this.taskQuery = taskQuery;
    }

    @Override
    public boolean run(ReadOnlyTask task) {

        return isNameFound(task) && isDateTimeFound(task)
                && isPriorityFound(task) && isStatusFound(task)
                && isTagFound(task);
    }

    private Boolean isNameFound(ReadOnlyTask task) {
        if (taskQuery.getNameKeywordsAsList().get(StringUtil.START_INDEX)
                .isEmpty()) {
            return true;
        } else {
            return taskQuery.getNameKeywordsAsList().stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(
                            task.getName().taskName, keyword))
                    .count() == taskQuery.getNameKeywordsAsList().size();
        }
    }

    private Boolean isDateTimeFound(ReadOnlyTask task) {
        if (taskQuery.getDateTimeQueryRange() == null) {
            return true;
        } else {
            return DateTimeUtil.isDateTimeWithinRange(task.getDateTime(),
                    taskQuery.getDateTimeQueryRange());
        }
    }

    private Boolean isPriorityFound(ReadOnlyTask task) {
        if (taskQuery.getPriorityKeywordsAsList().get(StringUtil.START_INDEX)
                .isEmpty()) {
            return true;
        } else {
            return taskQuery.getPriorityKeywordsAsList().stream()
                    .filter(keyword -> StringUtil
                            .containsIgnoreCase(task.priorityString(), keyword))
                    .count() == taskQuery.getPriorityKeywordsAsList().size();
        }
    }

    private Boolean isStatusFound(ReadOnlyTask task) {
        if (taskQuery.getStatusQuery().isEmpty()) {
            return true;
        } else {
            return taskQuery.getStatusQuery() == task.getStatus().toString();
        }
    }

    private Boolean isTagFound(ReadOnlyTask task) {
        if (taskQuery.getTagKeywordsAsList().get(StringUtil.START_INDEX)
                .isEmpty()) {
            return true;
        } else {
            String stringOfTags = task.tagsString()
                    .replace(StringUtil.STRING_COMMA.trim(),
                            StringUtil.EMPTY_STRING)
                    .replace(StringUtil.STRING_SQUARE_BRACKET_OPEN,
                            StringUtil.EMPTY_STRING)
                    .replace(StringUtil.STRING_SQUARE_BRACKET_CLOSE,
                            StringUtil.EMPTY_STRING);
            return taskQuery.getTagKeywordsAsList().stream()
                    .filter(keyword -> StringUtil
                            .containsIgnoreCase(stringOfTags, keyword))
                    .count() == taskQuery.getTagKeywordsAsList().size();
        }
    }

}
