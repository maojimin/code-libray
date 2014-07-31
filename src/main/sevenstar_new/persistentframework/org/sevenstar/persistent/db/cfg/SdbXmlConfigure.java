package org.sevenstar.persistent.db.cfg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.Element;
import org.sevenstar.persistent.db.cfg.model.FindModel;
import org.sevenstar.persistent.db.cfg.model.FindParamModel;
import org.sevenstar.persistent.db.cfg.model.FindsModel;
import org.sevenstar.persistent.db.cfg.model.RuleModel;
import org.sevenstar.persistent.db.cfg.model.RulesModel;
import org.sevenstar.persistent.db.cfg.model.SdbModel;
import org.sevenstar.persistent.db.cfg.model.SqlmapModel;
import org.sevenstar.persistent.db.exception.ConfigureException;
import org.sevenstar.util.XmlHelper;

public class SdbXmlConfigure
  implements ISdbConfigure
{
  private String XMLFILE = "file";
  private Map paramMap;
  private static SdbModel sdbModel;

  public SdbModel getSdbModel()
  {
    if (sdbModel != null) {
      return sdbModel;
    }
    if (getParamMap().get(this.XMLFILE) == null) {
      throw new ConfigureException("has't configure xml file name");
    }
    String filePath = (String)getParamMap().get(this.XMLFILE);
    synchronized (this.XMLFILE) {
      Document doc = XmlHelper.readByClassPath(filePath);
      Element root = (Element)doc.selectObject("/sdb");
      sdbModel = (SdbModel)XmlHelper.initialize(SdbModel.class, root);
      Element sqlmapEle = (Element)doc.selectObject("/sdb/sqlmap");
      if (sqlmapEle == null) {
        throw new ConfigureException("hasn't configure /sdb/sqlmap");
      }
      sdbModel.setSqlmapModel((SqlmapModel)XmlHelper.initialize(
        SqlmapModel.class, sqlmapEle));

      Element findsEle = (Element)doc.selectObject("/sdb/finds");
      FindsModel findsModel = new FindsModel();
      if (findsEle != null) {
        List findEleList = findsEle.elements();
        for (int i = 0; i < findEleList.size(); i++) {
          Element findEle = (Element)findEleList.get(i);
          FindModel findModel = new FindModel();
          findModel.setName(
            XmlHelper.getAttributeValue(findEle, 
            "name"));
          findModel.setClassName(
            XmlHelper.getAttributeValue(findEle, 
            "class"));
          List paramEleList = findEle.elements();
          if ((paramEleList != null) && (paramEleList.size() > 0)) {
            for (int j = 0; j < paramEleList.size(); j++) {
              Element paramEle = (Element)paramEleList.get(j);
              FindParamModel findParamModel = (FindParamModel)
                XmlHelper.initialize(FindParamModel.class, paramEle);
              findModel.addParamModel(findParamModel);
            }
          }
          findsModel.addFindModel(findModel);
        }
      }
      sdbModel.setFindsModel(findsModel);

      Element rulesEle = (Element)doc.selectObject("/sdb/rules");
      RulesModel rulesModel = new RulesModel();
      if (rulesEle != null) {
        List ruleEleList = rulesEle.elements();
        for (int i = 0; i < ruleEleList.size(); i++) {
          Element ruleEle = (Element)ruleEleList.get(i);
          rulesModel.addRuleModel((RuleModel)XmlHelper.initialize(RuleModel.class, ruleEle));
        }
      }
      sdbModel.setRulesModel(rulesModel);
      return sdbModel;
    }
  }

  public void setSdbModel(SdbModel sdbModel) {
    sdbModel = sdbModel;
  }

  public Map getParamMap() {
    if (this.paramMap == null) {
      this.paramMap = new HashMap();
    }
    return this.paramMap;
  }

  public void setParamMap(Map map) {
    this.paramMap = map;
  }
}