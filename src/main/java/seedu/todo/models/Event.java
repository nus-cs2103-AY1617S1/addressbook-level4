package seedu.todo.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event implements CalendarItem {
    
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ArrayList<String> tagList;
    
    private static final int MAX_TAG_LIST_SIZE = 20;
    
    /**
     * Get the start date of an Event.
     * @return startDate
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * Set the start date of an Event.
     * @param startDate
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * Get the end date of an Event.
     * @return endDate
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Set the end date of an Event.
     * @param endDate
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public LocalDateTime getCalendarDT() {
        return getStartDate(); 
    }

    @Override
    public void setCalendarDT(LocalDateTime datetime) {
        setStartDate(datetime);
    }
    
    @Override
    public boolean isOver() {
        if (endDate == null) {
            return false;
        } else {
            return endDate.isBefore(LocalDateTime.now());
        }
    }

    @Override
    public ArrayList<String> getTagList() {
        return tagList;
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
        // TODO Auto-generated method stub
        return tagList.size();
    }

}
