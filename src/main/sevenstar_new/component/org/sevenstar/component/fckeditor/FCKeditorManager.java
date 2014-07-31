package org.sevenstar.component.fckeditor;


import org.sevenstar.web.context.WebContext;

public class FCKeditorManager {

	private String id;
	private String value = "";
	private String basePath = null;
	private String toolbarSet = null;
	private String width = null;
	private String height = null;
	private String customConfigurationsPath = null;
	private String editorAreaCSS = null;
	private String baseHref = null;
	private String skinPath = null;
	private String pluginsPath = null;
	private String fullPage = null;
	private String debug = null;
	private String autoDetectLanguage = null;
	private String defaultLanguage = null;
	private String contentLangDirection = null;
	private String enableXHTML = null;
	private String enableSourceXHTML = null;
	private String fillEmptyBlocks = null;
	private String formatSource = null;
	private String formatOutput = null;
	private String formatIndentator = null;
	private String geckoUseSPAN = null;
	private String startupFocus = null;
	private String forcePasteAsPlainText = null;
	private String forceSimpleAmpersand = null;
	private String tabSpaces = null;
	private String useBROnCarriageReturn = null;
	private String toolbarStartExpanded = null;
	private String toolbarCanCollapse = null;
	private String fontColors = null;
	private String fontNames = null;
	private String fontSizes = null;
	private String fontFormats = null;
	private String stylesXmlPath = null;
	private String linkBrowserURL = null;
	private String imageBrowserURL = null;
	private String flashBrowserURL = null;
	private String linkUploadURL = null;
	private String imageUploadURL = null;
	private String flashUploadURL = null;

