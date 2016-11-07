package seedu.todo.ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class CardStyler {
    
    public static Color COMPLETION_COLOR = Color.LIGHTGREY;
    public static Color COMPLETION_PRIORITY_COLOR = Color.WHITE;
    public static Color OVERDUE_COLOR = Color.web("#ef5350");
    
    public static void styleForCompletion(Text name, Text details, Text onDate, 
            Text byDate, Text tags, Text recurrence, Circle priorityLevel) {
        
        name.setFill(COMPLETION_COLOR);
        name.setStyle("-fx-strikethrough: true");
        name.setOpacity(50);
        
        details.setFill(COMPLETION_COLOR);
        onDate.setFill(COMPLETION_COLOR);
        byDate.setFill(COMPLETION_COLOR);
        recurrence.setFill(COMPLETION_COLOR);
        tags.setFill(COMPLETION_COLOR);

        priorityLevel.setFill(COMPLETION_PRIORITY_COLOR);
        priorityLevel.setStroke(COMPLETION_PRIORITY_COLOR);

    }
    
    public static void styleForOverdue(Text name, Text details, Text onDate, 
            Text byDate, Text tags, Text recurrence) {
        name.setFill(OVERDUE_COLOR);
        details.setFill(OVERDUE_COLOR);
        onDate.setFill(OVERDUE_COLOR);
        byDate.setFill(OVERDUE_COLOR);
        recurrence.setFill(OVERDUE_COLOR);
        tags.setFill(OVERDUE_COLOR);
    }
}
