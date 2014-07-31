package org.sevenstar.persistent.db.find.annotation.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SSOneToOne
{
  /*public abstract String column();
  public abstract String table();
  public abstract String condition();
  public abstract String select();
  public abstract String ownColumn();*/
	String column() default "";
	String table()  default "";
	String condition()  default "";
	String select()  default "";
	String ownColumn() default "";
}
