package seedu.dailyplanner.model;

import javafx.collections.ObservableList;
import seedu.dailyplanner.model.category.Category;
import seedu.dailyplanner.model.category.UniqueCategoryList;
import seedu.dailyplanner.model.task.ReadOnlyTask;
import seedu.dailyplanner.model.task.Task;
import seedu.dailyplanner.model.task.UniqueTaskList;
import seedu.dailyplanner.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the daily-planner level Duplicates are not allowed (by
 * .equals comparison)
 */
public class DailyPlanner implements ReadOnlyDailyPlanner {

    private final UniqueTaskList tasks;
    private final UniqueCategoryList cats;

    {
        tasks = new UniqueTaskList();
        cats = new UniqueCategoryList();
    }

    public DailyPlanner() {}

    /**
     * Tasks and Categories are copied into this dailyplanner
     */
    public DailyPlanner(ReadOnlyDailyPlanner toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueCatList());
    }

    /**
     * Tasks and Categories are copied into this dailyplanner
     */
    public DailyPlanner(UniqueTaskList tasks, UniqueCategoryList cats) {
        resetData(tasks.getInternalList(), cats.getInternalList());
    }

    public static ReadOnlyDailyPlanner getEmptyDailyPlanner() {
        return new DailyPlanner();
    }

//// list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }
    
    public ObservableList<Task> getPinnedTasks() {
   	return tasks.getInternalPinnedList();
       }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }

    public void setCategories(Collection<Category> categories) {
        this.cats.getInternalList().setAll(categories);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<Category> newCategories) {
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
        setCategories(newCategories);
    }

    public void resetData(ReadOnlyDailyPlanner newData) {
        resetData(newData.getTaskList(), newData.getCategoryList());
    }

//// task-level operations

    /**
     * Adds a task to the daily planner.
     * Also checks the new task's categories and updates {@link #cats} with any new categories found,
     * and updates the Category objects in the task to point to those in {@link #cats}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
        syncCategoriesWithMasterList(p);
        tasks.add(p);
    }

    /**
     * Ensures that every category in this task:
     *  - exists in the master list {@link #cats}
     *  - points to a Category object in the master list
     */
    private void syncCategoriesWithMasterList(Task task) {
        final UniqueCategoryList taskCategories = task.getCats();
        cats.mergeFrom(taskCategories);

        // Create map with values = category object references in the master list
        final Map<Category, Category> masterCategoryObjects = new HashMap<>();
        for (Category category : cats) {
            masterCategoryObjects.put(category, category);
        }

        // Rebuild the list of task categories using references from the master list
        final Set<Category> commonCategoryReferences = new HashSet<>();
        for (Category category : taskCategories) {
            commonCategoryReferences.add(masterCategoryObjects.get(category));
        }
        task.setCategories(new UniqueCategoryList(commonCategoryReferences));
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
    public void markTaskAsComplete(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        tasks.complete(key);
    }
    

    public void pinTask(ReadOnlyTask taskToPin) throws TaskNotFoundException {
        tasks.pin(taskToPin);
    }
    
    public void unpinTask(int targetIndex) {
    	tasks.unpin(targetIndex);
	}
    
    public void uncompleteTask(int targetIndex) {
        tasks.uncomplete(targetIndex);
        
    }
    
	public void resetPinBoard() {
		tasks.resetPinBoard();
	}
    
    public int indexOf(Task task) {
        return tasks.getIndexOf(task);
    }
    
    public void updatePinBoard() {
        tasks.updatePinBoard();
    }
//// category-level operations

    public void addCategory(Category t) throws UniqueCategoryList.DuplicateCategoryException {
        cats.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, " + cats.getInternalList().size() +  " cats";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public List<Category> getCategoryList() {
        return Collections.unmodifiableList(cats.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

    @Override
    public UniqueCategoryList getUniqueCatList() {
        return this.cats;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DailyPlanner // instanceof handles nulls
                && this.tasks.equals(((DailyPlanner) other).tasks)
                && this.cats.equals(((DailyPlanner) other).cats));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, cats);
    }


}