package seedu.emeraldo.logic.commands;

import java.util.Random;

//@@author A0139196U
/**
 * Motivates the user with motivational quotes upon request in the task manager.
 */
public class MotivateMeCommand extends Command {
    
    public static final String [] MESSAGE_MOTIVATE_LIST = {
            "Don't wish it was easier, wish you were better.", 
            "If not now, when?",
            "It does not matter how slowly you go as long as you do not stop.",
            "Time will pass, will you?",
            "When you feel like quitting, think about why you started.",
            "Every morning you have two choices: \ncontinue to sleep with your dreams, or wake up to chase them.",
            "Pain is temporary, quitting lasts forever.",
            "Do something instead of killing time. Because time is killing you.",
            "The only person you should try to be better than is the person you were yesterday.",
            "Do one thing everyday that scares you.",
            "If plan A doesn't work, you still have 25 letters.\nIf plan Z doesn't work, you still have numbers.",
            "The difference between ordinary and extraordinary is that little extra. Work it and don't give up!!",
            "Stop counting the days. Start making the days count.",
            "The secret to getting ahead is getting started. Start on one of your tasks today!"
    };
    
    public static final String [] TEXT_EMOJI_LIST = {
            "  ＼（○＾ω＾○）／",
            "  ﾍ(￣▽￣*)ﾉ",
            "  ٩(^ᴗ^)۶",
            "  ﾟ╲(｡◕‿◕｡)╱ﾟ",
            "  (ง °◡° )ง",
            "  (▰˘◡˘▰)",
            "  ( ง '̀-'́) ง",
            "  (๑˃̵ᴗ˂̵)و",
            "  (๑•̀ㅂ•́)و✧",
            "  ೕ(•̀ᴗ•́)",
    };
    
    public static final String COMMAND_WORD = "motivateme";

    public MotivateMeCommand() {}
    
    public CommandResult execute() {
        
        Random randMessage = new Random();
        Random randTextEmoji = new Random();
        int msgShuffler = randMessage.nextInt(MESSAGE_MOTIVATE_LIST.length);
        int emojiShuffler = randTextEmoji.nextInt(TEXT_EMOJI_LIST.length);
        
        return new CommandResult(String.format(MESSAGE_MOTIVATE_LIST[msgShuffler] + TEXT_EMOJI_LIST[emojiShuffler]));
    }
    
}
