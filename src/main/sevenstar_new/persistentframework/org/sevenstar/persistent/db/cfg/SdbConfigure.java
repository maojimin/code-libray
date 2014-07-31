package org.sevenstar.persistent.db.cfg;

import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sevenstar.persistent.db.cfg.model.FindModel;
import org.sevenstar.persistent.db.cfg.model.FindParamModel;
import org.sevenstar.persistent.db.cfg.model.FindsModel;
import org.sevenstar.persistent.db.cfg.model.RuleModel;
import org.sevenstar.persistent.db.cfg.model.RulesModel;
import org.sevenstar.persistent.db.cfg.model.SdbModel;
import org.sevenstar.persistent.db.cfg.model.SqlmapModel;
import org.sevenstar.persistent.db.exception.ConfigureException;
import org.sevenstar.persistent.db.find.IFind;
import org.sevenstar.util.BeanHelper;
import org.sevenstar.util.RegexpHelper;

public final class SdbConfigure
{
  private static Log LOG = LogFactory.getLog(SdbConfigure.class);

  public static SqlmapModel getSqlmapModel() {
    return SdbConfigureFactory.getSdbModel().getSqlmapModel();
  }

  public static SdbModel getSdbModel() {
    return SdbConfigureFactory.getSdbModel();
  }

  public static int getPagesize() {
    if ((getSdbModel().getPagesize() != null) && 
      (!"".equals(getSdbModel().getPagesize()))) {
      try {
        return Integer.parseInt(getSdbModel().getPagesize());
      } catch (Throwable e) {
        return 18;
      }
    }
    return 18;
  }

  public static IFind getFind(Class clazz) {
    List rulesList = getSdbModel().getRulesModel().getRuleList();
    for (int i = 0; i < rulesList.size(); i++) {
      RuleModel rule = (RuleModel)rulesList.get(i);
      if (RegexpHelper.isGlobmatches(clazz.getName(), rule.getPattern())) {
        List findList = getSdbModel().getFindsModel().getFindList();
        for (int j = 0; j < findList.size(); j++) {
          FindModel find = (FindModel)findList.get(j);
          if (find.getName().equals(rule.getFind())) {
            LOG.debug("find find[" + rule.getFind() + 
              "] for class[" + clazz.getName() + "]");
            IFind result = (IFind)BeanHelper.newInstance(find
              .getClassName());
            if (find.getParamModelList().size() > 0) {
              for (int k = 0; k < find.getParamModelList().size(); k++) {
                FindParamModel fpm = 
                  (FindParamModel)find
                  .getParamModelList().get(k);
                if ((fpm.getName() == null) || 
                  ("".equals(fpm.getName()))) continue;
                result.getParamMap().put(fpm.getName(), 
                  fpm.getValue());
              }
            }

            return result;
          }
        }
      }
    }
    throw new ConfigureException("hasn't find [find] for class[" + 
      clazz.getName() + "]");
  }
}