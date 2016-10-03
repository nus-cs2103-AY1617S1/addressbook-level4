package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.todo.ReadOnlyToDo;
import seedu.address.model.todo.Title;
import seedu.address.model.todo.ToDo;

import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB-friendly version of the to-do
 */
public class XmlAdaptedToDo {

    @XmlElement(required = true)
    private String title;
//    @XmlElement(required = true)
//    private String phone;
//    @XmlElement(required = true)
//    private String email;
//    @XmlElement(required = true)
//    private String address;
//
//    @XmlElement
//    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedToDo() {}

    /**
     * Converts a given to-do into this class for JAXB use.
     *
     * @param toDo future changes to this will not affect the created XmlAdaptedToDo
     */
    public XmlAdaptedToDo(ReadOnlyToDo toDo) {
        title = toDo.getTitle().title;
//        phone = source.getPhone().value;
//        email = source.getEmail().value;
//        address = source.getAddress().value;
//        tagged = new ArrayList<>();
//        for (Tag tag : source.getTags()) {
//            tagged.add(new XmlAdaptedTag(tag));
//        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's to-do
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public ToDo toModelType() throws IllegalValueException {
//        final List<Tag> personTags = new ArrayList<>();
//        for (XmlAdaptedTag tag : tagged) {
//            personTags.add(tag.toModelType());
//        }
        final Title title = new Title(this.title);
//        final Phone phone = new Phone(this.phone);
//        final Email email = new Email(this.email);
//        final Address address = new Address(this.address);
//        final UniqueTagList tags = new UniqueTagList(personTags);
        return new ToDo(title);
    }
}