	public String create(String id,String bodyString){
		this.id = id;
		fcked=new FCKeditor(WebContext.getRequest(),id);
		String contextUrl = (WebContext.getRequest()).getContextPath();
		if(toolbarSet!=null)
			fcked.setToolbarSet(toolbarSet);
		if(basePath!=null){
			fcked.setBasePath(basePath);
		}
		if(width!=null){
			fcked.setWidth(width);
		}else{
			fcked.setWidth("800");
		}
		if(height!=null){
			fcked.setHeight(height);
		}else{
			fcked.setHeight("400");
		}
		if (customConfigurationsPath != null)
			fcked.getConfig().put("CustomConfigurationsPath",customConfigurationsPath);
		if (editorAreaCSS != null)
			fcked.getConfig().put("EditorAreaCSS",editorAreaCSS);
		if (baseHref != null)
			fcked.getConfig().put("BaseHref",baseHref);
		if (skinPath != null)
			fcked.getConfig().put("SkinPath",skinPath);
		if (pluginsPath != null)
			fcked.getConfig().put("PluginsPath",pluginsPath);
		if (fullPage != null)
			fcked.getConfig().put("FullPage",fullPage);
		if (debug != null)
			fcked.getConfig().put("Debug",debug);
		if (autoDetectLanguage != null)
			fcked.getConfig().put("AutoDetectLanguage",autoDetectLanguage);
		if (defaultLanguage != null){
			fcked.getConfig().put("DefaultLanguage",defaultLanguage);
		}else{
			fcked.getConfig().put("DefaultLanguage","zh-cn");
		}
		if (contentLangDirection != null)
			fcked.getConfig().put("ContentLangDirection",contentLangDirection);
		if (enableXHTML != null)
			fcked.getConfig().put("EnableXHTML",enableXHTML);
		if (enableSourceXHTML != null)
			fcked.getConfig().put("EnableSourceXHTML",enableSourceXHTML);
		if (fillEmptyBlocks != null)
			fcked.getConfig().put("FillEmptyBlocks",fillEmptyBlocks);
		if (formatSource != null)
			fcked.getConfig().put("FormatSource",formatSource);
		if (formatOutput != null)
			fcked.getConfig().put("FormatOutput",formatOutput);
		if (formatIndentator != null)
			fcked.getConfig().put("FormatIndentator",formatIndentator);
		if (geckoUseSPAN != null)
			fcked.getConfig().put("GeckoUseSPAN",geckoUseSPAN);
		if (startupFocus != null)
			fcked.getConfig().put("StartupFocus",startupFocus);
		if (forcePasteAsPlainText != null)
			fcked.getConfig().put("ForcePasteAsPlainText",forcePasteAsPlainText);
		if (forceSimpleAmpersand != null)
			fcked.getConfig().put("ForceSimpleAmpersand",forceSimpleAmpersand);
		if (tabSpaces != null)
			fcked.getConfig().put("TabSpaces",tabSpaces);
		if (useBROnCarriageReturn != null)
			fcked.getConfig().put("UseBROnCarriageReturn",useBROnCarriageReturn);
		if (toolbarStartExpanded != null)
			fcked.getConfig().put("ToolbarStartExpanded",toolbarStartExpanded);
		if (toolbarCanCollapse != null)
			fcked.getConfig().put("ToolbarCanCollapse",toolbarCanCollapse);
		if (fontColors != null)
			fcked.getConfig().put("FontColors",fontColors);
		if (fontNames != null)
			fcked.getConfig().put("FontNames",fontNames);
		if (fontSizes != null)
			fcked.getConfig().put("FontSizes",fontSizes);
		if (fontFormats != null)
			fcked.getConfig().put("FontFormats",fontFormats);
		if (stylesXmlPath != null)
			fcked.getConfig().put("StylesXmlPath",stylesXmlPath);
		if (linkBrowserURL != null)
			fcked.getConfig().put("LinkBrowserURL",linkBrowserURL);
		if (imageBrowserURL != null){
			fcked.getConfig().put("ImageBrowserURL",imageBrowserURL);
		}else{
			fcked.getConfig().put("ImageBrowserURL",contextUrl+"/component/editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector");
		}
		if (flashBrowserURL != null){
			fcked.getConfig().put("FlashBrowserURL",flashBrowserURL);
		}else{
			fcked.getConfig().put("FlashBrowserURL",contextUrl+"/component/editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector");
		}
		if (linkUploadURL != null){
			fcked.getConfig().put("LinkUploadURL",linkUploadURL);
		}else{
			fcked.getConfig().put("LinkUploadURL",contextUrl+"/component/editor/filemanager/upload/simpleuploader?Type=File");
		}
		if (imageUploadURL != null){
			fcked.getConfig().put("ImageUploadURL",imageUploadURL);
		}else{
			fcked.getConfig().put("ImageUploadURL",contextUrl+"/component/editor/filemanager/upload/simpleuploader?Type=Image");
		}
		if (flashUploadURL != null){
			fcked.getConfig().put("FlashUploadURL",flashUploadURL);
		}else{
			fcked.getConfig().put("FlashUploadURL",contextUrl+"/fckeditor/editor/filemanager/upload/simpleuploader?Type=Flash");
		}
       if(bodyString != null){
    	   fcked.setValue(bodyString);
       }
       return fcked.create();
	}




   /**
     * The underlying FCKeditor object
     *
     */
	protected FCKeditor fcked = null;


    /**
     * Set the unique id of the editor
     *
     * @param value name
     */
	public void setId(String value) {
		id=value;
	}

    /**
     * Set the dir where the FCKeditor files reside on the server
     *
     * @param value path
     */
	public void setBasePath(String value) {
		basePath=value;
	}

    /**
     * Set the name of the toolbar to display
     *
     * @param value toolbar name
     */
	public void setToolbarSet(String value) {
		toolbarSet=value;
	}

    /**
     * Set the width of the textarea
     *
     * @param value width
     */
	public void setWidth(String value) {
		width=value;
	}

    /**
     * Set the height of the textarea
     *
     * @param value height
     */
	public void setHeight(String value) {
		height=value;
	}

