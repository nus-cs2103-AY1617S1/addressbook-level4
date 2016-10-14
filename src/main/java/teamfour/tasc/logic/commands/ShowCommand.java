package teamfour.tasc.logic.commands;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import teamfour.tasc.commons.exceptions.IllegalValueException;

/**
 * Shows only tasks filtered from current listing results to the user.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Showing only tasks with filters from current results. "
            + "Parameters: [TYPE] [on DATE] [by DEADLINE] [from START_TIME [to END_TIME]] [tag TAG...]\n"
            + "Example: " + COMMAND_WORD
            + " tasks on 18 Sep, tag urgent";

    private final String type;
    private final Date deadline;
    private final Date startTime;
    private final Date endTime;
    private final Set<String> tags;
    private final boolean hasDate;
    
    /**
     * Show Command
     * Convenience constructor using raw values.
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public ShowCommand(String type, String date, String deadline, String startTime, 
                        String endTime, Set<String> tags) throws IllegalValueException {
        if (deadline != null) {
            this.deadline = CommandHelper.convertStringToDate(deadline);
        } else {
            this.deadline = null;
        }
        
        if (date != null) {
            hasDate = true;
            Date convertedDate = CommandHelper.convertStringToDate(date);
            Calendar c = Calendar.getInstance();
            c.setTime(convertedDate);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            this.startTime = c.getTime();
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            c.set(Calendar.MILLISECOND, 999);
            this.endTime = c.getTime();
        } else {
            hasDate = false;
            if (startTime != null) {
                this.startTime = CommandHelper.convertStringToDate(startTime);
            } else {
                this.startTime = null;
            }
            
            if (endTime != null) {
                this.endTime = CommandHelper.convertStringToDate(endTime);
            } else {
                this.endTime = null;
            }
        }
        
        this.tags = new HashSet<>();
        for (String tagName : tags) {
            this.tags.add(tagName);
        }
        this.type = type;
    }

    @Override
    public CommandResult execute() {
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
        model.updateFilteredTaskListByFilter();
        
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

    @Override
    public boolean canUndo() {
        return false;
    }

    @Override
    public CommandResult executeUndo() {
        return null;
    }
}
