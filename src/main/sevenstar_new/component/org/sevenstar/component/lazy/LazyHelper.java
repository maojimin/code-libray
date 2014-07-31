package org.sevenstar.component.lazy;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sevenstar.util.BeanHelper;

import net.sf.cglib.proxy.Enhancer;

public class LazyHelper {

	private static Log LOG = LogFactory.getLog(LazyHelper.class);

	public static void copy(Object source, Object to) {
		if (source == null || to == null) {
			return;
		}
		if (!source.getClass().equals(to.getClass())) {
			if (!source.getClass().getName()
					.startsWith(to.getClass().getName())
					&& !to.getClass().getName().startsWith(
							source.getClass().getName())) {
				throw new RuntimeException("copy的两个对象类型不一致,source("
						+ source.getClass() + "),to(" + to.getClass() + ")");
			}
		}
		Field[] fields = BeanHelper.getFields(source.getClass());
		long start = System.currentTimeMillis();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if (!field.isAccessible()) {
				field.setAccessible(true);
			}
			try {
				if (field.getModifiers() != 2) {
					/**
					 * 非private
					 */
					Object value = field.get(source);
					field.set(to, value);
					continue;
				} else {
					if (!BeanHelper.isPrimitiveType(field.getType())) {
						if (field.getType().equals(List.class)) {
							/**
							 * 返回lazyList
							 */
							LazySupportList lazyList = new LazySupportList(
									source, field.getName());
							field.set(to, lazyList);
							continue;
						} else if (field.getType().equals(Map.class)) {
							Object value = BeanHelper.getPropertyValue(field
									.getName(), source);
							if (value != null) {
								field.set(to, value);
							}
							continue;
						} else {
							/**
							 * 生成增强的Bean
							 */
							Object newObject = createLazyObject(
									field.getType(), source, field.getName());
							field.set(to, newObject);
							continue;
						}
					} else {
						
						Object value = field.get(source);
						if (value != null) {
							
							field.set(to, value);
							continue;
						}
					}
				}
			} catch (IllegalAccessException e) {
				// pass
			}

		}
		LOG.debug("lazy copy costtime["+(System.currentTimeMillis()-start)+"] class["+source.getClass().getName()+"]");
	}

	public static Object createLazyObject(Class klass, Object source,
			String prefix) {
		if (source == null || BeanHelper.isPrimitiveType(source.getClass())) {
			return source;
		}
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(klass);
		enhancer
				.setCallback(new ObjectDelegateMethodInterceptor(source, prefix));
		Object proxyObject = enhancer.create();
		return proxyObject;
	}

 
}
