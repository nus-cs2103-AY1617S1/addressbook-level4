package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that the TaskManager Storage location has changed in the Config
 *
 */
public class ConfigFilePathChangedEvent extends BaseEvent{

		public final String filePath;
		
		public ConfigFilePathChangedEvent(String filePath) {
			this.filePath = filePath;
		}
		
		@Override
		public String toString() {
			return "Storage location in Config has changed to " + filePath;
		}
}
