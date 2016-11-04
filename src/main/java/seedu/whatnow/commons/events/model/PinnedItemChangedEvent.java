package seedu.whatnow.commons.events.model;

import java.nio.file.Path;

import seedu.whatnow.commons.core.Config;
import seedu.whatnow.commons.events.BaseEvent;

/** Indicates the WhatNow in the model has changed */
public class PinnedItemChangedEvent extends BaseEvent {

    public final Config config;
    public final String type;
    public final String keyword;

    public PinnedItemChangedEvent(Config config, String type, String keyword) {
        this.config = config;
        this.type = type;
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "Type: " + type + " Keyword: " + keyword;
    }
}
