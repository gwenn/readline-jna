package org.gnu.readline;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

/**
 * @see Readline#setAttemptedCompletionFunction(org.gnu.readline.AttemptedCompletionFunction)
 */
public class AttemptedCompletionFunction implements Callback {
  private final CompletionCallback callback;

  public AttemptedCompletionFunction(CompletionCallback callback) {
    this.callback = callback;
  }

  public Pointer invoke(String text, int start, int end) {
    return Readline.rl_completion_matches(text, callback);
  }
}
