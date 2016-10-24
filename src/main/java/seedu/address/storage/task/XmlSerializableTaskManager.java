package seedu.address.storage.task;

import seedu.address.commons.collections.UniqueItemCollection;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Task;
import seedu.address.storage.task.XmlAdaptedTask;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * An Immutable TaskManager that is serializable to XML format
 */
@XmlRootElement(name = "taskmanager")
public class XmlSerializableTaskManager extends UniqueItemCollection<Task>{ 

	 private static final Logger logger = LogsCenter.getLogger(XmlSerializableTaskManager.class);
	
    @XmlElement
    private List<XmlAdaptedTask> tasks;
    {
        tasks = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableTaskManager() {}

    /**
     * Conversion
     */
    public XmlSerializableTaskManager(UniqueItemCollection<Task> src) {
        tasks.addAll(src.getInternalList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
    }
    
    /*
     * This method is called after all the properties (except IDREF) are unmarshalled for this object, 
     *  but before this object is set to the parent object. This allows us to set the correct internal
     *  when loading from XML storage.
     */
    void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
        for (XmlAdaptedTask t : tasks) {
            try {
                this.add(t.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
            	logger.severe(e.getMessage());
            }
        }
    }
    
    public boolean equals(XmlSerializableTaskManager o) {
        if(this.tasks.equals(o.tasks)) {
            return true;
        }
        else {
            return false;
        }
    }



}
