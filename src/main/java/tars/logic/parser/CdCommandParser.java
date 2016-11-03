package tars.logic.parser;

import tars.logic.commands.CdCommand;
import tars.logic.commands.Command;
import tars.logic.commands.IncorrectCommand;

/**
 * Change directory command parser
 * 
 * @author A0124333U
 */
public class CdCommandParser extends CommandParser {

  /**
   * Parses arguments in the context of the change storage file directory (cd) command.
   * 
   * @param args full command args string
   * @return the prepared command
   */
  @Override
  public Command prepareCommand(String args) {

    if (!isFileTypeValid(args.trim())) {
      return new IncorrectCommand(String.format(CdCommand.MESSAGE_INVALID_FILEPATH));
    }

    return new CdCommand(args.trim());
  }

  /**
   * Checks if new file type is a valid file type
   * 
   * @param args
   * @return Boolean variable of whether the file type is valid
   **/
  private Boolean isFileTypeValid(String args) {
    String filePath = args.trim();
    String extension = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
    if (extension.equals(CdCommand.getXmlFileExt())) {
      return true;
    }
    return false;
  }

}
