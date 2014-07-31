/*
 * Created on 2004-5-8
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.sevenstar.component.tag.tree;
import java.util.*;
/**
 * @author jwjiang
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TreeEntity {

	private StringBuffer children = new StringBuffer();
        /**
         * add branch,the word branh means branch,^^
         *
         * @param id String  the unique value of the tree
         * @param branch String the name of the branch
         * @param params Map the params of the branch
         */
        public void addBranh(String id,String branch,Map params){
          String param = makeParam(params);
          String tbranch = branch;
          int i = tbranch.indexOf(">");
          int e = tbranch.indexOf("<",i);
          if(i != -1&& e != -1){
            tbranch = branch.substring(i+1,e);
          }
          if(!id.equals(Constants.INVALIDATE_ID)){
            children.append("<table border=0 cellspacing=0 cellpadding=0><tr>" +
                            "<td load='no' class='text_b' colspan=2 valign=center height=10 style='line-height: 15px;cursor:hand;'>" +
                            "<img alt='click the array to get the sub files' id='img_" +
                            id + "' src=" + Constants.PIC_CLOSE_ +
                            " onclick=javascript:tree.loadTree('" + id +
                            "'); ><img id='img" + id +
                            "' alt='click the folder reload the sub files' src=" +
                            Constants.PIC_CLOSED +
                            " onclick=javascript:tree.freshTree('" + id + "');>" +

                            "&nbsp;<span class='branch' onclick=javascript:tree.clickBranch('" +
                            id + "','"+tbranch+"','"+param+"'); >" + branch + "</span></td></tr><tr>" +
                            "<td width=12 height=0 class='text_b'></td><td eb=no load=no class='text_b' id='" + id +
                            "' height=0><div id='div" + id + "'></div></td>" +
                            "</tr></table>");
          }else{
            children.append("<table border=0 cellspacing=0 cellpadding=0><tr>" +
                            "<td load='no' class='text_b' colspan=2 valign=center height=10 style='line-height: 15px;cursor:hand;'>" +
                            "<img alt='click the array to get the sub files' id='img_" +
                            id + "' src=" + Constants.PIC_CLOSE_ +
                            " onclick=javascript:tree.loadTree('" + id +
                            "');><img id='img" + id +
                            "' alt='click the folder reload the sub files' src=" +
                            Constants.PIC_CLOSED +
                            " onclick=javascript:tree.freshTree('" + id + "');>" +

                            "&nbsp;<span class='branch' >" + branch + "</span></td></tr><tr>" +
                            "<td width=12 height=0 class='text_b'></td><td eb=no load=no id='" + id +
                            "' height=0><div id='div" + id + "'></div></td>" +
                            "</tr></table>");

          }
	}

        /**
         * add leaf
         * @param id String
         * @param leaf String
         * @param params Map
         */
        public void addLeaf(String id,String leaf,Map params){
          String param = makeParam(params);

          String tleaf = leaf;
          int i = tleaf.indexOf(">");
          int e = tleaf.indexOf("<", i);
          if (i != -1 && e != -1) {
            tleaf = leaf.substring(i + 1, e);
          }

          if(!id.equals(Constants.INVALIDATE_ID)){
            children.append("<table border=0 cellspacing=0 cellpadding=0><tr>" +
                            "<td valign=center height=10 class='text_b' style='line-height: 15px;cursor:hand;'><img id='img" +
                            id + "' src=" + Constants.PIC_DOCS + ">" +

                            "&nbsp;<span class='leaf' onclick=javascript:tree.clickLeaf('" +
                            id + "','"+tleaf+"','"+param+"');>" + leaf + "</span></td></tr><tr>" +

                            "</tr></table>");
          }else{
            children.append("<table border=0 cellspacing=0 cellpadding=0><tr>" +
                            "<td valign=center height=10 class='text_b' style='line-height: 15px;cursor:hand;'><img id='img" +
                            id + "' src=" + Constants.PIC_DOCS + ">" +

                            "&nbsp;<span class='leaf'>" + leaf + "</span></td></tr><tr>" +

                            "</tr></table>");

          }
	}
        private String makeParam(Map params) {
               String param = "";
               if (params != null) {
                 Set keys = params.keySet();
                 if (keys != null) {
                   Iterator itr = keys.iterator();
                   if (itr != null) {
                     while(itr.hasNext()){
                       String key = (String) itr.next();
                       if ("".equals(param)) {
                         param += key + "=" + params.get(key);
                       }
                       else {
                         param += "&" + key + "=" + params.get(key);
                       }
                     }
                   }
                 }
               }
               return param;
             }

	public String getChildren(){

		return children.toString();
	}

}
