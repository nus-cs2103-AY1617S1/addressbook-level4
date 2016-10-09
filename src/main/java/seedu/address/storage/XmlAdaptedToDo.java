package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.todo.DateRange;
import seedu.address.model.todo.DueDate;
import seedu.address.model.todo.ReadOnlyToDo;
import seedu.address.model.todo.Title;
import seedu.address.model.todo.ToDo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB-friendly version of the to-do
 */
public class XmlAdaptedToDo {
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String dueDate;
    @XmlElement(required = true)
    private String dateRangeStart;
    @XmlElement(required = true)
    private String dateRangeEnd;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedToDo() {}

    /**
     * Converts a given to-do into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedToDo
     */
    public XmlAdaptedToDo(ReadOnlyToDo source) {
        title = source.getTitle().title;

        if (source.getDueDate().isPresent()) {
            dueDate = dateFormatter.format(source.getDueDate().get().dueDate);
        }

        if (source.getDateRange().isPresent()) {
            dateRangeStart = dateFormatter.format(source.getDateRange().get().startDate);
            dateRangeEnd = dateFormatter.format(source.getDateRange().get().endDate);
        }

        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted ToDo object into the model's to-do
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted ToDo
     */
    public ToDo toModelType() throws IllegalValueException {
        final Title title = new Title(this.title);
        ToDo todo = new ToDo(title);
        
        final Set<Tag> toDoTags = new HashSet<>();
        for (XmlAdaptedTag tag : tagged) {
            toDoTags.add(tag.toModelType());
        }
        todo.setTags(toDoTags);

        // Check if the dueDate is empty
        if (dueDate != null){
        	try {
                todo.setDueDate(new DueDate(LocalDateTime.parse(dueDate, dateFormatter)));
            } catch (DateTimeParseException exception) {
                // invalid due date, ignore
            }
        }

        // Check if the dateRange is empty
        if (dateRangeStart != null && dateRangeEnd != null){
            try {
                todo.setDateRange(new DateRange(
                    LocalDateTime.parse(dateRangeStart, dateFormatter),
                    LocalDateTime.parse(dateRangeEnd, dateFormatter)
                ));
            } catch (DateTimeParseException exception) {
                // invalid date range, ignore
            }
        }
        
        return todo;
    }
}