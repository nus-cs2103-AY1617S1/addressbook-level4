package seedu.menion.commons.core;

import java.util.ArrayList;
import java.util.Random;

//@@author A0139164A
/**
 * Update here the number of quotes, Currently there is : 20
 * A class to return a random quote from a list.
 */
public class MotivationalQuotes {

    public static String getRandomQuote() {
        ArrayList<String> quotes = new ArrayList<String>();
        Random ran = new Random();
        int random = ran.nextInt(20);
        
        quotes.add("Don’t let what you cannot do interfere with what you can do. – John Wooden");
        quotes.add("It always seems impossible until it’s done.");
        quotes.add("You have to expect things of yourself before you can do them.");
        quotes.add("Believe you can and you’re halfway there.");
        quotes.add("Start where you are. Use what you have. Do what you can. – Arthur Ashe");
        quotes.add("Successful and unsuccessful people do not vary greatly in their abilities. They vary in their desires to reach their potential. – John Maxwell");
        quotes.add("Strive for progress, not perfection.");
        quotes.add("Don’t wish it were easier; wish you were better. – Jim Rohn");
        quotes.add("The secret to getting ahead is getting started.");
        quotes.add("You don’t have to be great to start, but you have to start to be great.");
        quotes.add("Push yourself, because no one else is going to do it for you.");
        quotes.add("There is no substitute for hard work. – Thomas Edison");
        quotes.add("The difference between ordinary and extraordinary is that little 'extra'");
        quotes.add("If people only knew how hard I’ve worked to gain my mastery, it wouldn’t seem so wonderful at all. – Michelangelo");
        quotes.add("If it is important to you, you will find a way. If not, you will find an excuse.");
        quotes.add("Don’t say you don’t have enough time. You have exactly the same number of hours per day that were given to Helen Keller, Pasteur, Michelangelo, Mother Teresea, Leonardo da Vinci, Thomas Jefferson, and Albert Einstein. – H. Jackson Brown Jr.");
        quotes.add("If you’re going through hell, keep going. – Winston Churchill");
        quotes.add("There are no traffic jams on the extra mile. – Zig Ziglar");
        quotes.add("The only place where success comes before work is in the dictionary. – Vidal Sassoon");
        quotes.add("It’s not about how bad you want it. It’s about how hard you’re willing to work for it.");
                
        return quotes.get(random);
    }
}
