package tars.ui;

import java.util.Date;
import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tars.commons.core.LogsCenter;
import tars.commons.events.model.TarsChangedEvent;
import tars.commons.events.storage.TarsStorageDirectoryChangedEvent;
import tars.commons.util.FxViewUtil;
import tars.commons.util.StringUtil;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart {
    private static final Logger logger =
            LogsCenter.getLogger(StatusBarFooter.class);
    private StatusBar syncStatus;
    private StatusBar saveLocationStatus;

    private GridPane mainPane;

    @FXML
    private AnchorPane saveLocStatusBarPane;

    @FXML
    private AnchorPane syncStatusBarPane;

    private AnchorPane placeHolder;

    private Label saveLocationLabel;
    private Label syncStatusLabel;

    private static final String FXML = "StatusBarFooter.fxml";

    public static StatusBarFooter load(Stage stage, AnchorPane placeHolder,
            String saveLocation) {
        StatusBarFooter statusBarFooter = UiPartLoader.loadUiPart(stage,
                placeHolder, new StatusBarFooter());
        statusBarFooter.configure(saveLocation);
        return statusBarFooter;
    }

    public void configure(String saveLocation) {
        addMainPane();
        addSyncStatus();
        setSyncStatus("Not updated yet in this session");
        addSaveLocation();
        setSaveLocation("Storage Directory: " + saveLocation);
        registerAsAnEventHandler(this);
    }

    private void addMainPane() {
        FxViewUtil.applyAnchorBoundaryParameters(mainPane, 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(mainPane);
    }

    // @@author A0139924W
    private void setSaveLocation(String location) {
        this.saveLocationLabel.setText(location);
    }

    private void addSaveLocation() {
        this.saveLocationStatus = new StatusBar();
        this.saveLocationLabel = new Label();
        this.saveLocationStatus.setText(StringUtil.EMPTY_STRING);
        this.saveLocationStatus.getRightItems().add(saveLocationLabel);
        FxViewUtil.applyAnchorBoundaryParameters(saveLocationStatus, 0.0, 0.0,
                0.0, 0.0);
        saveLocStatusBarPane.getChildren().add(saveLocationStatus);
    }

    private void setSyncStatus(String status) {
        this.syncStatusLabel.setText(status);
    }

    private void addSyncStatus() {
        this.syncStatus = new StatusBar();
        this.syncStatusLabel = new Label();
        this.syncStatus.setText(StringUtil.EMPTY_STRING);
        this.syncStatus.getLeftItems().add(syncStatusLabel);
        FxViewUtil.applyAnchorBoundaryParameters(syncStatus, 0.0, 0.0, 0.0,
                0.0);
        syncStatusBarPane.getChildren().add(syncStatus);
    }
    
    //@@author

    @Override
    public void setNode(Node node) {
        mainPane = (GridPane) node;
    }

    @Override
    public void setPlaceholder(AnchorPane placeholder) {
        this.placeHolder = placeholder;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Subscribe
    public void handleTarsChangedEvent(TarsChangedEvent event) {
        String lastUpdated = (new Date()).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                "Setting last updated status to " + lastUpdated));
        setSyncStatus("Last Updated: " + lastUpdated);
    }

    // @@author A0124333U
    @Subscribe
    private void handleTarsStorageChangeDirectoryEvent(
            TarsStorageDirectoryChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setSaveLocation(
                "Storage Location Changed: " + event.getNewFilePath());
    }
}
