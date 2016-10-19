package seedu.taskitty.commons.util;

import javafx.scene.image.Image;
import seedu.taskitty.MainApp;
import seedu.taskitty.commons.core.UnmodifiableObservableList;
import seedu.taskitty.model.Model;
import seedu.taskitty.model.task.ReadOnlyTask;

/**
 * A container for App specific utility functions
 */
public class AppUtil {

    public static Image getImage(String imagePath) {
        assert imagePath != null;
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }
    
    public static UnmodifiableObservableList<ReadOnlyTask> getCorrectListBasedOnCategoryIndex(Model model, int categoryIndex) {
        assert categoryIndex == 0 || categoryIndex == 1 || categoryIndex == 2;
        if (categoryIndex == 0) {
            return model.getFilteredTodoList();
        }
        else if (categoryIndex == 1) {
            return model.getFilteredDeadlineList();
        }
        else {
            return model.getFilteredEventList();
        }
    }

}
