package seedu.task.model.task;

import javafx.scene.paint.Color;
import seedu.task.commons.exceptions.IllegalValueException;
//@@author A0153751H

public class TaskColor {
    private Color color;
    private String identification;
    public static final String MESSAGE_COLOR_CONSTRAINTS = "Color input is incorrect.";
    
    public TaskColor(String color) throws IllegalValueException {
        if (isValidColor(color)) {
            assignColor(color);
        }
        else {
            throw new IllegalValueException(MESSAGE_COLOR_CONSTRAINTS);
        }
    }
    
    public static boolean isValidColor(String color) {
        if (color == null) {
            return false;
        }
        
        if (color.isEmpty() && !color.equalsIgnoreCase("blue")
                && !color.equalsIgnoreCase("green") 
                && !color.equalsIgnoreCase("red") 
              //&& !color.equalsIgnoreCase("cyan")
                && !color.equalsIgnoreCase("none")) {
            return false;
        }
        return true;
    }
    
    public void assignColor(String color) {
        if(color.equalsIgnoreCase("blue")) {
            this.color = Color.BLUE;
            identification = color.toLowerCase();
        }
        else if (color.equalsIgnoreCase("green")) {
            this.color = Color.GREEN;
            identification = color.toLowerCase();
        }
        else if (color.equalsIgnoreCase("red")) {
            this.color = Color.RED;
            identification = color.toLowerCase();
        }
        else if (color.equalsIgnoreCase("none")) {
            this.color = Color.WHITE;
            identification = color.toLowerCase();
        }
      //else if (color.equalsIgnoreCase("cyan")) {
          //this.color = Color.CYAN;
          //identification = color.toLowerCase();
      //}
    }
    
    @Override
    public String toString() {
        return identification;
    }
    
    public Color getColor() {
        return color;
    }
    
    public String toStyle() {
        if(identification.equalsIgnoreCase("blue")) {
            return "-fx-background-color: rgba(0, 0, 225, 0.5);";
        }
        else if (identification.equalsIgnoreCase("green")) {
            return "-fx-background-color: rgba(0, 128, 0, 0.5);";
        }
        else if (identification.equalsIgnoreCase("red")) {
            return "-fx-background-color: rgba(255, 0, 0, 0.5);";
        }
        else if (identification.equalsIgnoreCase("none")) {
            return "-fx-background-color: rgba(255, 255, 255, 0.5);";
        }
//        else if (identification.equalsIgnoreCase("cyan")) {
//            return "-fx-background-color: #00FFFF;";
//        }
        return "-fx-background-color: rgba(255, 255, 255, 0.7);";
    }
}
