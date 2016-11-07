# A0139021Ureused
###### \java\seedu\todo\model\UserPrefs.java
``` java
/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private static final Logger logger = LogsCenter.getLogger(UserPrefs.class);
    private UserPrefsStorage storage;

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public UserPrefs(){
        this.setDefaultGuiSettings();
    }

    public UserPrefs(Config config) {
        assert config != null;

        String prefsFilePath = config.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        this.storage = new UserPrefsStorage(prefsFilePath);
        try {
            this.updateLastUsedGuiSetting(storage.read().getGuiSettings());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. " +
                    "Using default user prefs");
            this.setDefaultGuiSettings();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        this.save();
    }

    private void setDefaultGuiSettings() {
        this.setGuiSettings(680, 780, 0, 0);
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    public void save() {
        try {
            storage.save(this);
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this){
            return true;
        }
        if (!(other instanceof UserPrefs)){ //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs)other;

        return Objects.equals(guiSettings, o.guiSettings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings);
    }

    @Override
    public String toString(){
        return guiSettings.toString();
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
     * @param source future changes to this will not affect the created
     * XmlAdaptedTask
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
                // This likely means that the task format changed between versions of 
                // the app. Unfortunately there's no good way to migrate data yet, 
                // so unfortunately we will lose some data here
                return null;
            }
        }).filter(task -> task != null)
            .collect(Collectors.toCollection(ArrayList::new));
    }
}
```
