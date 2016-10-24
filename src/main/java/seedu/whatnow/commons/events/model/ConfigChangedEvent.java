package seedu.whatnow.commons.events.model;

import java.nio.file.Path;

import seedu.whatnow.commons.core.Config;
import seedu.whatnow.commons.events.BaseEvent;

/** Indicates the WhatNow in the model has changed*/
public class ConfigChangedEvent extends BaseEvent {

    public final Path destination;
    
    public final Config config;
    
    public ConfigChangedEvent(Path destination, Config config){
        
        this.destination = destination;
        
        this.config = config;
    }

    @Override
    public String toString(){
        return "destination = " + destination;
        
    }
}
