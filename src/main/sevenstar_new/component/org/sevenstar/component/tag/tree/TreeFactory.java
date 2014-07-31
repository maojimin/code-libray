package org.sevenstar.component.tag.tree;

public class TreeFactory {
  //private static Tree tree = null;
  public static Tree getInstance(String classpatch)throws Exception{
    
	Tree  tree = (Tree) Class.forName(classpatch).newInstance();
    return tree;
  }

}
