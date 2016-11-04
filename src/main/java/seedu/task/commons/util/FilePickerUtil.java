package seedu.task.commons.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.apache.commons.io.FilenameUtils;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seedu.task.commons.core.LogsCenter;

// @@author A0147944U
/**
 * A class for JFileChooser, a GUI to select a file.
 */
public class FilePickerUtil {

    private static final Logger logger = LogsCenter.getLogger(ConfigUtil.class);

    /**
     * Retrieves filepath of the selected XML file via a Open GUI
     * 
     * @return FilePath as a String without its extension
     * @throws IOException
     *             if there was an error accessing the file
     */
    public static String openXMLFile() {
        FileChooser chooser = prepareFileChooser();
        chooser.setTitle("Open Data File"); // Set FileChooser window title
        String FilePath = "";
        try {
            FilePath = chooser.showOpenDialog(new Stage()).getCanonicalFile().toString();
        } catch (NullPointerException e) { // Intended
            logger.info("User exited FileChooser without selecting a file.");
            return "";
        } catch (IOException e) {
            logger.warning("Error selecting file");
            e.printStackTrace();
        }
        if (FilePath != null) {
            // Remove extension first as it will be readded later when parsing
            return FilenameUtils.removeExtension(FilePath);
        } else { // Unintended
            logger.warning("UNINTENDED: FilePath is null");
            return "";
        }
    }

    /**
     * Retrieves filepath of the selected XML file via a Save GUI
     * 
     * @return FilePath as a String without its extension
     * @throws IOException
     *             if there was an error accessing the file
     */
    public static String saveXMLFile() {
        FileChooser chooser = prepareFileChooser();
        chooser.setTitle("Save Data File"); // Set FileChooser window title
        String FilePath = "";
        try {
            FilePath = chooser.showSaveDialog(new Stage()).getCanonicalFile().toString();
        } catch (NullPointerException e) { // Intended
            logger.info("User exited FileChooser without selecting a file.");
            return "";
        } catch (IOException e) {
            logger.warning("Error selecting file");
            e.printStackTrace();
        }
        if (FilePath != null) {
            // Remove extension first as it will be readded later when parsing
            return FilenameUtils.removeExtension(FilePath);
        } else { // Unintended
            logger.warning("UNINTENDED: FilePath is null");
            return "";
        }
    }

    /**
     * Prepares the FileChooser with defined settings
     * 
     * @return FileChooser the prepared FileChooser
     */
    public static FileChooser prepareFileChooser() {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("."));
        chooser.setInitialFileName("backup.xml");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files", "*.xml"));
        return chooser;
    }

    /* JFileChooser method (Did not manage to get it working on MacOS)
     public static String openXMLFile() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e1) {
            e1.printStackTrace();
        }
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XML files", "xml");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        String FilePath = "";
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                FilePath = chooser.getSelectedFile().getCanonicalPath().toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return FilenameUtils.removeExtension(FilePath); */
}