	/**
	 * Set the path of a custom file that can override some configurations.<br>
	 * It is recommended to use absolute paths (starting with /), like "/myfckconfig.js".
	 *
	 * @param value path
	 */
	public void setCustomConfigurationsPath(String value) {
		customConfigurationsPath=value;
	}


	/**
	 * Set the CSS styles file to be used in the editing area.<br>
	 * In this way you can point to a file that reflects your web site styles.
	 *
	 * @param value path
	 */
	public void setEditorAreaCSS(String value) {
		editorAreaCSS=value;
	}


	/**
	 * Base URL used to resolve links (on images, links, styles, etc.).<br>
	 * For example, if BaseHref is set to 'http://www.fredck.com', an image that points to "/images/Logo.gif" will be interpreted by the editor as "http://www.fredck.com/images/Logo.gif", without touching the "src" attribute of the image.
	 *
	 * @param value URL
	 */
	public void setBaseHref(String value) {
		baseHref=value;
	}


	/**
	 * Sets the path to the skin (graphical interface settings) to be used by the editor.
	 *
	 * @param value path
	 */
	public void setSkinPath(String value) {
		skinPath=value;
	}


	/**
	 * Sets the base path used when looking for registered plugins.
	 *
	 * @param value path
	 */
	public void setPluginsPath(String value) {
		pluginsPath=value;
	}


	/**
	 * Enables full page editing (from &lt;HTML&gt; to &lt;/HTML&gt;).<br>
	 * It also enables the "Page Properties" toolbar button.
	 *
	 * @param value true/false
	 * @throws JspException if value is not true or false
	 */
	public void setFullPage(String value)  {
		if(! value.equals("true") && ! value.equals("false"))
			throw new RuntimeException("fullPage attribute can only be true or false");
		fullPage=value;
	}


	/**
	 * Enables the debug window to be shown when calling the FCKDebug.Output() function.
	 *
	 * @param value true/false
	 * @throws JspException if value is not true or false
	 */
	public void setDebug(String value)   {
		if(! value.equals("true") && ! value.equals("false"))
			throw new RuntimeException("debug attribute can only be true or false");
		debug=value;
	}


	/**
	 * Tells the editor to automatically detect the user language preferences to adapt its interface language.<br>
	 * With Internet Explorer, the language configured in the Windows Control Panel is used.<br>
	 * With Firefox, the browser language is used.
	 *
	 * @param value true/false
	 * @throws JspException if value is not true or false
	 */
	public void setAutoDetectLanguage(String value)   {
		if(! value.equals("true") && ! value.equals("false"))
			throw new RuntimeException("autoDetectLanguage attribute can only be true or false: here was " + value);
		autoDetectLanguage=value;
	}


	/**
	 * Sets the default language used for the editor's interface localization.<br>
	 * The default language is used when the AutoDetectLanguage options is disabled or when the user language is not available.
	 *
	 * @param value language code
	 */
	public void setDefaultLanguage(String value) {
		defaultLanguage=value;
	}


	/**
	 * Sets the direction of the editor area contents.<br>
	 * The possible values are:
	 * <ul>
     * <li>ltr - Left to Right
     * <li>rtl - Right to Left
     * </ul>
	 *
	 * @param value ltr/rtl
	 * @  if value is not ltr or rtl
	 */
	public void setContentLangDirection(String value)   {
		if(! value.equals("true") && ! value.equals("false"))
			throw new RuntimeException("debug attribute can only be ltr or rtl");
		contentLangDirection=value;
	}


	/**
	 * Tells the editor to process the HTML source to XHTML on form post.
	 *
	 * @param value true/false
	 * @  if value is not true or false
	 */
	public void setEnableXHTML(String value)   {
		if(! value.equals("true") && ! value.equals("false"))
			throw new RuntimeException("enableXHTML attribute can only be true or false");
		enableXHTML=value;
	}


