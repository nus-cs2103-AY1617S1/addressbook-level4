# A0141052Yunused
###### \java\seedu\task\logic\commands\AddCommand.java
``` java
    // Recurring tasks scrapped due to lack of time (and unforseen circumstances)
    /**
     * Creates a set number of Tasks, with distance between two adjacent tasks being 1 week, and adds it to the
     * TaskList.
     * @param timesToRecur number of weeks to recur the Task for
     * @throws IllegalValueException if there's a duplicate task or if 
     */
    private void createRecurringTask(int timesToRecur) throws IllegalValueException {
        for (int i = 0; i < timesToRecur; i++) {
            DateTime newOpenTime = DateTime.fromDateTimeOffset(toAdd.getOpenTime(), i * 7, ChronoUnit.DAYS);
            DateTime newCloseTime = DateTime.fromDateTimeOffset(toAdd.getCloseTime(), i * 7, ChronoUnit.DAYS);
            Task newTask = new Task(
                        toAdd.getName(),
                        newOpenTime,
                        newCloseTime,
                        false,
                        false,
                        toAdd.getTags());
            model.addTask(newTask);
        }
    }
```
