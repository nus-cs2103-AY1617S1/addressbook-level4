package seedu.task.model.task;

import javafx.scene.paint.Color;
import seedu.task.commons.exceptions.IllegalValueException;
//@@author A0153751H
public class TaskColor {
	public enum Colors {
		RED, GREEN, BLUE
	}
	private Color color;
	public static final String MESSAGE_COLOR_CONSTRAINTS = "Color input is incorrect.";
	
	public TaskColor(String color) throws IllegalValueException {
		if (isValidColor(color)) {
			assignColor(color);
		} else {
			throw new IllegalValueException(MESSAGE_COLOR_CONSTRAINTS);
		}
	}
	
	public static boolean isValidColor(String color) {
    	if(color.isEmpty()
    			&& !color.equalsIgnoreCase("blue")
    			&& !color.equalsIgnoreCase("green")
    			&& !color.equalsIgnoreCase("red")) {
    		return false;  		
    	}
        return true;
    }
	
	public void assignColor(String color) {
		if(color.equalsIgnoreCase("blue")) {
			this.color = Color.BLUE;
		} else if (color.equalsIgnoreCase("green")) {
			this.color = Color.GREEN;
		} else if (color.equalsIgnoreCase("red")) {
			this.color = Color.RED;
		}
	}
}
