# generated
###### /java/seedu/task/model/item/EventDuration.java
``` java
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		EventDuration other = (EventDuration) obj;
		return this.toString().equals(other.toString());
	}


	@Override
	public int compareTo(EventDuration o) {
		if (this.startTime.compareTo(o.startTime) == 0) {
			return this.endTime.compareTo(o.endTime);
		} else {
			return this.startTime.compareTo(o.startTime);
		}
	}
}
```
