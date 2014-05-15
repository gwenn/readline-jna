package org.gnu.readline;

import com.sun.jna.Structure;

import java.util.Collections;
import java.util.List;

public class HistoryEntry extends Structure {
  public String line;
  @Override
  protected List getFieldOrder() {
    return Collections.singletonList("line");
  }
}
