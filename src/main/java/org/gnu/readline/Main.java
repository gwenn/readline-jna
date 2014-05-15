package org.gnu.readline;

import com.sun.jna.Callback;
import com.sun.jna.CallbackReferenceHack;

public class Main {
  public static void main(String[] args) {
    Readline.using_history();
    Readline.clear_history();
    System.out.println("unstifle_history: " + Readline.unstifle_history());
    Readline.stifle_history(100);
    System.out.println("history_is_stifled: " + Readline.history_is_stifled());
    System.out.println("rl_completer_word_break_characters: '" + Readline.rl_completer_word_break_characters.getString(0) + "'");

    final AttemptedCompletionFunction completion = new AttemptedCompletionFunction(new CompletionCallback() {
      @Override
      public String complete(String text, int state) {
        if (state == 0) {
          /*System.out.println("rl_line_buffer: '" + Readline.getLineBuffer() + "'");
          System.out.println("rl_point: " + Readline.getPoint());
          System.out.println("rl_end: " + Readline.getEnd());*/
          return text + "!!!";
        } else if (state == 1) {
          return text + "s";
        }
        Readline.setAttemptedCompletionOver(true);
        return null;
      }
    });

    Readline.setAttemptedCompletionFunction(completion);

    System.out.println("read_history: " + Readline.read_history("readline-jna-history"));
    String line;
    while ((line = Readline.readline("> ")) != null) {
      System.out.println(line);
      Readline.addHistory(line);
      System.out.println("history_base: " + Readline.getHistoryBase());
      final int hLength = Readline.getHistoryLength();
      System.out.println("history_length:" + hLength);
      System.out.println("last history entry: '" + Readline.GetHistory(-1) + "'");
      System.out.println("current_history: '" + Readline.getCurrentHistory() + "'");
    }
    System.out.println("write_history: " + Readline.write_history("readline-jna-history"));
    System.out.println("history_truncate_file: " + Readline.history_truncate_file("readline-jna-history", 10));
    System.out.println();
  }
}
