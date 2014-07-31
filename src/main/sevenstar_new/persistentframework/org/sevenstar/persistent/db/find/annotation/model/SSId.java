package org.sevenstar.persistent.db.find.annotation.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SSId
{
  /*public abstract String generateType();
  public abstract String seq();
  public abstract String primaryKeyParameterType();*/
	String generateType() default "seq";
	String seq() default "";
	String primaryKeyParameterType() default "";
	
}
