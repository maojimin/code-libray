package org.sevenstar.persistent.db.cache;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibatis.sqlmap.engine.cache.CacheController;
import com.ibatis.sqlmap.engine.cache.CacheModel;
import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

/**
 * @author rtm 2008-5-8 修正了一个ibatis
 *         cachekey生成的错误，据此，ibatis提供的cache已经不可靠，所有需要cache的地方都用本CacheController
 *         在添加删除修改一条数据库记录的情况下，其他本表记录的缓存也会更新，ibatis删除了所有本缓存的记录，shit，暂时无法修正，但对于变化少的表，还是可以接受的
 */
public class OsCacheController implements CacheController {

	private static final GeneralCacheAdministrator CACHE = new GeneralCacheAdministrator();

	private static Log LOG = LogFactory.getLog(OsCacheController.class);

	public void flush(CacheModel cacheModel) {
		CACHE.flushGroup(cacheModel.getId());
	}

	public Object getObject(CacheModel cacheModel, Object key) {
		String keyString = modifyKey(key.toString());
		try {
			int refreshPeriod = (int) (cacheModel.getFlushIntervalSeconds());
			Object value = CACHE.getFromCache(keyString, refreshPeriod);
			LOG.debug("get from cache key[" + keyString + "] value[" + value + "]");
			return value;
		} catch (NeedsRefreshException e) {
			CACHE.cancelUpdate(keyString);
			LOG.debug("get from cache key[" + keyString + "] null");
			return null;
		}
	}

	public Object removeObject(CacheModel cacheModel, Object key) {
		Object result;
		String keyString = modifyKey(key.toString());
		try {
			int refreshPeriod = (int) (cacheModel.getFlushIntervalSeconds());
			Object value = CACHE.getFromCache(keyString, refreshPeriod);
			if (value != null) {
				CACHE.flushEntry(keyString);
			}
			result = value;
			LOG.debug("remove in cache key[" + keyString + "] ");
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

	public void putObject(CacheModel cacheModel, Object key, Object object) {
		String keyString = modifyKey(key.toString());
		CACHE
				.putInCache(keyString, object, new String[] { cacheModel
						.getId() });
		LOG.debug("put in cache key[" + keyString + "] value[" + object + "]");
	}

	/**
	 * ibatis的cachekey的生成有一个bug,在添加SessionScope时，不同的SessionScope相同的查询有不同的cachekey,这里添加修改，消除SessionScope的id以及前面两项
	 * 由id带来的变化 BTW:ibatis为每一个SessionScope生成了一个id
	 * eg:-936198158|-2002798768|S_moduleDomain_base_selectall|-781951596|
	 * select * from s_module where sts<>'N' order by id desc
	 * |com.ibatis.sqlmap.engine.scope.SessionScope@81|executeQueryForList|0|-999999
	 */
	private String modifyKey(String key) {

		if (key == null) {
			return null;
		}
		if (key.indexOf("SessionScope@") != -1) {
			String key1 = key.substring(0, key.indexOf("SessionScope@")
					+ "SessionScope@".length());
			/**
			 * 去除前两项id带来的变化
			 */
			key1 = key1.substring(key.indexOf("|") + 1);
			key1 = key1.substring(key1.indexOf("|") + 1);
			/**
			 * 继续
			 */
			String key2 = key.substring(key.indexOf("SessionScope@")
					+ "SessionScope@".length());
			key2 = key2.substring(key2.indexOf("|"));
			return key1 + key2;
		}
		return key;
	}

	public void configure(Properties props) {
	}

	public static void main(String[] args) {
		String key = "-936400770|-2002798769|S_moduleDomain_base_selectall|-781951596|  		    select * from s_module where  sts<>'N'  		  		  			 order by id desc 		   	  |com.ibatis.sqlmap.engine.scope.SessionScope@80|executeQueryForList|0|-999999";
		/**
		 * ibatis的cachekey的生成有一个bug,在添加SessionScope时，不同的SessionScope相同的查询有不同的cachekey,这里添加修改，消除SessionScope的id
		 * eg:-936198158|-2002798768|S_moduleDomain_base_selectall|-781951596|
		 * select * from s_module where sts<>'N' order by id desc
		 * |com.ibatis.sqlmap.engine.scope.SessionScope@81|executeQueryForList|0|-999999
		 */
		if (key.indexOf("SessionScope@") != -1) {
			String key1 = key.substring(0, key.indexOf("SessionScope@")
					+ "SessionScope@".length());
			String key2 = key.substring(key.indexOf("SessionScope@")
					+ "SessionScope@".length());
			key2 = key2.substring(key2.indexOf("|"));
			System.out.println(key1 + key2);
		}
		System.out.println(key);
	}

}
