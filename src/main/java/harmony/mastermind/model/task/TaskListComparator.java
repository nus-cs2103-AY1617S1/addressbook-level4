package harmony.mastermind.model.task;

import java.util.Comparator;

//@@author A0138862W
public class TaskListComparator implements Comparator<ReadOnlyTask> {

    /*
     * 
     * By default, compare by creation dates.
     * This comparator use for sorting table view so edit/delete will not add to the bottom of the list
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(ReadOnlyTask o1, ReadOnlyTask o2) {
        if(o1.getCreatedDate() == null || o2.getCreatedDate() == null){
            return 0;
        }
       
        if (o1.getCreatedDate().after(o2.getCreatedDate())) {
            return 1;
        } else if (o1.getCreatedDate().before(o2.getCreatedDate())) {
            return -1;
        } else {
            return 0;
        }                
    }
}
