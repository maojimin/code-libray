package org.sevenstar.persistent.db.find.annotation.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SSProperty{
 /* public abstract String parameter();
  public abstract String jdbctype();
  public abstract String select_type();
  public abstract String select_property();
  public abstract String select_min_property();
  public abstract String select_max_property();
  public abstract String select_sql();
  public abstract String select_prepend();
  public abstract boolean update();
  public abstract boolean updateNull();
  public abstract boolean insert();
  public abstract String value();
  public abstract String defaultValue();
  public abstract String column();
  public abstract boolean disuse();*/
  
    String parameter()  default "";
	String jdbctype()  default "";
	String select_type()  default "";
	String select_property()  default "";
	String select_min_property()  default "";
	String select_max_property()  default "" ;
	String select_sql() default "";
	String select_prepend() default "and";
	boolean update() default true;
	boolean updateNull() default false;
	boolean insert() default true;
	String value() default "";
	String defaultValue() default "";
	String column() default "";
	boolean disuse() default false;
	
}