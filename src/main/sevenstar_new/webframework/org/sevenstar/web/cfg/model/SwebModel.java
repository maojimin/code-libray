package org.sevenstar.web.cfg.model;

public class SwebModel
{
 /* private ActionModel actionModel;
  private String encode;
  private String locale;
  private String resource;
  private ResourcesModel resourcesModel;
  private String welcomeFile;
  private InterceptorsModel interceptorsModel;
  private ParsesModel parsesModel;
  private ResultTypesModel resultTypesModel;
  private GlobalResultsModel globalResultsModel;
  private FindsModel findsModel;
  private ResultLocationsModel resultLocationsModel;
  private InvocationsModel invocationsModel;
  */

	private ActionModel actionModel;
	private String encode;
	/**
	 * 国际化
	 */
	private String locale;
	private String resource;
	private ResourcesModel resourcesModel;
	private String welcomeFile;
	private InterceptorsModel interceptorsModel;
	private ParsesModel parsesModel;
	private ResultTypesModel resultTypesModel;
	private GlobalResultsModel globalResultsModel;
	private FindsModel findsModel;
	private ResultLocationsModel resultLocationsModel;
	private InvocationsModel invocationsModel;

  public String getResource() {
    return this.resource;
  }

  public void setResource(String resource) {
     this.resource = resource;
  }

  public String getLocale() {
     return this.locale;
  }

  public void setLocale(String locale) {
     this.locale = locale;
  }

  public InvocationsModel getInvocationsModel() {
     return this.invocationsModel;
  }

  public void setInvocationsModel(InvocationsModel invocationsModel) {
     this.invocationsModel = invocationsModel;
  }

  public ResultLocationsModel getResultLocationsModel() {
    return this.resultLocationsModel;
  }

  public void setResultLocationsModel(ResultLocationsModel resultLocationsModel)
  {
    this.resultLocationsModel = resultLocationsModel;
  }

  public FindsModel getFindsModel() {
     if (this.findsModel == null) {
       this.findsModel = new FindsModel();
    }
     return this.findsModel;
  }

  public void setFindsModel(FindsModel findsModel) {
     this.findsModel = findsModel;
  }

  public InterceptorsModel getInterceptorsModel() {
    if (this.interceptorsModel == null) {
       this.interceptorsModel = new InterceptorsModel();
    }
     return this.interceptorsModel;
  }

  public void setInterceptorsModel(InterceptorsModel interceptorsModel) {
     this.interceptorsModel = interceptorsModel;
  }

  public ParsesModel getParsesModel() {
     if (this.parsesModel == null) {
       this.parsesModel = new ParsesModel();
    }
    return this.parsesModel;
  }

  public void setParsesModel(ParsesModel parseModel) {
     this.parsesModel = parseModel;
  }

  public ResultTypesModel getResultTypesModel() {
     if (this.resultTypesModel == null) {
       this.resultTypesModel = new ResultTypesModel();
    }
    return this.resultTypesModel;
  }

  public void setResultTypesModel(ResultTypesModel resultTypesModel) {
    this.resultTypesModel = resultTypesModel;
  }

  public GlobalResultsModel getGlobalResultsModel() {
     if (this.globalResultsModel == null) {
       this.globalResultsModel = new GlobalResultsModel();
    }
     return this.globalResultsModel;
  }

  public void setGlobalResultsModel(GlobalResultsModel globalResultsModel) {
    this.globalResultsModel = globalResultsModel;
  }

  public ActionModel getActionModel() {
     if (this.actionModel == null) {
       this.actionModel = new ActionModel();
    }
     return this.actionModel;
  }

  public void setActionModel(ActionModel actionModel) {
     this.actionModel = actionModel;
  }

  public String getEncode() {
     if (this.encode == null) {
       this.encode = "GBK";
    }
     return this.encode;
  }

  public void setEncode(String encode) {
     this.encode = encode;
  }

  public String getWelcomeFile() {
    return this.welcomeFile;
  }

  public void setWelcomeFile(String welcomeFile) {
     this.welcomeFile = welcomeFile;
  }

  public ResourcesModel getResourcesModel() {
     if (this.resourcesModel == null) {
       this.resourcesModel = new ResourcesModel();
    }
    return this.resourcesModel;
  }

  public void setResourcesModel(ResourcesModel resourcesModel) {
    this.resourcesModel = resourcesModel;
  }
}
