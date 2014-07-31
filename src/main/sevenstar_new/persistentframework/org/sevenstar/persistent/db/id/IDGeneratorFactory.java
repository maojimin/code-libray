package org.sevenstar.persistent.db.id;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.sevenstar.persistent.db.exception.PersistentException;

public class IDGeneratorFactory
{
  private static final HashMap GENERATORS = new HashMap();

  static { 
	GENERATORS.put("uuid.hex", UUIDHexGenerator.class);
    GENERATORS.put("uuid.string", UUIDStringGenerator.class);
    GENERATORS.put("seq", SequenceGenerator.class);
    GENERATORS.put("assign", AssignGenerator.class); }

  public static void main(String[] args)
  {
    System.out.println(generateUUidHex());
    System.out.println(generateUUidString());
  }

  public static Serializable generateUUidHex() {
    return generate("uuid.hex", null);
  }

  public static Serializable generateUUidString() {
    return generate("uuid.string", null);
  }

  public static Serializable generate(String strategy, Map map) {
    IDGenerator idgen = null;
    if ((strategy != null) && (!"".equals(strategy.trim()))) {
      Class clazz = (Class)GENERATORS.get(strategy);
      if (clazz == null)
        throw new PersistentException("hasn't define idgenerator strategy[" + strategy + "]");
      try
      {
        idgen = (IDGenerator)clazz.newInstance();
      } catch (InstantiationException e) {
        throw new PersistentException(e);
      } catch (IllegalAccessException e) {
        throw new PersistentException(e);
      }
    } else {
      idgen = new UUIDHexGenerator();
    }
    return idgen.generate(map);
  }
}