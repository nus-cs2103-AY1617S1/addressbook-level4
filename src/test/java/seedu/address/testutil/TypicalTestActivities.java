package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ActivityManager;
import seedu.address.model.activity.*;

/**
 *
 */
public class TypicalTestActivities {

    public static TestActivity alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestActivities() {
        try {
            alice =  new ActivityBuilder().withName("Alice Pauline").withTags("friends").build();
            benson = new ActivityBuilder().withName("Benson Meier").withTags("owesMoney", "friends").build();
            carl = new ActivityBuilder().withName("Carl Kurz").build();
            daniel = new ActivityBuilder().withName("Daniel Meier").build();
            elle = new ActivityBuilder().withName("Elle Meyer").build();
            fiona = new ActivityBuilder().withName("Fiona Kunz").build();
            george = new ActivityBuilder().withName("George Best").build();

            //Manually added
            hoon = new ActivityBuilder().withName("Hoon Meier").build();
            ida = new ActivityBuilder().withName("Ida Mueller").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadActivityManagerWithSampleData(ActivityManager ab) {

//        try {
            ab.addActivity(new Activity(alice));
            ab.addActivity(new Activity(benson));
            ab.addActivity(new Activity(carl));
            ab.addActivity(new Activity(daniel));
            ab.addActivity(new Activity(elle));
            ab.addActivity(new Activity(fiona));
            ab.addActivity(new Activity(george));
//        } catch (UniquePersonList.DuplicatePersonException e) {
//            assert false : "not possible";
//        }
    }

    public TestActivity[] getTypicalPersons() {
        return new TestActivity[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public ActivityManager getTypicalActivityManager(){
        ActivityManager ab = new ActivityManager();
        loadActivityManagerWithSampleData(ab);
        return ab;
    }
}
