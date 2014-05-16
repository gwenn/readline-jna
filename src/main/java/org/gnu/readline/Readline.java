package org.gnu.readline;

import com.sun.jna.CallbackReferenceHack;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;

/**
 * JNA Binding to Editline/Readline library.
 */
public class Readline {
  // FIXME edit versus readline
  public static final String JNA_LIBRARY_NAME = "edit";

  private static final NativeLibrary JNA_NATIVE_LIB;
  static {
    Native.register(JNA_LIBRARY_NAME);
    JNA_NATIVE_LIB = NativeLibrary.getInstance(Readline.JNA_LIBRARY_NAME);
  }

  //public static native int rl_initialize();

  private static Pointer rl_line_buffer = JNA_NATIVE_LIB.getGlobalVariableAddress("rl_line_buffer");
  /**
   * This is the line gathered so far.
   * <a href="http://cnswww.cns.cwru.edu/php/chet/readline/readline.html#SEC28">rl_line_buffer</a>
   */
  public static String getLineBuffer() {
    final Pointer pointer = Readline.rl_line_buffer.getPointer(0);
    return pointer == null ? null : pointer.getString(0);
  }
  private static Pointer rl_point = JNA_NATIVE_LIB.getGlobalVariableAddress("rl_point");
  /**
   * The offset of the current cursor position in rl_line_buffer (the point).
   * <a href="http://cnswww.cns.cwru.edu/php/chet/readline/readline.html#SEC28>rl_point</a>
   */
  public static int getPoint() {
    return rl_point.getInt(0);
  }
  private static Pointer rl_end = JNA_NATIVE_LIB.getGlobalVariableAddress("rl_end");
  /**
   * The number of characters present in rl_line_buffer. When rl_point is at the end of the line, rl_point and rl_end are equal.
   * <a href="http://cnswww.cns.cwru.edu/php/chet/readline/readline.html#SEC28">rl_end</a>
   */
  public static int getEnd() {
    return rl_end.getInt(0);
  }
  // static Pointer rl_catch_sigwinch; // only for readline (not editline)

  /**
   * The function readline() prints a prompt and then reads and returns a single line of text from the user.
   * The line readline returns is allocated with malloc(); the caller should free() the line when it has finished with it.
   * <a href="http://cnswww.cns.cwru.edu/php/chet/readline/readline.html#SEC24">readline</a>
   * @param prompt If prompt is NULL or the empty string, no prompt is displayed.
   * @return If readline encounters an EOF while reading the line, and the line is empty at that point, then NULL is returned.
   * Otherwise, the line is ended just as if a newline had been typed.
   */
  public static native String readline(String prompt);
  // static native void rl_resize_terminal(); // only for readline (not editline)

  private static Pointer history_length = JNA_NATIVE_LIB.getGlobalVariableAddress("history_length");
  /**
   * The number of entries currently stored in the history list.
   * <a href="http://cnswww.cns.cwru.edu/php/chet/readline/history.html#SEC17">history_length</a>
   */
  public static int getHistoryLength() {
    return history_length.getInt(0);
  }
  private static Pointer history_base = JNA_NATIVE_LIB.getGlobalVariableAddress("history_base");
  /**
   * The logical offset of the first entry in the history list.
   * <a href="http://cnswww.cns.cwru.edu/php/chet/readline/history.html#SEC17">history_base</a>
   */
  public static int getHistoryBase() {
    return history_base.getInt(0);
  }

  /**
   * Begin a session in which the history functions might be used. This initializes the interactive variables.
   * <a href="http://cnswww.cns.cwru.edu/php/chet/readline/history.html#SEC10">using_history</a>
   */
  public static native void using_history();
  private static native void add_history(String line);
  /**
   * Place string at the end of the history list. The associated data field (if any) is set to NULL.
   * <a href="http://cnswww.cns.cwru.edu/php/chet/readline/history.html#SEC11">add_history</a>
   */
  public static void addHistory(String line) {
    if (line == null || line.length() == 0 || line.trim().length() == 0) {
      return;
    }
    if (Character.isSpaceChar(line.charAt(0))) { // ignorespace
      return;
    }
    if (line.equals(getCurrentHistory())) { // ignore consecutive dups // TODO Validate getCurrentHistory versus GetHistory(-1)
      return;
    }
    add_history(line);
  }

  /**
   * Add the contents of filename to the history list, a line at a time.
   * If filename is NULL, then read from `~/.history'.
   * Returns 0 if successful, or errno if not.
   * <a href="http://cnswww.cns.cwru.edu/php/chet/readline/history.html#SEC15">read_history</a>
   */
  public static native int read_history(String filename);
  /**
   * Write the current history to filename, overwriting filename if necessary.
   * If filename is NULL, then write the history list to `~/.history'.
   * Returns 0 on success, or errno on a read or write error.
   * <a href="http://cnswww.cns.cwru.edu/php/chet/readline/history.html#SEC15">write_history</a>
   */
  public static native int write_history(String filename);
  //static native int append_history(int nelements, String filename); // only in readline (not editline)

