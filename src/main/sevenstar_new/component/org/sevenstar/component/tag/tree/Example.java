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
public class Example {

	public static TreeExample getTreeExmaple(String id){

			return new TreeExample(String.valueOf(Integer.parseInt(id)+1),"root"+id);

	}


}
