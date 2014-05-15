package org.gnu.readline;

import com.sun.jna.Callback;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;

/**
 * @see AttemptedCompletionFunction#AttemptedCompletionFunction(org.gnu.readline.CompletionCallback)
 */
public abstract class CompletionCallback implements Callback {
  public String invoke(String text, int state) {
    return complete(text, state);
  }

  protected abstract String complete(String text, int state);
}
