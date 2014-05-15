package org.gnu.readline;

import com.sun.jna.*;
import com.sun.jna.ptr.PointerByReference;

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
  public static String getLineBuffer() {
    final Pointer pointer = Readline.rl_line_buffer.getPointer(0);
    return pointer == null ? null : pointer.getString(0);
  }
  private static Pointer rl_point = JNA_NATIVE_LIB.getGlobalVariableAddress("rl_point");
  public static int getPoint() {
    return rl_point.getInt(0);
  }
  private static Pointer rl_end = JNA_NATIVE_LIB.getGlobalVariableAddress("rl_end");
  public static int getEnd() {
    return rl_end.getInt(0);
  }
  // static Pointer rl_catch_sigwinch; // only for readline (not editline)

  /**
   * @param prompt
   * @return null at EOF
   */
  public static native String readline(String prompt);
  // static native void rl_resize_terminal(); // only for readline (not editline)

  private static Pointer history_length = JNA_NATIVE_LIB.getGlobalVariableAddress("history_length");
  public static int getHistoryLength() {
    return history_length.getInt(0);
  }
  private static Pointer history_base = JNA_NATIVE_LIB.getGlobalVariableAddress("history_base");
  public static int getHistoryBase() {
    return history_base.getInt(0);
  }
  public static native void using_history();
  private static native void add_history(String line);
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
  public static native int read_history(String filename);
  public static native int write_history(String filename);
  //static native int append_history(int nelements, String filename); // only in readline (not editline)
  public static native int history_truncate_file(String filename, int nLines);
  public static native void clear_history();
  public static native void stifle_history(int max);
  public static native int unstifle_history();
  public static native boolean history_is_stifled();
  private static native HistoryEntry history_get(int index);
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
  public static String getCurrentHistory() {
    final HistoryEntry entry = current_history();
    return entry == null ? null : entry.line;
  }

  private static Pointer rl_attempted_completion_function = JNA_NATIVE_LIB.getGlobalVariableAddress("rl_attempted_completion_function");
  public static void setAttemptedCompletionFunction(AttemptedCompletionFunction acf) {
    rl_attempted_completion_function.setPointer(0, CallbackReferenceHack.getFunctionPointer(acf));
  }
  private static Pointer rl_attempted_completion_over = JNA_NATIVE_LIB.getGlobalVariableAddress("rl_attempted_completion_over");
  public static void setAttemptedCompletionOver(boolean b) {
    rl_attempted_completion_over.setInt(0, b ? 1 : 0);
  }
  public static native Pointer rl_completion_matches(String text, CompletionCallback entryFunc);
  static Pointer rl_completer_word_break_characters = JNA_NATIVE_LIB.getGlobalVariableAddress("rl_completer_word_break_characters"); // String
}
