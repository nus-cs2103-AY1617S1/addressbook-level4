package taskle.ui;

import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import taskle.model.help.CommandGuide;

/**
 * Custom Help Table Cell that uses TextFlow to set custom 
 * colors for Help Window texts.
 * @author Abel
 *
 */
public class HelpTableCell extends TableCell<CommandGuide, String> {
    
    private static final String REGEX_SPACE_SPLIT = "\\s+";
    private static final int INDEX_COMMAND_WORD = 0;
    private static final String SPACE = " ";

    @Override
    protected void updateItem(String str, boolean empty) {
        if (str != null) {
            TextFlow flow = new TextFlow();
            String[] strings = str.split(REGEX_SPACE_SPLIT);
            assert strings.length > 0;
            setCommandWordStyle(flow, strings);
            setArgsStyle(flow, strings);
            flow.setPrefHeight(10);
            setGraphic(flow);
        }
    }
    
    /**
     * This method sets the style of the command word in format rows.
     * @param flow TextFlow for the row.
     * @param strings Strings to be binded to row.
     */
    private void setCommandWordStyle(TextFlow flow, String[] strings) {
        Text commandText = new Text(strings[INDEX_COMMAND_WORD]);
        commandText.setFill(Color.WHITE);
        flow.getChildren().add(commandText);
    }
    
    /**
     * This method sets the style of the arguments in format rows.
     * @param flow TextFlow for the row.
     * @param strings Strings to be binded to row.
     */
    private void setArgsStyle(TextFlow flow, String[] strings) {
        for (int i = 1; i < strings.length; i++) {
            switch (i) {
            case 1:
                Text argText = new Text(SPACE + strings[i]);
                argText.setFill(Color.DARKORANGE);
                argText.setWrappingWidth(Double.MAX_VALUE);
                flow.getChildren().add(argText);
                break;
            default:
                Text timeArgText = new Text(SPACE + strings[i]);
                timeArgText.setFill(Color.LIGHTGREEN);
                timeArgText.setWrappingWidth(Double.MAX_VALUE);
                flow.getChildren().add(timeArgText);
                break;
            }
        }
    }
}
