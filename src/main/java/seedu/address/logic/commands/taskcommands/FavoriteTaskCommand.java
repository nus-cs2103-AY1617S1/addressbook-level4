package seedu.address.logic.commands.taskcommands;

import javafx.collections.ObservableList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.HideHelpRequestEvent;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.task.Task;

/**
 * Favorites a task identified using it's last displayed index from TaskManager.
 */
public class FavoriteTaskCommand extends TaskCommand {

	public static final String COMMAND_WORD = "favorite";

    public static final String MESSAGE_USAGE = "Favorite a task: \t" + "favorite <index>";

    public static final String MESSAGE_FAVORITE_TASK_SUCCESS = "Favorited task: %1$s";

    public final int targetIndex;

    public FavoriteTaskCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {

	    ObservableList<Task> lastShownList = model.getCurrentFilteredTasks();

        if (lastShownList.size() < targetIndex || targetIndex <= 0) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToFavorite = lastShownList.get(targetIndex - 1);
        model.favoriteTask(taskToFavorite);
        EventsCenter.getInstance().post(new HideHelpRequestEvent());
        return new CommandResult(String.format(MESSAGE_FAVORITE_TASK_SUCCESS, taskToFavorite));
    }

}
