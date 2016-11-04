package tars.logic.commands;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import tars.commons.core.Config;
import tars.commons.core.EventsCenter;
import tars.commons.events.ui.ScrollToTopEvent;
import tars.commons.exceptions.DataConversionException;
import tars.commons.util.ConfigUtil;
import tars.commons.util.FileUtil;
import tars.model.ReadOnlyTars;
import tars.storage.Storage;
import tars.storage.StorageManager;

/**
 * @@author A0124333U
 * Changes the directory of the Tars storage file, tars.xml
 */
public class CdCommand extends Command {

  public static final String COMMAND_WORD = "cd";

  public static final String MESSAGE_USAGE =
      COMMAND_WORD + ": Changes the directory of the " + "TARS storage file.\n"
          + "Parameters: <FILE_PATH.xml> \n" + "Example: " + COMMAND_WORD + " data/tars.xml";

  public static final String MESSAGE_INVALID_FILEPATH =
      "Invalid file path. File paths should end with the file type .xml \n" + "Example: "
          + COMMAND_WORD + " data/tars.xml";

  public static final String MESSAGE_SUCCESS_NEW_FILE =
      "Change Directory Success! New file created! Directory of TARS storage file"
          + " changed to: '%1$s'.";

  public static final String MESSAGE_SUCCESS_EXISTING_FILE =
      "Change Directory Success! File read successfully! Directory of TARS storage "
          + "file changed to : '%1$s'.";

  public static final String MESSAGE_FAILURE_WRITE_FILE =
      "Unable to write to location, please choose another directory";

  public static final String MESSAGE_FAILURE_READ_FILE =
      "Unable to read from location, please choose another directory";

  private final String newFilePath;
  private final static String xmlFileExt = "xml";;
  private Storage storageUpdater = new StorageManager();
  private Config newConfig = new Config();

  public CdCommand(String filepath) {
    this.newFilePath = filepath;
  }

  public final static String getXmlFileExt() {
    return xmlFileExt;
  }

  @Override
  public String toString() {
    return this.newFilePath;
  }

  @Override
  public CommandResult execute() {
        
    newConfig.setTarsFilePath(newFilePath);
    File file = new File(newFilePath);
    
    EventsCenter.getInstance().post(new ScrollToTopEvent());
    return FileUtil.isFileExists(file) ? readTarsFromNewFilePath() : saveTarsToNewFilePath();
  }

  private CommandResult saveTarsToNewFilePath() {
    try {
      storageUpdater.saveTarsInNewFilePath(model.getTars(), newFilePath); // try to save TARS data
                                                                          // into new file

      if (storageUpdater.isFileSavedSuccessfully(newFilePath)) {
        updateTarsSystemWithNewFilePath();
        return new CommandResult(String.format(MESSAGE_SUCCESS_NEW_FILE, newFilePath));
      } else {
        return new CommandResult(MESSAGE_FAILURE_WRITE_FILE);
      }
    } catch (IOException ioe) {
      return new CommandResult(MESSAGE_FAILURE_WRITE_FILE);
    }

  }

  private CommandResult readTarsFromNewFilePath() {
    Optional<ReadOnlyTars> tarsOptional;
    ReadOnlyTars tarsDataToOverwrite;
    try {
      tarsOptional = storageUpdater.readTarsFromNewFilePath(newFilePath);

      tarsDataToOverwrite = tarsOptional.orElse(null);

      if (tarsDataToOverwrite != null) {
        model.overwriteDataFromNewFilePath(tarsDataToOverwrite);
        updateTarsSystemWithNewFilePath();
        return new CommandResult(String.format(MESSAGE_SUCCESS_EXISTING_FILE, newFilePath));
      } else {
        return new CommandResult(MESSAGE_FAILURE_READ_FILE);
      }
    } catch (DataConversionException | IOException e) {
      return new CommandResult(MESSAGE_FAILURE_READ_FILE);
    }

  }

  private void updateTarsSystemWithNewFilePath() throws IOException {
    storageUpdater.updateTarsStorageDirectory(newFilePath, newConfig);
    ConfigUtil.updateConfig(newConfig);
  }

}
