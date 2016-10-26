# A0139021Ureused
###### \src\main\java\seedu\todo\storage\XmlAdaptedTask.java
``` java
/**
 * JAXB-friendly version of the task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String title;
    @XmlElement
    private String description;
    @XmlElement
    private String location;

    @XmlElement(required = true)
    private boolean pinned;
    @XmlElement(required = true)
    private boolean completed;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime startTime;
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime endTime;

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime lastUpdated;
    @XmlElement(required = true)
    private UUID uuid;

    @XmlElement
    private Set<XmlAdaptedTag> tags = new HashSet<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {
    }

    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source
     *            future changes to this will not affect the created
     *            XmlAdaptedPerson
     */
    public XmlAdaptedTask(ImmutableTask source) {
        title = source.getTitle();
        description = source.getDescription().orElse(null);
        location = source.getLocation().orElse(null);

        pinned = source.isPinned();
        completed = source.isCompleted();

        startTime = source.getStartTime().orElse(null);
        endTime = source.getEndTime().orElse(null);

        for (Tag tag : source.getTags()) {
            tags.add(new XmlAdaptedTag(tag));
        }

        lastUpdated = source.getCreatedAt();
        uuid = source.getUUID();
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task
     * object.
     *
     * @throws IllegalValueException
     *             if there were any data constraints violated in the adapted
     *             person
     */
    public Task toModelType() throws IllegalValueException {
        Task task = new Task(title);
        task.setDescription(description);
        task.setLocation(location);

        task.setPinned(pinned);
        task.setCompleted(completed);

        task.setStartTime(startTime);
        task.setEndTime(endTime);

        Set<Tag> setOfTags = new HashSet<>();
        for (XmlAdaptedTag tag : tags) {
            setOfTags.add(tag.toModelType());
        }
        task.setTags(setOfTags);

        task.setCreatedAt(lastUpdated);
        return task;
    }
}
```
###### \src\main\java\seedu\todo\storage\XmlSerializableTodoList.java
``` java
/**
 * An Immutable TodoList that is serializable to XML format
 */
@XmlRootElement(name = "todolist")
public class XmlSerializableTodoList implements ImmutableTodoList {

    @XmlElement
    private List<XmlAdaptedTask> tasks;

    {
        tasks = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableTodoList() {}

    /**
     * Conversion
     */
    public XmlSerializableTodoList(ImmutableTodoList src) {
        tasks.addAll(src.getTasks().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
    }

    @Override
    public List<ImmutableTask> getTasks() {
        return tasks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }
}
```
