package org.gnu.readline;

import com.sun.jna.Callback;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public class Readline {
  // FIXME edit versus readline
  public static final String JNA_LIBRARY_NAME = "edit";

  private static final NativeLibrary JNA_NATIVE_LIB;
  static {
    Native.register(JNA_LIBRARY_NAME);
    JNA_NATIVE_LIB = NativeLibrary.getInstance(Readline.JNA_LIBRARY_NAME);
  }

  static Pointer rl_line_buffer = JNA_NATIVE_LIB.getGlobalVariableAddress("rl_line_buffer"); // string or char[]
  static Pointer rl_point = JNA_NATIVE_LIB.getGlobalVariableAddress("rl_point"); // int
  static Pointer rl_end = JNA_NATIVE_LIB.getGlobalVariableAddress("rl_end"); // int
  // static Pointer rl_catch_sigwinch; // only for readline (not editline)

  /**
   * @param prompt
   * @return null at EOF
   */
  static native String readline(String prompt);
  // static native void rl_resize_terminal(); // only for readline (not editline)

  static Pointer history_length = JNA_NATIVE_LIB.getGlobalVariableAddress("history_length"); // int
  static Pointer history_base = JNA_NATIVE_LIB.getGlobalVariableAddress("history_base"); // int
  static native void using_history();
  static native void add_history(String line);
  static native int read_history(String filename);
  static native int write_history(String filename);
  //static native int append_history(int nelements, String filename); // only in readline (not editline)
  static native int history_truncate_file(String filename, int nLines);
  static native void clear_history();
  static native void stifle_history(int max);
  static native int unstifle_history();
  static native boolean history_is_stifled();
  static native String history_get(int index);

  // static native rl_attempted_completion_function; // char **()(const char *text, int start, int end)
  static Pointer rl_attempted_completion_over = JNA_NATIVE_LIB.getGlobalVariableAddress("rl_attempted_completion_over"); // boolean
  static native PointerByReference rl_completion_matches(String text, Callback entryFunc); // char*(entryFunc)(const char *, int)
  static Pointer rl_completer_word_break_characters = JNA_NATIVE_LIB.getGlobalVariableAddress("rl_completer_word_break_characters"); // String
}
