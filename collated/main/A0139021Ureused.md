# A0139021Ureused
###### \java\seedu\todo\model\task\Task.java
``` java
/**
 * Represents a single task
 */
public class Task implements MutableTask {
    private StringProperty title = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private StringProperty location = new SimpleStringProperty();

    private BooleanProperty pinned = new SimpleBooleanProperty();
    private BooleanProperty completed = new SimpleBooleanProperty();

    private ObjectProperty<LocalDateTime> startTime = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> endTime = new SimpleObjectProperty<>();

    private ObjectProperty<Set<Tag>> tags = new SimpleObjectProperty<>(new HashSet<Tag>());
    private LocalDateTime createdAt = LocalDateTime.now();
    private UUID uuid;

    /**
     * Creates a new task
     */
    public Task(String title) {
        this.setTitle(title);
        this.uuid = UUID.randomUUID();
    }

    /**
     * Constructs a Task from a ReadOnlyTask
     */
    public Task(ImmutableTask task) {
        this.setTitle(task.getTitle());
        this.setDescription(task.getDescription().orElse(null));
        this.setLocation(task.getLocation().orElse(null));
        this.setStartTime(task.getStartTime().orElse(null));
        this.setEndTime(task.getEndTime().orElse(null));
        this.setCompleted(task.isCompleted());
        this.setPinned(task.isPinned());
        this.setCreatedAt(task.getCreatedAt());
        this.uuid = task.getUUID();
        this.setTags(task.getTags());
    }

    @Override
    public String getTitle() {
        return title.get();
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.ofNullable(description.get());
    }

    @Override
    public Optional<String> getLocation() {
        return Optional.ofNullable(location.get());
    }

    @Override
    public Optional<LocalDateTime> getStartTime() {
        return Optional.ofNullable(startTime.get());
    }

    @Override
    public Optional<LocalDateTime> getEndTime() {
        return Optional.ofNullable(endTime.get());
    }

    @Override
    public boolean isPinned() {
        return pinned.get();
    }

    @Override
    public boolean isCompleted() {
        return completed.get();
    }

    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.get());
    }
    
    @Override
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public void setTitle(String title) {
        this.title.set(title);
    }

    @Override
    public void setPinned(boolean pinned) {
        this.pinned.set(pinned);
    }

    @Override
    public void setCompleted(boolean completed) {
        this.completed.set(completed);
    }

    @Override
    public void setDescription(String description) {
        this.description.set(description);
    }

    @Override
    public void setLocation(String location) {
        this.location.set(location);
    }

    @Override
    public void setStartTime(LocalDateTime startTime) {
        this.startTime.set(startTime);
    }

    @Override
    public void setEndTime(LocalDateTime endTime) {
        this.endTime.set(endTime);
    }

    @Override
    public void setTags(Set<Tag> tags) {
        this.tags.set(tags);
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        
        this.createdAt = createdAt;
    }

    public Observable[] getObservableProperties() {
        return new Observable[] {
            title, description, location, startTime, endTime, tags, completed, pinned,
        };
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ImmutableTask)) {
            return false;
        }

        return uuid.equals(((ImmutableTask) o).getUUID());
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
    
    @Override
    public String toString() {
        return title.get();
    }
}
```
###### \java\seedu\todo\storage\XmlAdaptedTask.java
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
###### \java\seedu\todo\storage\XmlSerializableTodoList.java
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
