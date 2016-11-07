# A0138978Ereused
###### /java/w15c2/tusk/commons/util/TaskConfigUtil.java
``` java
/**
 * A class for accessing the Config File.
 */
public class TaskConfigUtil {

    private static final Logger logger = LogsCenter.getLogger(TaskConfigUtil.class);

    /**
     * Returns the Config object from the given file or {@code Optional.empty()} object if the file is not found.
     *   If any values are missing from the file, default values will be used, as long as the file is a valid json file.
     * @param configFilePath cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public static Optional<TaskConfig> readConfig(String configFilePath) throws DataConversionException {

        assert configFilePath != null;

        File configFile = new File(configFilePath);

        if (!configFile.exists()) {
            logger.info("Config file "  + configFile + " not found");
            return Optional.empty();
        }

        TaskConfig config;

        try {
            config = FileUtil.deserializeObjectFromJsonFile(configFile, TaskConfig.class);
        } catch (IOException e) {
            logger.warning("Error reading from config file " + configFile + ": " + e);
            throw new DataConversionException(e);
        }

        return Optional.of(config);
    }

    /**
     * Saves the Config object to the specified file.
     *   Overwrites existing file if it exists, creates a new file if it doesn't.
     * @param config cannot be null
     * @param configFilePath cannot be null
     * @throws IOException if there was an error during writing to the file
     */
    public static void saveConfig(TaskConfig config, String configFilePath) throws IOException {
        assert config != null;
        assert configFilePath != null;

        FileUtil.serializeObjectToJsonFile(new File(configFilePath), config);
    }

}
```
###### /java/w15c2/tusk/ui/TaskListPanel.java
``` java
    // From http://stackoverflow.com/questions/30457708/visible-items-of-listview
    /**
     * Gets approximately the first and last viewable items in the scrollable listview
     */
    private Pair<Integer, Integer> getFirstAndLastVisibleIndices(ListView<?> t) {
        try {
            @SuppressWarnings("restriction")
			ListViewSkin<?> ts = (ListViewSkin<?>) t.getSkin();
            @SuppressWarnings("restriction")
			VirtualFlow<?> vf = (VirtualFlow<?>) ts.getChildren().get(0);
            int first = vf.getFirstVisibleCell().getIndex();
            int last = vf.getLastVisibleCell().getIndex();
            return new Pair<Integer, Integer>(first, last);
        } catch (Exception ex) {
            logger.severe("getFirstAndLast for scrolling: Exception " + ex);
            throw ex;
        }
    }
    
```
