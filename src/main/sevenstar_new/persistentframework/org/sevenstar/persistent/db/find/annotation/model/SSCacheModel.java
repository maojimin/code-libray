package org.sevenstar.persistent.db.find.annotation.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SSCacheModel{
  /*public abstract String type();
  public abstract String readonly();
  public abstract String serialize();
  public abstract int cachesize();
  public abstract int flushinterval();
  public abstract String flushonexecute();
  public abstract boolean selectAll();*/
  
  String type() default "org.sevenstar.persistent.db.cache.EhCacheController";
  String readonly() default "false";
  String serialize() default "false";
  int cachesize() default 1000;
  int flushinterval() default 10;
  String flushonexecute() default "";
  boolean selectAll() default false;
  
}
