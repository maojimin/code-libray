/*
 * yutian.com Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 2014年4月17日 下午2:51:41
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Param {
	String value() default "";
	boolean required() default false;
}