	/**
	 * Tells the editor to process the HTML source to XHTML when switching from WYSIWYG to Source view
	 *
	 * @param value true/false
	 * @  if value is not true or false
	 */
	public void setEnableSourceXHTML(String value)   {
		if(! value.equals("true") && ! value.equals("false"))
			throw new RuntimeException("enableSourceXHTML attribute can only be true or false");
		enableSourceXHTML=value;
	}


	/**
	 * Block elements (like P, DIV, H1, PRE, etc...) are forced to have content (a &amp;nbsp;).<br>
	 * Empty blocks are "collapsed" by while browsing, so a empty &lt;p&gt;&lt;/p&gt; is not visible.<br>
	 * While editing, the editor "expand" empty blocks so you can insert content inside then.<br>
	 * Setting this option to "true" results useful to reflect the same output when browsing and editing.
	 *
	 * @param value true/false
	 * @  if value is not true or false
	 */
	public void setFillEmptyBlocks(String value)   {
		if(! value.equals("true") && ! value.equals("false"))
			throw new RuntimeException("fillEmptyBlocks attribute can only be true or false");
		fillEmptyBlocks=value;
	}


	/**
	 * The HTML shown by the editor, while switching from WYSIWYG to Source views, will be processed and formatted
	 *
	 * @param value true/false
	 * @  if value is not true or false
	 */
	public void setFormatSource(String value)   {
		if(! value.equals("true") && ! value.equals("false"))
			throw new RuntimeException("formatSource attribute can only be true or false");
		formatSource=value;
	}


	/**
	 * The output HTML generated by the editor will be processed and formatted.
	 *
	 * @param value true/false
	 * @  if value is not true or false
	 */
	public void setFormatOutput(String value)   {
		if(! value.equals("true") && ! value.equals("false"))
			throw new RuntimeException("formatOutput attribute can only be true or false");
		formatOutput=value;
	}

	/**
	 * Sets the characters to be used when indenting the HTML source when formatting it.<BR>
	 * Useful values are a sequence of spaces ('     ') or a tab char ('\t').
	 *
	 * @param value indentator
	 */
	public void setFormatIndentator(String value) {
		formatIndentator=value;
	}


	/**
	 * Tells Gecko browsers to use SPAN instead of &lt;B&gt;, &lt;I&gt; and &lt;U&gt; for bold, italic an underline
	 *
	 * @param value true/false
	 * @  if value is not true or false
	 */
	public void setGeckoUseSPAN(String value)   {
		if(! value.equals("true") && ! value.equals("false"))
			throw new RuntimeException("GeckoUseSPAN attribute can only be true or false");
		geckoUseSPAN=value;
	}


	/**
	 * Forces the editor to get the keyboard input focus on startup (page load)
	 *
	 * @param value true/false
	 * @  if value is not true or false
	 */
	public void setStartupFocus(String value)   {
		if(! value.equals("true") && ! value.equals("false"))
			throw new RuntimeException("startupFocus attribute can only be true or false");
		startupFocus=value;
	}


	/**
	 * Converts the clipboard contents to pure text on pasting operations
	 *
	 * @param value true/false
	 * @  if value is not true or false
	 */
	public void setForcePasteAsPlainText(String value)   {
		if(! value.equals("true") && ! value.equals("false"))
			throw new RuntimeException("forcePasteAsPlainText attribute can only be true or false");
		forcePasteAsPlainText=value;
	}


	/**
	 * Forces the ampersands (&) on tags attributes to not be converted to "&amp;amp;"<BR>
	 * This conversion is a W3C requirement for XHTML, so it is recommended to leave this option to "false".
	 *
	 * @param value true/false
	 * @  if value is not true or false
	 */
	public void setForceSimpleAmpersand(String value)   {
		if(! value.equals("true") && ! value.equals("false"))
			throw new RuntimeException("forceSimpleAmpersand attribute can only be true or false");
		forceSimpleAmpersand=value;
	}


