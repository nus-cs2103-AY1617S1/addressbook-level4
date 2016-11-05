package seedu.dailyplanner.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.model.tag.Tag;
import seedu.dailyplanner.model.tag.UniqueTagList;
import seedu.dailyplanner.model.task.*;

import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String startDate;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private String endDate;
    @XmlElement(required = true)
    private String endTime;
    @XmlElement(required = true)
    private boolean isComplete;
    @XmlElement(required = true)
    private boolean isPinned;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedPerson() {
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source
     *            future changes to this will not affect the created
     *            XmlAdaptedPerson
     */
    public XmlAdaptedPerson(ReadOnlyTask source) {
	name = source.getName();
	startDate = source.getStart().getDate().toString();
	startTime = source.getStart().getTime().toString();
	endDate = source.getEnd().getDate().toString();
	endTime = source.getEnd().getTime().toString();
	isComplete = source.isComplete();
	isPinned = source.isPinned();
	tagged = new ArrayList<>();
	for (Tag tag : source.getTags()) {
	    tagged.add(new XmlAdaptedTag(tag));
	}
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person
     * object.
     *
     * @throws IllegalValueException
     *             if there were any data constraints violated in the adapted
     *             person
     */
    public Task toModelType() throws IllegalValueException {

	final String taskName = this.name;
	final Date dateStart = new Date(startDate);
	final Time timeStart = new Time(startTime);
	final DateTime start = new DateTime(dateStart, timeStart);
	final Date dateEnd = new Date(endDate);
	final Time timeEnd = new Time(endTime);
	final DateTime end = new DateTime(dateEnd, timeEnd);
	final List<Tag> personTags = new ArrayList<>();
	for (XmlAdaptedTag tag : tagged) {
	    personTags.add(tag.toModelType());
	}
	final UniqueTagList tags = new UniqueTagList(personTags);

	Task newTask = new Task(taskName, start, end, isComplete, isPinned, tags);
	return newTask;

    }
}
