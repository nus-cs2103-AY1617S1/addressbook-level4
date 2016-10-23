package seedu.todo.ui.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.logic.commands.CommandSummary;
import seedu.todo.ui.UiPart;
import seedu.todo.ui.util.FxViewUtil;
import seedu.todo.ui.util.UiPartLoaderUtil;
import seedu.todo.ui.util.ViewGeneratorUtil;
import seedu.todo.ui.util.ViewStyleUtil;

import java.util.List;
import java.util.logging.Logger;

//@@author A0139021U

/**
 * A view that displays all the current relevant information.
 */
public class CommandPreviewView extends UiPart {

    /* Constants */
    private static final String FXML = "CommandPreviewView.fxml";

    /* Variables */
    private final Logger logger = LogsCenter.getLogger(CommandPreviewView.class);

    /*Layouts*/
    private AnchorPane placeholder;
    private VBox previewPanelView;

    @FXML
    private GridPane previewGrid;

    /**
     * Loads and initialise the feedback view element to the placeHolder
     * @param primaryStage of the application
     * @param placeholder where the view element {@link #previewPanelView} should be placed
     * @return an instance of this class
     */
    public static CommandPreviewView load(Stage primaryStage, AnchorPane placeholder) {
        CommandPreviewView previewView = UiPartLoaderUtil.loadUiPart(
                primaryStage, placeholder, new CommandPreviewView());
        previewView.configureLayout();
        previewView.hidePreviewPanel();
        return previewView;
    }

    /**
     * Configure the UI layout of {@link CommandErrorView}
     */
    private void configureLayout() {
        FxViewUtil.applyAnchorBoundaryParameters(previewPanelView, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(previewGrid, 0.0, 0.0, 0.0, 0.0);
    }

    /* Interfacing Methods */
    /**
     * Displays a list of commands into the previewPanelView
     */
    public void displayCommandSummaries(List<CommandSummary> commandSummaries) {
        this.showPreviewPanel();
        previewGrid.getChildren().clear();
        int rowIndex = 0;
        for (CommandSummary commandSummary : commandSummaries) {
            appendCommandSummary(rowIndex++, commandSummary);
        }
    }

    /**
     * Add a command summary to each row of the previewGrid
     * @param rowIndex the row number to which the command summary should append to
     * @param commandSummary to be displayed
     */
    private void appendCommandSummary(int rowIndex, CommandSummary commandSummary) {
        Text commandScenario = ViewGeneratorUtil.constructText(commandSummary.scenario, ViewStyleUtil.STYLE_TEXT_4);
        Text commandName = ViewGeneratorUtil.constructText(commandSummary.command, ViewStyleUtil.STYLE_TEXT_4);
        Text commandArgument = ViewGeneratorUtil.constructText(" " + commandSummary.arguments, ViewStyleUtil.STYLE_TEXT_4);

        ViewStyleUtil.addClassStyles(commandArgument, ViewStyleUtil.STYLE_CODE);
        ViewStyleUtil.addClassStyles(commandName, ViewStyleUtil.STYLE_CODE, ViewStyleUtil.STYLE_BOLDER);

        TextFlow combinedCommand = ViewGeneratorUtil.placeIntoTextFlow(commandName, commandArgument);
        previewGrid.addRow(rowIndex, commandScenario, combinedCommand);
    }

    /* Ui Methods */
    public void hidePreviewPanel() {
        FxViewUtil.setCollapsed(previewPanelView, true);
    }

    private void showPreviewPanel() {
        FxViewUtil.setCollapsed(previewPanelView, false);
    }

    /* Override Methods */
    @Override
    public void setPlaceholder(AnchorPane placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    public void setNode(Node node) {
        this.previewPanelView = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}