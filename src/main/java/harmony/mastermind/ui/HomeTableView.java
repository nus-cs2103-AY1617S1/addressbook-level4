package harmony.mastermind.ui;

import org.ocpsoft.prettytime.PrettyTime;

import com.google.common.eventbus.Subscribe;

import harmony.mastermind.commons.events.ui.HighlightLastActionedRowRequestEvent;
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

//@@author A0138862W
public class HomeTableView extends DefaultTableView {
    private static final String RECUR_ICON = "/images/recur_white.png";
    private static final String FXML = "HomeTableView.fxml";
    
    private static final PrettyTime prettyTime = new PrettyTime();
    
    private AnchorPane placeholder;
    
    private Logic logic;
    
    @FXML
    private TableView<ReadOnlyTask> homeTableView;

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

    
    public static HomeTableView load(Stage primaryStage, AnchorPane defaultTableViewPlaceholder, Logic logic){
        HomeTableView ui = UiPartLoader.loadUiPart(primaryStage, defaultTableViewPlaceholder, new HomeTableView());
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
    //@@author
    
    
    @Override
    public void setPlaceholder(AnchorPane placeholder){
        this.placeholder = placeholder;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void setNode(Node node) {
        this.homeTableView = (TableView<ReadOnlyTask>) node;
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
        placeholder.getChildren().add(homeTableView);
        FxViewUtil.applyAnchorBoundaryParameters(homeTableView, 0, 0, 0, 0);
    }
    
    @Override
    public TableView<ReadOnlyTask> getTableView(){
        return homeTableView;
    }

    // @@author A0138862W
    /**
     * Initializes the indexing of tasks
     */
    protected void initIndex() {
        indexColumn.setSortable(false);
        indexColumn.prefWidthProperty().bind(homeTableView.widthProperty().multiply(WIDTH_MULTIPLIER_INDEX));
        indexColumn.setCellFactory(column -> renderIndexCell());
    }

    // @@author A0138862W
    /**
     * Initialize the Names of the tasks
     */
    protected void initName() {
        nameColumn.setSortable(false);
        nameColumn.prefWidthProperty().bind(homeTableView.widthProperty().multiply(WIDTH_MULTIPLIER_NAME));
        nameColumn.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue()));
        nameColumn.setCellFactory(col -> renderNameCell());
    }

    // @@author A0138862W
    /**
     * Initialize the start dates of the tasks
     */
    protected void initStartDate() {
        startDateColumn.setSortable(false);
        startDateColumn.prefWidthProperty().bind(homeTableView.widthProperty().multiply(WIDTH_MULTIPLIER_STARTDATE));
        startDateColumn.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue()));

        startDateColumn.setCellFactory(col -> renderStartDateCell());

    }

    // @@author A0138862W
    /**
     * Initialize the end dates of the tasks
     */
    protected void initEndDate() {
        endDateColumn.setSortable(false);
        endDateColumn.prefWidthProperty().bind(homeTableView.widthProperty().multiply(WIDTH_MULTIPLIER_ENDDATE));
        endDateColumn.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue()));

        endDateColumn.setCellFactory(col -> renderEndDateCell());

    }

    // @@author A0138862W
    /**
     * Initialize the tags of the tasks
     */
    protected void initTags() {
        tagsColumn.setSortable(false);
        tagsColumn.prefWidthProperty().bind(homeTableView.widthProperty().multiply(WIDTH_MULTIPLIER_TAGS));
        tagsColumn.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue()));

        tagsColumn.setCellFactory(col -> renderTagsCell());

    }

    // @@author A0124797R
    /**
     * Initialize a checkbox to determine whether task is recurring
     */
    protected void initRecur() {
        recurColumn.setSortable(false);
        recurColumn.prefWidthProperty().bind(homeTableView.widthProperty().multiply(WIDTH_MULTIPLIER_RECUR));
        recurColumn.setGraphic(new ImageView(AppUtil.getImage(RECUR_ICON)));
        recurColumn.setCellValueFactory(task -> new SimpleBooleanProperty(task.getValue().isRecur()));
        recurColumn.setCellFactory(col -> renderRecurCell());
    }
    
 // @@author A0138862W
    @Subscribe
    public void highlightLastActionedRow(HighlightLastActionedRowRequestEvent event){
        homeTableView.getSelectionModel().select(event.task);
        homeTableView.scrollTo(event.task);
    }
}

