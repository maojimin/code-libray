package org.sevenstar.persistent.db.find.annotation.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SSDomain{
  /*public abstract String table();
  public abstract String insertSql();
  public abstract String updateSql();
  public abstract String deleteSql();
  public abstract String loadSql();
  public abstract String condition();
  public abstract String order();
  public abstract String select_type_string();*/
	
	   String table();
	   String insertSql() default "";
	   String updateSql() default "";
	   String deleteSql() default "";
	   String loadSql() default "";
	   String condition() default "";
	   String order() default "";
	   String select_type_string() default "";
	   
}
