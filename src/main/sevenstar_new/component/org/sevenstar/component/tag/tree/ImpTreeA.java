/*
 * Created on 2004-10-9
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.sevenstar.component.tag.tree;
import java.util.*;
/**
 * @author jjw
 *
 */
public class ImpTreeA extends Tree {

	/* (non-Javadoc)
	 * @see com.pub.tag.tree.Tree#getChildren(java.lang.String)
	 */
	public void getChildren(String id) {
		// TODO Auto-generated method stub
                /**
                 * you also can implement like this
                 * /
                TreeExample te = Example.getTreeExmaple(id);
                entity.addBranh(te.getId(),te.getName()+"branch",null);
                entity.addLeaf(te.getId(),te.getName()+"leaf",null);
                **/

               /**
                * the second example implement
                * 1.have the params
                * 2.the show name has the color
                */
               TreeExample te = Example.getTreeExmaple(id);
                Map map = new HashMap();
                map.put("a","a"+id);
                map.put("b","b"+id);
                String color= "";
                if(Integer.parseInt(te.getId())%2 == 0){
                  color = "<font color=green>";
                }else
                  color = "<font color=blue>";
                entity.addBranh(te.getId(),color+te.getName()+"branch"+"</font>",map);

                entity.addLeaf(te.getId(),color+te.getName()+"leaf"+"</font>",map);



	}


}
