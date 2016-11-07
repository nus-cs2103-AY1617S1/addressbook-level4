package harmony.mastermind.ui;

import java.util.Date;

import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.shade.org.apache.commons.lang.time.DurationFormatUtils;

import harmony.mastermind.commons.util.FxViewUtil;
import harmony.mastermind.logic.Logic;
import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.task.ReadOnlyTask;
import javafx.beans.DefaultProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public abstract class DefaultTableView extends UiPart {

    protected static final double WIDTH_MULTIPLIER_INDEX = 0.049;
    protected static final double WIDTH_MULTIPLIER_NAME = 0.28;
    protected static final double WIDTH_MULTIPLIER_STARTDATE = 0.19;
    protected static final double WIDTH_MULTIPLIER_ENDDATE = 0.19;
    protected static final double WIDTH_MULTIPLIER_TAGS = 0.175;
    protected static final double WIDTH_MULTIPLIER_RECUR = 0.1;
    
    protected static final PrettyTime prettyTime = new PrettyTime();
    
    public abstract TableView getTableView();
    
    //@@author A0138862W
    protected TableCell<ReadOnlyTask, ReadOnlyTask> renderIndexCell() {
        return new TableCell<ReadOnlyTask, ReadOnlyTask>() {
            @Override
            public void updateIndex(int index) {
                super.updateIndex(index);

                Text indexText = new Text(Integer.toString(index + 1)+ ".");
                indexText.getStyleClass().add("index-column");

                if (isEmpty() || index < 0) {
                    this.setGraphic(null);
                } else {
                    this.setGraphic(indexText);
                }
            }

        };
    }
    
    //@@author A0138862W
    protected TableCell<ReadOnlyTask, ReadOnlyTask> renderNameCell() {
        return new TableCell<ReadOnlyTask, ReadOnlyTask>() {

            @Override
            public void updateItem(ReadOnlyTask readOnlyTask, boolean isEmpty) {
                super.updateItem(readOnlyTask, isEmpty);

                if (!isEmpty()) {

                    VBox vBox = new VBox(3);

                    Text taskName = generateStyledText(readOnlyTask, readOnlyTask.getName());
                    taskName.getStyleClass().add("task-name-column");
                    vBox.getChildren().add(taskName);

                    generateSpecialTag(readOnlyTask, vBox);

                    this.setGraphic(vBox);
                    this.setPrefHeight(50);

                } else {
                    this.setGraphic(null);
                }

            }

        };
    }
    
    //@@author A0138862W
    protected TableCell<ReadOnlyTask, ReadOnlyTask> renderStartDateCell() {
        return new TableCell<ReadOnlyTask, ReadOnlyTask>() {

            @Override
            public void updateItem(ReadOnlyTask readOnlyTask, boolean isEmpty) {
                super.updateItem(readOnlyTask, isEmpty);
                if (!isEmpty()
                    && readOnlyTask.getStartDate() != null) {

                    TextFlow textFlow = generateStyledDate(readOnlyTask, readOnlyTask.getStartDate());

                    this.setGraphic(textFlow);
                    this.setPrefHeight(50);

                } else {
                    this.setGraphic(null);
                }

            }

        };
    }
    
    //@@author A0138862W
    protected TableCell<ReadOnlyTask, ReadOnlyTask> renderEndDateCell() {
        return new TableCell<ReadOnlyTask, ReadOnlyTask>() {

            @Override
            public void updateItem(ReadOnlyTask readOnlyTask, boolean isEmpty) {
                super.updateItem(readOnlyTask, isEmpty);
                if (!isEmpty()
                    && readOnlyTask.getEndDate() != null) {

                    TextFlow textFlow = generateStyledDate(readOnlyTask, readOnlyTask.getEndDate());

                    this.setGraphic(textFlow);
                    this.setPrefHeight(50);

                } else {
                    this.setGraphic(null);
                }

            }
        };
    }
    
    //@@author A0138862W
    protected TableCell<ReadOnlyTask, ReadOnlyTask> renderTagsCell() {
        return new TableCell<ReadOnlyTask, ReadOnlyTask>() {

            @Override
            public void updateItem(ReadOnlyTask readOnlyTask, boolean isEmpty) {
                super.updateItem(readOnlyTask, isEmpty);
                if (!isEmpty()
                    && readOnlyTask.getTags() != null) {

                    HBox tags = new HBox(5);

                    for (Tag tag : readOnlyTask.getTags()) {
                        Button tagBubble = new Button();
                        tagBubble.setText(tag.tagName);
                        tagBubble.getStyleClass().add("tag");
                        tags.getChildren().add(tagBubble);
                    }

                    this.setGraphic(tags);
                } else {
                    this.setGraphic(null);
                }
            }
        };
    }
    
    // @@author A0124797R
    protected TableCell<ReadOnlyTask, Boolean> renderRecurCell() {
        return new TableCell<ReadOnlyTask, Boolean>() {

            @Override
            public void updateItem(Boolean isRecur, boolean isEmpty) {
                super.updateItem(isRecur, isEmpty);
                if (!isEmpty()) {
                    CheckBox box = new CheckBox();
                    box.setSelected(isRecur);
                    box.setDisable(true);
                    box.setStyle("-fx-opacity: 1");

                    this.setAlignment(Pos.CENTER);
                    this.setGraphic(box);
                } else {
                    this.setGraphic(null);
                    ;
                }
            }
        };
    }

    // @@author A0138862W
    /*
     * Generate styled row base on the task status: due(red), happening(orange),
     * normal(blue)
     * 
     */
    protected Text generateStyledText(ReadOnlyTask readOnlyTask, String text) {
        Text taskName = new Text(text);

        if (readOnlyTask.isHappening()) {
            taskName.getStyleClass().add("happening");
        } else if (readOnlyTask.isDue()) {
            taskName.getStyleClass().add("overdue");
        } else {
            taskName.getStyleClass().add("normal");
        }
        return taskName;
    }

    // @@author A0138862W
    /**
     * THis method generate Due, Happening, and duration depend on the nature of the task
     * 
     * @param readOnlyTask the task object
     * @param vBox The container
     */
    protected void generateSpecialTag(ReadOnlyTask readOnlyTask, VBox vBox) {
        HBox hBox = new HBox(5);

        Button status = new Button();
        if (readOnlyTask.isHappening()) {
            status.setText("HAPPENING");
            status.getStyleClass().add("tag-happening");
            hBox.getChildren().add(status);
        } else if (readOnlyTask.isDue()) {
            status.setText("DUE");
            status.getStyleClass().add("tag-overdue");
            hBox.getChildren().add(status);
        }

        if (readOnlyTask.isEvent()) {
            Button eventDuration = new Button();
            eventDuration.setText("DURATION: " + DurationFormatUtils.formatDurationWords(readOnlyTask.getEventDuration().toMillis(), true, true).toUpperCase());
            eventDuration.getStyleClass().add("tag-event-duration");
            hBox.getChildren().add(eventDuration);
        } else if (readOnlyTask.isDeadline()
                   && !readOnlyTask.isDue()) {
            Button dueDuration = new Button();
            dueDuration.setText("DUE IN " + DurationFormatUtils.formatDurationWords(readOnlyTask.getDueDuration().toMillis(), true, true));
            dueDuration.getStyleClass().add("tag-due-duration");
            hBox.getChildren().add(dueDuration);
        }

        vBox.getChildren().add(hBox);
    }
    
    
    // @@author A0138862W
    /**
     * 
     * This method generate the double-line text for date column
     * 
     * @param readOnlyTask the task object
     * @return TextFlow the styled text
     */
    protected TextFlow generateStyledDate(ReadOnlyTask readOnlyTask, Date date) {
        TextFlow textFlow = new TextFlow();

        Text prettyDate = generateStyledText(readOnlyTask, prettyTime.format(date));
        prettyDate.getStyleClass().add("pretty-date");

        Text lineBreak = new Text("\n\n");
        lineBreak.setStyle("-fx-font-size:2px;");

        Text uglyDate = generateStyledText(readOnlyTask, readOnlyTask.parse(date));
        uglyDate.getStyleClass().add("ugly-date");

        textFlow.getChildren().add(prettyDate);
        textFlow.getChildren().add(lineBreak);
        textFlow.getChildren().add(uglyDate);
        return textFlow;
    }
    
}

