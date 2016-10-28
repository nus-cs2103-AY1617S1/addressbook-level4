package seedu.todo.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Task model
 * 
 * @@author A0093907W
 *
 */
public class Task implements CalendarItem {
    
    private String name;
    private LocalDateTime dueDate;
    private boolean isCompleted = false;
    private ArrayList<String> tagList = new ArrayList<String>();
    
    private static final int MAX_TAG_LIST_SIZE = 20;

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
    public LocalDateTime getCalendarDT() {
        return getDueDate();
    }
    
    @Override
    public void setCalendarDT(LocalDateTime date) {
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

}
