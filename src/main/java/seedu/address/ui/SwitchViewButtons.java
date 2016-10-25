package seedu.address.ui;

import com.google.common.eventbus.Subscribe;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import seedu.address.commons.events.ui.ChangeToListDoneViewEvent;
import seedu.address.commons.events.ui.ChangeToListUndoneViewEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.*;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.history.InputHistory;
import seedu.address.commons.core.LogsCenter;

import java.util.logging.Logger;

public class SwitchViewButtons extends UiPart {
    private final Logger logger = LogsCenter.getLogger(SwitchViewButtons.class);
    private static final String FXML = "SwitchViewButtons.fxml";

    private AnchorPane placeHolderPane;
    private AnchorPane commandPane;
    private ResultDisplay resultDisplay;


    private Logic logic;
    
    @FXML
    private HBox hb;

    @FXML
    private ToggleButton showListUndone;
    
    @FXML
    private ToggleButton showListDone;
    
    public static SwitchViewButtons load(Stage primaryStage, AnchorPane commandBoxPlaceholder, ResultDisplay resultDisplay, Logic logic, InputHistory history) {
        SwitchViewButtons switchViewButtons = UiPartLoader.loadUiPart(primaryStage, commandBoxPlaceholder, new SwitchViewButtons());
        switchViewButtons.configure(resultDisplay, logic);
        switchViewButtons.addToPlaceholder();
        return switchViewButtons;
    }

    public void configure(ResultDisplay resultDisplay, Logic logic) {
        this.resultDisplay = resultDisplay;
        this.logic = logic;
        registerAsAnEventHandler(this);
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(hb);
        //placeHolderPane.getChildren().add(commandTextField);
        FxViewUtil.applyAnchorBoundaryParameters(hb, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandPane, 0.0, 0.0, 0.0, 0.0);
        
        final ToggleGroup group = new ToggleGroup();
        showListUndone.setToggleGroup(group);
        showListDone.setToggleGroup(group);
        showListUndone.setSelected(true);
    }

    @Override
    public void setNode(Node node) {
        commandPane = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }
    
    @FXML
    private void switchToListDoneView() {
        CommandResult cmdRes = logic.execute("list done");
        resultDisplay.postMessage(cmdRes.feedbackToUser);
    }
    
    @FXML
    private void switchToListUndoneView() {
        CommandResult cmdRes = logic.execute("list");
        resultDisplay.postMessage(cmdRes.feedbackToUser);
    }
    
    @Subscribe
    private void handleChangeToListDoneViewEvent(ChangeToListDoneViewEvent event) {
        showListDone.setSelected(true);
    }
    
    @Subscribe
    private void handleChangeToListUndoneViewEvent(ChangeToListUndoneViewEvent event) {
        showListUndone.setSelected(true);
    }
    
  
}
