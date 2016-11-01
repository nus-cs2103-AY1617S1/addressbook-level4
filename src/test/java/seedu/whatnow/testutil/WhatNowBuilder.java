package seedu.whatnow.testutil;

import seedu.whatnow.commons.exceptions.IllegalValueException;
import seedu.whatnow.model.WhatNow;
import seedu.whatnow.model.tag.Tag;
import seedu.whatnow.model.task.Task;
import seedu.whatnow.model.task.UniqueTaskList;

/**
 * A utility class to help with building Whatnow objects. Example usage: <br>
 * {@code WhatNow ab = new WhatNowBuilder().withTask("John", "Doe").withTag("Friend").build();}
 */
public class WhatNowBuilder {

    private WhatNow whatNow;

    public WhatNowBuilder(WhatNow whatNow) {
        this.whatNow = whatNow;
    }

    public WhatNowBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        whatNow.addTask(task);
        return this;
    }

    public WhatNowBuilder withTag(String tagName) throws IllegalValueException {
        whatNow.addTag(new Tag(tagName));
        return this;
    }

    public WhatNow build() {
        return whatNow;
    }
}
