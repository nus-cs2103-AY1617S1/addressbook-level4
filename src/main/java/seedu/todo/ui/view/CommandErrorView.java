package seedu.todo.ui.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.commons.util.FxViewUtil;
import seedu.todo.model.ErrorBag;
import seedu.todo.ui.UiPart;
import seedu.todo.ui.UiPartLoader;

import java.util.logging.Logger;

/**
 * A view class that displays specific command errors in greater detail.
 * @author Wang Xien Dong
 */
public class CommandErrorView extends UiPart {

    private final Logger logger = LogsCenter.getLogger(CommandFeedbackView.class);
    private static final String FXML = "CommandErrorView.fxml";

    private AnchorPane placeholder;
    private VBox errorViewBox;
    @FXML
    private VBox nonFieldErrorBox, fieldErrorBox;
    @FXML
    private GridPane nonFieldErrorGrid, fieldErrorGrid;

    public static CommandErrorView load(Stage primaryStage, AnchorPane placeHolder) {
        CommandErrorView errorView = UiPartLoader.loadUiPart(primaryStage, placeHolder, new CommandErrorView());
        errorView.configure();
        return errorView;
    }

    private void configure() {
        FxViewUtil.applyAnchorBoundaryParameters(errorViewBox, 0.0, 0.0, 0.0, 0.0);
        this.placeholder.getChildren().add(errorViewBox);
    }

    public void displayErrorDetails(ErrorBag errorBag) {
        System.out.println(errorBag.getNonFieldErrors());
        System.out.println(errorBag.getNonFieldErrors());
    }

    /* Override Methods */

    @Override
    public void setPlaceholder(AnchorPane placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    public void setNode(Node node) {
        this.errorViewBox = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
