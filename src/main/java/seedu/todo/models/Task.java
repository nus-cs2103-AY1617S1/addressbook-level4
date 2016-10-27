package seedu.todo.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Task implements CalendarItem {
    
    private String name;
    private LocalDateTime dueDate;
    private boolean isCompleted = false;
    private ArrayList<String> tagList;
    
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
    public ArrayList<String> getTagList() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean addTag(String tagName) {
        if(getTagListSize() < 20) {
            tagList.add(tagName);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeTag(String tagName) {
        return tagList.remove(tagName);
    }

    @Override
    public int getTagListSize() {
        return tagList.size();
    }

}
