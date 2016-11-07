# generated
###### /java/harmony/mastermind/testutil/TypicalTestTasks.java
``` java
    public TaskManager getTypicalTaskManager(){
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }
}
```
###### /java/harmony/mastermind/testutil/TestTask.java
``` java
    @Override
    public boolean isDue() {
        return false;
    }

    @Override
    public boolean isHappening() {
        return false;
    }

    @Override
    public Duration getDueDuration() {
        return null;
    }
    
    @Override
    public Duration getEventDuration(){
        return null;
    } 
}
```
###### /java/harmony/mastermind/testutil/TestUtil.java
``` java
    public static List<Task> generateSampleTaskData() {
        return Arrays.asList(sampleTaskData);
    }

```
###### /java/harmony/mastermind/testutil/TestUtil.java
``` java
    public static Tag[] getTagList(String tags) {

        if (tags.equals("")) {
            return new Tag[]{};
        }

        final String[] split = tags.split(", ");

        final List<Tag> collect = Arrays.asList(split).stream().map(e -> {
            try {
                return new Tag(e.replaceFirst("Tag: ", ""));
            } catch (IllegalValueException e1) {
                assert false;
                return null;
            }
        }).collect(Collectors.toList());

        return collect.toArray(new Tag[split.length]);
    }

}
```
