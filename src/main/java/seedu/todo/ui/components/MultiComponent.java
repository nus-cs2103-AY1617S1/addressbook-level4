package seedu.todo.ui.components;

import javafx.scene.layout.Pane;

/**
 * A MultiComponent is a special type of {@link Component}, except that 
 * the render method behaves differently. Successive calls to {@code render()}
 * would cause the node to the rendered to the placeholder multiple times, 
 * instead of replacing the old node. This is especially useful for 
 * rendering lists of variable items, using a loop.
 * 
 * @@author A0139812A
 */
public abstract class MultiComponent extends Component {

    @Override
    public void render() {
        if (placeHolderPane != null) {
            // Replace placeholder children with node.
            placeHolderPane.getChildren().add(mainNode);
        }

        // Callback once view is loaded.
        componentDidMount();
    }

    /**
     * Resets the items in the specified {@code placeholder}.
     * 
     * @param placeholder    Placeholder pane whose children items are to be cleared.
     */
    public static void reset(Pane placeholder) {
        placeholder.getChildren().clear();
    }

}
