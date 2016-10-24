package teamfour.tasc.logic.commands;

import teamfour.tasc.model.task.*;
import teamfour.tasc.commons.core.EventsCenter;
import teamfour.tasc.commons.core.Messages;
import teamfour.tasc.commons.core.UnmodifiableObservableList;
import teamfour.tasc.commons.events.ui.JumpToListRequestEvent;
import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.model.keyword.AddCommandKeyword;
import teamfour.tasc.model.tag.Tag;
import teamfour.tasc.model.tag.UniqueTagList;
import teamfour.tasc.model.task.Complete;
import teamfour.tasc.model.task.Deadline;
import teamfour.tasc.model.task.Name;
import teamfour.tasc.model.task.Period;
import teamfour.tasc.model.task.ReadOnlyTask;
import teamfour.tasc.model.task.Recurrence;
import teamfour.tasc.model.task.Task;
import teamfour.tasc.model.task.UniqueTaskList;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Adds a task to the task list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = AddCommandKeyword.keyword;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task list. "
            + "Parameters: NAME [by DEADLINE] [from STARTTIME] [to ENDTIME] [repeat RECURRENCE COUNT] [tag TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " \"Watch Movie\" tag recreation";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";

    private final Task toAdd;

    /**
     * Add Command
     * Convenience constructor using raw values.
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String by, String startTime, String endTime, String repeat, Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        // TODO ensure that we input the correct details!
        //Input validation
        Deadline deadline = new Deadline();
        if(by != null){
            deadline = new Deadline(CommandHelper.convertStringToDate(by));
        }
        Period period = new Period();
        if((startTime != null)&&(endTime != null)){
            List<Date> dates = CommandHelper.convertStringToMultipleDates(startTime + " and " + endTime);
            if(dates.size() < 2){
                throw new IllegalValueException("Invalid Dates");
            }
            period = new Period(dates.get(0), dates.get(1));
        }
        else if((startTime != null) && (by != null)){
            List<Date> dates = CommandHelper.convertStringToMultipleDates(startTime + " and " + by);
            if(dates.size() < 2){
                throw new IllegalValueException("Invalid Dates");
            }
            period = new Period(dates.get(0), dates.get(1));
        }
        Recurrence taskRecurrence = new Recurrence();
        if(repeat != null) {
            if ((startTime != null && endTime != null) || deadline != null) {
                taskRecurrence = CommandHelper.getRecurrence(repeat);
            }
        }

        this.toAdd = new Task(
                new Name(name),
                new Complete(false),
                deadline,
                period,
                taskRecurrence,
                new UniqueTagList(tagSet)
        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            //selecting added task
            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
            int targetIndex = lastShownList.size();
            EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));

            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

    @Override
    public boolean canUndo() {
        return true;
    }

}
