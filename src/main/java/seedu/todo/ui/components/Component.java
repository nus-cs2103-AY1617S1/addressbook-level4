package seedu.todo.ui.components;

import java.util.logging.Logger;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.ui.UiPart;
import seedu.todo.ui.UiPartLoader;
import seedu.todo.ui.views.View;

/**
 * The UI is predicated on the concept of a {@code Component}. 
 * A {@code Component} is a single sub-unit of the UI, and should preferably 
 * only be responsible for a single item or functionality in the UI. 
 * <br><br>
 * For example, a task item in the UI is a single {@code Component}, as it is 
 * responsible for purely displaying the task information. A task list 
 * is also a {@code Component}, as it contains multiple task items, and it is 
 * responsible just for rendering each task item.
 * <br><br>
 * A {@code Component} has the following properties:
 * 
 * <ul>
 * <li>Associated with FXML files</li>
 * <li>Loaded with load</li>
 * <li>Able to accept props</li>
 * <li>Rendered in placeholder panes</li>
 * <li>Can load sub-{@code Component}s</li>
 * </ul>
 * 
 * <em>Note: The concept of {@code Component}s and their associated behaviours came 
 * from React, a modern JavaScript library for the web.</em>
 * <br><br>
 * 
 * @@author A0139812A
 */
public abstract class Component extends UiPart {

    private static final Logger logger = LogsCenter.getLogger(View.class);

    protected Pane placeHolderPane;
    protected Pane mainNode;
    
    /**
     * Loads a component into a placeholder.
     * 
     * <ul>
     * <li>Gets the FXML file specified in the {@link Component}, and loads the JavaFX node.</li>
     * <li>Loads a view controller that controls the node, and returns the view controller (ie. {@link Component}).</li>
     * </ul>
     * 
     * @param primaryStage      Stage to load the component on. Typically, a Component rendering other Components should pass in primaryStage.
     * @param placeholder       Placeholder {@code Pane} to render the component in.
     * @param componentClass    Class of the Component to render.
     * @return                  The Component, that controls the rendered JavaFX node.
     */
    public <T extends Component> T load(Stage primaryStage, Pane placeholder, Class<T> componentClass) {
        return UiPartLoader.loadUiPart(primaryStage, placeholder, componentClass);
    }

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
