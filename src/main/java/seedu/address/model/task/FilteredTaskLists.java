package seedu.address.model.task;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class FilteredTaskLists {
	
	private final FilteredList<Task> todayTaskList;
    private final FilteredList<Task> tomorrowTaskList;
    private final FilteredList<Task> in7DaysTaskList; 
    private final FilteredList<Task> in30DaysTaskList;
    private final FilteredList<Task> somedayTaskList;
	
	public FilteredTaskLists(ObservableList<Task> uniqueTaskInternalList) {
	    todayTaskList = new FilteredList<Task>(uniqueTaskInternalList, today -> true);
	    tomorrowTaskList = new FilteredList<Task>(uniqueTaskInternalList, tomorrow -> true);
	    in7DaysTaskList = new FilteredList<Task>(uniqueTaskInternalList, in7Days -> true);
	    in30DaysTaskList = new FilteredList<Task>(uniqueTaskInternalList, in30Days -> true);
	    somedayTaskList = new FilteredList<Task>(uniqueTaskInternalList, someday -> true);
	}
	
	

	public ObservableList<Task> getTodayTaskList() {
		return todayTaskList;
	}

	public ObservableList<Task> getTomorrowTaskList() {
		return tomorrowTaskList;
	}

	public ObservableList<Task> getIn7DaysTaskList() {
		return in7DaysTaskList;
	}

	public ObservableList<Task> getIn30DaysTaskList() {
		return in30DaysTaskList;
	}

	public ObservableList<Task> getSomedayTaskList() {
		return somedayTaskList;
	}


}
