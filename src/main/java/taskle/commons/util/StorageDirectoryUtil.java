package taskle.commons.util;

import java.io.File;
import java.io.IOException;

import taskle.commons.core.Config;
import taskle.logic.Logic;

/**
 * 
 * Manage changes in directory of storage file
 *
 */
public class StorageDirectoryUtil {

    public static void updateDirectory(Config config, Logic logic, File selectedDirectory) {
        try {
            new File(selectedDirectory.getAbsolutePath(), config.getTaskManagerFileName()).delete();
            new File(config.getTaskManagerFilePath()).renameTo(new File(selectedDirectory.getAbsolutePath(), config.getTaskManagerFileName()));
            config.setTaskManagerFileDirectory(selectedDirectory.getAbsolutePath());
            ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
            logic.changeDirectory(config.getTaskManagerFilePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
