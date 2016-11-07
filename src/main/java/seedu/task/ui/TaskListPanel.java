package seedu.task.ui;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Name;
import seedu.task.model.task.StartTime;
import seedu.task.model.task.EndTime;
import seedu.task.model.task.Deadline;
import seedu.task.model.task.Status;

/**
 * Panel containing the list of tasks.
 */
// @@author A0133369B
public class TaskListPanel extends UiPart {
    private static final String FX_STYLE_ALERT = "-fx-font-weight: bold; -fx-text-fill: #8B0000; -fx-font-size: 12pt; -fx-alignment: center;";
    private static final String DASH = "-";
    private static final String EMPTY_STRING = "";
    private static final String FX_ALIGNMENT_CENTER = "-fx-alignment: center;";
    private static final String FXML = "TaskListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private TableView<ReadOnlyTask> taskTable;
    @FXML
    private TableColumn<ReadOnlyTask, Number> idColumn;
    @FXML
    private TableColumn<ReadOnlyTask, Name> taskNameColumn;
    @FXML
    private TableColumn<ReadOnlyTask, StartTime> startTimeColumn;
    @FXML
    private TableColumn<ReadOnlyTask, EndTime> endTimeColumn;
    @FXML
    private TableColumn<ReadOnlyTask, Deadline> dueTimeColumn;
    @FXML
    private TableColumn<ReadOnlyTask, String> tagColumn;
    @FXML
    private TableColumn<ReadOnlyTask, Status> statusColumn;

    private static final String YESTERDAY = "YESTERDAY AT ";
    private static final String TODAY = "TODAY AT ";
    private static final String TOMORROW = "TOMORROW AT ";

