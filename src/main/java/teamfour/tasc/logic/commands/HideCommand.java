package teamfour.tasc.logic.commands;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import teamfour.tasc.commons.exceptions.IllegalValueException;

/**
 * Hides only tasks filtered from current listing results to the user.
 */
public class HideCommand extends Command {

    public static final String COMMAND_WORD = "hide";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Hides listing results with specified type, date or tags. "
            + "Parameters: [TYPE...] [on DATE] [by DEADLINE] [from START_TIME] [to END_TIME] [tag \"TAG\"...]\n"
            + "Example: " + COMMAND_WORD
            + " completed events";

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
