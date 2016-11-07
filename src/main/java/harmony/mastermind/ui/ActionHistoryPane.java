package harmony.mastermind.ui;

import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import com.google.common.eventbus.Subscribe;

import harmony.mastermind.commons.events.ui.ExecuteCommandEvent;
import harmony.mastermind.commons.events.ui.NewResultAvailableEvent;
import harmony.mastermind.commons.events.ui.ToggleActionHistoryEvent;
import harmony.mastermind.commons.util.FxViewUtil;
import harmony.mastermind.logic.Logic;
import javafx.fxml.FXML;

public class ActionHistoryPane extends UiPart {

    
    private static final String FXML = "ActionHistoryPane.fxml";
    
    private AnchorPane placeholder;
    
    private Logic logic;
    
    @FXML
    private TitledPane actionHistoryPane;
    
    @FXML
    private ListView<ActionHistory> actionHistory;
    
    @FXML
    private TextArea consoleOutput;
    
    public static ActionHistoryPane load(Stage primaryStage, AnchorPane actionHistoryPanePlaceholder, Logic logic){
        ActionHistoryPane ui = UiPartLoader.loadUiPart(primaryStage, actionHistoryPanePlaceholder, new ActionHistoryPane());
        ui.configure(logic);
        return ui;
    }
    
    @FXML
    private void initialize(){
        initActionHistory();
    }
    
    @Override
    public void setPlaceholder(AnchorPane placeholder){
        this.placeholder = placeholder;
    }
    
    @Override
    public void setNode(Node node) {
        this.actionHistoryPane = (TitledPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
    
    private void configure(Logic logic){
        this.logic = logic;
        this.addToPlaceholder();
        this.registerAsAnEventHandler(this);
    }

    private void addToPlaceholder(){
        placeholder.getChildren().add(actionHistoryPane);
        FxViewUtil.applyAnchorBoundaryParameters(actionHistoryPane, 0, 0, 0, 0);
    }
    
    // @@author A0138862W
    protected void initActionHistory() {

        actionHistory.setOnMouseClicked(value -> {
            consoleOutput.setText(actionHistory.getSelectionModel().getSelectedItem().getDescription());
        });
        
        actionHistory.setCellFactory(listView -> {
            ListCell<ActionHistory> actionCell = new ListCell<ActionHistory>() {

                @Override
                protected void updateItem(ActionHistory item, boolean isEmpty) {
                    super.updateItem(item, isEmpty);

                    if (!isEmpty) {

                        ActionHistoryEntry actionHistoryEntry = UiPartLoader.loadUiPart(new ActionHistoryEntry());

                        actionHistoryEntry.setTitle(item.getTitle().toUpperCase());
                        actionHistoryEntry.setDate(item.getDateActioned().toString().toUpperCase());

                        if (item.getTitle().toUpperCase().equals("INVALID COMMAND")) {
                            actionHistoryEntry.setTypeFail();
                        } else {
                            actionHistoryEntry.setTypeSuccess();
                        }

                        this.setGraphic(actionHistoryEntry.getNode());

                        this.setPrefHeight(50);
                        this.setPrefWidth(250);

                        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    } else {
                        this.setGraphic(null);
                    }
                }
            };

            return actionCell;
        });
        
        
    }
    //@@author
    
    //@@author A0138862W
    public void toggleActionHistory(){
        actionHistoryPane.setExpanded(!actionHistoryPane.isExpanded());
}

    // @@A0138862W
    protected void pushToActionHistory(String title, String description) {        
        ActionHistory aHistory = new ActionHistory(title, description);

        actionHistory.getItems().add(aHistory);
        actionHistory.scrollTo(actionHistory.getItems().size()- 1);
    }
    // @@author
    
    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event){
        this.consoleOutput.setText(event.message);
        this.actionHistoryPane.setText(event.message);
        this.pushToActionHistory(event.title, event.message);
    }
    
    @Subscribe
    private void handleToggleActionHistoryEvent(ToggleActionHistoryEvent event){
        toggleActionHistory();
    }
    
}
