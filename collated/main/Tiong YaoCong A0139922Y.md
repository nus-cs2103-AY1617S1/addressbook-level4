# Tiong YaoCong A0139922Y
###### \java\seedu\todo\models\CalendarItem.java
``` java
     */
    public ArrayList<String> getTagList();
   
    /**
     * Add a new tag in the list of tag of the calendar item. 
     * 
     * @param tagName <String>
     * @return true if it has not reached the max tag list size, false if tag list already reach the max size
```
###### \java\seedu\todo\models\CalendarItem.java
``` java
     */
    public boolean addTag(String tagName);
    
    /**
     * Remove a existing tag in the tag list of tag of the calendar item. 
     * 
     * @param tagName <String>
     * @return true if tagName is removed successfully, false if failed to remove tagName due to unable to find
```
###### \java\seedu\todo\models\CalendarItem.java
``` java
     */
    public boolean removeTag(String tagName);

}
```
###### \java\seedu\todo\models\Event.java
``` java
    public ArrayList<String> getTagList() {
        return tagList;
    }

    @Override
```
###### \java\seedu\todo\models\Event.java
``` java
    public boolean addTag(String tagName) {
        if(tagList.size() < MAX_TAG_LIST_SIZE) {
            tagList.add(tagName);
            return true;
        } else {
            return false;
        }
    }

    @Override
```
###### \java\seedu\todo\models\Event.java
``` java
    public boolean removeTag(String tagName) {
        return tagList.remove(tagName);
    }
    
    /**
```
###### \java\seedu\todo\models\Task.java
``` java
    public ArrayList<String> getTagList() {
        return tagList;
    }

    @Override
```
###### \java\seedu\todo\models\Task.java
``` java
    public boolean addTag(String tagName) {
        if(tagList.size() < MAX_TAG_LIST_SIZE) {
            tagList.add(tagName);
            return true;
        } else {
            return false;
        }
    }

    @Override
```
###### \java\seedu\todo\models\Task.java
``` java
    public boolean removeTag(String tagName) {
        return tagList.remove(tagName);
    }
    
    
    /**
```
###### \java\seedu\todo\models\TodoListDB.java
``` java
     */   
    public List<Event> getAllCurrentEvents() {
        ArrayList<Event> currentEvents = new ArrayList<Event>();
        Iterator<Event> iterator = events.iterator();
        while (iterator.hasNext()) {
            Event currEvent = iterator.next();
            if (!currEvent.isOver()) {
                currentEvents.add(currEvent);
            }
        }
        return currentEvents;
    }
    
    /**
     * Get a list of Incomplete Tasks in the DB.
     * 
     * @return tasks
```
###### \java\seedu\todo\models\TodoListDB.java
``` java
     */
    public List<Task> getIncompleteTasksAndTaskFromTodayDate() {
        ArrayList<Task> incompleteTasks = new ArrayList<Task>();
        Iterator<Task> iterator = tasks.iterator();
        LocalDateTime todayDate = DateUtil.floorDate(LocalDateTime.now());
        while (iterator.hasNext()) {
            Task currTask = iterator.next();
            if (!currTask.isCompleted()) { //if incompleted
                incompleteTasks.add(currTask);
            } else {
                if (currTask.getDueDate() != null && DateUtil.floorDate(currTask.getDueDate()).compareTo(todayDate) >= 0) {
                    incompleteTasks.add(currTask);
                }
            }
        }
        return incompleteTasks;
    }

    
}
```
