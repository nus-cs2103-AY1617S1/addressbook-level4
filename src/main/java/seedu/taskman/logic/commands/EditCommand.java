package seedu.taskman.logic.commands;

import seedu.taskman.commons.core.Messages;
import seedu.taskman.commons.core.UnmodifiableObservableList;
import seedu.taskman.commons.exceptions.IllegalValueException;
import seedu.taskman.model.event.*;
import seedu.taskman.model.tag.Tag;
import seedu.taskman.model.tag.UniqueTagList;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

/**
 * Edits an existing task
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    // kiv: let parameters be objects. we can easily generate the usage in that case
    // todo: update message
    // UG/DG
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits an existing task. "
            + "Parameters: INDEX [TITLE] [d/DEADLINE] [c/STATUS] [s/SCHEDULE] [f/FREQUENCY] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " 1 CS2103T Tutorial d/fri 11.59pm c/complete s/mon 2200 to tue 0200 f/1 week t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "Task updated: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "A task with the same name already exists in TaskMan";

    private final ArgumentContainer argsContainer;
    private Activity beforeEdit;
    private Activity afterEdit;
    private Activity.ActivityType activityType;

    /**
     * Convenience constructor using raw values.
     * Fields which are null are assumed not to be replaced
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public EditCommand(int targetIndex,
                       @Nullable String title, @Nullable String deadline, @Nullable String status,
                       @Nullable String schedule, @Nullable String frequency, @Nullable Set<String> tags) {
        argsContainer = new ArgumentContainer(targetIndex, title, deadline, status, schedule, frequency, tags);
    }

    @Override
    public CommandResult execute() {
        assert model != null;

        try {
            initMembers(argsContainer);
        } catch (IllegalValueException e) {
            return new CommandResult(e.getMessage());
        }

        try {
            model.deleteActivity(beforeEdit);
            model.addActivity(afterEdit);
            return new CommandResult(String.format(MESSAGE_SUCCESS, afterEdit));
        } catch (UniqueActivityList.ActivityNotFoundException pnfe) {

            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        } catch (UniqueActivityList.DuplicateActivityException e) {

            try {
                model.addActivity(beforeEdit);
            } catch (UniqueActivityList.DuplicateActivityException e1) {
                assert false : "Deleted activity should be able to be added back.";
            }
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

    private void initMembers(ArgumentContainer argsContainer) throws IllegalValueException {
        UnmodifiableObservableList<Activity> lastShownList = model.getFilteredActivityList();

        if (lastShownList.size() < argsContainer.targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            throw new IllegalValueException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        beforeEdit = lastShownList.get(argsContainer.targetIndex - 1);
        activityType = beforeEdit.getType();

        Set<Tag> tagSet = new HashSet<>();
        if(argsContainer.tags != null){
            for (String tagName : argsContainer.tags) {
                tagSet.add(new Tag(tagName));
            }
        }

        switch (activityType){
            case TASK:
            default: {
                afterEdit = new Activity(new Task(
                        argsContainer.title == null
                                ? beforeEdit.getTitle()
                                : new Title(argsContainer.title),
                        argsContainer.tags == null
                                ? beforeEdit.getTags()
                                : new UniqueTagList(tagSet),
                        argsContainer.deadline == null
                                ? beforeEdit.getDeadline().orElse(null)
                                : new Deadline(argsContainer.deadline),
                        argsContainer.schedule == null
                                ? beforeEdit.getSchedule().orElse(null)
                                : new Schedule (argsContainer.schedule),
                        argsContainer.frequency == null
                                ? beforeEdit.getFrequency().orElse(null)
                                : new Frequency(argsContainer.frequency)
                ));
            }
        }
    }

    private static class ArgumentContainer {
        public final int targetIndex;
        public final String title;
        public final String deadline;
        public final String status;
        public final String schedule;
        public final String frequency;
        public final Set<String> tags;

        public ArgumentContainer(int targetIndex, String title, String deadline, String status, String schedule, String frequency, Set<String> tags) {
            this.targetIndex = targetIndex;
            this.title = title;
            this.deadline = deadline;
            this.status = status;
            this.schedule = schedule;
            this.frequency = frequency;
            this.tags = tags;
        }
    }
}
