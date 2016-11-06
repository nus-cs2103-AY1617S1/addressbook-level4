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
    private static final String FXML = "TaskListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyTask> taskListView;
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

                        setStyle("");

                    }

                }

            };
        });

        // statusColumn.setCellValueFactory(new
        // Callback<TableColumn.CellDataFeatures<ReadOnlyTask, Status>,
        // ObservableValue<Status>>());
        // taskTable.getItems().get(0)
        idColumn.setCellValueFactory(
                column -> new ReadOnlyObjectWrapper<Number>(taskTable.getItems().indexOf(column.getValue()) + 1));

        taskNameColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getName()));

        startTimeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getStartTime()));
        startTimeColumn.setCellFactory(column -> {
            return new TableCell<ReadOnlyTask, StartTime>() {
                @Override
                protected void updateItem(StartTime item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("-fx-alignment: center;");
                    } else if (item.toString().equals("")) {
                        setText("-");
                        setStyle("-fx-alignment: center;");

                    }

                    else {

                        setStyle("-fx-alignment: center;");
                        setText(compareWithCurrentTime(item.toString()));
                        if (compareWithCurrentTime(item.toString()).startsWith(TODAY)) {
                            setStyle(
                                    "-fx-font-weight: bold; -fx-text-fill: #8B0000; -fx-font-size: 12pt; -fx-alignment: center;");
                        }
                        if (compareWithCurrentTime(item.toString()).startsWith(TOMORROW)) {
                            setStyle(
                                    "-fx-font-weight: bold; -fx-text-fill: #8B0000; -fx-font-size: 12pt; -fx-alignment: center;");
                        }
                        if (compareWithCurrentTime(item.toString()).startsWith(YESTERDAY)) {
                            setStyle(
                                    "-fx-font-weight: bold; -fx-text-fill: #8B0000; -fx-font-size: 12pt; -fx-alignment: center;");
                        }

                    }
                }
            };
        });

        endTimeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getEndTime()));
        endTimeColumn.setCellFactory(column -> {
            return new TableCell<ReadOnlyTask, EndTime>() {
                @Override
                protected void updateItem(EndTime item, boolean empty) {
                    super.updateItem(item, empty);
                    setStyle("-fx-alignment: center;");
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else if (item.toString().equals("")) {
                        setText("-");

                    } else {

                        setText(compareWithCurrentTime(item.toString()));
                        if (compareWithCurrentTime(item.toString()).startsWith(TODAY)) {
                            setStyle(
                                    "-fx-font-weight: bold; -fx-text-fill: #8B0000; -fx-font-size: 12pt; -fx-alignment: center;");
                        }
                        if (compareWithCurrentTime(item.toString()).startsWith(TOMORROW)) {
                            setStyle(
                                    "-fx-font-weight: bold; -fx-text-fill: #8B0000; -fx-font-size: 12pt; -fx-alignment: center;");
                        }
                        if (compareWithCurrentTime(item.toString()).startsWith(YESTERDAY)) {
                            setStyle(
                                    "-fx-font-weight: bold; -fx-text-fill: #8B0000; -fx-font-size: 12pt; -fx-alignment: center;");
                        }

                    }
                }
            };
        });

        dueTimeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getDeadline()));
        dueTimeColumn.setCellFactory(column -> {
            return new TableCell<ReadOnlyTask, Deadline>() {
                @Override
                protected void updateItem(Deadline item, boolean empty) {
                    super.updateItem(item, empty);
                    setStyle("-fx-alignment: center;");
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else if (item.toString().equals("")) {
                        setText("-");

                    } else {

                        setText(compareWithCurrentTime(item.toString()));
                        if (compareWithCurrentTime(item.toString()).startsWith(TODAY)) {
                            setStyle(
                                    "-fx-font-weight: bold; -fx-text-fill: #8B0000; -fx-font-size: 12pt; -fx-alignment: center;");
                        }
                        if (compareWithCurrentTime(item.toString()).startsWith(TOMORROW)) {
                            setStyle(
                                    "-fx-font-weight: bold; -fx-text-fill: #8B0000; -fx-font-size: 12pt; -fx-alignment: center;");
                        }
                        if (compareWithCurrentTime(item.toString()).startsWith(YESTERDAY)) {
                            setStyle(
                                    "-fx-font-weight: bold; -fx-text-fill: #8B0000; -fx-font-size: 12pt; -fx-alignment: center;");
                        }

                    }
                }
            };
        });

        tagColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().tagsString()));
        tagColumn.setCellFactory(column -> {
            return new TableCell<ReadOnlyTask, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setStyle("-fx-alignment: center;");
                    if (item == null || empty) {
                        setText(null);
                        setStyle("-");
                    } else {
                        // Format date.
                        setText(item);
                        setStyle("");

                    }
                }
            };
        });
        tagColumn.setStyle("-fx-alignment: center;");
        taskTable.setRowFactory(tv -> new TableRow<ReadOnlyTask>() {
            @Override
            public void updateItem(ReadOnlyTask item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                } else if (item.getStatus().getDoneStatus()) {
                    setStyle("-fx-background-color: #ADDBAC; -fx-border-color: #006400");

                }

                else if (item.getStatus().getOverdueStatus()) {
                    setStyle("-fx-background-color: #FFCCCB;  -fx-border-color: #FF0000;");
                }

                else {
                    setStyle("");

                }
            }
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

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        taskTable.setPlaceholder(new Label("Come on and add something!"));

        taskTable.setItems(taskList);

        initialize();

        disableTableColumnReordering();

        addAutoScroll(taskTable);

    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);

        placeHolderPane.getChildren().add(panel);

    }

    public String compareWithCurrentTime(String time) {
        String strDatewithTime = time.replace(" ", "T");
        LocalDateTime newTaskDateTime = LocalDateTime.parse(strDatewithTime);

        Date currentDate = new Date();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(currentDate.toInstant(), ZoneId.systemDefault());
        SimpleDateFormat timeOnly = new SimpleDateFormat("h.mm a");
        Date getTime = Date.from(newTaskDateTime.atZone(ZoneId.systemDefault()).toInstant());
        String strTime = timeOnly.format(getTime);

        if (newTaskDateTime.toLocalDate().isEqual(localDateTime.toLocalDate().minusDays(1))) {
            return YESTERDAY + strTime;
        } else if (newTaskDateTime.toLocalDate().isEqual(localDateTime.toLocalDate())) {
            return TODAY + strTime;
        } else if (newTaskDateTime.toLocalDate().isEqual(localDateTime.toLocalDate().plusDays(1))) {
            return TOMORROW + strTime;
        } else {
            SimpleDateFormat dateTime = new SimpleDateFormat("[E] d-M-yyyy h.mm a");
            Date out = Date.from(newTaskDateTime.atZone(ZoneId.systemDefault()).toInstant());
            String strDate = dateTime.format(out);

            return strDate;
        }

    }

}
