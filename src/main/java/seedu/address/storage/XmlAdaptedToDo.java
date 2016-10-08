package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.todo.DateRange;
import seedu.address.model.todo.DueDate;
import seedu.address.model.todo.ReadOnlyToDo;
import seedu.address.model.todo.Title;
import seedu.address.model.todo.ToDo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB-friendly version of the to-do
 */
public class XmlAdaptedToDo {

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private Optional<DueDate> dueDate;
    @XmlElement(required = true)
    private Optional<DateRange> dateRange;
    @XmlElement(required = true, nillable=true)
    private Date startDate;
    @XmlElement(required = true, nillable=true)
    private Date endDate;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedToDo() {}

    /**
     * Converts a given to-do into this class for JAXB use.
     *
     * @param toDo future changes to this will not affect the created XmlAdaptedToDo
     */
    public XmlAdaptedToDo(ReadOnlyToDo source) {
        title = source.getTitle().title;
        dueDate = source.getDueDate();
        dateRange = source.getDateRange();
        if(dateRange.isPresent()){
            startDate = source.getDateRange().get().startDate;
            endDate = source.getDateRange().get().endDate;
        }
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted toDo object into the model's to-do
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted toDo
     */
    public ToDo toModelType() throws IllegalValueException {
        final Title title = new Title(this.title);
        ToDo todo = new ToDo(title);
        
        final Set<Tag> toDoTags = new HashSet<>();
        for (XmlAdaptedTag tag : tagged) {
            toDoTags.add(tag.toModelType());
        }
        todo.setTags(toDoTags);

        //Check if the dueDate is empty
        if(this.dueDate.isPresent()){
        	final DueDate dueDate = new DueDate(this.dueDate.get().dueDate);
        	todo.setDueDate(dueDate);
        }
        //Check if the dateRange is empty
        if(this.dateRange.isPresent()){
        	final DateRange dateRange = new DateRange(this.startDate,this.endDate);
            todo.setDateRange(dateRange);
        }
        
        return todo;
    }
}