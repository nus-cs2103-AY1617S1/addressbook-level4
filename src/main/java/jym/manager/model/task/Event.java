package jym.manager.model.task;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

import jym.manager.model.tag.UniqueTagList;

public class Event extends TaskManagerItem implements ReadOnlyTask {
	
	private Description descr;
	private Location loc;
	private Deadline eventDate;
	private double duration; //implementation gimmick - unnecessary to create another LDT because it should be fairly close to the eventDate. Therefore keep an int instead to make it more convenient.
	private Priority pri;
	
	public Event(Description d, Location l, Deadline ldt, double dur, Priority p) {
		this.descr = d;
		this.loc = l;
		this.eventDate = ldt;
		this.duration = dur;
		pri = p;
	}
	public Event(Description d, Deadline ldt, double dur){
		this.descr = d;
		this.loc = null;
		this.eventDate = ldt;
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
		return this.eventDate;
	}
	public LocalTime getStartTime(){
		return this.eventDate.getDate().toLocalTime();
	}
	public LocalTime getEndTime(){
		//this is bad form, fix when you can (when you're not strapped for time for a deadline two days away)
		long numHours = (long)duration;
		long numMins = (long) ((duration - (long)duration) * 60);
		long numSecs =  (long) (((duration - (long)duration) * 60) - (long)((duration - (long)duration) * 60));
		
		return this.eventDate.getDate().plusHours(numHours).plusMinutes(numMins).plusSeconds(numSecs).toLocalTime();
	}
	public String toString(){
		return getAsText();
	}

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(this.descr, this.loc, this.eventDate);
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
