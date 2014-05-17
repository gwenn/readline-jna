package org.gnu.readline;

import com.sun.jna.Structure;

import java.util.Collections;
import java.util.List;

/**
 * <a href="http://cnswww.cns.cwru.edu/php/chet/readline/history.html#SEC8"></a>
 */
public class HistoryEntry extends Structure {
  public String line;
  @Override
  protected List getFieldOrder() {
    return Collections.singletonList("line");
  }
}