  /**
   * Truncate the history file filename, leaving only the last nlines lines.
   * If filename is NULL, then `~/.history' is truncated.
   * Returns 0 on success, or errno on failure.
   * <a href="http://cnswww.cns.cwru.edu/php/chet/readline/history.html#SEC15">history_truncate_file</a>
   */
  public static native int history_truncate_file(String filename, int nLines);

  /**
   * Clear the history list by deleting all the entries.
   * <a href="http://cnswww.cns.cwru.edu/php/chet/readline/history.html#SEC11">clear_history</a>
   */
  public static native void clear_history();

  /**
   * Stifle the history list, remembering only the last max entries.
   * <a href="http://cnswww.cns.cwru.edu/php/chet/readline/history.html#SEC11">stifle_history</a>
   */
  public static native void stifle_history(int max);
  /**
   * Stop stifling the history.
   * This returns the previously-set maximum number of history entries (as set by stifle_history()).
   * The value is positive if the history was stifled, negative if it wasn't.
   * <a href="http://cnswww.cns.cwru.edu/php/chet/readline/history.html#SEC11">unstifle_history</a>
   */
  public static native int unstifle_history();
  /**
   * Returns non-zero if the history is stifled, zero if it is not.
   * <a href="http://cnswww.cns.cwru.edu/php/chet/readline/history.html#SEC11">history_is_stifled</a>
   */
  public static native boolean history_is_stifled();

  private static native HistoryEntry history_get(int index);
  /**
   * Return the history entry at position offset, starting from history_base.
   * If there is no entry there, or if offset is greater than the history length, return a NULL pointer.
   * <a href="http://cnswww.cns.cwru.edu/php/chet/readline/history.html#SEC12">history_get</a>
   */
  public static String GetHistory(int index) {
    final int length = getHistoryLength();
    if (index < 0) {
      index += length;
    }
    if (index < 0 || index >= length) {
      return null;
    }
    index += getHistoryBase(); // TODO
    final HistoryEntry entry = history_get(index);
    if (entry == null) {
      return null;
    }
    return entry.line;
  }

  private static native HistoryEntry current_history();

  /**
   * Return the history entry at the current position, as determined by where_history(). If there is no entry there, return a NULL pointer.
   * <a href="http://cnswww.cns.cwru.edu/php/chet/readline/history.html#SEC12">current_history</a>
   */
  public static String getCurrentHistory() {
    final HistoryEntry entry = current_history();
    return entry == null ? null : entry.line;
  }

  private static Pointer rl_attempted_completion_function = JNA_NATIVE_LIB.getGlobalVariableAddress("rl_attempted_completion_function");
  /**
   * A pointer to an alternative function to create matches. The function is called with text, start, and end.
   * start and end are indices in rl_line_buffer defining the boundaries of text, which is a character string.
   * If this function exists and returns NULL, or if this variable is set to NULL,
   * then rl_complete() will call the value of rl_completion_entry_function to generate matches,
   * otherwise the array of strings returned will be used.
   * If this function sets the rl_attempted_completion_over variable to a non-zero value,
   * Readline will not perform its default completion even if this function returns no matches.
   * <a href="http://cnswww.cns.cwru.edu/php/chet/readline/readline.html#SEC48">rl_attempted_completion_function</a>
   */
  public static void setAttemptedCompletionFunction(AttemptedCompletionFunction acf) {
    rl_attempted_completion_function.setPointer(0, CallbackReferenceHack.getFunctionPointer(acf));
  }
  private static Pointer rl_attempted_completion_over = JNA_NATIVE_LIB.getGlobalVariableAddress("rl_attempted_completion_over");
  /**
   * If an application-specific completion function assigned to rl_attempted_completion_function sets this variable to a non-zero value,
   * Readline will not perform its default filename completion even if the application's completion function returns no matches.
   * It should be set only by an application's completion function.
   * <a href="http://cnswww.cns.cwru.edu/php/chet/readline/readline.html#SEC48">rl_attempted_completion_over</a>
   */
  public static void setAttemptedCompletionOver(boolean b) {
    rl_attempted_completion_over.setInt(0, b ? 1 : 0);
  }
  /**
   * Returns an array of strings which is a list of completions for text. If there are no completions, returns NULL.
   * The first entry in the returned array is the substitution for text. The remaining entries are the possible completions.
   * The array is terminated with a NULL pointer.
   * entry_func is a function of two args, and returns a char *. The first argument is text.
   * The second is a state argument; it is zero on the first call, and non-zero on subsequent calls.
   * entry_func returns a NULL pointer to the caller when there are no more matches.
   * <a href="http://cnswww.cns.cwru.edu/php/chet/readline/readline.html#SEC47">rl_completion_matches</a>
   */
  public static native Pointer rl_completion_matches(String text, CompletionCallback entryFunc);
  static Pointer rl_completer_word_break_characters = JNA_NATIVE_LIB.getGlobalVariableAddress("rl_completer_word_break_characters"); // String
  /**
   * The list of characters that signal a break between words.
   * <a href="http://cnswww.cns.cwru.edu/php/chet/readline/readline.html#SEC48">rl_completer_word_break_characters</a>
   */
  public void setCompleterWordBreakCharacters(String breaks) {
    rl_completer_word_break_characters.setString(0, breaks);
  }
}
