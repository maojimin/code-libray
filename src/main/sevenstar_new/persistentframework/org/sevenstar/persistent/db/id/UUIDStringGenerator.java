package org.sevenstar.persistent.db.id;

import java.io.Serializable;
import java.util.Map;

import org.sevenstar.util.BytesHelper;

public class UUIDStringGenerator extends UUIDGenerator
  implements IDGenerator
{
  private String sep = "";

  public Serializable generate(Map map) { return 20 + 
      toString(getIP()) + this.sep + 
      toString(getJVM()) + this.sep + 
      toString(getHiTime()) + this.sep + 
      toString(getLoTime()) + this.sep + 
      toString(getCount()); }

  public static void main(String[] args)
    throws Exception
  {
    UUIDStringGenerator gen = new UUIDStringGenerator();
    for (int i = 0; i < 5; i++) {
      String id = (String)gen.generate(null);
      System.out.println(id + ": " + id.length());
    }
  }

  private static String toString(int value) {
    return new String(BytesHelper.toBytes(value));
  }

  private static String toString(short value) {
    return new String(BytesHelper.toBytes(value));
  }
}