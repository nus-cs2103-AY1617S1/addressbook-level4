package seedu.cmdo.commons.core;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Serializable class that contains the GUI settings.
 */
public class StorageSettings implements Serializable {

    private static final String DEFAULT_FILEPATH = "data/cmdo.xml";

	private String filePath;

	public StorageSettings() {
		this.filePath = DEFAULT_FILEPATH; 
	}
	
    public StorageSettings(String filePath) {
    	this.filePath = filePath;
    }

    public String getFilePath() {
    	return filePath;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other == this){
            return true;
        }
        if (!(other instanceof StorageSettings)){ //this handles null as well.
            return false;
        }

        StorageSettings o = (StorageSettings)other;

        return Objects.equals(filePath, o.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePath);
    }
}