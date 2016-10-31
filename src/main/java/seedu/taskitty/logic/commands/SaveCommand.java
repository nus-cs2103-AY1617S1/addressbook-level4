package seedu.taskitty.logic.commands;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import seedu.taskitty.commons.util.ConfigUtil;
import seedu.taskitty.commons.util.StringUtil;
import seedu.taskitty.storage.Storage;
import seedu.taskitty.ui.MainWindow;
import seedu.taskitty.MainApp;
import seedu.taskitty.commons.core.Config;
import seedu.taskitty.commons.core.EventsCenter;
import seedu.taskitty.commons.events.storage.PathLocationChangedEvent;
import seedu.taskitty.commons.exceptions.DataConversionException;

//@@author A0135793W
/**
 * Saves TasKitty data to a folder specified by the user
 * @author JiaWern
 *
 */
public class SaveCommand extends Command{
    
    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_PARAMETER = COMMAND_WORD
            + " filepath.xml";
    public static final String MESSAGE_USAGE = "This command saves data in TasKitty to a location of your choice, Meow!\n";
    public static final String MESSAGE_VALID_FILEPATH_USAGE = "Filepath must end with .xml";

    public static final String MESSAGE_SUCCESS = "Data saved to: %1$s";
    public static final String MESSAGE_CANCELLED = "Save function cancelled.";
    public static final String MESSAGE_FAILED = "Failed to save data to: %1$s";
    public static final String MESSAGE_INVALID_FILEPATH = "Filepath is invalid. \n%1$s";
    public static final String MESSAGE_INVALID_MISSING_FILEPATH = "Filepath is invalid. \n%1$s";
    
    Config config = MainApp.getConfig();
    Storage storage = MainApp.getStorage();
    String configFile = config.DEFAULT_CONFIG_FILE;
    
    public final String filepath;
    
    public SaveCommand(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public CommandResult execute() {
       
        try {
            if (!storage.toOverwriteOrLoad(filepath)) {
                return new CommandResult(MESSAGE_CANCELLED);
            }
            
            config.setTaskManagerFilePath(filepath);
            ConfigUtil.saveConfig(config, configFile);

            storage.setFilePath(config.getTaskManagerFilePath());
            
            MainWindow.getStatusBarFooter().setSaveLocation("./"+config.getTaskManagerFilePath());
            
            restartApplication();
            
            EventsCenter.getInstance().post(new PathLocationChangedEvent(config.getTaskManagerFilePath()));
            
            return new CommandResult(String.format(MESSAGE_SUCCESS, filepath));
        } catch (IOException io) {
            return new CommandResult(MESSAGE_FAILED + StringUtil.getDetails(io));
        } catch (DataConversionException dc) {
            return new CommandResult(MESSAGE_FAILED + StringUtil.getDetails(dc));
        } catch (Exception e) {
            return new CommandResult(MESSAGE_FAILED + StringUtil.getDetails(e));
        } 
    }
    
    /**
     * Restarts the application
     * Code from stack overflow - http://stackoverflow.com/questions/4159802/how-can-i-restart-a-java-application
     */
    private void restartApplication() throws URISyntaxException, IOException{
        final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        File currentJar;
        try {
            currentJar = new File(MainApp.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        
            /* is it a jar file? */
            if(!currentJar.getName().endsWith(".jar")) {
                return;
            }
            
            /* Build command: java -jar application.jar */
            final ArrayList<String> command = new ArrayList<String>();
            command.add(javaBin);
            command.add("-jar");
            command.add(currentJar.getPath());

            final ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();
            System.exit(0);
        } catch (URISyntaxException e) {
            throw e;
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}
