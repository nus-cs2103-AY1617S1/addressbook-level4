# A0138967Junused
###### \java\seedu\todo\model\ModelManager.java
``` java
    @Override
    public void updateFilteredTaskListTodayDate(LocalDateTime datetime){
        updateFilteredTaskList(new PredicateExpression(new TodayDateQualifier(datetime)));
    }
```
