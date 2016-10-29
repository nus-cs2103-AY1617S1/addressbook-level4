package seedu.todoList.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.todoList.model.task.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//@@author A0144061U-reused
public class EventCard extends UiPart{

    private static final String FXML = "EventCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label endDate;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label done;

    private Event task;
    private int displayedIndex;

    public EventCard(){

    }

    public static EventCard load(ReadOnlyTask task, int displayedIndex){
        EventCard card = new EventCard();
        card.task = (Event) task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }
    
    //@@author A0139923X
    public boolean checkEndDateTime(){
        //CHECK END DATE -------------------------------
        String endTaskDate = task.getEndDate().endDate;
        int month;
        String day;
        
        //CHECK END TIME -------------------------------
        String endTaskTime = task.getEndTime().endTime;
        int getHour = 12, getMinute;
        //get hour
        String hour = (task.getEndTime().endTime).substring(0, 2);
        //get min
        String minute = (task.getEndTime().endTime).substring(3,5);
        //get AM/PM
        String AM_PM = (task.getEndTime().endTime).substring(5,7);
           
        //Date object-----------------------------------------
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date dateobj = new Date();
        String [] dateArr = endTaskDate.split(" ");
        String [] curDate = df.format(dateobj).split("-");
        day = dateArr[0].substring(0, 2);
        
        //Time object-----------------------------------------
        DateFormat tf = new SimpleDateFormat("h:m:a");
        Date timeobj = new Date();
        String [] curTime = tf.format(timeobj).split(":");
            
        //Convert month to int value
        switch(dateArr[1]){
           case "January" : month = 1; break;
           case "Febuary" : month = 2; break;
           case "March" : month = 3; break;
           case "April" : month = 4; break;
           case "May" : month = 5; break;
           case "June" : month = 6; break;
           case "July" : month = 7; break;
           case "August" : month = 8; break;
           case "September" : month = 9; break;
           case "October" : month = 10; break;
           case "November" : month = 11; break;
           default: month = 12;
        }
        
        //Convert string hour to int 
        switch(hour){
            case "01": getHour = 1; break;
            case "02": getHour = 2; break;
            case "03": getHour = 3; break;
            case "04": getHour = 4; break;
            case "05": getHour = 5; break;
            case "06": getHour = 6; break;
            case "07": getHour = 7; break;
            case "08": getHour = 8; break;
            case "09": getHour = 9; break;
            case "10": getHour = 10; break;
            case "11": getHour = 11; break;
            default: getHour = 12;
        }
        
        switch(minute){
            case "00": getMinute = 0; break;
            case "01": getMinute = 1; break;
            case "02": getMinute = 2; break;
            case "03": getMinute = 3; break;
            case "04": getMinute = 4; break;
            case "05": getMinute = 5; break;
            case "06": getMinute = 6; break;
            case "07": getMinute = 7; break;
            case "08": getMinute = 8; break;
            case "09": getMinute = 9; break;
            default: getMinute = Integer.parseInt(minute);
        }
        
        //Check year or if same year, check month or if same year, same month , check day
        if((Integer.parseInt(dateArr[2]) < Integer.parseInt(curDate[2]))
            || (Integer.parseInt(dateArr[2]) == Integer.parseInt(curDate[2]) && month < Integer.parseInt(curDate[1]))
            || (Integer.parseInt(dateArr[2]) == Integer.parseInt(curDate[2]) && month == Integer.parseInt(curDate[1]) && Integer.parseInt(day) < Integer.parseInt(curDate[0]))){
            return false;
        //Same day so check time    
        }else if(Integer.parseInt(dateArr[2]) == Integer.parseInt(curDate[2]) && month == Integer.parseInt(curDate[1]) && Integer.parseInt(day) == Integer.parseInt(curDate[0])){
            if((AM_PM.equals("am") && curTime[2].equals("AM")) || (AM_PM.equals("pm") && curTime[2].equals("PM"))){
               //Check if same hour then check minute difference
                if(getHour == Integer.parseInt(curTime[0])){
                   if(getMinute < Integer.parseInt(curTime[1])){
                       return false;
                   }else{
                       return true;
                   }
               //Check if task end time is less than current time
               //Check if task is in morning or noon and 12 is suppose to be lesser than current time (1am/pm is more than 12am/pm)    
               }else if((getHour < Integer.parseInt(curTime[0]))
                       || ((AM_PM.equals("am") || AM_PM.equals("pm")) && getHour == 12 && getHour > Integer.parseInt(curTime[0]))){
                   return false;
               }else{
                   return true;
               }
            //Check if task end time is am(morning) while current time is pm(night)   
            }else if((AM_PM.equals("am") && curTime[2].equals("PM"))){
                return false;
            }else{
                return true;
            }
        }else{
            return true;
        }       
    }
    
    @FXML
    public void initialize() {
        name.setText(task.getName().name);
        id.setText(displayedIndex + ". ");
        date.setText("Start Date: "+ task.getStartDate().date);
        if(checkEndDateTime() && this.task.getDone().equals("true")) {
            endDate.setText("End Date: " + task.getEndDate().endDate);
            startTime.setText("Start Time: " + task.getStartTime().startTime);
            endTime.setText("End Time: " + task.getEndTime().endTime);
        	done.setText("Completed");
    		cardPane.setStyle("-fx-background-color: #01DF01");
    	}else if(!checkEndDateTime() && this.task.getDone().equals("false")) {
            endDate.setText("End Date: " + task.getEndDate().endDate);
            startTime.setText("Start Time: " + task.getStartTime().startTime);
            endTime.setText("End Time: " + task.getEndTime().endTime);
            done.setText("Overdue");
            cardPane.setStyle("-fx-background-color: #ff2002");
        }else {
            endDate.setText("End Date: " + task.getEndDate().endDate);
            startTime.setText("Start Time: " + task.getStartTime().startTime);
            endTime.setText("End Time: " + task.getEndTime().endTime);
    		done.setText("Not Completed");
    		cardPane.setStyle("-fx-background-color: #FFFFFF");
    	}
    }

    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
