/*
 * Created on 2004-5-8
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.sevenstar.component.tag.tree;

/**
 * @author jwjiang
 * 所有的tree实现必须是这个接口
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
abstract public class  Tree {
	public TreeEntity entity = new TreeEntity();
        /**
         * you can implement the getChildren like this

        public String getChildren(String id) {
          TreeExample te = Example.getTreeExmaple(id);
          entity.addBranh(te.getId(),te.getName()+"branch",null);
          entity.addLeaf(te.getId(),te.getName()+"leaf",null);
          return entity.getChildren();
       }
       */
      public String getSubTree(String id) {
        getChildren(id);
        return entity.getChildren();
      }

      abstract public void getChildren(String id);

      //abstract public String getChildren(String id,String type);
    }
