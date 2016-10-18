package seedu.todo.ui.components;

import java.util.logging.Logger;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.ui.UiPart;
import seedu.todo.ui.views.View;

public abstract class Component extends UiPart {

    private static final Logger logger = LogsCenter.getLogger(View.class);

    protected Pane placeHolderPane;
    protected Pane mainNode;

    /**
     * This method renders the View in the specified placeholder, if provided.
     * 
     * Note that all as specified in the FXML file, in the placeholder pane.
     * After loading the FXML file, the execution is then passed onto setNode, 
     * which will replace the placeholder contents accordingly.
     * 
     * @param primaryStage   The primary stage that contains the main application window.
     * @param placeholder    The placeholder pane where this View should reside.
     */    
    public void render() {
        // If the View is not loaded from the FXML file, we have no node to render.
        if (mainNode == null) {
            return;
        }

        assert mainNode != null;

        if (placeHolderPane != null) {
            // Replace placeholder children with node.
            placeHolderPane.getChildren().setAll(mainNode);
        } else {
            logger.warning(this.getClass().getName() + " has no placeholder.");
        }

        // Callback once view is rendered.
        componentDidMount();
    }

    public Node getNode() {
        return mainNode;
    }

    /**
     * Runs once the {@code render()} is called. Used to perform any of the following actions:
     * <li>Modify JavaFX components</li>
     * <li>Set the state of JavaFX components (such as value)</li>
     * <li>Load and render any children components</li>
     * 
     * Declaration is optional, and will default to not doing anything if it is not overridden in child components.
     */
    public void componentDidMount() {
        // Does nothing by default.
    }

    @Override
    public void setPlaceholder(Pane pane) {
        this.placeHolderPane = pane;
    }

    @Override
    public void setNode(Node node) {
        mainNode = (Pane) node;
    }

}
