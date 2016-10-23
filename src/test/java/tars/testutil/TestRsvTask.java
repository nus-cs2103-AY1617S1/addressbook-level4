package tars.testutil;

import tars.model.task.DateTime;
import tars.model.task.rsv.RsvTask;

public class TestRsvTask extends RsvTask {
    
    public TestRsvTask() {
        
    }
    
   
    public void setDateTimeList(DateTime...dateTimes) {
        for (DateTime dt : dateTimes) {
            dateTimeList.add(dt);
        }
    }
        
    public String getRsvCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("rsv " + this.getName().taskName + " ");
        for (DateTime dt : dateTimeList) {
            sb.append("/dt " + dt.toString() + " ");
        }
        
        return sb.toString();
    }
    

}
