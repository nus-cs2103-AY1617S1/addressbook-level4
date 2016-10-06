package seedu.address.model.item;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Item in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Item implements ReadOnlyPerson {

    private ItemType itemType;
    private Name name;
    private Email email;
    private Address address;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Item(ItemType itemType, Name name, Email email, Address address, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(itemType, name, email, address, tags);
        this.itemType = itemType;
        this.name = name;
        this.email = email;
        this.address = address;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Item(ReadOnlyPerson source) {
        this(source.getItemType(), source.getName(), source.getEmail(), source.getAddress(), source.getTags());
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
    public Email getEmail() {
        return email;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(itemType, name, email, address, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