	/**
	 * Set the number of spaces (&amp;nbsp;) to be inserted when the user hits the "tab" key.<BR>
	 * This is an Internet Explorer only feature. Other browsers insert spaces automatically by default.
	 *
	 * @param value number of spaces
	 */
	public void setTabSpaces(String value) {
		tabSpaces=value;
	}


	/**
	 * Inserts a &lt;BR&gt; tag when the user hits the "enter" key, instead of starting a new paragraph (&lt;P&gt; or &lt;DIV&gt;).<BR>
	 * This is an Internet Explorer only feature. Other browsers insert the &lt;BR&gt; tag by default.
	 *
	 * @param value true/false
	 * @  if value is not true or false
	 */
	public void setUseBROnCarriageReturn(String value)    {
		if(! value.equals("true") && ! value.equals("false"))
			throw new RuntimeException("useBROnCarriageReturn attribute can only be true or false");
		useBROnCarriageReturn=value;
	}


	/**
	 * The toolbar is Expanded on startup, otherwise it is Collapsed and the user must click on it to show it.
	 *
	 * @param value true/false
	 * @  if value is not true or false
	 */
	public void setToolbarStartExpanded(String value)     {
		if(! value.equals("true") && ! value.equals("false"))
			throw new RuntimeException("ToolbarStartExpanded attribute can only be true or false");
		toolbarStartExpanded=value;
	}


	/**
	 * Tells the editor that the toolbar can be Collapsed/Expanded by the user when clicking the vertical bar placed on the left of it (on the right for "rtl" languages).
	 *
	 * @param value true/false
	 * @  if value is not true or false
	 */
	public void setToolbarCanCollapse(String value)      {
		if(! value.equals("true") && ! value.equals("false"))
			throw new RuntimeException("ToolbarCanCollapse attribute can only be true or false");
		toolbarCanCollapse=value;
	}


	/**
	 * Sets the colors that must be shown in the colors panels (in the toolbar).
	 *
	 * @param value colors
	 */
	public void setFontColors(String value) {
		fontColors=value;
	}


	/**
	 * Sets the list of fonts to be shown in the "Font" toolbar command.
	 *
	 * @param value fonts
	 */
	public void setFontNames(String value) {
		fontNames=value;
	}


	/**
	 * Sets the list of font sizes to be shown in the "Size" toolbar command.
	 *
	 * @param value sizes
	 */
	public void setFontSizes(String value) {
		fontSizes=value;
	}


	/**
	 * Sets the list of formats to be shown in the "Format" toolbar command.
	 *
	 * @param value format list
	 */
	public void setFontFormats(String value) {
		fontFormats=value;
	}


	/**
	 * Sets the path to the XML file that has the definitions and rules of the styles used by the "Style" toolbar command
	 *
	 * @param value path
	 */
	public void setStylesXmlPath(String value) {
		stylesXmlPath=value;
	}


	/**
	 * Sets the URL of the page called when the user clicks the "Browse Server" button in the "Link" dialog window.<BR>
	 * In this way, you can create your custom File Browser that is well integrated with your system.
	 *
	 * @param value path
	 */
	public void setLinkBrowserURL(String value) {
		linkBrowserURL=value;
	}


	/**
	 * Sets the URL of the page called when the user clicks the "Browse Server" button in the "Image" dialog window.<BR>
	 * In this way, you can create your custom Image Browser that is well integrated with your system.
	 *
	 * @param value path
	 */
	public void setImageBrowserURL(String value) {
		imageBrowserURL=value;
	}

	/**
	 * Sets the URL of the page called when the user clicks the "Browse Server" button in the "Flash" dialog window.<BR>
	 * In this way, you can create your custom Flash Browser that is well integrated with your system.
	 *
	 * @param value path
	 */
	public void setFlashBrowserURL(String value) {
		flashBrowserURL=value;
	}


