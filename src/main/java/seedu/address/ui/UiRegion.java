package seedu.address.ui;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.Region;

public class UiRegion extends Region {

    public Node getNode() {
        return getChildren().size() > 0 ? getChildren().get(0) : null;
    }

    public void setNode(Node child) {
        getChildren().clear();
        getChildren().add(child);
    }

    @Override
    protected void layoutChildren() {
        for (Node node : getChildren()) {
            layoutInArea(node, 0, 0, getWidth(), getHeight(), 0, HPos.LEFT, VPos.TOP);
        }
    }

}
