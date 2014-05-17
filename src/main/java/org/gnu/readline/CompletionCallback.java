package org.gnu.readline;

import com.sun.jna.Callback;

/**
 * <a href="http://cnswww.cns.cwru.edu/php/chet/readline/readline.html#SEC26">rl_compentry_func_t</a>
 * @see AttemptedCompletionFunction#AttemptedCompletionFunction(org.gnu.readline.CompletionCallback)
 */
public abstract class CompletionCallback implements Callback {
  public String invoke(String text, int state) {
    // TODO Validate:
    // https://groups.google.com/forum/#!msg/jna-users/Xy7Sm8Fb-Gc/CJ4DYo07NXEJ
    // "Java Strings passed to C are converted to a native C string, which memory is valid for the duration of the native call only."
    // http://cnswww.cns.cwru.edu/php/chet/readline/readline.html#SEC46
    // "Each string the generator function returns as a match must be allocated with malloc(); Readline frees the strings when it has finished with them.
    return complete(text, state);
  }

  protected abstract String complete(String text, int state);
}
