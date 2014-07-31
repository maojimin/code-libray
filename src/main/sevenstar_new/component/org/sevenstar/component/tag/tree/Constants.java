package org.sevenstar.component.tag.tree;

public class Constants {
  public static String ROOT = "/tree";
  public static String SERVLET = "tree.subtree";
  public static String SUBTREE = "TreeInvoker?act=sub";
  public static String PIC_DOCS = "TreeInvoker?act=docs";
  public static String PIC_FLUSH = "TreeInvoker?act=flush";
  public static String PIC_CLOSE = "TreeInvoker?act=close";
  public static String PIC_OPEN = "TreeInvoker?act=open";

  public static String PIC_OPEN_ = "TreeInvoker?act=open_";
  public static String PIC_OPEND = "TreeInvoker?act=opend";
  public static String PIC_CLOSE_ = "TreeInvoker?act=close_";
  public static String PIC_CLOSED = "TreeInvoker?act=closed";

  public static String IMP_TREE_CLASS = "org.sevenstar.component.tag.tree.ImpTreeA";
  public static String BRANCHURL = "alert('branch');var id=\"";
  public static String LEAFURL = "alert('leaf');var id=\"";
  public static String INVALIDATE_ID = "-8080";
  public static String REPLACEID = "#ID#";
  public static String REPLACENAME = "#NAME#";
    public static String REPLACEPARAM= "#PARAM#";
  public static String getTreeJS() {
    StringBuffer buffer = new StringBuffer();

    buffer.append("\r\n function STree() {");
    buffer.append("\r\n         this.cbicon = \"" + Constants.PIC_OPEND + "\";");
    buffer.append("\r\n         this.ebicon = \"" + Constants.PIC_CLOSED + "\";");
    buffer.append("\r\n         this.cbicon_ = \"" + Constants.PIC_OPEN_ + "\";");
    buffer.append("\r\n         this.ebicon_ = \"" + Constants.PIC_CLOSE_ + "\";");
    buffer.append("\r\n         this.refreshicon = \"\";");
    buffer.append("\r\n         this.branchicon = \"\";");
    buffer.append("\r\n         this.leaficon = \"\";");
    buffer.append("\r\n         this.blankicon = \"\";");
    buffer.append(
        "        this.loadinfo = \"<table width=100% border=0 cellspacing=1 cellpadding=1><tr><td bordercolor=#FFCC33 bgcolor=#FFFFCC width=200>Loading ...</td></tr></table>\";");
    buffer.append(
        "        this.subtree = \"" + Constants.SUBTREE + "&id=\";");

    buffer.append("\r\n }");
    buffer.append("\r\n STree.prototype = new STree();");
    buffer.append("\r\n STree.prototype.loadTree = function(id) {");

    buffer.append("\r\n         var targetImg = eval(\"img\" + id);");
    buffer.append("\r\n         var targetImg_ = eval(\"img_\" + id);");
    buffer.append("\r\n         var child = document.all(id);");
    buffer.append("\r\n         var targetDiv = eval(\"div\" + id);");
    buffer.append("\r\n         //如果没有载入");

    buffer.append("\r\n         if(child.load == \"no\"){");
    buffer.append("\r\n                 targetDiv.style.display=\"block\";");
    buffer.append("\r\n                 targetDiv.innerHTML=this.loadinfo;");
    buffer.append("\r\n                 targetImg.src= this.cbicon;");
    buffer.append("\r\n                 targetImg_.src= this.cbicon_;");
    buffer.append("\r\n                 document.frames[\"hiddenframe\"].location.replace(this.subtree+id);");
    buffer.append("\r\n         }");
    buffer.append("\r\n         //如果已经载入");
    buffer.append("\r\n        else{");
    buffer.append("\r\n ");
    buffer.append("\r\n                 if(child.eb == \"yes\"){");
    buffer.append("\r\n                        targetDiv.style.display=\"none\";");
    buffer.append("\r\n                         targetImg.src= this.ebicon;");
    buffer.append("\r\n                         targetImg_.src= this.ebicon_;");
    buffer.append("\r\n                         child.eb = \"no\";");
    buffer.append("\r\n                 }else");
    buffer.append("\r\n                 {");

    buffer.append("\r\n                        targetDiv.style.display=\"block\";");
    buffer.append("\r\n                        targetImg.src= this.cbicon;");
    buffer.append("\r\n                        targetImg_.src= this.cbicon_;");
    buffer.append("\r\n                        child.eb = \"yes\";");
    buffer.append("\r\n                }");
    buffer.append("\r\n        }");
    buffer.append("\r\n }");
    buffer.append("\r\n STree.prototype.freshTree = function(id) {");

    buffer.append("\r\n         var child = document.all(id);");
    buffer.append("\r\n         child.load = \"no\";");

    buffer.append("\r\n         this.loadTree(id);");
    buffer.append("\r\n }");
    buffer.append("\r\n STree.prototype.clickBranch = function(id,name,param){");
    //buffer.append("var suffix=\"id=\"+id;");
    buffer.append("\r\nvar vclick=\""+BRANCHURL+"\";");

    buffer.append("\r\nvclick = vclick.replace(\""+REPLACEID+"\", id);");
    buffer.append("\r\nvclick = vclick.replace(\""+REPLACENAME+"\", name);");
       buffer.append("\r\nvclick = vclick.replace(\""+REPLACEPARAM+"\", param);");
    buffer.append("\r\neval(vclick);");
    //buffer.append("\r\n        " + BRANCHURL + "+suffix");
    buffer.append("\r\n }");
    buffer.append("\r\n STree.prototype.clickLeaf = function(id,name,param){");
    buffer.append("\r\nvar vclick=\""+LEAFURL+"\";");

    buffer.append("\r\nvclick = vclick.replace(\""+REPLACEID+"\", id);");
    buffer.append("\r\nvclick = vclick.replace(\""+REPLACENAME+"\", name);");
        buffer.append("\r\nvclick = vclick.replace(\""+REPLACEPARAM+"\", param);");
    buffer.append("\r\neval(vclick);");

    //buffer.append("var suffix=\"id=\"+id;");
   // buffer.append("\r\n        " + LEAFURL + "+suffix");
    // buffer.append("\r\n         parent.document.all('mainFrame').src=\"/urp/urp.do?method=getUnit&id=\"+id;");
    //其实应该用一个标志来替换的，这样就可以写任何支持click的东西了

    //parent.document.all.unitview.src=\"/urp/urp.do?method=getUnit&id=\"+id;
    //alert(\"this is leaf id=\"+id);
    buffer.append("\r\n }");
    buffer.append("\r\n STree.prototype.addChildren = function(id,children){");
    buffer.append(
        "        parent.document.all(\"div\"+id).innerHTML=children;");
    buffer.append("\r\n         parent.document.all(id).load=\"yes\";");
    buffer.append("\r\n         parent.document.all(id).eb=\"yes\";");
    buffer.append("\r\n }");
    buffer.append("\r\n var tree = new STree();");
    buffer.append(
        "document.write(\"<iframe width=0 height=0  id=hiddenframe></iframe>\");");

    buffer.append("\r\n STree.prototype.outPutRoot = function(id,name){");
    buffer.append(
        "        document.write(\"<table border=0 cellspacing=1 cellpadding=1><tr>\");");
    buffer.append(
        "        document.write(\"<td colspan=2 height=10 class=text_b style='line-height: 15px;'><img alt='click the array to get the sub files' src="
        + Constants.PIC_CLOSE_
        +
        " id='img_\"+id+\"' onclick=javascript:tree.loadTree('\"+id+\"');><img alt='click the folder reload the sub files' id='img\"+id+\"' src="
        + Constants.PIC_CLOSED
        + " onclick=javascript:tree.freshTree('\"+id+\"');>\");");
    buffer.append(
        "        document.write(\"&nbsp;<span class=branch >\"+name+\"</span>\");");
// document.write(\"&nbsp;<span class=branch onclick=javascript:tree.clickBranch('\"+id+\"');>\"+name+\"</span>\");");
    buffer.append("\r\n         document.write(\"</td>\");");
    buffer.append("\r\n         document.write(\"</tr><tr>\");");
    buffer.append(
        "        document.write(\"<td width=10 height=0></td><td id=\"+id+\" load=no><div id=div\"+id+\"></div></td>\");");
    buffer.append("\r\n         document.write(\"</tr>\");");
    buffer.append("\r\n         document.write(\"</table>\");");
    buffer.append("\r\n }");

    //输出第一个根目录
    return buffer.toString();
  }
}
