package seedu.address.storage;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;

import seedu.address.model.TaskBook;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;

public class JsonStorageModule extends Module {

    @Override
    public String getModuleName() {
        return "JsonStorageModule";
    }

    @Override
    public Version version() {
        return Version.unknownVersion();
    }

    @Override
    public void setupModule(SetupContext context) {
        context.setMixInAnnotations(Name.class, JsonNameMixin.class);
        context.setMixInAnnotations(Task.class, JsonTaskMixin.class);
        context.setMixInAnnotations(TaskBook.class, JsonTaskBookMixin.class);
    }

}
