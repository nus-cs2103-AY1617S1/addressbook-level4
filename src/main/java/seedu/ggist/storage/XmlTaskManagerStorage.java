package seedu.ggist.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.ggist.commons.core.LogsCenter;
import seedu.ggist.commons.exceptions.DataConversionException;
import seedu.ggist.commons.util.FileUtil;
import seedu.ggist.model.ReadOnlyTaskManager;

/**
 * A class to access TaskManager data stored as an xml file on the hard disk.
 */
public class XmlTaskManagerStorage implements TaskManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTaskManagerStorage.class);

    private String filePath;

    public XmlTaskManagerStorage(String filePath){
        this.filePath = filePath;
    }

    public String getTaskManagerFilePath(){
        return filePath;
    }

    /**
     * Similar to {@link #readAddressBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTaskManager> readTaskManager(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File taskManagerFile = new File(filePath);

        if (!taskManagerFile.exists()) {
            logger.info("TaskManager file "  + taskManagerFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTaskManager taskManagerOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(taskManagerOptional);
    }

    /**
     * Similar to {@link #saveTaskManager(ReadOnlyTaskManager)}
     * @param filePath location of the data. Cannot be null
     */
    public void savetaskManager(ReadOnlyTaskManager taskManager, String filePath) throws IOException {

        assert taskManager != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTaskManager(taskManager));
    }

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException {
        return readTaskManager(filePath);
    }

    @Override
    public void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException {
        savetaskManager(taskManager, filePath);
    }

    @Override
    public void setTaskManagerFilePath(String newFilePath) {
        filePath = newFilePath;
    }

	@Override
	public void saveTaskManager(ReadOnlyTaskManager taskManager, String filePath) throws IOException {
		savetaskManager(taskManager, filePath);
		
	}
}
