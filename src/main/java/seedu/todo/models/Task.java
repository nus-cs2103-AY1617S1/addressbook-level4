package seedu.todo.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * @@author A0093907W
 * 
 * Task model
 */
public class Task implements CalendarItem {
    
    private String name;
    private LocalDateTime dueDate;
    private boolean isCompleted = false;
    private ArrayList<String> tagList = new ArrayList<String>();
    
    public static final int MAX_TAG_LIST_SIZE = 20;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the due date of a Task.
     * @return dueDate
     */
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    /**
     * Set the due date of a Task.
     * @param dueDate
     */
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
    
    @Override
    public LocalDateTime getCalendarDateTime() {
        return getDueDate();
    }
    
    @Override
    public void setCalendarDateTime(LocalDateTime date) {
        setDueDate(date);
    }
    
    @Override
    public boolean isOver() {
        if (dueDate == null) {
            return false;
        } else {
            return dueDate.isBefore(LocalDateTime.now());
        }
    }

    /**
     * Returns true if the Task is completed, false otherwise.
     * @return isCompleted
     */
    public boolean isCompleted() {
        return isCompleted;
    }
    
    /**
     * Marks a Task as completed.
     */
    public void setCompleted() {
        this.isCompleted = true;
    }

    /**
     * Marks a Task as incomplete.
     */
    public void setIncomplete() {
        this.isCompleted = false;
    }

    @Override
    //@@author Tiong YaoCong A0139922Y
    public ArrayList<String> getTagList() {
        return tagList;
    }

    @Override
    //@@author Tiong YaoCong A0139922Y
    public boolean addTag(String tagName) {
        if(tagList.size() < MAX_TAG_LIST_SIZE) {
            tagList.add(tagName);
            return true;
        } else {
            return false;
        }
    }

    @Override
    //@@author Tiong YaoCong A0139922Y
    public boolean removeTag(String tagName) {
        return tagList.remove(tagName);
    }
    
    
    /**
     * @@author A0093907W
     * 
     * Filtering methods intended to replace hacky one-filter-method-per-permutation from Yaocong.
     * Seriously, why??!!
     */
    public static List<Task> where(List<Predicate<Task>> predicates) {
        List<Task> result = TodoListDB.getInstance().getAllTasks();
        for (Predicate<Task> predicate : predicates) {
            filter(predicate, result);
        }
        return result;
    }
    
    public static Predicate<Task> predByName(String name) {
        return (Task task) -> Pattern.compile(String.format("\\b%s", name), Pattern.CASE_INSENSITIVE)
                .matcher(task.getName()).find();
    }
    
    public static Predicate<Task> predByNameAny(List<String> names) {
        return (Task task) -> {
            for (String name : names) {
                if (predByName(name).test(task)) {
                    return true;
                }
            }
            return false;
        };
    }
    
    public static Predicate<Task> predBeforeDueDate(LocalDateTime date) {
        return (Task task) -> task.getDueDate() != null && task.getDueDate().isBefore(date);
    }
    
    public static Predicate<Task> predAfterDueDate(LocalDateTime date) {
        return (Task task) -> task.getDueDate() != null && task.getDueDate().isAfter(date);
    }
    
    public static Predicate<Task> predCompleted(boolean completed) {
        return (Task task) -> task.isCompleted() == completed;
    }
    
    public static Predicate<Task> predTag(String tag) {
        return (Task task) -> {
            for (String currTag : task.getTagList()) {
                if (currTag.toLowerCase().equals(tag.toLowerCase())) {
                    return true;
                }
            }
            return false;
        };
    }
    
    public static void filter(Predicate<Task> predicate, List<Task> taskList) {
        for (int i = taskList.size() - 1; i >= 0; i--) {
            if (!predicate.test(taskList.get(i))) {
                taskList.remove(i);
            }
        }
    }

}
