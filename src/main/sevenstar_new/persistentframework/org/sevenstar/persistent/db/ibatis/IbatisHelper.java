package org.sevenstar.persistent.db.ibatis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sevenstar.component.freemarker.FreemarkerHelper;
import org.sevenstar.persistent.db.cfg.SdbConfigure;
import org.sevenstar.persistent.db.dialect.DialectFactory;
import org.sevenstar.persistent.db.model.Domain;
import org.sevenstar.util.ResourceHelper;

public class IbatisHelper
{
  private static Log LOG = LogFactory.getLog(IbatisHelper.class);

  public static String generateSqlMap(Domain domain) {
    if (domain == null) {
      return null;
    }
    Map map = new HashMap();
    map.put("domain", domain);
    map.put("dialet", DialectFactory.getDialect(SdbConfigure.getSdbModel().getDatabase()));
    return FreemarkerHelper.process(
      "org/sevenstar/persistent/db/ibatis/SqlmapTemplate.ftl", map);
  }

  public static List generateSqlMapTmpFileList(String rootPackages, String pattern)
  {
    List domainList = ResourceHelper.findClassList(rootPackages, pattern);
    List sqlmapFileList = new ArrayList();
    for (int i = 0; i < domainList.size(); i++) {
      Class klass = (Class)domainList.get(i);
      try {
        String xmlcontent = generateSqlMap(klass);
        if ((xmlcontent != null) && (!"".endsWith(xmlcontent))) {
          File file = File.createTempFile(klass.getSimpleName() + 
            System.currentTimeMillis() + "_base", "xml");
          LOG.debug(xmlcontent);
          FileOutputStream fos = new FileOutputStream(file);
          try {
            fos.write(xmlcontent.getBytes("utf-8"));
          } finally {
            fos.close();
            fos = null;
          }
          sqlmapFileList.add(file.getPath());
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    return sqlmapFileList;
  }

  public static String generateSqlMap(Class clazz) {
    return generateSqlMap(SdbConfigure.getFind(clazz).find(clazz));
  }
}