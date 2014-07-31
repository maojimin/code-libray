package org.sevenstar.persistent.db.ibatis;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sevenstar.component.freemarker.FreemarkerHelper;
import org.sevenstar.persistent.db.cfg.SdbConfigure;
import org.sevenstar.persistent.db.cfg.model.RuleModel;
import org.sevenstar.persistent.db.cfg.model.RulesModel;
import org.sevenstar.persistent.db.cfg.model.SdbModel;
import org.sevenstar.persistent.db.cfg.model.SqlmapModel;
import org.sevenstar.persistent.db.exception.ConfigureException;
import org.sevenstar.util.ResourceHelper;

public class MyAppSqlConfig
{
  private static Log LOG = LogFactory.getLog(MyAppSqlConfig.class);
  private static SqlMapClient sqlMap;
  private static final Object LockedObject = new Object();
  public static final String defaultSqlmapconfigFile = "sqlmapconfig.xml";
  private static Map sqlmaps;

  public static SqlMapClient getSqlMapInstance()
  {
    return getSqlMapInstance("sqlmapconfig.xml");
  }

  public static SqlMapClient getSqlMapInstance(String resource)
  {
    if (sqlmaps == null) {
      synchronized (LockedObject) {
        sqlmaps = new HashMap();
      }
    }
    if (sqlmaps.containsKey(resource)) {
      SqlMapClient client = (SqlMapClient)sqlmaps.get(resource);
      if (client == null) {
        throw new ConfigureException(
          "configure error,sqlmapclient is null");
      }
      return client;
    }
    synchronized (LockedObject)
    {
      if (sqlmaps.containsKey(resource)) {
        SqlMapClient client = (SqlMapClient)sqlmaps.get(resource);
        if (client == null) {
          throw new ConfigureException(
            "configure error,sqlmapclient is null");
        }
        return client;
      }
      try {
        List ruleList = SdbConfigure.getSdbModel().getRulesModel()
          .getRuleList();
        List sqlmapUrlFileList = new ArrayList();
        for (int i = 0; i < ruleList.size(); i++) {
          RuleModel rm = (RuleModel)ruleList.get(i);
          sqlmapUrlFileList.addAll(
            IbatisHelper.generateSqlMapTmpFileList(rm.getPackages(), 
            rm.getPattern()));
        }
        List sqlmapList = ResourceHelper.findResourceList(
          SdbConfigure.getSqlmapModel().getPackages(), 
          SdbConfigure.getSqlmapModel().getPatterns());

        if (!sqlmapList
          .contains("org/sevenstar/persistent/db/ibatis/example.xml")) {
          sqlmapList
            .add("org/sevenstar/persistent/db/ibatis/example.xml"); } Map map = new HashMap();
        map.put("sqlmapList", sqlmapList);
        map.put("sqlmapUrlFileList", sqlmapUrlFileList);
        SqlMapClient client = null;
        try { String sqlmapconfig = FreemarkerHelper.process(resource, map);
          LOG.debug(sqlmapconfig);
          Reader reader = new StringReader(sqlmapconfig);
          client = SqlMapClientBuilder.buildSqlMapClient(reader);
        } finally {
          for (int i = 0; i < sqlmapUrlFileList.size(); i++) {
            try {
              File tmpFile = new File((String)sqlmapUrlFileList.get(i));
              if (tmpFile.exists()) {
                LOG.debug("delete tmp file [" + 
                  sqlmapUrlFileList.get(i) + "]");
                tmpFile.delete();
              }
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
        sqlmaps.put(resource, client);
        if (client == null) {
          throw new ConfigureException(
            "configure error,sqlmapclient is null");
        }
        return client;
      }
      catch (Exception e) {
        throw new ConfigureException(e);
      }
    }
  }
}