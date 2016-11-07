# A0135805Hunused
###### \java\guitests\guihandles\CommandFeedbackViewHandle.java
``` java
    //Lack of time to implement this inside GUI tests.
    /**
     * Returns true if this feedback view has error style applied.
     */
    public boolean isErrorStyleApplied() {
        return UiTestUtil.containsStyleClass(getFeedbackLabel(), "error");
    }
}
```
###### \java\guitests\guihandles\TaskCardViewHandle.java
``` java
    //For future testing use
    public Set<String> getDisplayedTags() {
        FlowPane tagsBox = (FlowPane) getNode(TITLE_PANE_ID);
        List<Node> displayedTagNodes = tagsBox.getChildren()
                .filtered(node -> node.getId().equals(TaskCardView.TAG_LABEL_ID));
        
        return displayedTagNodes.stream()
                .map(node -> ((Label) node).getText()).collect(Collectors.toSet());
    }

```
###### \java\guitests\guihandles\TodoListViewHandle.java
``` java
    //For future testing use
    /**
     * Gets the first occurring element from {@link #getImmutableTaskList()}
     * where the detail matches the {@code task} param.
     *
     * @return Returns a value bounded from 0 to length - 1.
     *         Also returns {@code NOT_FOUND} (value of -1) if not found in the list.
     */
    public int getFirstTaskIndex(ImmutableTask task) {
        List<ImmutableTask> tasks = getImmutableTaskList();
        for (int listIndex = 0; listIndex < tasks.size(); listIndex++) {
            ImmutableTask taskInList = tasks.get(listIndex);
            if (TestUtil.isShallowEqual(task, taskInList)) {
                return listIndex;
            }
        }
        return NOT_FOUND;
    }

```
