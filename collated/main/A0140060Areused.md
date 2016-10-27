# A0140060Areused
###### \java\seedu\taskmanager\logic\commands\SelectCommand.java
``` java
/**
 * Selects an item identified using it's last displayed index from the task manager
 */
public class SelectCommand extends Command {

    public final int targetIndex;

    public static final String COMMAND_WORD = "select";
    
```
###### \java\seedu\taskmanager\model\item\Item.java
``` java
/**
 * Represents a Item in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 * Date and Time can be empty strings.
 */
public class Item implements ReadOnlyItem {

    private ItemType itemType;
    private Name name;
    private ItemDate startDate;
    private ItemTime startTime;
    private ItemDate endDate;
    private ItemTime endTime;
    private boolean done;

    private UniqueTagList tags;

    /**
     * Convenience constructor for tasks. Calls primary constructor with empty fields for startDate, startTime, endDate, endTime
     *
     */
    public Item(ItemType itemType, Name name, UniqueTagList tags) {
        this(itemType, name, new ItemDate(), new ItemTime(), new ItemDate(), new ItemTime(), tags);
    }
    
    /**
     * Convenience constructor for deadlines. Calls primary constructor with empty fields for startDate and startTime
     *
     */
    public Item(ItemType itemType, Name name, ItemDate endDate, ItemTime endTime, UniqueTagList tags) {
        this(itemType, name, new ItemDate(), new ItemTime(), endDate, endTime, tags);
    }
    
    
    /**
     * Every field must be present and not null.
     */
    public Item(ItemType itemType, Name name, ItemDate startDate, ItemTime startTime, ItemDate endDate, ItemTime endTime, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(itemType, name, startDate, startTime, endDate, endTime, tags);
        this.itemType = itemType;
        this.name = name;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.done = false;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    /**
     * Every field must be present and not null.
     */
    public Item(ItemType itemType, Name name, ItemDate startDate, ItemTime startTime, ItemDate endDate, ItemTime endTime, boolean done, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(itemType, name, startDate, startTime, endDate, endTime, done, tags);
        this.itemType = itemType;
        this.name = name;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.done = done;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Item(ReadOnlyItem source) {
        this(source.getItemType(), source.getName(), source.getStartDate(), source.getStartTime(), source.getEndDate(), source.getEndTime(), source.getDone(), source.getTags());
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
    public ItemDate getStartDate() {
        return startDate;
    }
    
    @Override
    public ItemTime getStartTime() {
        return startTime;
    }   
    
    @Override
    public ItemDate getEndDate() {
        return endDate;
    }
    
    @Override
    public ItemTime getEndTime() {
        return endTime;
    }
    
```
###### \java\seedu\taskmanager\model\item\ItemType.java
``` java
/**
 * Represents a Item's type in the task manager.
 */
public class ItemType {

	public static final String EVENT_WORD = "event";
	public static final String DEADLINE_WORD = "deadline";
	public static final String TASK_WORD = "task";
	
    public static final String MESSAGE_ITEM_TYPE_CONSTRAINTS = "Item types should only be 'task', 'deadline' or 'event'.";
    public static final String ITEMTYPE_VALIDATION_REGEX = EVENT_WORD + "|" + DEADLINE_WORD + "|" + TASK_WORD;

    public final String value;
    
```
###### \java\seedu\taskmanager\model\item\Name.java
``` java
/**
 * Represents a Item's name in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_NAME_CONSTRAINTS = "Item names should not contain the symbol " + "'#'";
    public static final String NAME_VALIDATION_REGEX = "[^" + "#" + "]+";

    public final String value;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Name(String name) throws IllegalValueException {
        assert name != null;
        name = name.trim();
        if (!isValidName(name)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.value = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

```
###### \java\seedu\taskmanager\model\Model.java
``` java
    /** Deletes the given item. */
    void deleteItem(ReadOnlyItem target, String actionTaken) throws UniqueItemList.ItemNotFoundException;

    /** Adds the given item */
    void addItem(Item item, String actionTaken) throws UniqueItemList.DuplicateItemException;

```
###### \java\seedu\taskmanager\model\Model.java
``` java
    /** Updates the filter of the filtered item list to filter by the given keywords*/
    void updateFilteredItemList(Set<String> keywords);
    
```
