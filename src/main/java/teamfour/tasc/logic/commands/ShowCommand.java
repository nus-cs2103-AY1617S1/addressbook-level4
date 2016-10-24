package teamfour.tasc.logic.commands;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import teamfour.tasc.commons.core.LogsCenter;
import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.model.keyword.ByKeyword;
import teamfour.tasc.model.keyword.FromKeyword;
import teamfour.tasc.model.keyword.OnKeyword;
import teamfour.tasc.model.keyword.ShowCommandKeyword;
import teamfour.tasc.model.keyword.TagKeyword;
import teamfour.tasc.model.keyword.ToKeyword;

/**
 * Shows only tasks filtered from current listing results to the user.
 */
public class ShowCommand extends Command {
    public static final String COMMAND_WORD = ShowCommandKeyword.keyword;
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows only listing results with specified type, date or tags. "
            + "Parameters: [TYPE...] [on DATE] [by DEADLINE] [from START_TIME] [to END_TIME] [tag \"TAG\"...]\n"
            + "Example: " + COMMAND_WORD
            + " events on 24 Sep, tag \"Important\"";

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
    public ShowCommand(String type, String date, String deadline, String startTime, 
                        String endTime, Set<String> tags) throws IllegalValueException {
        this.deadline = CommandHelper.convertStringToDateIfPossible(deadline);
        Date convertedDate = CommandHelper.convertStringToDateIfPossible(date);
        if (convertedDate != null) {
            hasDate = true;
            this.startTime = CommandHelper.getStartOfTheDate(convertedDate);
            this.endTime = CommandHelper.getEndOfTheDate(convertedDate);
        } else {
            hasDate = false;
            this.startTime = CommandHelper.convertStringToDateIfPossible(startTime);
            this.endTime = CommandHelper.convertStringToDateIfPossible(endTime);
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
            model.addTaskListFilterByType(type, false);
        if (deadline != null)
            model.addTaskListFilterByDeadline(deadline, false);
        if (hasDate) {
            model.addTaskListFilterByStartToEndTime(startTime, endTime, false);
        } else {
            if (startTime != null)
                model.addTaskListFilterByStartTime(startTime, false);
            if (endTime != null)
                model.addTaskListFilterByEndTime(endTime, false);
        }
        if (!tags.isEmpty())
            model.addTaskListFilterByTags(tags, false);
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
