package seedu.forgetmenot.commons.events.storage;
import seedu.forgetmenot.commons.events.BaseEvent;

/**
 * indicates that the storage location has changed
 *
 */
public class StorageLocationChangedEvent extends BaseEvent{

	public final String filePath;
		
		public StorageLocationChangedEvent(String filePath) {
			this.filePath = filePath;
		}
		
		@Override
		public String toString() {
			return "The storage location has been changed to " + filePath;
		}
}
