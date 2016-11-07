# A0130853Lreused
###### \java\seedu\taskitty\ui\TaskListPanel.java
``` java
/**
 * Base class for the 3 panels containing the list of tasks.
 */
public class TaskListPanel extends UiPart {
    
```
###### \java\seedu\taskitty\ui\TaskListPanel.java
``` java
    protected VBox panel;
    protected AnchorPane placeHolderPane;
    
    private static final String FXML = "TaskListPanel.fxml";

    @FXML
    private Label header;
    
    @FXML
    private ImageView listIcon;
    
    @FXML
    private ListView<ReadOnlyTask> taskListView;

    @Override
    public void setNode(Node node) {
        panel = (VBox) node;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }
    
    public void configure(ObservableList<ReadOnlyTask> taskList, int type) {
        initializeListView(type);
        setConnections(taskListView, taskList);
        addToPlaceholder();
    }
    
```
###### \java\seedu\taskitty\ui\TaskListPanel.java
``` java
    
    public static TaskListPanel load(Stage primaryStage, AnchorPane taskListPlaceholder,
                                       ObservableList<ReadOnlyTask> taskList, TaskListPanel listPanel, int type) {
        TaskListPanel taskListPanel =  UiPartLoader.loadUiPart(primaryStage, taskListPlaceholder, listPanel);
        taskListPanel.configure(taskList, type);
        return taskListPanel;
    }    
    
    private void setConnections(ListView<ReadOnlyTask> taskListView, ObservableList<ReadOnlyTask> taskList) {
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    class TaskListViewCell extends ListCell<ReadOnlyTask> {

        @Override
        public void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(TaskCard.load(task, getIndex() + 1).getLayout());       
            }
        }
    }
    
    @Override
    public String getFxmlPath() {
        return FXML;
    }

}
```