	/**
	 * Sets the URL of the upload handler called when the user clicks the "Send it to server" button in the "Link" dialog window.<BR>
	 * In this way, you can create your custom Link Uploader that is well integrated with your system.
	 *
	 * @param value path
	 */
	public void setLinkUploadURL(String value) {
		linkUploadURL=value;
	}


	/**
	 * Sets the URL of the upload handler called when the user clicks the "Send it to server" button in the "Image" dialog window.<BR>
	 * In this way, you can create your custom Image Uploader that is well integrated with your system.
	 *
	 * @param value path
	 */
	public void setImageUploadURL(String value) {
		imageUploadURL=value;
	}


	/**
	 * Sets the URL of the upload handler called when the user clicks the "Send it to server" button in the "Flash" dialog window.<BR>
	 * In this way, you can create your custom Flash Uploader that is well integrated with your system.
	 *
	 * @param value path
	 */
	public void setFlashUploadURL(String value) {
		flashUploadURL=value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public FCKeditor getFcked() {
		return fcked;
	}

	public void setFcked(FCKeditor fcked) {
		this.fcked = fcked;
	}

	public String getId() {
		return id;
	}

	public String getBasePath() {
		return basePath;
	}

	public String getToolbarSet() {
		return toolbarSet;
	}

	public String getWidth() {
		return width;
	}

	public String getHeight() {
		return height;
	}

	public String getCustomConfigurationsPath() {
		return customConfigurationsPath;
	}

	public String getEditorAreaCSS() {
		return editorAreaCSS;
	}

	public String getBaseHref() {
		return baseHref;
	}

	public String getSkinPath() {
		return skinPath;
	}

	public String getPluginsPath() {
		return pluginsPath;
	}

	public String getFullPage() {
		return fullPage;
	}

	public String getDebug() {
		return debug;
	}

	public String getAutoDetectLanguage() {
		return autoDetectLanguage;
	}

	public String getDefaultLanguage() {
		return defaultLanguage;
	}

	public String getContentLangDirection() {
		return contentLangDirection;
	}

	public String getEnableXHTML() {
		return enableXHTML;
	}

	public String getEnableSourceXHTML() {
		return enableSourceXHTML;
	}

	public String getFillEmptyBlocks() {
		return fillEmptyBlocks;
	}

	public String getFormatSource() {
		return formatSource;
	}

	public String getFormatOutput() {
		return formatOutput;
	}

	public String getFormatIndentator() {
		return formatIndentator;
	}

	public String getGeckoUseSPAN() {
		return geckoUseSPAN;
	}

	public String getStartupFocus() {
		return startupFocus;
	}

	public String getForcePasteAsPlainText() {
		return forcePasteAsPlainText;
	}

	public String getForceSimpleAmpersand() {
		return forceSimpleAmpersand;
	}

	public String getTabSpaces() {
		return tabSpaces;
	}

	public String getUseBROnCarriageReturn() {
		return useBROnCarriageReturn;
	}

	public String getToolbarStartExpanded() {
		return toolbarStartExpanded;
	}

	public String getToolbarCanCollapse() {
		return toolbarCanCollapse;
	}

	public String getFontColors() {
		return fontColors;
	}

	public String getFontNames() {
		return fontNames;
	}

	public String getFontSizes() {
		return fontSizes;
	}

	public String getFontFormats() {
		return fontFormats;
	}

	public String getStylesXmlPath() {
		return stylesXmlPath;
	}

	public String getLinkBrowserURL() {
		return linkBrowserURL;
	}

	public String getImageBrowserURL() {
		return imageBrowserURL;
	}

	public String getFlashBrowserURL() {
		return flashBrowserURL;
	}

	public String getLinkUploadURL() {
		return linkUploadURL;
	}

	public String getImageUploadURL() {
		return imageUploadURL;
	}

	public String getFlashUploadURL() {
		return flashUploadURL;
	}



}
