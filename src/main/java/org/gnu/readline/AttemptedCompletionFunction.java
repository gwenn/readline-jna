package org.gnu.readline;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

/**
 * <a href="http://cnswww.cns.cwru.edu/php/chet/readline/readline.html#SEC48">rl_attempted_completion_over</a>
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
