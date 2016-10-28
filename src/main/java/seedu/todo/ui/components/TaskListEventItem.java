package seedu.todo.ui.components;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import seedu.todo.commons.util.DateUtil;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.models.Event;

/**
 * @@author A0139812A
 */
public class TaskListEventItem extends MultiComponent {

    private static final String FXML_PATH = "components/TaskListEventItem.fxml";
    private static final String ICON_PATH = "/images/icon-calendar.png";
    
    // Props
    public Event event;
    public Integer displayIndex;

    // FXML
    @FXML
    private Text eventText;
    @FXML
    private Text eventTime;
    @FXML
    private Text rowIndex;
    @FXML
    private ImageView rowIconImageView;
    @FXML
    private Text eventTagListText;
    
    @Override
    public String getFxmlPath() {
        return FXML_PATH;
    }

    @Override
    public void componentDidMount() {
        eventText.setText(event.getName());
        eventTime.setText(DateUtil.formatDateFromTo(event.getStartDate(), event.getEndDate()));
        rowIndex.setText(displayIndex.toString());
        eventTagListText.setText(StringUtil.checkEmptyList(event.getTagList())); //TODO : Change FXML file to support TagList
        
        // Set image
        rowIconImageView.setImage(new Image(ICON_PATH));
        
        // If over, set style
        if (event.isOver()) {
            eventText.getStyleClass().add("completed");
        }
    }

}
