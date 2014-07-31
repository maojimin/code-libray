package org.sevenstar.persistent.db.find.annotation.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SSManyToOne
{
  /*public abstract String parameter();
  public abstract String jdbctype();
  public abstract String javatype();
  public abstract String column();
  public abstract String table();
  public abstract String condition();
  public abstract String select();
  public abstract boolean update();
  public abstract boolean insert();
  public abstract boolean updateNull();*/
	
	String parameter()  default "";
	String jdbctype()  default "";
	String javatype()  default "java.lang.Long";
	String column() default "";
	String table()  default "";
	String condition()  default "";
	String select()  default "";
	boolean update() default true;
	boolean insert() default true;
	boolean updateNull() default false;
	
}


/* Location:           D:\Program Files\JD-GUI\test\SevenStarPersistent15.jar
 * Qualified Name:     org.sevenstar.persistent.db.find.annotation.model.SSManyToOne
 * JD-Core Version:    0.6.0
 */