    @Override
    public void setNode(Node node) {
        panel = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public static TaskListPanel load(Stage primaryStage, AnchorPane taskListPlaceholder,
            ObservableList<ReadOnlyTask> taskList) {
        TaskListPanel taskListPanel = UiPartLoader.loadUiPart(primaryStage, taskListPlaceholder, new TaskListPanel());
        taskListPanel.configure(taskList);

        return taskListPanel;
    }

    // table initialization
    private void initialize() {

        setFavColumn();
        setIdColumn();
        setTaskNameColumn();
        setStartTimeColumn();
        setEndTimeColumn();
        setDeadlineColumn();
        setTagColumn();

        updateTableRowColumn();
    }

    private void setFavColumn() {
        statusColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getStatus()));
        statusColumn.setCellFactory(column -> {
            return new TableCell<ReadOnlyTask, Status>() {
                @Override
                protected void updateItem(Status item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("-fx-background-color");
                    } else if (item.getFavoriteStatus()) {

                        setStyle("-fx-background-color: yellow");

                    } else {

                        setStyle(EMPTY_STRING);

                    }

                }

            };
        });
    }

    // id column initialization
    private void setIdColumn() {
        idColumn.setCellValueFactory(
                column -> new ReadOnlyObjectWrapper<Number>(taskTable.getItems().indexOf(column.getValue()) + 1));
    }

    // task name column initialization
    private void setTaskNameColumn() {
        taskNameColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getName()));
    }
    // @@author

    private void updateTableRowColumn() {
        // @@author A0147335E
        taskTable.setRowFactory(tv -> new TableRow<ReadOnlyTask>() {
            @Override
            public void updateItem(ReadOnlyTask item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle(EMPTY_STRING);
                } else if (item.getStatus().getDoneStatus()) {
                    setStyle("-fx-background-color: #E2F0B6; -fx-border-color: #E2F0B6");

                }

                else if (item.getStatus().getOverdueStatus()) {
                    setStyle("-fx-background-color: #FFE4E4;  -fx-border-color: #FFE4E4;");
                }

                else {
                    setStyle(EMPTY_STRING);

                }
            }
        });
        // @@author
    }
    
    

	// @@author A0133369B
    // tag column initialization
    private void setTagColumn() {
        tagColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().tagsString()));
        tagColumn.setCellFactory(column -> {
            return new TableCell<ReadOnlyTask, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setStyle(FX_ALIGNMENT_CENTER);
                    if (item == null || empty) {
                        setText(null);
                        setStyle(DASH);
                    } else {
                        // Format date.
                        setText(item);
                        setStyle(EMPTY_STRING);

                    }
                }
            };
        });
        tagColumn.setStyle(FX_ALIGNMENT_CENTER);
    }

    //due time column initialization
    private void setDeadlineColumn() {

        dueTimeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getDeadline()));

        dueTimeColumn.setCellFactory(column -> {
            return new TableCell<ReadOnlyTask, Deadline>() {
                @Override
                protected void updateItem(Deadline item, boolean empty) {
                    super.updateItem(item, empty);
                    setStyle(FX_ALIGNMENT_CENTER);
                    if (item == null || empty) {
                        setText(null);
                        setStyle(EMPTY_STRING);
                    } else if (item.toString().equals(EMPTY_STRING)) {
                        setText(DASH);

                    } else {

                        setText(compareWithCurrentTime(item.toString()));
                        displayDeadlineAlert(item);

                    }
                }

                private void displayDeadlineAlert(Deadline item) {
                    if (compareWithCurrentTime(item.toString()).startsWith(TODAY)) {
                        setStyle(FX_STYLE_ALERT);
                    }
                    if (compareWithCurrentTime(item.toString()).startsWith(TOMORROW)) {
                        setStyle(FX_STYLE_ALERT);
                    }
                    if (compareWithCurrentTime(item.toString()).startsWith(YESTERDAY)) {
                        setStyle(FX_STYLE_ALERT);
                    }
                }
            };
        });
    }

    // end time column initialization
    private void setEndTimeColumn() {

        endTimeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getEndTime()));
        endTimeColumn.setCellFactory(column -> {
            return new TableCell<ReadOnlyTask, EndTime>() {
                @Override
                protected void updateItem(EndTime item, boolean empty) {
                    super.updateItem(item, empty);

                    setStyle(FX_ALIGNMENT_CENTER);
                    if (item == null || empty) {
                        setText(null);
                    } else if (item.toString().equals(EMPTY_STRING)) {
                        setText(DASH);

                    } else {

                        setText(compareWithCurrentTime(item.toString()));
                        displayEndTimeAlert(item);

                    }
                }

                private void displayEndTimeAlert(EndTime item) {
                    if (compareWithCurrentTime(item.toString()).startsWith(TODAY)) {
                        setStyle(FX_STYLE_ALERT);
                    }
                    if (compareWithCurrentTime(item.toString()).startsWith(TOMORROW)) {
                        setStyle(FX_STYLE_ALERT);
                    }
                    if (compareWithCurrentTime(item.toString()).startsWith(YESTERDAY)) {
                        setStyle(FX_STYLE_ALERT);
                    }
                }
            };
        });
    }

    // start time column initialization
    private void setStartTimeColumn() {
        startTimeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getStartTime()));
        startTimeColumn.setCellFactory(column -> {
            return new TableCell<ReadOnlyTask, StartTime>() {
                @Override
                protected void updateItem(StartTime item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else if (item.toString().equals(EMPTY_STRING)) {
                        setText(DASH);
                        setStyle(FX_ALIGNMENT_CENTER);
                    } else {

                        setStyle(FX_ALIGNMENT_CENTER);
                        setText(compareWithCurrentTime(item.toString()));

                        // set font alerts for today,yesterday, and tomorrow
                        displayStartTimeAlert(item);

                    }
                }

                private void displayStartTimeAlert(StartTime item) {
                    if (compareWithCurrentTime(item.toString()).startsWith(TODAY)) {
                        setStyle(FX_STYLE_ALERT);
                    }
                    if (compareWithCurrentTime(item.toString()).startsWith(TOMORROW)) {
                        setStyle(FX_STYLE_ALERT);
                    }
                    if (compareWithCurrentTime(item.toString()).startsWith(YESTERDAY)) {
                        setStyle(FX_STYLE_ALERT);
                    }
                }
            };
        });
    }

    // prenvent columns reordering
    private void disableTableColumnReordering() {
        TableColumn[] columns = { statusColumn, idColumn, taskNameColumn, startTimeColumn, endTimeColumn, dueTimeColumn,
                tagColumn };
        taskTable.getColumns().clear();

        taskTable.getColumns().addListener(new ListChangeListener<TableColumn>() {
            public boolean reordered = false;

            @Override
            public void onChanged(Change change) {

                change.next();
                if (change.wasReplaced() && !reordered) {
                    reordered = true;
                    taskTable.getColumns().setAll(columns);
                    reordered = false;
                }
            }
        });
        taskTable.getColumns().addAll(columns);
    }

    // auto scroll to a specific index
    public <S> void addAutoScroll(final TableView<ReadOnlyTask> view) {
        if (view == null) {
            throw new NullPointerException();
        }

        taskTable.getItems().addListener((ListChangeListener<ReadOnlyTask>) (c -> {
            c.next();
            final int size = view.getItems().size();
            if (size > 0) {
                view.scrollTo(c.getFrom());
            }

            statusColumn.setVisible(false);
            statusColumn.setVisible(true);

            taskTable.getSelectionModel().select(c.getFrom());
        }));
    }

    private void configure(ObservableList<ReadOnlyTask> taskList) {
        setConnections(taskList);
        addToPlaceholder();

    }

    // connect tableview with observable list
    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        taskTable.setPlaceholder(new Label("Come on and add something!"));

        taskTable.setItems(taskList);

        initialize();

        disableTableColumnReordering();

        addAutoScroll(taskTable);

    }

    //add v box to main window anchor panel
    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);

        placeHolderPane.getChildren().add(panel);

    }
    
    //@@ author

    // @@author A0147335E
    public String compareWithCurrentTime(String time) {

        // parse time
        String strDatewithTime = time.replace(" ", "T");
        LocalDateTime newTaskDateTime = LocalDateTime.parse(strDatewithTime);

        // current time
        Date currentDate = new Date();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(currentDate.toInstant(), ZoneId.systemDefault());

        // time format
        SimpleDateFormat timeOnly = new SimpleDateFormat("h.mm a");
        Date getTime = Date.from(newTaskDateTime.atZone(ZoneId.systemDefault()).toInstant());
        String strTime = timeOnly.format(getTime);

        // compare task time with current time
        if (isYesterday(newTaskDateTime, localDateTime)) {
            return YESTERDAY + strTime;
        }

        else if (isToday(newTaskDateTime, localDateTime)) {
            return TODAY + strTime;
        }

        else if (isTomorrow(newTaskDateTime, localDateTime)) {
            return TOMORROW + strTime;
        }

        else {
            // date format for start time, end time, and deadline
            SimpleDateFormat dateTime = new SimpleDateFormat("[E] d-M-yyyy h.mm a");
            Date out = Date.from(newTaskDateTime.atZone(ZoneId.systemDefault()).toInstant());
            String strDate = dateTime.format(out);
            return strDate;
        }

    }

    private boolean isTomorrow(LocalDateTime newTaskDateTime, LocalDateTime localDateTime) {
        return newTaskDateTime.toLocalDate().isEqual(localDateTime.toLocalDate().plusDays(1));
    }

    private boolean isToday(LocalDateTime newTaskDateTime, LocalDateTime localDateTime) {
        return newTaskDateTime.toLocalDate().isEqual(localDateTime.toLocalDate());
    }

    private boolean isYesterday(LocalDateTime newTaskDateTime, LocalDateTime localDateTime) {
        return newTaskDateTime.toLocalDate().isEqual(localDateTime.toLocalDate().minusDays(1));
    }
    // @@author
}
