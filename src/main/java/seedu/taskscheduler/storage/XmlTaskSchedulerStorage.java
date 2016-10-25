package seedu.taskscheduler.storage;

import seedu.taskscheduler.commons.core.LogsCenter;
import seedu.taskscheduler.commons.exceptions.DataConversionException;
import seedu.taskscheduler.commons.util.FileUtil;
import seedu.taskscheduler.model.ReadOnlyTaskScheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A class to access Task Scheduler data stored as an xml file on the hard disk.
 */
public class XmlTaskSchedulerStorage implements TaskSchedulerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTaskSchedulerStorage.class);

    private String filePath;

    public XmlTaskSchedulerStorage(String filePath){
        this.filePath = filePath;
    }

    public String getTaskSchedulerFilePath(){
        return filePath;
    }

    /**
     * Similar to {@link #readTaskScheduler()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTaskScheduler> readTaskScheduler(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File taskSchedulerFile = new File(filePath);

        if (!taskSchedulerFile.exists()) {
            logger.info("TaskScheduler file "  + taskSchedulerFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTaskScheduler taskSchedulerOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(taskSchedulerOptional);
    }

    /**
     * Similar to {@link #saveTaskScheduler(ReadOnlyTaskScheduler)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTaskScheduler(ReadOnlyTaskScheduler taskScheduler, String filePath) throws IOException {
        assert taskScheduler != null;
        assert filePath != null;
        
        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTaskScheduler(taskScheduler));
    }

    @Override
    public Optional<ReadOnlyTaskScheduler> readTaskScheduler() throws DataConversionException, IOException {
        return readTaskScheduler(filePath);
    }

    @Override
    public void saveTaskScheduler(ReadOnlyTaskScheduler taskScheduler) throws IOException {
        saveTaskScheduler(taskScheduler, filePath);
    }
}
