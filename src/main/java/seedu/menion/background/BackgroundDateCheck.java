package seedu.menion.background;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import seedu.menion.model.Model;
import seedu.menion.model.ReadOnlyActivityManager;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.ReadOnlyActivity;

/**
 * This class does a background check on Menion for any Activities which may have 
 * it's deadline passed.
 * @author lowjiansheng
 *
 */
public class BackgroundDateCheck {

	public BackgroundDateCheck(){};
	
	/**
	 * This method does a check on all the activities in Menion
	 */
	public static void checkActivities(Model model){
		
		Calendar currentTime = Calendar.getInstance();
		checkTasks(model, currentTime);
		checkEvents(model, currentTime);
		
	}
	
	/**
	 * This method scans through Menion to find any tasks which may have passed.
	 * @param model
	 * @param currentTime
	 */
	private static void checkTasks(Model model, Calendar currentTime){
		
		ReadOnlyActivityManager activityManager = model.getActivityManager();
		List<ReadOnlyActivity> taskList = activityManager.getTaskList();
		
		for (int i = 0 ; i < taskList.size(); i++){	
			ReadOnlyActivity taskToCheck = taskList.get(i);
			
			if (isActivityOver(currentTime, taskToCheck)){
				SendEmailStub.send(taskToCheck);
			};	
		}
	}
	
	/**
	 * This method scans through Menion to find any events which may have passed.
	 * @param model
	 * @param currentTime
	 */
	private static void checkEvents(Model model, Calendar currentTime){
		
		ReadOnlyActivityManager activityManager = model.getActivityManager();
		List<ReadOnlyActivity> eventList = activityManager.getEventList();
		
		for (int i = 0 ; i < eventList.size(); i++){
			ReadOnlyActivity eventToCheck = eventList.get(i);
			
			if (isActivityOver(currentTime, eventToCheck)){
				SendEmailStub.send(eventToCheck);
			}
			
		}
		
	}
	
	/**
	 * @param currentTime
	 * @param activityToCheck
	 * @return true: the current time is later than the activity time. 
	 */
	private static boolean isActivityOver(Calendar currentTime, ReadOnlyActivity activityToCheck){
		
		assert(activityToCheck != null);
		
		String activityDateString = activityToCheck.getActivityStartDate().toString();
		String activityTimeString = activityToCheck.getActivityStartTime().toString();
		int [] dateValues = new int[3];
		int [] timeValues = new int[2];
		extractDateValues(activityDateString, dateValues);
		extractTimeValues(activityTimeString, timeValues);
		
		Calendar activityDateCal = Calendar.getInstance();
		activityDateCal.set(dateValues[2], dateValues[1], dateValues[0], timeValues[0], timeValues[1]);
		
		// Activity has passed
		if (currentTime.compareTo(activityDateCal) >= 0){
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * This method extracts the time values from a time string.
	 * @param time
	 * @param timeValues[0]: hours, timeValues[1]: minutes
	 */
	private static void extractTimeValues(String time, int[] timeValues){
	
		// Makes sure that the date is in the correct HHMM format.
		assert(time.length() == 4);
		
		timeValues[0] = Integer.parseInt(time.substring(0, 2));
		timeValues[1] = Integer.parseInt(time.substring(2,4));
		
	}
	
	/**
	 * This method returns the individual values of the String date.
	 * @param String date
	 * @param dateValues[0]: day in months, dateValues[1]: month, dateValues[2]: year
	 */
	private static void extractDateValues(String date, int[] dateValues){
		
		String[] parts = date.split("-");
		dateValues[0] = Integer.parseInt(parts[0]);
		dateValues[1] = Integer.parseInt(parts[1]) - 1;
		dateValues[2] = Integer.parseInt(parts[2]);
		
	}
	
}
