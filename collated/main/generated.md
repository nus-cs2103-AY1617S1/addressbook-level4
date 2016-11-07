# generated
###### /java/harmony/mastermind/model/ModelManager.java
``` java
    @Override
    public void updateFilteredList(Set<String> keywords) {
        updateFilteredList(new PredicateExpression(new NameQualifier(keywords)));
        indicateTaskManagerChanged();
    }

```
###### /java/harmony/mastermind/model/Model.java
``` java
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();
    
```
###### /java/harmony/mastermind/model/task/Task.java
``` java
    public String getName() {
        return name;
    }

```
###### /java/harmony/mastermind/model/task/Task.java
``` java
    public void setName(String name) {
        this.name = name;
    }

    @Override
```
###### /java/harmony/mastermind/model/task/Task.java
``` java
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
```
###### /java/harmony/mastermind/model/task/Task.java
``` java
    public Date getStartDate() {
        return startDate;
    }

```
###### /java/harmony/mastermind/model/task/Task.java
``` java
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
```
###### /java/harmony/mastermind/model/task/Task.java
``` java
    public Date getEndDate() {
        return endDate;
    }

```
###### /java/harmony/mastermind/model/task/Task.java
``` java
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

```
###### /java/harmony/mastermind/model/task/Task.java
``` java
    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }
    
    @Override
```
###### /java/harmony/mastermind/model/task/TaskBuilder.java
``` java
    public String getName() {
        return name;
    }
```
###### /java/harmony/mastermind/model/task/TaskBuilder.java
``` java
    public Date getStartDate() {
        return startDate;
    }
```
###### /java/harmony/mastermind/model/task/TaskBuilder.java
``` java
    public Date getEndDate() {
        return endDate;
    }
```
###### /java/harmony/mastermind/model/task/TaskBuilder.java
``` java
    public Date getCreatedDate() {
        return createdDate;
    }
```
###### /java/harmony/mastermind/model/task/TaskBuilder.java
``` java
    public UniqueTagList getTags() {
        return tags;
    }
```
###### /java/harmony/mastermind/model/task/TaskBuilder.java
``` java
    public String getRecur() {
        return recur;
    }
```
###### /java/harmony/mastermind/model/task/TaskBuilder.java
``` java
    public boolean isMarked() {
        return isMarked;
    }
    
    
    
}
```
###### /java/harmony/mastermind/model/TaskManager.java
``` java
    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().sort(comparator);
        this.tasks.getInternalList().setAll(tasks);
    }

```
###### /java/harmony/mastermind/model/TaskManager.java
``` java
    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

```
###### /java/harmony/mastermind/storage/XmlSerializableTaskManager.java
``` java
    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags);
    }

}
```
###### /java/harmony/mastermind/logic/parser/Parser.java
``` java
    /**
     * Returns the specified index in the {@code command} IF a positive unsigned
     * integer is given as the index. Returns an {@code Optional.empty()}
     * otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if (!StringUtil.isUnsignedInteger(index)) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }
    
    
```
###### /java/harmony/mastermind/logic/parser/Parser.java
``` java
    /**
     * Parses arguments in the context of the find task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet, mem);
    }
    

```
