package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    private static int START_DATE_INDEX = 0;
    private static int END_DATE_INDEX = 2;

    @XmlElement(required = true)
    private String name;
    @XmlElement
    private String date;
    @XmlElement
    private boolean isDone;
    @XmlElement
    private boolean isRecurring;
    @XmlElement
    private String frequency = "";

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {
    }

    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source
     *            future changes to this will not affect the created
     *            XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().taskName;
        date = source.getDate().getValue();
        isDone = source.isDone();
        isRecurring = source.isRecurring();
        if (isRecurring)
            frequency = source.getRecurring().recurringFrequency;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task
     * object.
     *
     * @throws IllegalValueException
     *             if there were any data constraints violated in the adapted
     *             task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final Date date;
        assert this.date != null;
        if (Deadline.isValidDeadline(this.date)) {
            date = new Deadline(this.date);
        } else {
            String[] dates = this.date.trim().split(" ");
            assert dates.length == 3;
            date = new EventDate(dates[START_DATE_INDEX], dates[END_DATE_INDEX]);
        }
        final UniqueTagList tags = new UniqueTagList(personTags);
        if (isRecurring){
           // System.out.println("isrecurring is true");
            return new Task(name, date, tags, isDone, new Recurring(frequency));
        }
        
            return new Task(name, date, tags, isDone, false);
        
    }
}
