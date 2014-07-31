package org.sevenstar.persistent.db.cfg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.Element;
import org.sevenstar.persistent.db.cfg.model.SdbModel;
import org.sevenstar.util.BeanHelper;
import org.sevenstar.util.XmlHelper;

public class SdbConfigureFactory
{
  private static ISdbConfigure configureImpl;
  private static final String CONFIGPATH = "sdb-config.xml";
  private static SdbConfigModel configModel;

  public static SdbModel getSdbModel()
  {
    return getConfigure().getSdbModel();
  }

  public static void setSdbModel(SdbModel sdbModel) {
    getConfigure().setSdbModel(sdbModel);
  }

  private static ISdbConfigure getConfigure() {
    if (configureImpl == null) {
      synchronized ("sdb-config.xml") {
        if (configModel == null) {
          configModel = getConfigModel();
        }
        configureImpl = (ISdbConfigure)BeanHelper.newInstance(configModel
          .getClassName());
        if (configModel.getParamList().size() > 0) {
          List paramModelList = configModel.getParamList();
          Map paramMap = new HashMap();
          for (int i = 0; i < paramModelList.size(); i++) {
            SdbConfigureParamModel paramModel = 
              (SdbConfigureParamModel)paramModelList
              .get(i);
            paramMap.put(paramModel.getName(), 
              paramModel.getValue());
          }
          configureImpl.setParamMap(paramMap);
        }
      }
    }
    return configureImpl;
  }

  private static SdbConfigModel getConfigModel() {
    Document doc = XmlHelper.readByClassPath("sdb-config.xml");
    Element configEle = (Element)doc.selectObject("/sdb/config");
    SdbConfigModel model = new SdbConfigModel();
    model.setName(XmlHelper.getAttributeValue(configEle, "name"));
    model.setClassName(XmlHelper.getAttributeValue(configEle, "class"));
    List paramEleList = configEle.elements();
    if ((paramEleList != null) && (paramEleList.size() > 0)) {
      for (int i = 0; i < paramEleList.size(); i++) {
        Element paramEle = (Element)paramEleList.get(i);
        SdbConfigureParamModel paramModel = new SdbConfigureParamModel();
        paramModel.setName(XmlHelper.getAttributeValue(paramEle, "name"));
        paramModel.setValue(XmlHelper.getAttributeValue(paramEle, "value"));
        model.addParamModel(paramModel);
      }
    }
    return model;
  }
}