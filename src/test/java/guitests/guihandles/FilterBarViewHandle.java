package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import seedu.todo.TestApp;

import java.util.List;
import java.util.stream.Collectors;

//@@author A0135805H
/**
 * A handler for indicating which view the user is at via {@link seedu.todo.ui.view.FilterBarView}
 */
public class FilterBarViewHandle extends GuiHandle {
    /* Constants */
    private static final String FILTER_BAR_VIEW_ID = "#filterBarView";

    /**
     * Constructs a handle to the {@link seedu.todo.ui.view.HelpView}
     *
     * @param guiRobot {@link GuiRobot} for the current GUI test.
     * @param primaryStage The stage where the views for this handle is located.
     */
    public FilterBarViewHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    /**
     * Gets the labels of all the view categories on the filter bar view
     */
    public List<String> getFilterBarViewLabels() {
        List<HBox> tagViewBoxes = getFilterViewLabelContainers();
        return tagViewBoxes.stream()
                .map(this::getTextFromHBox)
                .collect(Collectors.toList());
    }

    /**
     * Gets the selected filter view category.
     */
    public String getSelectedFilterView() {
        List<HBox> tagViewBoxes = getFilterViewLabelContainers();
        List<HBox> selectedViewBoxes = tagViewBoxes.stream()
                .filter(hBox -> hBox.getStyleClass().contains("selected"))
                .collect(Collectors.toList());

        //Just to check if there is only 1 item selected.
        assert selectedViewBoxes.size() == 1;

        //Get the correct item.
        HBox selectedItem = selectedViewBoxes.get(0);
        return getTextFromHBox(selectedItem);
    }

    /* Helper Methods */
    /**
     * Get all the displayed filter view labels contained in the respective HBoxes.
     */
    private List<HBox> getFilterViewLabelContainers() {
        FlowPane filterBarView = (FlowPane) getNode(FILTER_BAR_VIEW_ID);
        return filterBarView.getChildren()
                .stream()
                .map(node -> (HBox) node)
                .collect(Collectors.toList());
    }

    /**
     * Extracts all the labels from the single {@code hBox} view.
     */
    private List<Label> getLabelsFromHBox(HBox hBox) {
        return hBox.getChildren()
                .stream()
                .map(node -> ((Label) node))
                .collect(Collectors.toList());
    }

    /**
     * Get a single text string from all the {@code labels}.
     */
    private String getTextFromLabels(List<Label> labels) {
        List<String> strings = labels.stream()
                .map(Labeled::getText)
                .collect(Collectors.toList());

        StringBuilder builder = new StringBuilder();
        strings.forEach(builder::append);
        return builder.toString();
    }

    /**
     * Extracts the text from the labels contained in {@code hBox}.
     */
    private String getTextFromHBox(HBox hBox) {
        List<Label> labels = getLabelsFromHBox(hBox);
        return getTextFromLabels(labels);
    }
}
