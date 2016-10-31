package seedu.todo.testutil;

import com.github.javafaker.Faker;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.ocpsoft.prettytime.shade.edu.emory.mathcs.backport.java.util.Collections;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.model.tag.Tag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;

//@@author A0135817B
public class TaskFactory {
    private static final Faker faker = new Faker(new Locale("en", "SG"));
    private static final Random random = new Random();
    
    private static final List<String> friends = ImmutableList.of(
        "Govind", "Li Kai", "Louie", "Xien Dong", "Alex", "Jim"
    );
    
    private static final List<String> locations = ImmutableList.of(
        "LT15", "School of Computing", "Engineering Canteen", "Cafe Nowhere", 
        "Cinnamon College", "West Coast Plaza"
    );
    
    // For realism the test subject shall only have 5 modules 
    // And no Govind, he's not taking a double degree
    private static final List<String> modules = ImmutableList.of(
        "MA1521", "CS2104", "GET1025", "CS1231", "CS2100");
    
    private static final List<String> tags = ImmutableList.of(
        "urgent", "homework", "assignment", "tea_time", "pokemon_go", "to-do-12",
        "sports", "dinner", "CS2103T", "physics", "chemistry", "econs", "math"
    );
    
    private static final List<String> events = ImmutableList.of(
        "Watch {sport} match with {friend}",
        "Watch the next {superhero} movie with {friend}",
        "Attend concert with {friend}"
    );
    
    private static final List<String> tasks = ImmutableList.of(
        "Finish {module} homework {number}", 
        "Do {module} assignment #{number}", 
        "Do {module} lab #{number}", 
        "Prepare for {module} test", 
        "Discuss {module} project with {friend}",
        "Fix bug #{number}", 
        "Read new novel from {name}",
        "Research on new ways to get {power}"
    );
    
    private static <T> T choice(List<T> choices) {
        return choices.get(random.nextInt(choices.size()));
    }
    
    private static String substitute(String format) {
        return format
            .replace("{friend}", choice(friends))
            .replace("{module}", choice(modules))
            .replace("{name}", faker.name().fullName())
            .replace("{sport}", faker.team().sport())
            .replace("{superhero}", faker.superhero().name())
            .replace("{power}", faker.superhero().power())
            .replace("{number}", Integer.toString(random.nextInt(6) + 1));
    }
    
    private static LocalDateTime randomDate() {
        return TimeUtil.today()
            .plusDays(random.nextInt(20))
            .plusHours(random.nextInt(12) + 9)
            .plusMinutes(random.nextInt(3) * 15);
    }
    
    public static String taskTitle() {
        return substitute(choice(tasks));
    }
    
    public static String eventTitle() {
        return substitute(choice(events));
    }

    //@@author A0135805H
    /**
     * Generates a random tag from the pool of tags.
     */
    public static String randomTag() {
        return tags.get(random.nextInt(tags.size()));
    }

    /**
     * Generates a list of unique tags from the pool of tags.
     */
    public static String[] randomTags(int numberOfTags) {
        List<String> copyOfTagNames = new ArrayList<>(tags);
        Collections.shuffle(copyOfTagNames);
        return copyOfTagNames.subList(0, numberOfTags).toArray(new String[0]);
    }

    /**
     * Generates a randomly sized list of unique tags from the pool of tags.
     */
    public static String[] randomTags() {
        return randomTags(1 + random.nextInt(4));
    }

    //@@author A0135817B
    public static ImmutableTask task() {
        return TaskBuilder.name(taskTitle()).build();
    }

    public static ImmutableTask fullTask() {
        return TaskBuilder.name(taskTitle())
            .description(faker.lorem().paragraph(2))
            .due(randomDate())
            .build();
    }

    public static ImmutableTask fullTaggedTask() {
        return TaskBuilder.name(taskTitle())
            .description(faker.lorem().paragraph(2))
            .due(randomDate())
            .tagged(randomTags())
            .build();
    }
    
    public static ImmutableTask event() {
        LocalDateTime start = randomDate();
        return TaskBuilder.name(eventTitle())
            .event(start, start.plusMinutes((random.nextInt(6) + 2) * 15))
            .build();
    }
    
    public static ImmutableTask fullEvent() {
        LocalDateTime start = randomDate();
        return TaskBuilder.name(eventTitle())
            .event(start, start.plusMinutes((random.nextInt(6) + 2) * 15))
            .location(choice(locations))
            .build();
    }

    public static ImmutableTask fullTaggedEvent() {
        LocalDateTime start = randomDate();
        return TaskBuilder.name(eventTitle())
            .event(start, start.plusMinutes((random.nextInt(6) + 2) * 15))
            .location(choice(locations))
            .tagged(randomTags())
            .build();
    }
    
    public static ImmutableTask random() {
        switch (random.nextInt(6)) {
            case 0:
                return task();
            case 1:
                return fullTask();
            case 2:
                return fullTaggedTask();
            case 3:
                return event();
            case 4:
                return fullEvent();
            default:
                return fullTaggedEvent();
        }
    }
    
    public static List<ImmutableTask> list() {
        List<ImmutableTask> tasks = Lists.newArrayList(random());
        
        while (random.nextInt(5) != 0) {
            tasks.add(random());
        }
        
        return tasks;
    }

    //@@author A0135805H
    /**
     * Generates a list of random tasks with a random size between {@code lowerBound}
     * and {@code upperBound} inclusive.
     *
     * @return Returns a randomly generated list.
     */
    public static List<ImmutableTask> list(int lowerBound, int upperBound) {
        int size = lowerBound + random.nextInt(upperBound - lowerBound + 1);
        ArrayList<ImmutableTask> tasks = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            tasks.add(random());
        }
        return tasks;
    }

    /**
     * Helps to convert a varang of tag names to a set of tags.
     */
    public static Set<Tag> convertTagNamesToTags(String... tagNames) {
        Set<Tag> outcomeTags = new HashSet<>();
        for (String tagName : tagNames) {
            outcomeTags.add(new Tag(tagName));
        }
        return outcomeTags;
    }
}
