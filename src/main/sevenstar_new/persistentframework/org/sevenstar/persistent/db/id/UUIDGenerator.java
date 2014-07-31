package org.sevenstar.persistent.db.id;

import java.net.InetAddress;
import org.sevenstar.util.BytesHelper;

public class UUIDGenerator
{
  private static final int IP;
  private static short counter;
  private static final int JVM;

  static {
    int ipadd;
    try {
      ipadd = BytesHelper.toInt(InetAddress.getLocalHost().getAddress());
    }catch (Exception e){
      ipadd = 0;
    }
    IP = ipadd;
    counter = 0;
    JVM = (int)(System.currentTimeMillis() >>> 8);
  }

  protected int getJVM()
  {
    return JVM;
  }

  protected short getCount()
  {
    synchronized (UUIDGenerator.class) {
      if (counter < 0) counter = 0;
      return counter++;
    }
  }

  protected int getIP()
  {
    return IP;
  }

  protected short getHiTime()
  {
    return (short)(int)(System.currentTimeMillis() >>> 32);
  }
  protected int getLoTime() {
    return (int)System.currentTimeMillis();
  }
}