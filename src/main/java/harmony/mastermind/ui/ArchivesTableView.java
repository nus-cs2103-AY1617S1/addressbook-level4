package harmony.mastermind.ui;

import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.shade.org.apache.commons.lang.time.DurationFormatUtils;

import harmony.mastermind.commons.util.FxViewUtil;
import harmony.mastermind.logic.Logic;
import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.task.ReadOnlyTask;
import javafx.beans.DefaultProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

//@@author A0138862W
public class ArchivesTableView extends UiPart {
    
    private static final double WIDTH_MULTIPLIER_INDEX = 0.042;
    private static final double WIDTH_MULTIPLIER_NAME = 0.285;
    private static final double WIDTH_MULTIPLIER_STARTDATE = 0.18;
    private static final double WIDTH_MULTIPLIER_ENDDATE = 0.18;
    private static final double WIDTH_MULTIPLIER_TAGS = 0.18;
    private static final double WIDTH_MULTIPLIER_RECUR = 0.1;
    
    private static final String FXML = "ArchivesTableView.fxml";
    
    private static final PrettyTime prettyTime = new PrettyTime();
    
    private AnchorPane placeholder;
    
    private Logic logic;
    
    @FXML
    private TableView<ReadOnlyTask> archivesTableView;

    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> indexColumn;

    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> nameColumn;

    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> startDateColumn;

    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> endDateColumn;

    @FXML
    private TableColumn<ReadOnlyTask, ReadOnlyTask> tagsColumn;

    @FXML
    private TableColumn<ReadOnlyTask, Boolean> recurColumn;

    
    public static ArchivesTableView load(Stage primaryStage, AnchorPane defaultTableViewPlaceholder, Logic logic){
        ArchivesTableView ui = UiPartLoader.loadUiPart(primaryStage, defaultTableViewPlaceholder, new ArchivesTableView());
        ui.configure(logic);
        return ui;
    }
    
    // @@author A0124797R
    /**
     * Initialize the displaying of tabs
     */
    @FXML
    private void initialize() {
        this.initIndex();
        this.initName();
        this.initStartDate();
        this.initEndDate();
        this.initTags();
        this.initRecur();
    }
    
    
    @Override
    public void setPlaceholder(AnchorPane placeholder){
        this.placeholder = placeholder;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void setNode(Node node) {
        this.archivesTableView = (TableView<ReadOnlyTask>) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
    
    protected void configure(Logic logic){
        this.logic = logic;
        this.addToPlaceholder();
        this.registerAsAnEventHandler(this);
    }

    private void addToPlaceholder(){
        placeholder.getChildren().add(archivesTableView);
        FxViewUtil.applyAnchorBoundaryParameters(archivesTableView, 0, 0, 0, 0);
    }
    
    public TableView<ReadOnlyTask> getTableView(){
        return archivesTableView;
    }

    /**
     * Initializes the indexing of tasks
     */
    // @@author A0138862W
    protected void initIndex() {
        indexColumn.prefWidthProperty().bind(archivesTableView.widthProperty().multiply(WIDTH_MULTIPLIER_INDEX));
        indexColumn.setCellFactory(column -> new TableCell<ReadOnlyTask, ReadOnlyTask>() {
            @Override
            public void updateIndex(int index) {
                super.updateIndex(index);

                Text indexText = new Text(Integer.toString(index + 1)+ ".");
                indexText.getStyleClass().add("index-column");

                if (isEmpty()
                    || index < 0) {
                    this.setGraphic(null);
                } else {
                    this.setGraphic(indexText);
                }
            }

        });
    }

    /**
     * Initialize the Names of the tasks
     */
    // @@author A0138862W
    protected void initName() {
        nameColumn.prefWidthProperty().bind(archivesTableView.widthProperty().multiply(WIDTH_MULTIPLIER_NAME));
        nameColumn.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue()));

        nameColumn.setCellFactory(col -> new TableCell<ReadOnlyTask, ReadOnlyTask>() {

            @Override
            public void updateItem(ReadOnlyTask readOnlyTask, boolean isEmpty) {
                super.updateItem(readOnlyTask, isEmpty);

                if (!isEmpty()) {

                    VBox vBox = new VBox(3);

                    Text taskName = generateStyledText(readOnlyTask, readOnlyTask.getName());
                    taskName.getStyleClass().add("task-name-column");
                    vBox.getChildren().add(taskName);

                    HBox hBox = new HBox(5);

                    Button status = new Button();
                    if (readOnlyTask.isHappening()) {
                        status.setText("HAPPENING");
                        status.getStyleClass().add("tag-happening");
                        hBox.getChildren().add(status);
                    } else if (readOnlyTask.isDue()) {
                        status.setText("DUE");
                        status.getStyleClass().add("tag-overdue");
                        hBox.getChildren().add(status);
                    }

                    if (readOnlyTask.isEvent()) {
                        Button eventDuration = new Button();
                        eventDuration.setText("DURATION: "
                                              + DurationFormatUtils.formatDurationWords(readOnlyTask.getEventDuration().toMillis(), true, true).toUpperCase());
                        eventDuration.getStyleClass().add("tag-event-duration");
                        hBox.getChildren().add(eventDuration);
                    } else if (readOnlyTask.isDeadline()
                               && !readOnlyTask.isDue()) {
                        Button dueDuration = new Button();
                        dueDuration.setText("DUE IN "
                                            + DurationFormatUtils.formatDurationWords(readOnlyTask.getDueDuration().toMillis(), true, true));
                        dueDuration.getStyleClass().add("tag-due-duration");
                        hBox.getChildren().add(dueDuration);
                    }

                    vBox.getChildren().add(hBox);

                    this.setGraphic(vBox);
                    this.setPrefHeight(50);

                } else {
                    this.setGraphic(null);
                }

            }
        });

    }

