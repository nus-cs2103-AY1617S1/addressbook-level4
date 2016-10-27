package seedu.todo.guitests.guihandles;

import java.time.LocalDate;
import java.util.Optional;

import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.todo.guitests.GuiRobot;

public class TaskListHandle extends GuiHandle {

    private static final String TASKLIST_ID = "#taskListDateItemsPlaceholder";

    public TaskListHandle(GuiRobot guiRobot, Stage primaryStage, String stageTitle) {
        super(guiRobot, primaryStage, stageTitle);
    }
    
    /**
     * Returns a TaskListDateItemHandle that corresponds to the LocalDate specified.
     * If it doesn't exist, it returns null.
     */
    public TaskListDateItemHandle getTaskListDateItem(LocalDate dateToGet) {
        // TODO: Account for floating dateItems.
        
        Optional<Node> dateItemNode = guiRobot.lookup(TASKLIST_ID).queryAll().stream()
                .filter(dateItem -> new TaskListDateItemHandle(guiRobot, primaryStage, dateItem).getDate().isEqual(dateToGet))
                .findFirst();
        
        if (dateItemNode.isPresent()) {
            return new TaskListDateItemHandle(guiRobot, primaryStage, dateItemNode.get());
        } else {
            return null;
        }
    }
}
