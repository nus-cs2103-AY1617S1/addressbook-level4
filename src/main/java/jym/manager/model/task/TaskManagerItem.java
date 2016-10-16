package jym.manager.model.task;

import java.time.LocalDateTime;
import java.util.Date;


public abstract class TaskManagerItem {

	
	
	public abstract Description getDescription();
	public abstract Location getLocation();
	public abstract LocalDateTime getDate();

	
}
