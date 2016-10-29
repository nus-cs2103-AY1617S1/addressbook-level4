package seedu.menion.background;

import seedu.menion.model.activity.ReadOnlyActivity;

public class SendEmailStub {

	public SendEmailStub(){};
	
	public static void send(ReadOnlyActivity activityToCheck){
		
		System.out.println("Email Sent for : " + activityToCheck.getActivityName());
	}
	
}
