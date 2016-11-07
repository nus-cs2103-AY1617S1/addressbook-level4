<<<<<<< HEAD:src/main/java/jym/manager/commons/util/ConfigUtil.java
package jym.manager.commons.util;
=======
package seedu.address.commons.util;

import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataConversionException;
>>>>>>> nus-cs2103-AY1617S1/master:src/main/java/seedu/address/commons/util/ConfigUtil.java

import java.io.IOException;
import java.util.Optional;

import jym.manager.commons.core.Config;
import jym.manager.commons.core.LogsCenter;
import jym.manager.commons.exceptions.DataConversionException;

/**
 * A class for accessing the Config File.
 */
public class ConfigUtil {

    public static Optional<Config> readConfig(String configFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(configFilePath, Config.class);
    }

    public static void saveConfig(Config config, String configFilePath) throws IOException {
        JsonUtil.saveJsonFile(config, configFilePath);
    }

}
