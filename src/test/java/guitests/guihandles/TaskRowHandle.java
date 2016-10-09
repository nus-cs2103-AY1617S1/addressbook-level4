package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;
import seedu.taskman.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task row in the task list panel.
 */
public class TaskRowHandle extends GuiHandle {
    private ReadOnlyTask task;

    public TaskRowHandle(GuiRobot guiRobot, Stage primaryStage, ReadOnlyTask task){
        super(guiRobot, primaryStage, null);
        this.task = task;
    }

    public String getFullTitle() {
        return task.getTitle().title;
    }

    public String getAddress() {
        return task.getAddress().value;
    }

    public String getDeadline() {
        return task.getDeadline().value;
    }

    public String getEmail() {
        return task.getEmail().value;
    }

    public boolean isSameTask(ReadOnlyTask task){
        return getFullTitle().equals(task.getTitle().title) && getDeadline().equals(task.getDeadline().value)
                && getEmail().equals(task.getEmail().value) && getAddress().equals(task.getAddress().value);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskRowHandle) {
            TaskRowHandle handle = (TaskRowHandle) obj;
            return getFullTitle().equals(handle.getFullTitle())
                    && getAddress().equals(handle.getAddress()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullTitle() + " " + getAddress();
    }
}
