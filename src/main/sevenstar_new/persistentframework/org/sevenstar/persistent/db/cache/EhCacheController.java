package org.sevenstar.persistent.db.cache;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibatis.sqlmap.engine.cache.CacheController;
import com.ibatis.sqlmap.engine.cache.CacheModel;
import org.sevenstar.component.cache.ehcache.EHCacheHelper;
 public class EhCacheController implements CacheController{
	
	private static Log LOG = LogFactory.getLog(EhCacheController.class);

	public void configure(Properties properties) {
 		
	}

	public void flush(CacheModel cacheModel) {
	 	EHCacheHelper.getCache(cacheModel.getId()).removeAll();
	}

	public Object getObject(CacheModel cacheModel, Object key) {
		String keyString = modifyKey(key.toString());
		if(!EHCacheHelper.exists(cacheModel.getId())){
			EHCacheHelper.addCache(cacheModel.getId());
			EHCacheHelper.getCache(cacheModel.getId()).getCacheConfiguration().setTimeToIdleSeconds(cacheModel.getFlushIntervalSeconds());
			EHCacheHelper.getCache(cacheModel.getId()).getCacheConfiguration().setTimeToLiveSeconds(cacheModel.getFlushIntervalSeconds());
		}
		LOG.debug("get from cache key[" + keyString + "] value[" + EHCacheHelper.get(cacheModel.getId(), keyString) + "]");
		return EHCacheHelper.get(cacheModel.getId(), keyString);
	}

	public void putObject(CacheModel cacheModel, Object key, Object object) {
		String keyString = modifyKey(key.toString());
		if(!EHCacheHelper.exists(cacheModel.getId())){
			EHCacheHelper.addCache(cacheModel.getId());
			EHCacheHelper.getCache(cacheModel.getId()).getCacheConfiguration().setTimeToIdleSeconds(cacheModel.getFlushIntervalSeconds());
			EHCacheHelper.getCache(cacheModel.getId()).getCacheConfiguration().setTimeToLiveSeconds(cacheModel.getFlushIntervalSeconds());
		}
		EHCacheHelper.put(cacheModel.getId(), keyString, object);
		LOG.debug("put in cache key[" + keyString + "] value[" + object + "]");
	}

	public Object removeObject(CacheModel cacheModel, Object key) {
		String keyString = modifyKey(key.toString());
		if(!EHCacheHelper.exists(cacheModel.getId())){
			EHCacheHelper.addCache(cacheModel.getId());
			EHCacheHelper.getCache(cacheModel.getId()).getCacheConfiguration().setTimeToIdleSeconds(cacheModel.getFlushIntervalSeconds());
			EHCacheHelper.getCache(cacheModel.getId()).getCacheConfiguration().setTimeToLiveSeconds(cacheModel.getFlushIntervalSeconds());
		}
		Object value = EHCacheHelper.get(cacheModel.getId(), keyString);
		EHCacheHelper.remove(cacheModel.getId(), keyString);
		LOG.debug("remove in cache key[" + keyString + "] ");
		return value;
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

}
