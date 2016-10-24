package teamfour.tasc.logic.commands;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.model.Model;
import teamfour.tasc.model.keyword.ByKeyword;
import teamfour.tasc.model.keyword.FromKeyword;
import teamfour.tasc.model.keyword.ListCommandKeyword;
import teamfour.tasc.model.keyword.SortKeyword;
import teamfour.tasc.model.keyword.TagKeyword;
import teamfour.tasc.model.keyword.ToKeyword;

/**
 * Lists all tasks in the task list to the user.
 */
public class ListCommand extends Command {
    public static final String COMMAND_WORD = ListCommandKeyword.keyword;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all tasks/events with filters. "
            + "Parameters: [TYPE...] [by DEADLINE] [from START_TIME] [to END_TIME] [tag \"TAG\"...] [sort SORTING_ORDER]\n"
            + "Example: " + COMMAND_WORD
            + " completed tasks, tag \"Important\", sort earliest first";

    public static final String KEYWORD_DEADLINE = ByKeyword.keyword;
    public static final String KEYWORD_PERIOD_START_TIME = FromKeyword.keyword;
    public static final String KEYWORD_PERIOD_END_TIME = ToKeyword.keyword;
    public static final String KEYWORD_TAG = TagKeyword.keyword;
    public static final String KEYWORD_SORT = SortKeyword.keyword;

    public static final String[] VALID_KEYWORDS = { COMMAND_WORD, KEYWORD_DEADLINE,
            KEYWORD_PERIOD_START_TIME, KEYWORD_PERIOD_END_TIME, KEYWORD_TAG, KEYWORD_SORT};

    private final String type;
    private final Date deadline;
    private final Date startTime;
    private final Date endTime;
    private final Set<String> tags;
    private final String sortOrder;

    /**
     * List Command with default values
     * Lists all uncompleted tasks and events from now.
     */
    public ListCommand() throws IllegalValueException {
        this.type = "uncompleted";
        this.deadline = null;
        this.startTime = null;
        this.endTime = null;
        this.tags = new HashSet<>();
        this.sortOrder = Model.SORT_ORDER_BY_EARLIEST_FIRST;
    }

    /**
     * List Command
     * Convenience constructor using raw values.
     * Set any parameter as null if it is not required.
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public ListCommand(String type, String deadline, String startTime, String endTime,
                        Set<String> tags, String sortingOrder) throws IllegalValueException {
        this.deadline = CommandHelper.convertStringToDateIfPossible(deadline);
        this.startTime = CommandHelper.convertStringToDateIfPossible(startTime);
        this.endTime = CommandHelper.convertStringToDateIfPossible(endTime);

        this.tags = new HashSet<>();
        for (String tagName : tags) {
            this.tags.add(tagName);
        }
        this.type = type;
        this.sortOrder = sortingOrder;
    }

    /**
     * Precondition: model is not null.
     * Adds the filters in this command to the model.
     * Does not update the list yet.
     */
    private void addCommandFiltersToModel() {
        assert model != null;
        model.resetTaskListFilter();
        if (type != null)
            model.addTaskListFilterByType(type, false);
        if (deadline != null)
            model.addTaskListFilterByDeadline(deadline, false);
        if (startTime != null)
            model.addTaskListFilterByStartTime(startTime, false);
        if (endTime != null)
            model.addTaskListFilterByEndTime(endTime, false);
        if (!tags.isEmpty())
            model.addTaskListFilterByTags(tags, false);
    }

    @Override
    public CommandResult execute() {
        assert model != null;

        addCommandFiltersToModel();
        model.updateFilteredTaskListByFilter();

        if (sortOrder != null)
            model.sortFilteredTaskListByOrder(sortOrder);

        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

    @Override
    public boolean canUndo() {
        return false;
    }

}
