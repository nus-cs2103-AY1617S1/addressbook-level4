package jym.manager.model.task;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import jym.manager.commons.exceptions.IllegalValueException;
import jym.manager.commons.util.CollectionUtil;
import jym.manager.model.tag.UniqueTagList;

public class Event extends TaskManagerItem implements ReadOnlyTMItem {
	
	private Description descr;
	private Location loc;
	private Deadline startTime;
	private Deadline endTime;
	private double duration; //implementation gimmick - unnecessary to create another LDT because it should be fairly close to the eventDate. Therefore keep an int instead to make it more convenient.
	private Priority pri;
	
	public Event(Description description, Object ... objects) throws IllegalValueException{
		assert !CollectionUtil.isAnyNull(description, objects);
		this.descr = description;
		this.loc = new Location();
		this.startTime = new Deadline();
		this.pri = new Priority(0);
		for(int i = 0; i < objects.length; i++){
    		Object o = objects[i];
    		if(o instanceof String){
    			this.loc = new Location((String)o);
    		} else if(o instanceof Location){ 
    			this.loc = (Location)o;
    		} else if(o instanceof List){
    			List<LocalDateTime> l = (List<LocalDateTime>)o;
    			this.startTime = new Deadline(l.get(0));
    			this.endTime = new Deadline(l.get(1));
    		} else if(o instanceof Priority){
    			this.pri = (Priority)o;
    		} else if(o instanceof Integer){
    			this.pri = new Priority((Integer)o);
    		}
    	}
	}
	
	public Event(Description d, Location l, Deadline ldt, double dur, Priority p) {
		this.descr = d;
		this.loc = l;
		this.startTime = ldt;
		this.duration = dur;
		pri = p;
	}
	public Event(Description d, Deadline ldt, double dur){
		this.descr = d;
		this.loc = null;
		this.startTime = ldt;
		this.duration = dur;
	}
	
	@Override
	public Description getDescription() {
		return this.descr;
	}

	@Override
	public Location getLocation() {
		return this.loc;
	}

	@Override
	public Deadline getDate() {
		return this.startTime;
	}
	public LocalTime getStartTime(){
		return this.startTime.getDate().toLocalTime();
	}
	public LocalTime getEndTime(){
		//this is bad form, fix when you can (when you're not strapped for time for a deadline two days away)
		long numHours = (long)duration;
		long numMins = (long) ((duration - (long)duration) * 60);
		long numSecs =  (long) (((duration - (long)duration) * 60) - (long)((duration - (long)duration) * 60));
		
		return this.startTime.getDate().plusHours(numHours).plusMinutes(numMins).plusSeconds(numSecs).toLocalTime();
	}
	public String toString(){
		return getAsText();
	}

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(this.descr, this.loc, this.startTime);
    }
	@Override
	public Priority getPriority() {
		return this.pri;
	}
	@Override
	public UniqueTagList getTags() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Complete getComplete() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
