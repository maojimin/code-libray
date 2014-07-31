package org.sevenstar.web.cfg;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.Element;
import org.sevenstar.util.BeanHelper;
import org.sevenstar.util.XmlHelper;
import org.sevenstar.web.cfg.model.SwebModel;

public class SwebConfigureFactory {
  /*private static ISwebConfigure configureImpl;
  private static final String CONFIGPATH = "sweb-config.xml";
  private static SwebConfigModel configModel;
  private static SwebModel swebModel;*/
  
	private static ISwebConfigure configureImpl;
	private static final String CONFIGPATH = "sweb-config.xml";
	private static SwebConfigModel configModel;
	private static  SwebModel swebModel;
	

  static{
     Document doc = XmlHelper.readByClassPath("sweb-config.xml");
     Element configEle = (Element)doc.selectObject("/sweb/config");
     configModel = new SwebConfigModel();
     configModel.setName(XmlHelper.getAttributeValue(configEle, "name"));
     configModel.setClassName(XmlHelper.getAttributeValue(configEle, "class"));
     List paramEleList = configEle.elements();
     if ((paramEleList != null) && (paramEleList.size() > 0)) {
       for (int i = 0; i < paramEleList.size(); i++) {
        Element paramEle = (Element)paramEleList.get(i);
         SwebConfigureParamModel paramModel = new SwebConfigureParamModel();
         paramModel.setName(XmlHelper.getAttributeValue(paramEle, "name"));
        paramModel.setValue(XmlHelper.getAttributeValue(paramEle, "value"));
         configModel.addParamModel(paramModel);
      }
    }
     configureImpl = (ISwebConfigure)BeanHelper.newInstance(configModel.getClassName());
     if (configModel.getParamList().size() > 0) {
       List paramModelList = configModel.getParamList();
       Map paramMap = new HashMap();
      for (int i = 0; i < paramModelList.size(); i++) {
         SwebConfigureParamModel paramModel = (SwebConfigureParamModel)paramModelList.get(i);
         paramMap.put(paramModel.getName(),paramModel.getValue());
      }
      configureImpl.setParamMap(paramMap);
    }
     swebModel = configureImpl.getSwebModel();
  }

  public static SwebModel getSwebModel() {
     return swebModel;
  }

  private static ISwebConfigure getConfigure()
  {
     return configureImpl;
  }
}

