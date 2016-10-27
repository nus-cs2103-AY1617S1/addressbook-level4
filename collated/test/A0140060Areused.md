# A0140060Areused
###### \java\seedu\taskmanager\logic\LogicManagerTest.java
``` java
        /**
         * Generates a TaskManager with auto-generated items.
         */
        TaskManager generateTaskManager(int numGenerated) throws Exception{
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, numGenerated);
            return taskManager;
        }

        /**
         * Generates a TaskManager based on the list of Items given.
         */
        TaskManager generateTaskManager(List<Item> items) throws Exception{
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, items);
            return taskManager;
        }

        /**
         * Adds auto-generated Item objects to the given TaskManager
         * @param taskManager The TaskManager to which the Items will be added
         */
        void addToTaskManager(TaskManager taskManager, int numGenerated) throws Exception{
            addToTaskManager(taskManager, generateItemList(numGenerated));
        }

        /**
         * Adds the given list of Items to the given TaskManager
         */
        void addToTaskManager(TaskManager taskManager, List<Item> itemsToAdd) throws Exception{
            for(Item p: itemsToAdd){
                taskManager.addItem(p);
            }
        }

```
###### \java\seedu\taskmanager\logic\LogicManagerTest.java
``` java
        /**
         * Generates a list of Items based on the flags.
         */
        List<Item> generateItemList(int numGenerated) throws Exception{
            List<Item> items = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                items.add(generateItem(i));
            }
            return items;
        }

        List<Item> generateItemList(Item... items) {
            return Arrays.asList(items);
        }

        /**
         * Generates a Item object with given name. Other fields will have some dummy values.
         */
        Item generateItemWithName(String name) throws Exception {
        	String itemType = "deadline";
            return new Item(
                    new ItemType(itemType),
                    new Name(name),
                    new ItemDate(""),
                    new ItemTime(""),
                    new ItemDate("2016-12-15"),
                    new ItemTime("01:39"),
                    new UniqueTagList(new Tag("tag"))
            );
        }
    }
}
```
###### \java\seedu\taskmanager\testutil\ItemBuilder.java
``` java
public class ItemBuilder {

    private TestItem item;

    public ItemBuilder() {
        this.item = new TestItem();
    }

    public ItemBuilder withItemType(String itemType) throws IllegalValueException {
        this.item.setItemType(new ItemType(itemType));
        return this;
    }

    public ItemBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            item.getTags().add(new Tag(tag));
        }
        return this;
    }

    public ItemBuilder withEndDate(String endDate) throws IllegalValueException {
        this.item.setEndDate(new ItemDate(endDate));
        return this;
    }
    
    public ItemBuilder withEndTime(String endTime) throws IllegalValueException {
        this.item.setEndTime(new ItemTime(endTime));
        return this;
    }

    public ItemBuilder withName(String name) throws IllegalValueException {
        this.item.setName(new Name(name));
        return this;
    }

    public ItemBuilder withStartDate(String startDate) throws IllegalValueException {
        this.item.setStartDate(new ItemDate(startDate));
        return this;
    }
    
    public ItemBuilder withStartTime(String startTime) throws IllegalValueException {
        this.item.setStartTime(new ItemTime(startTime));
        return this;
    }

    public TestItem build() {
        return this.item;
    }

}
```
###### \java\seedu\taskmanager\testutil\TaskManagerBuilder.java
``` java
/**
 * A utility class to help with building TaskManager objects.
 * Example usage: <br>
 *     {@code TaskManager ab = new TaskManagerBuilder().withItem("CS2103").withTag("Work").build();}
 */
public class TaskManagerBuilder {

    private TaskManager taskManager;

    public TaskManagerBuilder(TaskManager taskManager){
        this.taskManager = taskManager;
    }

    public TaskManagerBuilder withItem(Item item) throws UniqueItemList.DuplicateItemException {
        taskManager.addItem(item);
        return this;
    }

    public TaskManagerBuilder withTag(String tagName) throws IllegalValueException {
        taskManager.addTag(new Tag(tagName));
        return this;
    }

    public TaskManager build(){
        return taskManager;
    }
}
```
###### \java\seedu\taskmanager\testutil\TestUtil.java
``` java
    private static Item[] getSampleItemData() {
        try {
            return new Item[]{
                    new Item(new ItemType("event"), new Name("Game of Life"), new ItemDate("07-07"), new ItemTime("00:00"), new ItemDate("08-07"), new ItemTime("12:00"), new UniqueTagList()),
                    new Item(new ItemType("deadline"), new Name("This is a deadline"), new ItemDate(""), new ItemTime(""), new ItemDate("2017-05-05"), new ItemTime("23:59"), new UniqueTagList()),
                    new Item(new ItemType("task"), new Name("Win at Life"), new ItemDate(""), new ItemTime(""), new ItemDate(""), new ItemTime(""), new UniqueTagList()),
                    new Item(new ItemType("event"), new Name("This is an event"), new ItemDate("2018-05-05"), new ItemTime("23:59"), new ItemDate("2019-01-01"), new ItemTime("02:30"), new UniqueTagList()),
                    new Item(new ItemType("deadline"), new Name("Pay my bills"), new ItemDate(""), new ItemTime(""), new ItemDate("2020-12-30"), new ItemTime("04:49"), new UniqueTagList()),
                    new Item(new ItemType("task"), new Name("This is a task"), new ItemDate(""), new ItemTime(""), new ItemDate(""), new ItemTime(""), new UniqueTagList()),
                    new Item(new ItemType("event"), new Name("2103 exam"), new ItemDate("2018-05-05"), new ItemTime("21:59"), new ItemDate("2022-01-01"), new ItemTime("19:21"), new UniqueTagList()),
                    new Item(new ItemType("deadline"), new Name("Submit report"), new ItemDate(""), new ItemTime(""), new ItemDate("2023-03-03"), new ItemTime("14:21"), new UniqueTagList()),
                    new Item(new ItemType("task"), new Name("Buy a dozen cartons of milk"), new ItemDate(""), new ItemTime(""), new ItemDate("2016-11-21"), new ItemTime("13:10"), new UniqueTagList()),
                    new Item(new ItemType("event"), new Name("Java Workshop"), new ItemDate("2017-01-02"), new ItemTime("08:00"), new ItemDate("2017-01-02"), new ItemTime("12:00"), new UniqueTagList()),
                    new Item(new ItemType("deadline"), new Name("Submit essay assignment"), new ItemDate(""), new ItemTime(""), new ItemDate("2016-11-28"), new ItemTime("21:29"), new UniqueTagList()),
                    new Item(new ItemType("task"), new Name("Call for the electrician"), new ItemDate(""), new ItemTime(""), new ItemDate(""), new ItemTime(""), new UniqueTagList())
            };
        } catch (IllegalValueException e) {
            assert false;
            //not possible
            return null;
        }
    }
```
###### \java\seedu\taskmanager\testutil\TestUtil.java
``` java
    /**
     * Removes a subset from the list of items.
     * @param items The list of items
     * @param itemsToRemove The subset of items.
     * @return The modified items after removal of the subset from items.
     */
    public static TestItem[] removeItemsFromList(final TestItem[] items, TestItem... itemsToRemove) {
        List<TestItem> listOfItems = asList(items);
        listOfItems.removeAll(asList(itemsToRemove));
        return listOfItems.toArray(new TestItem[listOfItems.size()]);
    }


    /**
     * Returns a copy of the list with the item at specified index removed.
     * @param list original list to copy from
     * @param targetIndexInOneIndexedFormat e.g. if the first element to be removed, 1 should be given as index.
     */
    public static TestItem[] removeItemFromList(final TestItem[] list, int targetIndexInOneIndexedFormat) {
        return removeItemsFromList(list, list[targetIndexInOneIndexedFormat-1]);
    }
    
    /**
     * Returns a copy of the list with items at the specified multiple indexes removed.
     * @param list original list to copy from
     * @param targetIndexes the integer array of indexes of the items to be removed
     */
    public static TestItem[] removeItemsFromList(final TestItem[] list, int[] targetIndexes) {
        TestItem[] itemsToRemove = new TestItem[targetIndexes.length];
        int numToRemove = 0;
        for (int targetIndex : targetIndexes) {
            itemsToRemove[numToRemove] = list[targetIndex-1];
            numToRemove += 1;
        }

        return removeItemsFromList(list, itemsToRemove);
    }

    /**
     * Replaces items[i] with an item.
     * @param items The array of items.
     * @param item The replacement item
     * @param index The index of the item to be replaced.
     * @return
     */
    public static TestItem[] replaceItemFromList(TestItem[] items, TestItem item, int index) {
        items[index] = item;
        return items;
    }

    /**
     * Appends items to the array of items.
     * @param items A array of items.
     * @param itemsToAdd The items that are to be appended behind the original array.
     * @return The modified array of items.
     */
    public static TestItem[] addItemsToList(final TestItem[] items, TestItem... itemsToAdd) {
        List<TestItem> listOfItems = asList(items);
        listOfItems.addAll(asList(itemsToAdd));
        return listOfItems.toArray(new TestItem[listOfItems.size()]);
    }

    private static <T> List<T> asList(T[] objs) {
        List<T> list = new ArrayList<>();
        for(T obj : objs) {
            list.add(obj);
        }
        return list;
    }

    public static boolean compareCardAndItem(TaskCardHandle card, ReadOnlyItem item) {
        return card.isSameItem(item);
    }

    public static Tag[] getTagList(String tags) {

        if (tags.equals("")) {
            return new Tag[]{};
        }

        final String[] split = tags.split(", ");

        final List<Tag> collect = Arrays.asList(split).stream().map(e -> {
            try {
                return new Tag(e.replaceFirst("Tag: ", ""));
            } catch (IllegalValueException e1) {
                //not possible
                assert false;
                return null;
            }
        }).collect(Collectors.toList());

        return collect.toArray(new Tag[split.length]);
    }

}
```
###### \java\seedu\taskmanager\testutil\TypicalTestItems.java
``` java
    public static void loadTaskManagerWithSampleData(TaskManager ab) {

        try {
            ab.addItem(new Item(event1));
            ab.addItem(new Item(deadline1));
            ab.addItem(new Item(task1));
            ab.addItem(new Item(event2));
            ab.addItem(new Item(deadline2));
            ab.addItem(new Item(task2));
            ab.addItem(new Item(event3));
            ab.addItem(new Item(deadline3));
            ab.addItem(new Item(task3));
            ab.addItem(new Item(event4));
//            ab.addItem(new Item(deadline4));
//            ab.addItem(new Item(task4));
        } catch (UniqueItemList.DuplicateItemException e) {
            assert false : "not possible";
        }
    }

    public TestItem[] getTypicalItems() {
        return new TestItem[]{event1, deadline1, task1, event2, deadline2, task2, event3, deadline3, task3,
                event4}; //deadline4, task4};
    }
    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
```
