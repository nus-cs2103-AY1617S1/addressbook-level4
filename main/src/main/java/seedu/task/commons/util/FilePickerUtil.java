package seedu.task.commons.util;

import java.io.IOException;
import org.apache.commons.io.FilenameUtils;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

// @@author A0147944U
/**
 * A class for JFileChooser, a GUI to select a file.
 */
public class FilePickerUtil {

    /**
     * Retrieves filepath of the selected XML file via a Open GUI
     * 
     * @return FilePath as a String without its extension
     * @throws IOException
     *             if there was an error during reading the file
     */
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
        return FilenameUtils.removeExtension(FilePath);
    }

    /**
     * Retrieves filepath of the selected XML file via a Save GUI
     * 
     * @return FilePath as a String without its extension
     * @throws IOException
     *             if there was an error during reading the file
     */
    public static String saveXMLFile() {
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
        int returnVal = chooser.showSaveDialog(null);
        String FilePath = "";
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                FilePath = chooser.getSelectedFile().getCanonicalPath().toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return FilenameUtils.removeExtension(FilePath);
    }

}
