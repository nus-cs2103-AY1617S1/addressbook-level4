# A0135805Hreused
###### \java\guitests\guihandles\CommandInputViewHandle.java
``` java
    /**
     * Enters the given command in the Command Box and presses enter.
     * @param command Command text to be executed.
     */
    public void runCommand(String command) {
        enterCommand(command);
        pressEnter();
        guiRobot.sleep(GUI_SLEEP_DURATION); //Give time for the command to take effect
    }

    /* Text View Helper Methods */
    /**
     * Gets the text stored in a text area given the id to the text area
     *
     * @param textFieldId ID of the text area.
     * @return Returns the text that is contained in the text area.
     */
    private String getTextAreaText(String textFieldId) {
        return ((TextArea) getNode(textFieldId)).getText();
    }

    /**
     * Keys in the given {@code newText} to the specified text area given its ID.
     *
     * @param textFieldId ID for the text area.
     * @param newText Text to be keyed in to the text area.
     */
    private void setTextAreaText(String textFieldId, String newText) {
        guiRobot.clickOn(textFieldId);
        TextArea textArea = (TextArea)guiRobot.lookup(textFieldId).tryQuery().get();
        Platform.runLater(() -> textArea.setText(newText));
        guiRobot.sleep(GUI_SLEEP_DURATION); // so that the texts stays visible on the GUI for a short period
    }
}
```
###### \java\guitests\guihandles\TaskCardViewHandle.java
``` java
    /**
     * Search and returns exactly one matching node.
     *
     * @param fieldId Field ID to search inside the parent node.
     * @return Returns one appropriate node that matches the {@code fieldId}.
     * @throws NullPointerException when no node with {@code fieldId} can be found, intentionally breaking
     *         the tests.
     */
    @Override
    protected Node getNode(String fieldId) throws NullPointerException {
        Optional<Node> node = guiRobot.from(rootNode).lookup(fieldId).tryQuery();
        if (node.isPresent()) {
            return node.get();
        } else {
            throw new NullPointerException("Node " + fieldId + " is not found.");
        }
    }

```
###### \java\guitests\guihandles\TaskCardViewHandle.java
``` java
    /* Override Methods */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardViewHandle) {
            TaskCardViewHandle handle = (TaskCardViewHandle) obj;

            boolean hasEqualTitle = this.getDisplayedTitle()
                    .equals(handle.getDisplayedTitle());
            boolean hasEqualDescription = this.getDisplayedDescription()
                    .equals(handle.getDisplayedDescription());
            boolean hasEqualDateText = this.getDisplayedDateText()
                    .equals(handle.getDisplayedDateText());
            boolean hasEqualLocation = this.getDisplayedLocation()
                    .equals(handle.getDisplayedLocation());
            boolean hasEqualType = this.getDisplayedTypeLabel()
                    .equals(handle.getDisplayedTypeLabel());
            boolean hasEqualMoreInfoVisibility = this.getMoreInfoLabelVisibility()
                    == handle.getMoreInfoLabelVisibility();
            boolean hasEqualDescriptionBoxVisibility = this.getDescriptionBoxVisibility()
                    == handle.getDescriptionBoxVisibility();
            boolean hasEqualDateBoxVisibility = this.getDateBoxVisibility()
                    == handle.getDateBoxVisibility();
            boolean hasEqualLocationBoxVisibility = this.getLocationBoxVisibility()
                    == handle.getLocationBoxVisibility();
            boolean hasEqualPinImageVisibility = this.getPinImageVisibility()
                    == handle.getPinImageVisibility();
            boolean hasEqualSelectedStyleApplied = this.isSelectedStyleApplied()
                    == handle.isSelectedStyleApplied();
            boolean hasEqualCompletedStyleApplied = this.isCompletedStyleApplied()
                    == handle.isCompletedStyleApplied();
            boolean hasEqualOverdueStyleApplied = this.isOverdueStyleApplied()
                    == handle.isOverdueStyleApplied();

            return hasEqualTitle && hasEqualDescription && hasEqualDateText
                    && hasEqualLocation && hasEqualType && hasEqualMoreInfoVisibility
                    && hasEqualDescriptionBoxVisibility && hasEqualDateBoxVisibility
                    && hasEqualLocationBoxVisibility && hasEqualPinImageVisibility
                    && hasEqualSelectedStyleApplied && hasEqualCompletedStyleApplied
                    && hasEqualOverdueStyleApplied;
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getDisplayedTitle() + " " + getDisplayedDescription();
    }
}
```
###### \java\guitests\guihandles\TodoListViewHandle.java
``` java
    /**
     * Constructs a handle for {@link TodoListView}.
     *
     * @param guiRobot The GUI test robot.
     * @param primaryStage The main stage that is executed from the application's UI.
     */
    public TodoListViewHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    /* View Element Helper Methods */
    /**
     * Gets an instance of {@link ListView} of {@link TodoListView}
     */
    public ListView<ImmutableTask> getTodoListView() {
        return (ListView<ImmutableTask>) getNode(TODO_LIST_VIEW_ID);
    }

```
