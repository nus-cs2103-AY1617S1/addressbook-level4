package tars.logic.parser;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;

import tars.logic.commands.Command;
import tars.logic.commands.HelpCommand;
import tars.logic.commands.IncorrectCommand;
import tars.ui.UserGuide;

/**
 * Help command parser
 * 
 * @@author A0140022H
 */
public class HelpCommandParser extends CommandParser {

    private static final int EMPTY_ARGS = 0;
    
    @Override
    public Command prepareCommand(String args) {
        
        args = args.trim().toLowerCase();
        
        if(args.length() > EMPTY_ARGS) {
            ArrayList<String> keywordArray = fillKeywordArray();
            
            if (!keywordArray.contains(args)) {
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
            }
        }

        return new HelpCommand(args);
    }

    private ArrayList<String> fillKeywordArray() {
        ArrayList<String> keywordArray = new ArrayList<String>();
        keywordArray.add(UserGuide.ADD);
        keywordArray.add(UserGuide.RSV);
        keywordArray.add(UserGuide.RSV_DELETE);
        keywordArray.add(UserGuide.CONFIRM);
        keywordArray.add(UserGuide.EDIT);
        keywordArray.add(UserGuide.TAG_EDIT);
        keywordArray.add(UserGuide.TAG_DELETE);
        keywordArray.add(UserGuide.TAG_LIST);
        keywordArray.add(UserGuide.MARK_DONE);
        keywordArray.add(UserGuide.MARK_UNDONE);
        keywordArray.add(UserGuide.DELETE);
        keywordArray.add(UserGuide.LIST);
        keywordArray.add(UserGuide.FIND);
        keywordArray.add(UserGuide.FREE);
        keywordArray.add(UserGuide.UNDO);
        keywordArray.add(UserGuide.REDO);
        keywordArray.add(UserGuide.CLEAR);
        keywordArray.add(UserGuide.EXIT);
        keywordArray.add(UserGuide.CD);
        keywordArray.add(UserGuide.SUMMARY);
        return keywordArray;
    }

}
