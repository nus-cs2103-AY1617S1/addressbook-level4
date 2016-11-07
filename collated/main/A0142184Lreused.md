# A0142184Lreused
###### \java\seedu\address\ui\ListPanel.java
``` java
	public static ListPanel loadAliasList(Stage primaryStage, AnchorPane taskListPlaceholder,
                                       ObservableList<ReadOnlyAlias> aliasList) {
    	ListPanel taskListPanel = UiPartLoader.loadUiPart(primaryStage, taskListPlaceholder, new ListPanel());
        taskListPanel.configureAlias(aliasList);
        taskListPanel.hideTaskListView();
        return taskListPanel;
    }
    
    private void configureAlias(ObservableList<ReadOnlyAlias> aliasList) {
        setAliasConnections(aliasList);
        addToPlaceholder();
    }
    
    private void setAliasConnections(ObservableList<ReadOnlyAlias> aliasList) {
        aliasListView.setItems(aliasList);
        aliasListView.setCellFactory(listView -> new AliasListViewCell());
    }
    
```
###### \java\seedu\address\ui\ListPanel.java
``` java
    class AliasListViewCell extends ListCell<ReadOnlyAlias> {

        public AliasListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyAlias alias, boolean empty) {
            super.updateItem(alias, empty);

            if (empty || alias == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(AliasCard.load(alias, getIndex() + 1).getLayout());
            }
        }
     }
}
```
