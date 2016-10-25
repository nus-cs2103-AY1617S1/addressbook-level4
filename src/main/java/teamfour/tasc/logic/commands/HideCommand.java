//@@author A0148096W

package teamfour.tasc.logic.commands;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.model.keyword.ByKeyword;
import teamfour.tasc.model.keyword.FromKeyword;
import teamfour.tasc.model.keyword.HideCommandKeyword;
import teamfour.tasc.model.keyword.OnKeyword;
import teamfour.tasc.model.keyword.TagKeyword;
import teamfour.tasc.model.keyword.ToKeyword;

/**
 * Hides results from current listing results to the user that match the filters.
 */
public class HideCommand extends Command {
    public static final String COMMAND_WORD = HideCommandKeyword.keyword;
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Hides listing results with specified type, date or tags. "
            + "Parameters: [TYPE...] [on DATE] [by DEADLINE] [from START_TIME] [to END_TIME] [tag \"TAG\"...]\n"
            + "Example: " + COMMAND_WORD
            + " completed events";
    
    public static final String KEYWORD_DATE = OnKeyword.keyword;
    public static final String KEYWORD_DEADLINE = ByKeyword.keyword;
    public static final String KEYWORD_PERIOD_START_TIME = FromKeyword.keyword;
    public static final String KEYWORD_PERIOD_END_TIME = ToKeyword.keyword;
    public static final String KEYWORD_TAG = TagKeyword.keyword;
    
    public static final String[] VALID_KEYWORDS = { COMMAND_WORD, KEYWORD_DATE,
            KEYWORD_DEADLINE, KEYWORD_PERIOD_START_TIME, KEYWORD_PERIOD_END_TIME, KEYWORD_TAG};

    private final String type;
    private final Date deadline;
    private final Date startTime;
    private final Date endTime;
    private final Set<String> tags;
    private final boolean hasDate;
    
    /**
     * Show Command
     * Convenience constructor using raw values.
     * Set any parameter as null if it is not required.
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public HideCommand(String type, String date, String deadline, String startTime, 
                        String endTime, Set<String> tags) throws IllegalValueException {
        this.deadline = CommandHelper.tryConvertStringToDateOrReturnNull(deadline);
        Date convertedDate = CommandHelper.tryConvertStringToDateOrReturnNull(date);
        if (convertedDate != null) {
            hasDate = true;
            this.startTime = CommandHelper.getStartOfTheDate(convertedDate);
            this.endTime = CommandHelper.getEndOfTheDate(convertedDate);
        } else {
            hasDate = false;
            this.startTime = CommandHelper.tryConvertStringToDateOrReturnNull(startTime);
            this.endTime = CommandHelper.tryConvertStringToDateOrReturnNull(endTime);
        }
        
        this.tags = new HashSet<>();
        for (String tagName : tags) {
            this.tags.add(tagName);
        }
        this.type = type;
    }
    
    /**
     * Precondition: model is not null.
     * Adds the filters in this command to the model. 
     * Does not update the list yet.
     */
    private void addCommandFiltersToModel() {
        assert model != null;
        if (type != null)
            model.addTaskListFilterByType(type, true);
        if (deadline != null)
            model.addTaskListFilterByDeadline(deadline, true);
        if (hasDate) {
            model.addTaskListFilterByStartToEndTime(startTime, endTime, true);
        } else {
            if (startTime != null)
                model.addTaskListFilterByStartTime(startTime, true);
            if (endTime != null)
                model.addTaskListFilterByEndTime(endTime, true);
        }
        if (!tags.isEmpty())
            model.addTaskListFilterByTags(tags, true);
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        addCommandFiltersToModel();
        model.updateFilteredTaskListByFilter();
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

    @Override
    public boolean canUndo() {
        return false;
    }

}
