package seedu.todo.ui.components;

import javafx.scene.layout.Pane;

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
