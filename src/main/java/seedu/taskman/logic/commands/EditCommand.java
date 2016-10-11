package seedu.taskman.logic.commands;

import seedu.taskman.commons.core.Messages;
import seedu.taskman.commons.core.UnmodifiableObservableList;
import seedu.taskman.commons.exceptions.IllegalValueException;
import seedu.taskman.model.event.*;
import seedu.taskman.model.tag.Tag;
import seedu.taskman.model.tag.UniqueTagList;

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
            + "Parameters: INDEX [TITLE] [d/DEADLINE] [f/FREQUENCY] [s/SCHEDULE] [c/STATUS] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " 1 CS2103T Tutorial d/fri 11.59pm f/1w s/mon 2200 to tue 0200 t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "Task updated: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "A task with the same name already exists in the task man";

    private final Activity beforeEdit;
    private final Activity afterEdit;
    private final Activity.ActivityType activityType;

    /**
     * Convenience constructor using raw values.
     * Fields which are null are assumed not to be replaced
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public EditCommand(int targetIndex, String title, String deadline, String status, String frequency, String schedule, Set<String> tags)
            throws IllegalValueException {
        UnmodifiableObservableList<Activity> lastShownList = model.getFilteredActivityList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            throw new IllegalValueException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        beforeEdit = lastShownList.get(targetIndex - 1);

        activityType = beforeEdit.getType();


        Set<Tag> tagSet = null;
        if(tags != null){
            tagSet = new HashSet<>();
            for (String tagName : tags) {
                tagSet.add(new Tag(tagName));
            }
        }

        //todo: update Task constructor to accept status
        switch (activityType){
            case TASK:
            default: {
                afterEdit = new Activity( new Task(
                        title == null ? beforeEdit.getTitle() : new Title(title),
                        tagSet == null ? beforeEdit.getTags() : new UniqueTagList(tagSet),
                        deadline == null ?
                                (beforeEdit.getDeadline().isPresent() ? beforeEdit.getDeadline().get() : null)
                                : new Deadline(deadline),
                        frequency == null ?
                                (beforeEdit.getFrequency().isPresent() ? beforeEdit.getFrequency().get() : null)
                                : new Frequency(frequency),
                        new Schedule(schedule)
                    )
                );
            }
        }
    }

    @Override
    public CommandResult execute() {
        assert model != null;

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
            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        }

    }

}
