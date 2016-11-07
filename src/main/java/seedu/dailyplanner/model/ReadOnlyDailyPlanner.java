package seedu.dailyplanner.model;


import java.util.List;

import seedu.dailyplanner.model.category.Category;
import seedu.dailyplanner.model.category.UniqueCategoryList;
import seedu.dailyplanner.model.task.ReadOnlyTask;
import seedu.dailyplanner.model.task.UniqueTaskList;

/**
 * Unmodifiable view of an daily planner
 */
public interface ReadOnlyDailyPlanner {

    UniqueCategoryList getUniqueCatList();

    UniqueTaskList getUniqueTaskList();

    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of categories list
     */
    List<Category> getCategoryList();

}
