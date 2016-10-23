package seedu.todo.model.task;


import seedu.todo.commons.exceptions.IllegalValueException;



public class Recurrence {
    
    public enum Frequency {
        NONE,
        YEAR,
        MONTH,
        WEEK,
        DAY
    }
    
    private Frequency freq;
    
    public Recurrence(Frequency freq) throws IllegalValueException {
        assert freq != null;
        this.freq = freq;
    }
    
    public Frequency getFreq() {
        return this.freq;
    }
    
    public void setFreq(Frequency freq) {
        this.freq = freq;
    }
        
    public boolean isRecurring() {
       return this.freq != Frequency.NONE;
    }
    
    @Override
    public String toString() {
        switch(this.freq) {
            case YEAR :
                return "Year";
            case MONTH :
                return "Month";
            case WEEK:
                return "Week";
            case DAY :
                return "Day";
            default :
                return "NONE";
        }
    }
    
    public void UpdateTaskDate(Task task){
        switch(this.freq) {
        case YEAR :
            if (task.getOnDate().getDate() != null) {
                task.getOnDate().setDate(task.getOnDate().getDate().plusYears(1));
            }
            if (task.getByDate().getDate() != null) {
                task.getByDate().setDate(task.getByDate().getDate().plusYears(1));
            }
        case MONTH :
            if (task.getOnDate().getDate() != null) {
                task.getOnDate().setDate(task.getOnDate().getDate().plusMonths(1));
            }
            if (task.getByDate().getDate() != null) {
                task.getByDate().setDate(task.getByDate().getDate().plusMonths(1));
            }
        case WEEK:
            if (task.getOnDate().getDate() != null) {
                task.getOnDate().setDate(task.getOnDate().getDate().plusWeeks(1));
            }
            if (task.getByDate().getDate() != null) {
                task.getByDate().setDate(task.getByDate().getDate().plusWeeks(1));
            }
        case DAY :
            if (task.getOnDate().getDate() != null) {
                task.getOnDate().setDate(task.getOnDate().getDate().plusDays(1));
            }
            if (task.getByDate().getDate() != null) {
                task.getByDate().setDate(task.getByDate().getDate().plusDays(1));
            }
        default :
            //NONE - do nothing
        }
    }
    
}
