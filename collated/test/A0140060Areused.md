# A0140060Areused
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
