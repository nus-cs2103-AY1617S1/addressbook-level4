# generated
###### \java\harmony\mastermind\logic\parser\Parser.java
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
###### \java\harmony\mastermind\logic\parser\Parser.java
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
###### \java\harmony\mastermind\model\Model.java
``` java
    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

```
###### \java\harmony\mastermind\model\Model.java
``` java
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();
    
```
###### \java\harmony\mastermind\model\Model.java
``` java
    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

```
###### \java\harmony\mastermind\model\ModelManager.java
``` java
    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

```
###### \java\harmony\mastermind\model\ModelManager.java
``` java
    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        taskManager.resetData(newData);
        clearUndoHistory();
        clearRedoHistory();
        indicateTaskManagerChanged();
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
    }

```
###### \java\harmony\mastermind\model\ModelManager.java
``` java
    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

```
###### \java\harmony\mastermind\model\ModelManager.java
``` java
    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

```
###### \java\harmony\mastermind\model\ModelManager.java
``` java
    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

```
###### \java\harmony\mastermind\model\ModelManager.java
``` java
    private void searchTask(String keyword, Memory memory) {
        taskManager.searchTask(keyword, memory);
    }

    // ========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);

        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);

        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream().filter(keyword -> StringUtil.containsIgnoreCase(task.getName(), keyword)).findAny().isPresent();
        }

        @Override
        public String toString() {
            return "name="
                   + String.join(", ", nameKeyWords);
        }
    }

```
###### \java\harmony\mastermind\model\ModelManager.java
``` java
    public void searchTask(String input) {
        // implementing next milestone
    }
    
    @Subscribe
    private void handleTabChangedEvent(TabChangedEvent event){
        this.updateCurrentTab(event.toTabId);
    }

}
```
###### \java\harmony\mastermind\model\task\Task.java
``` java
    public String getName() {
        return name;
    }

```
###### \java\harmony\mastermind\model\task\Task.java
``` java
    public void setName(String name) {
        this.name = name;
    }

    @Override
```
###### \java\harmony\mastermind\model\task\Task.java
``` java
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
```
###### \java\harmony\mastermind\model\task\Task.java
``` java
    public Date getStartDate() {
        return startDate;
    }

```
###### \java\harmony\mastermind\model\task\Task.java
``` java
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
```
###### \java\harmony\mastermind\model\task\Task.java
``` java
    public Date getEndDate() {
        return endDate;
    }

```
###### \java\harmony\mastermind\model\task\Task.java
``` java
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

```
###### \java\harmony\mastermind\model\task\Task.java
``` java
    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }
    
    @Override
```
###### \java\harmony\mastermind\model\task\TaskBuilder.java
``` java
    public String getName() {
        return name;
    }
```
###### \java\harmony\mastermind\model\task\TaskBuilder.java
``` java
    public Date getStartDate() {
        return startDate;
    }
```
###### \java\harmony\mastermind\model\task\TaskBuilder.java
``` java
    public Date getEndDate() {
        return endDate;
    }
```
###### \java\harmony\mastermind\model\task\TaskBuilder.java
``` java
    public Date getCreatedDate() {
        return createdDate;
    }
```
###### \java\harmony\mastermind\model\task\TaskBuilder.java
``` java
    public UniqueTagList getTags() {
        return tags;
    }
```
###### \java\harmony\mastermind\model\task\TaskBuilder.java
``` java
    public String getRecur() {
        return recur;
    }
```
###### \java\harmony\mastermind\model\task\TaskBuilder.java
``` java
    public boolean isMarked() {
        return isMarked;
    }
    
    
    
}
```
###### \java\harmony\mastermind\model\TaskManager.java
``` java
    public static ReadOnlyTaskManager getEmptyTaskManager() {
        return new TaskManager();
    }

    //list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }
    
```
###### \java\harmony\mastermind\model\TaskManager.java
``` java
    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().sort(comparator);
        this.tasks.getInternalList().setAll(tasks);
    }

```
###### \java\harmony\mastermind\model\TaskManager.java
``` java
    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

```
###### \java\harmony\mastermind\model\TaskManager.java
``` java
    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Task task) {
        final UniqueTagList taskTags = task.getTags();
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        for (Tag tag : tags) {
            masterTagObjects.put(tag, tag);
        }

        // Rebuild the list of task tags using references from the master list
        final Set<Tag> commonTagReferences = new HashSet<>();
        for (Tag tag : taskTags) {
            commonTagReferences.add(masterTagObjects.get(tag));
        }
        task.setTags(new UniqueTagList(commonTagReferences));
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            this.getUniqueTaskList().getInternalList().sort(comparator);
            syncRemoveTask(key);
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
```
###### \java\harmony\mastermind\model\TaskManager.java
``` java
    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, " + tags.getInternalList().size() +  " tags,"
                + archives.getInternalList().size();
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }
    
```
###### \java\harmony\mastermind\model\TaskManager.java
``` java
    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

```
###### \java\harmony\mastermind\model\TaskManager.java
``` java
    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }

```
###### \java\harmony\mastermind\model\TaskManager.java
``` java
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }

    public void searchTask(String keyword, Memory memory) {
        ParserSearch.run(keyword, memory);
        
    }

}
```
###### \java\harmony\mastermind\storage\XmlSerializableTaskManager.java
``` java
    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags);
    }

}
```