    /**
     * Initialize the start dates of the tasks
     */
    // @@author A0138862W
    protected void initStartDate() {
        startDateColumn.prefWidthProperty().bind(archivesTableView.widthProperty().multiply(WIDTH_MULTIPLIER_STARTDATE));
        startDateColumn.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue()));

        startDateColumn.setCellFactory(col -> new TableCell<ReadOnlyTask, ReadOnlyTask>() {

            @Override
            public void updateItem(ReadOnlyTask readOnlyTask, boolean isEmpty) {
                super.updateItem(readOnlyTask, isEmpty);
                if (!isEmpty()
                    && readOnlyTask.getStartDate() != null) {

                    TextFlow textFlow = new TextFlow();

                    Text prettyDate = generateStyledText(readOnlyTask, prettyTime.format(readOnlyTask.getStartDate()));
                    prettyDate.getStyleClass().add("pretty-date");

                    Text lineBreak = new Text("\n\n");
                    lineBreak.setStyle("-fx-font-size:2px;");

                    Text uglyDate = generateStyledText(readOnlyTask, readOnlyTask.parse(readOnlyTask.getStartDate()));
                    uglyDate.getStyleClass().add("ugly-date");

                    textFlow.getChildren().add(prettyDate);
                    textFlow.getChildren().add(lineBreak);
                    textFlow.getChildren().add(uglyDate);

                    this.setGraphic(textFlow);
                    this.setPrefHeight(50);

                } else {
                    this.setGraphic(null);
                }

            }
        });

    }

    /**
     * Initialize the end dates of the tasks
     */
    // @@author A0138862W
    protected void initEndDate() {
        endDateColumn.prefWidthProperty().bind(archivesTableView.widthProperty().multiply(WIDTH_MULTIPLIER_ENDDATE));
        endDateColumn.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue()));

        endDateColumn.setCellFactory(col -> new TableCell<ReadOnlyTask, ReadOnlyTask>() {

            @Override
            public void updateItem(ReadOnlyTask readOnlyTask, boolean isEmpty) {
                super.updateItem(readOnlyTask, isEmpty);
                if (!isEmpty()
                    && readOnlyTask.getEndDate() != null) {

                    TextFlow textFlow = new TextFlow();

                    Text prettyDate = generateStyledText(readOnlyTask, prettyTime.format(readOnlyTask.getEndDate()));
                    prettyDate.getStyleClass().add("pretty-date");

                    Text lineBreak = new Text("\n\n");
                    lineBreak.setStyle("-fx-font-size:2px;");

                    Text uglyDate = generateStyledText(readOnlyTask, readOnlyTask.parse(readOnlyTask.getEndDate()));
                    uglyDate.getStyleClass().add("ugly-date");

                    textFlow.getChildren().add(prettyDate);
                    textFlow.getChildren().add(lineBreak);
                    textFlow.getChildren().add(uglyDate);

                    this.setGraphic(textFlow);
                    this.setPrefHeight(50);

                } else {
                    this.setGraphic(null);
                }

            }
        });

    }

    /**
     * Initialize the tags of the tasks
     */
    // @@author A0138862W
    protected void initTags() {
        tagsColumn.prefWidthProperty().bind(archivesTableView.widthProperty().multiply(WIDTH_MULTIPLIER_TAGS));
        tagsColumn.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue()));

        tagsColumn.setCellFactory(col -> new TableCell<ReadOnlyTask, ReadOnlyTask>() {

            @Override
            public void updateItem(ReadOnlyTask readOnlyTask, boolean isEmpty) {
                super.updateItem(readOnlyTask, isEmpty);
                if (!isEmpty()
                    && readOnlyTask.getTags() != null) {

                    HBox tags = new HBox(5);

                    for (Tag tag : readOnlyTask.getTags()) {
                        Button tagBubble = new Button();
                        tagBubble.setText(tag.tagName);
                        tagBubble.getStyleClass().add("tag");
                        tags.getChildren().add(tagBubble);
                    }

                    this.setGraphic(tags);
                } else {
                    this.setGraphic(null);
                }
            }
        });

    }

    // @@author A0124797R
    /**
     * Initialize a checkbox to determine whether task is recurring
     */
    protected void initRecur() {
        recurColumn.prefWidthProperty().bind(archivesTableView.widthProperty().multiply(WIDTH_MULTIPLIER_RECUR));
        recurColumn.setGraphic(new ImageView("file:src/main/resources/images/recur_white.png"));
        recurColumn.setCellValueFactory(task -> new SimpleBooleanProperty(task.getValue().isRecur()));
        recurColumn.setCellFactory(col -> new TableCell<ReadOnlyTask, Boolean>() {

            @Override
            public void updateItem(Boolean isRecur, boolean isEmpty) {
                super.updateItem(isRecur, isEmpty);
                if (!isEmpty()) {
                    CheckBox box = new CheckBox();
                    box.setSelected(isRecur);
                    box.setDisable(true);
                    box.setStyle("-fx-opacity: 1");

                    this.setAlignment(Pos.CENTER);
                    this.setGraphic(box);
                } else {
                    this.setGraphic(null);
                    ;
                }
            }
        });
    }

    /*
     * Generate styled row base on the task status: due(red), happening(orange),
     * normal(blue)
     * 
     */
    // @@author A0138862W
    private Text generateStyledText(ReadOnlyTask readOnlyTask, String text) {
        Text taskName = new Text(text);

        if (readOnlyTask.isHappening()) {
            taskName.getStyleClass().add("happening");
        } else if (readOnlyTask.isDue()) {
            taskName.getStyleClass().add("overdue");
        } else {
            taskName.getStyleClass().add("normal");
        }
        return taskName;
    }
}

