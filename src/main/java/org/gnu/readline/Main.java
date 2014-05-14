package org.gnu.readline;

public class Main {
  public static void main(String[] args) {
    Readline.using_history();
    System.out.println(Readline.history_is_stifled());
    System.out.println(Readline.unstifle_history());
    System.out.println(Readline.history_is_stifled());
    Readline.stifle_history(100);
    System.out.println(Readline.history_is_stifled());

    System.out.println();
    System.out.println("rl_completer_word_break_characters: '" + Readline.rl_completer_word_break_characters.getString(0) + "'");
    System.out.println("rl_attempted_completion_over: " + Readline.rl_attempted_completion_over);

    String line;
    while ((line = Readline.readline("> ")) != null) {
      System.out.println(line);
      System.out.println("rl_point: " + Readline.rl_point.getInt(0));
      System.out.println("rl_end: " + Readline.rl_end.getInt(0));
      System.out.println("rl_line_buffer: '" + Readline.rl_line_buffer.getString(0) + "'");
      System.out.println();
      Readline.add_history(line);
      System.out.println("history_base: " + Readline.history_base.getInt(0));
      final int hLength = Readline.history_length.getInt(0);
      System.out.println("history_length:" + hLength);
      System.out.println("last history entry: '" + Readline.history_get(hLength - 1) + "'"); // FIXME enconding?
    }
    Readline.clear_history();
  }
}
