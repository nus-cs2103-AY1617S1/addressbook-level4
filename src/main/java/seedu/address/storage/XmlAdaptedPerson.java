package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.deadline.UniqueDeadlineList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedPerson {

    @XmlElement(required = true)
    private String name;
    
    @XmlElement
    private String startline;
    
    @XmlElement(required = true)
    private String deadlined;
    
    @XmlElement(required = true)
    private String priority;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedPerson() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(ReadOnlyTask source) {
        name = source.getName().fullName;
        startline = source.getStartline().value;
        deadlined = source.deadlinesString();
        priority = source.getPriority().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {
    	final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        Set<String> deadlineString = getDeadlinesFromArgs(this.deadlined);
        final Set<Deadline> deadlineSet = new HashSet<>();
        for (String deadlineDate : deadlineString) {
        	deadlineSet.add(new Deadline(deadlineDate));
        }
        final UniqueDeadlineList deadlines = new UniqueDeadlineList(deadlineSet);
        final Startline startline = new Startline(getStartlineFromArgs(this.startline));
        final Priority priority = new Priority(this.priority);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(name, startline, deadlines, priority, tags);
    }
    
    private Set<String> getDeadlinesFromArgs(String deadlineArguments) {
    	// no tags
    	if (deadlineArguments.isEmpty()) {
    		return Collections.emptySet();
    	}
    	// replace first delimiter prefix, then split
    	final Collection<String> deadlineStrings = Arrays.asList(deadlineArguments.replaceFirst(" d/",  "").split("t/"));
    	return new HashSet<>(deadlineStrings);
    }
    
    private String getStartlineFromArgs(String args){
    	if(args.isEmpty()){
    		return null;
    	}
    	args = args.replaceFirst(" s/", "");
    	String[] strArr = args.split("\\s+");
    	if(strArr.length == 1){
    		return args + " " + "00:00";
    	}
    	return args;    	
    }
}
    
