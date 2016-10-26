# A0144919Wunused
###### \java\seedu\tasklist\model\ModelManager.java
``` java
    //unused as now, the task list is sorted without using these comparators
    private static class Comparators {
        public static Comparator<Task> DATE_TIME = new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.getStartTime().compareTo(o2.getStartTime());
            }
        };
        public static Comparator<Task> PRIORITY = new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                // extract only the date without time from the card string for
                // start time
                String date1 = o1.getStartTime().toCardString().split(" ")[0];
                String date2 = o2.getStartTime().toCardString().split(" ")[0];
                if (date1.equals(date2))
                    return o1.getPriority().compareTo(o2.getPriority());
                else
                    return o1.getStartTime().compareTo(o2.getStartTime());
            }
        };
    }
```
