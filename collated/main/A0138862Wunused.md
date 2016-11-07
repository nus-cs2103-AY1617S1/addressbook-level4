# A0138862Wunused
###### /java/harmony/mastermind/logic/commands/AddCommand.java
``` java
    /**
     * 
     * The builder constructor has taken care of all the construction of event, floating and deadline
     * @see AddCommand(AddCommandBuilder)
     * @deprecated
     * 
     */
    /*
    public AddCommand(String name, String endDateStr, Set<String> tags, String recur, Memory mem) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        Date createdDate = new Date();
        Date endDate = prettyTimeParser.parse(endDateStr).get(0);
        
        
        this.toAdd = new Task(name, endDate, new UniqueTagList(tagSet), recur, createdDate);
        
        //Converting Date end to Calendar end
        Calendar end = dateToCalendar(endDate);

        deadline = new GenericMemory(tags.toString(), name, "", end);
        mem.add(deadline);

    }
    */

    // floating
```
###### /java/harmony/mastermind/logic/commands/AddCommand.java
``` java
    /**
     * The builder constructor has taken care of all the construction of event, floating and deadline
     * @see AddCommand(AddCommandBuilder)
     * @deprecated
     * 
     */
    /*
    public AddCommand(String name, Set<String> tags, Memory mem) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        
        Date createdDate = new Date();

        this.toAdd = new Task(name, new UniqueTagList(tagSet), createdDate);
        
        task = new GenericMemory(tags.toString(), name, "");
        mem.add(task);
    }
    */

```
