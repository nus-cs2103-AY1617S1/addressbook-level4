package seedu.oneline.storage

import java.io.File
import java.io.IOException
import java.util.Optional
import java.util.logging.Logger
import seedu.oneline.commons.core.LogsCenter
import seedu.oneline.commons.exceptions.DataConversionException
import seedu.oneline.commons.util.FileUtil
import seedu.oneline.model.UserPrefs

/** 
 * A class to access UserPrefs stored in the hard disk as a json file
 */
class JsonUserPrefsStorage implements UserPrefsStorage {
	static final Logger logger = LogsCenter::getLogger(typeof(JsonUserPrefsStorage))
	String filePath

	new(String filePath) {
		this.filePath = filePath
	}

	override Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
		return readUserPrefs(filePath)
	}

	override void saveUserPrefs(UserPrefs userPrefs) throws IOException {
		saveUserPrefs(userPrefs, filePath)
	}

	/** 
	 * Similar to {@link #readUserPrefs()}
	 * @param prefsFilePath location of the data. Cannot be null.
	 * @throws DataConversionException if the file format is not as expected.
	 */
	def Optional<UserPrefs> readUserPrefs(String prefsFilePath) throws DataConversionException {
		if (!(prefsFilePath !== null)) {
			throw new AssertionError()
		}
		var File prefsFile = new File(prefsFilePath)
		if (!prefsFile.exists()) {
			logger.info('''Prefs file «prefsFile» not found'''.toString)
			return Optional::empty()
		}
		var UserPrefs prefs
		try {
			prefs = FileUtil::deserializeObjectFromJsonFile(prefsFile, typeof(UserPrefs))
		} catch (IOException e) {
			logger.warning('''Error reading from prefs file «prefsFile»: «e»'''.toString)
			throw new DataConversionException(e)
		}

		return Optional::of(prefs)
	}

	/** 
	 * Similar to {@link #saveUserPrefs(UserPrefs)}
	 * @param prefsFilePath location of the data. Cannot be null.
	 */
	def void saveUserPrefs(UserPrefs userPrefs, String prefsFilePath) throws IOException {
		if (!(userPrefs !== null)) {
			throw new AssertionError()
		}
		if (!(prefsFilePath !== null)) {
			throw new AssertionError()
		}
		FileUtil::serializeObjectToJsonFile(new File(prefsFilePath), userPrefs)
	}
}
