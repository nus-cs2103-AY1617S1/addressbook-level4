package taskle.ui;

import org.junit.Assert;

import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import taskle.model.help.CommandGuide;

public class HelpTableCell extends TableCell<CommandGuide, String> {
    
    private static final int INDEX_COMMAND_WORD = 0;

    @Override
    protected void updateItem(String str, boolean empty) {
        if (str != null) {
            TextFlow flow = new TextFlow();
            String[] strings = str.split("\\s+");
            assert strings.length > 0;
            setCommandWordStyle(flow, strings);
            setArgsStyle(flow, strings);
            setGraphic(flow);
        }
    }
    
    private void setCommandWordStyle(TextFlow flow, String[] strings) {
        Text commandWord = new Text(strings[INDEX_COMMAND_WORD]);
        commandWord.setFill(Color.WHITE);
        flow.getChildren().add(commandWord);
    }
    
    private void setArgsStyle(TextFlow flow, String[] strings) {
        for (int i = 1; i < strings.length; i++) {
            switch (i) {
            case 1:
                Text arg = new Text(strings[i]);
                arg.setFill(Color.DODGERBLUE);
                flow.getChildren().add(arg);
                break;
            default:
                Text timeArg = new Text(strings[i]);
                timeArg.setFill(Color.LIGHTGREEN);
                flow.getChildren().add(timeArg);
                break;
            }
        }
    }
}
