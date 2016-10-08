package seedu.address.testutil;

import seedu.address.model.item.*;
import seedu.address.model.tag.UniqueTagList;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyPerson {

    private ItemType itemType;
    private Time address;
    private Date email;
    private Name name;
    private UniqueTagList tags;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public void setAddress(Time address) {
        this.address = address;
    }

    public void setDate(Date email) {
        this.email = email;
    }

    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public ItemType getItemType() {
        return itemType;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Date getDate() {
        return email;
    }

    @Override
    public Time getTime() {
        return address;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getItemType().value + " ");
        sb.append("n/" + this.getName().value + " ");
        sb.append("e/" + this.getDate().value + " ");
        sb.append("a/" + this.getTime().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
