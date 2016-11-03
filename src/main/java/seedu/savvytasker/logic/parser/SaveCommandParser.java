//@@author A0138431L
package seedu.savvytasker.logic.parser;

import seedu.savvytasker.logic.commands.SaveCommand;

public class SaveCommandParser implements CommandParser<SaveCommand> {

  private static final String HEADER = "save";
  private static final String READABLE_FORMAT = HEADER + " FILE_PATH";
  
  @Override
  public String getHeader() {
      return HEADER;
  }

  @Override
  public String getRequiredFormat() {
      return READABLE_FORMAT;
  }

  @Override
  public SaveCommand parse(String commandText) throws ParseException {
	  
	  String[] pieces = commandText.split(" ");

          String filePath = pieces[1];

          return new SaveCommand(filePath);
  }

}
