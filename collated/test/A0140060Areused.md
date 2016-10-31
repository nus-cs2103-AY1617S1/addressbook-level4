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
