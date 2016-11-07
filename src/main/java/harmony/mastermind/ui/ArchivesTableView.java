package harmony.mastermind.ui;

import org.ocpsoft.prettytime.PrettyTime;

import harmony.mastermind.commons.util.AppUtil;
import harmony.mastermind.commons.util.FxViewUtil;
import harmony.mastermind.logic.Logic;
import harmony.mastermind.model.task.ReadOnlyTask;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ArchivesTableView extends DefaultTableView {
    private static final String RECUR_ICON = "/images/recur_white.png";
    
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
    protected void initIndex() {
        indexColumn.prefWidthProperty().bind(archivesTableView.widthProperty().multiply(WIDTH_MULTIPLIER_INDEX));
        indexColumn.setCellFactory(column -> renderIndexCell());
    }

    /**
     * Initialize the Names of the tasks
     */
    protected void initName() {
        nameColumn.prefWidthProperty().bind(archivesTableView.widthProperty().multiply(WIDTH_MULTIPLIER_NAME));
        nameColumn.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue()));
        nameColumn.setCellFactory(col -> renderNameCell());
    }

    /**
     * Initialize the start dates of the tasks
     */
    protected void initStartDate() {
        startDateColumn.prefWidthProperty().bind(archivesTableView.widthProperty().multiply(WIDTH_MULTIPLIER_STARTDATE));
        startDateColumn.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue()));

        startDateColumn.setCellFactory(col -> renderStartDateCell());

    }

    /**
     * Initialize the end dates of the tasks
     */
    protected void initEndDate() {
        endDateColumn.prefWidthProperty().bind(archivesTableView.widthProperty().multiply(WIDTH_MULTIPLIER_ENDDATE));
        endDateColumn.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue()));

        endDateColumn.setCellFactory(col -> renderEndDateCell());

    }


    /**
     * Initialize the tags of the tasks
     */
    protected void initTags() {
        tagsColumn.prefWidthProperty().bind(archivesTableView.widthProperty().multiply(WIDTH_MULTIPLIER_TAGS));
        tagsColumn.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue()));

        tagsColumn.setCellFactory(col -> renderTagsCell());

    }


    /**
     * Initialize a checkbox to determine whether task is recurring
     */
    protected void initRecur() {
        recurColumn.prefWidthProperty().bind(archivesTableView.widthProperty().multiply(WIDTH_MULTIPLIER_RECUR));
        recurColumn.setGraphic(new ImageView(AppUtil.getImage(RECUR_ICON)));
        recurColumn.setCellValueFactory(task -> new SimpleBooleanProperty(task.getValue().isRecur()));
        recurColumn.setCellFactory(col -> renderRecurCell());
    }
}

