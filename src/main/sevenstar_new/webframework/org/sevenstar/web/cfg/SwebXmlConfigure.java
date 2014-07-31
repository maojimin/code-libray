 package org.sevenstar.web.cfg;
 
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.dom4j.Document;
 import org.dom4j.Element;
 import org.sevenstar.util.XmlHelper;
 import org.sevenstar.web.cfg.model.ActionModel;
 import org.sevenstar.web.cfg.model.FindModel;
 import org.sevenstar.web.cfg.model.FindParamModel;
 import org.sevenstar.web.cfg.model.FindsModel;
 import org.sevenstar.web.cfg.model.GlobalResultModel;
 import org.sevenstar.web.cfg.model.GlobalResultsModel;
 import org.sevenstar.web.cfg.model.InterceptorModel;
 import org.sevenstar.web.cfg.model.InterceptorParamModel;
 import org.sevenstar.web.cfg.model.InterceptorsModel;
 import org.sevenstar.web.cfg.model.InvocationModel;
 import org.sevenstar.web.cfg.model.InvocationsModel;
 import org.sevenstar.web.cfg.model.ParseModel;
 import org.sevenstar.web.cfg.model.ParsesModel;
 import org.sevenstar.web.cfg.model.ResourceModel;
 import org.sevenstar.web.cfg.model.ResourceParamModel;
 import org.sevenstar.web.cfg.model.ResourcesModel;
 import org.sevenstar.web.cfg.model.ResultLocationModel;
 import org.sevenstar.web.cfg.model.ResultLocationsModel;
 import org.sevenstar.web.cfg.model.ResultTypeModel;
 import org.sevenstar.web.cfg.model.ResultTypeParamModel;
 import org.sevenstar.web.cfg.model.ResultTypesModel;
 import org.sevenstar.web.cfg.model.RuleExcludeRuleModel;
 import org.sevenstar.web.cfg.model.RuleModel;
 import org.sevenstar.web.cfg.model.SwebModel;
 import org.sevenstar.web.cfg.model.UrlModel;
 import org.sevenstar.web.exception.ActionException;
 
 public class SwebXmlConfigure
   implements ISwebConfigure
 {
   private String XMLFILE = "file";
   private Map paramMap;
   private static Log LOG = LogFactory.getLog(SwebXmlConfigure.class);
 
   private SwebModel swebModel = null;
 
   public void setSwebModel(SwebModel swebModel) {
     this.swebModel = swebModel;
   }
 
   public SwebModel getSwebModel() {
     if (this.swebModel != null) {
       return this.swebModel;
     }
     if (getParamMap().get(this.XMLFILE) == null) {
       throw new ActionException("没有配置xml配置文件名");
     }
     String filePath = (String)getParamMap().get(this.XMLFILE);
     return parseByDom4j(filePath);
   }
 
   public SwebModel parse(String filepath){
     SwebModel swebModel = new SwebModel();
     return swebModel;
   }
 
   private SwebModel parseByDom4j(String filePath){
     Document doc = XmlHelper.readByClassPath(filePath);
     Element root = (Element)doc.selectObject("/sweb");
     SwebModel swebModel = (SwebModel)XmlHelper.initialize(SwebModel.class, root);
     if ((swebModel.getEncode() == null) || ("".equals(swebModel.getEncode()))){
       swebModel.setEncode("GBK");
     }
 
     Element welcomeFileEle = (Element)doc.selectObject("/sweb/welcome-file");
     swebModel.setWelcomeFile((String)welcomeFileEle.getData());
 
     Element interceptorsEle = (Element)doc.selectObject("/sweb/interceptors");
     InterceptorsModel interceptorsModel = new InterceptorsModel();
     if (interceptorsEle != null) {
       List interceptorEleList = interceptorsEle.elements();
       for (int i = 0; i < interceptorEleList.size(); i++) {
         Element interceptorEle = (Element)interceptorEleList.get(i);
         InterceptorModel interceptorModel = new InterceptorModel();
         interceptorModel.setName(XmlHelper.getAttributeValue(interceptorEle, "name"));
         interceptorModel.setClassName(XmlHelper.getAttributeValue(interceptorEle, "class"));
         List paramEleList = interceptorEle.elements();
         if ((paramEleList != null) && (paramEleList.size() > 0)) {
           for (int j = 0; j < paramEleList.size(); j++) {
             Element paramEle = (Element)paramEleList.get(j);
             InterceptorParamModel interceptorParamModel = (InterceptorParamModel)
             XmlHelper.initialize(InterceptorParamModel.class, paramEle);
             interceptorModel.addParamModel(interceptorParamModel);
           }
         }
         interceptorsModel.addInterceptors(interceptorModel);
       }
     }
     swebModel.setInterceptorsModel(interceptorsModel);
 
     Element resourcesEle = (Element)doc.selectObject("/sweb/resources");
     ResourcesModel resourcesModel = new ResourcesModel();
     if (resourcesEle != null) {
       List resourceEleList = resourcesEle.elements();
       for (int i = 0; i < resourceEleList.size(); i++) {
         Element resourceEle = (Element)resourceEleList.get(i);
         ResourceModel resourceModel = new ResourceModel();
         resourceModel.setName(XmlHelper.getAttributeValue(resourceEle, "name"));
         resourceModel.setClassName(XmlHelper.getAttributeValue(resourceEle, "class"));
         List paramEleList = resourceEle.elements();
         if ((paramEleList != null) && (paramEleList.size() > 0)) {
           for (int j = 0; j < paramEleList.size(); j++) {
             Element paramEle = (Element)paramEleList.get(j);
             ResourceParamModel resourceParamModel = (ResourceParamModel)
             XmlHelper.initialize(ResourceParamModel.class, paramEle);
             resourceModel.addParamModel(resourceParamModel);
           }
         }
         resourcesModel.addResourceModel(resourceModel);
       }
     }
     swebModel.setResourcesModel(resourcesModel);
 
     Element parsesEle = (Element)doc.selectObject("/sweb/parses");
     ParsesModel parsesModel = new ParsesModel();
     if (parsesEle != null) {
       List parseEleList = parsesEle.elements();
       for (int i = 0; i < parseEleList.size(); i++) {
         Element parseEle = (Element)parseEleList.get(i);
         ParseModel parseModel = new ParseModel();
         parseModel.setName(XmlHelper.getAttributeValue(parseEle, "name"));
         parseModel.setClassName(XmlHelper.getAttributeValue(parseEle, "class"));
         parsesModel.addParseModel(parseModel);
       }
     }
     swebModel.setParsesModel(parsesModel);
 
     Element invocationsEle = (Element)doc.selectObject("/sweb/invocations");
     InvocationsModel invocationsModel = new InvocationsModel();
     if (invocationsEle != null) {
       List invocationsEleList = invocationsEle.elements();
       for (int i = 0; i < invocationsEleList.size(); i++) {
         Element invocationEle = (Element)invocationsEleList.get(i);
         InvocationModel invocationModel = new InvocationModel();
         invocationModel.setName(XmlHelper.getAttributeValue(invocationEle, "name"));
         invocationModel.setClassName(XmlHelper.getAttributeValue(invocationEle, "class"));
         invocationsModel.addInvocationModel(invocationModel);
       }
     }
     swebModel.setInvocationsModel(invocationsModel);
 
     Element resultTypesEle = (Element)doc.selectObject("/sweb/result-types");
     ResultTypesModel resultTypesModel = new ResultTypesModel();
     if (resultTypesEle != null) {
       List resultTypeEleList = resultTypesEle.elements();
       for (int i = 0; i < resultTypeEleList.size(); i++) {
         Element resultTypeEle = (Element)resultTypeEleList.get(i);
         ResultTypeModel resultTypeModel = new ResultTypeModel();
         resultTypeModel.setName(XmlHelper.getAttributeValue(resultTypeEle, "name"));
         resultTypeModel.setClassName(XmlHelper.getAttributeValue(resultTypeEle, "class"));
         List resultTypeParamEleList = resultTypeEle.elements();
         if ((resultTypeParamEleList != null) && (resultTypeParamEleList.size() > 0)) {
           for (int j = 0; j < resultTypeParamEleList.size(); j++) {
             Element resultTypeParamEle = (Element)resultTypeParamEleList.get(j);
             ResultTypeParamModel resultTypeParamModel = (ResultTypeParamModel)XmlHelper.initialize(ResultTypeParamModel.class, resultTypeParamEle);
             resultTypeModel.addResultTypeParamModel(resultTypeParamModel);
           }
         }
         resultTypesModel.addResultTypeModel(resultTypeModel);
       }
     }
     swebModel.setResultTypesModel(resultTypesModel);
     Element globalResultsEle = (Element)doc.selectObject("/sweb/global-results");
     GlobalResultsModel globalResultsModel = new GlobalResultsModel();
     if (globalResultsEle != null) {
       List globalResultEleList = globalResultsEle.elements();
       for (int i = 0; i < globalResultEleList.size(); i++) {
         Element globalResultEle = (Element)globalResultEleList.get(i);
         GlobalResultModel globalResultModel = (GlobalResultModel)XmlHelper.initialize(GlobalResultModel.class, globalResultEle);
         globalResultsModel.addResultModel(globalResultModel);
       }
     }
     swebModel.setGlobalResultsModel(globalResultsModel);
 
     Element resultLocationsEle = (Element)doc.selectObject("/sweb/result-locations");
     ResultLocationsModel resultLocationsModel = new ResultLocationsModel();
     if (resultLocationsEle != null) {
       List resultLocationsList = resultLocationsEle.elements();
       for (int i = 0; i < resultLocationsList.size(); i++) {
         Element resultLocationEle = (Element)resultLocationsList.get(i);
         ResultLocationModel resultLocationModel = new ResultLocationModel();
         resultLocationModel.setName(XmlHelper.getAttributeValue(resultLocationEle, "name"));
         resultLocationModel.setClassName(XmlHelper.getAttributeValue(resultLocationEle, "class"));
         resultLocationsModel.addLocationModel(resultLocationModel);
       }
     }
     swebModel.setResultLocationsModel(resultLocationsModel);
 
     Element findsEle = (Element)doc.selectObject("/sweb/finds");
     FindsModel findsModel = new FindsModel();
     if (findsEle != null) {
       List findEleList = findsEle.elements();
       for (int i = 0; i < findEleList.size(); i++) {
         Element findEle = (Element)findEleList.get(i);
         FindModel findModel = new FindModel();
         findModel.setName(XmlHelper.getAttributeValue(findEle, "name"));
         findModel.setClassName(XmlHelper.getAttributeValue(findEle, "class"));
         List paramEleList = findEle.elements();
         if ((paramEleList != null) && (paramEleList.size() > 0)) {
           for (int j = 0; j < paramEleList.size(); j++) {
             Element paramEle = (Element)paramEleList.get(j);
             FindParamModel findParamModel = (FindParamModel)XmlHelper.initialize(FindParamModel.class, paramEle);
             findModel.addParamModel(findParamModel);
           }
         }
         findsModel.addFindModel(findModel);
       }
     }
     swebModel.setFindsModel(findsModel);
 
     Element actionEle = (Element)doc.selectObject("/sweb/action");
     if (actionEle != null) {
       ActionModel actionModel = (ActionModel)XmlHelper.initialize(ActionModel.class, actionEle);
       swebModel.setActionModel(actionModel);
     }
     Element urlEle = (Element)doc.selectObject("/sweb/action/url");
     if (urlEle != null) {
       List ruleList = urlEle.elements();
       for (int i = 0; i < ruleList.size(); i++) {
         Element ruleEle = (Element)ruleList.get(i);
         RuleModel ruleModel = (RuleModel)XmlHelper.initialize(RuleModel.class, ruleEle);
         List excludeUrls = ruleEle.elements();
         if ((excludeUrls != null) && (excludeUrls.size() > 0)) {
           for (int j = 0; j < excludeUrls.size(); j++) {
             Element excludeUrlEle = (Element)excludeUrls.get(j);
             RuleExcludeRuleModel ruleExcludeRuleModel = (RuleExcludeRuleModel)XmlHelper.initialize(RuleExcludeRuleModel.class, excludeUrlEle);
             ruleModel.addRuleExcludeRuleModel(ruleExcludeRuleModel);
           }
         }
         swebModel.getActionModel().getUrlModel().addRuleModel(ruleModel);
       }
     }
     LOG.debug("Configure:init");
     return swebModel;
   }
 
   public Map getParamMap()
   {
     if (this.paramMap == null) {
       this.paramMap = new HashMap();
     }
     return this.paramMap;
   }
 
   public void setParamMap(Map map) {
     this.paramMap = map;
   }
 }
