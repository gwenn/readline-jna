package com.sun.jna;

public class CallbackReferenceHack {
  public static Pointer getFunctionPointer(Callback cb) {
    return CallbackReference.getFunctionPointer(cb);
  }
}
