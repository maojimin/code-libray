package org.sevenstar.util;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;


public class CacheHelper {
	private static final GeneralCacheAdministrator CACHE = new GeneralCacheAdministrator();

	private static Log LOG = LogFactory.getLog(CacheHelper.class);

	public static void main(String[] args) throws InterruptedException{
	/*	S_moduleDomain moduleDomain = new S_moduleDomain();
		List list = moduleDomain.selectAll();
		List list2 = moduleDomain.selectAll();
		moduleDomain.setId(new Long(190));
		moduleDomain.load();
		moduleDomain.load();
      */
		
		/*S_deptDomain deptDomain = new S_deptDomain();
		System.out.println("放入缓存");
		List deptlist = deptDomain.selectAll();
		deptDomain.setName("tttt");
		deptDomain.insert();
		deptDomain.load();
		System.out.println("deptlist2应该重新查询");
		List deptlist2 = deptDomain.selectAll();
		System.out.println("deptDomain应该可以从缓存得到");
		deptDomain.load();
		S_deptDomain deptDomain2 = new S_deptDomain();
		deptDomain2.setName("tttt2");
		deptDomain2.insert();
		System.out.println("deptDomain应该可以从缓存得到,shit，重新加载了");
		deptDomain.load();
		deptDomain2.load();
		System.out.println("deptDomain2应该可以从缓存得到");
		deptDomain2.load();
		deptDomain.delete();
		deptDomain2.delete();*/
		 CacheHelper.putObject("-1065792035|51715037","ddd");
		 CacheHelper.putObject("-1065792035|ee","ddd");
		 Thread.sleep(2000);
		 System.out.println(CacheHelper.getObject("-1065792035|51715037",1));
		System.out.println( CACHE.getCache().getSize());
		//System.out.println("result="+CacheHelper.getObject("-1065792035|51715037", 600));
	//	List list3 = domain.selectAll();
		//Thread.sleep(1025);
		//System.out.println("result="+CacheHelper.getObject("-1065792035|51715037", 600));
	}

	/**
	 * refreshPeriod 单位为秒
	 * @param key
	 * @param refreshPeriod
	 * @return
	 */
	public static Object getObject(Object key, int refreshPeriod) {
		String keyString = key.toString();
 		try {
			Object value = CACHE.getFromCache(keyString, refreshPeriod);
			LOG.debug("get from cache key["+key+"] value["+value+"]");
			return value;
		} catch (NeedsRefreshException e) {
			CACHE.cancelUpdate(keyString);
			return null;
		}
	}

	public static Object removeObject(Object key, int refreshPeriod) {
		Object result;
		String keyString = key.toString();
		try {
			Object value = CACHE.getFromCache(keyString, refreshPeriod);
			if (value != null) {
				CACHE.flushEntry(keyString);
			}
			result = value;
		} catch (NeedsRefreshException e) {
			try {
				CACHE.flushEntry(keyString);
			} finally {
				CACHE.cancelUpdate(keyString);
				result = null;
			}
		}
		return result;
	}

	public static void putObject(Object key, Object object) {
		String keyString = key.toString();
		CACHE.putInCache(keyString, object);
		LOG.debug("put in cache key["+key+"] value["+object+"]");
	}
}
