/*
 * Created on 2004-10-9
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.sevenstar.component.tag.tree;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TreeExample {

	public String id;
	public String name;
	
	
	/**
	 * 
	 */
	public TreeExample(String id,String name) {
		 this.id = id;
		 this.name = name;
	}

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}



	/**
	 * @param string
	 */
	public void setId(String string) {
		id = string;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}


}
