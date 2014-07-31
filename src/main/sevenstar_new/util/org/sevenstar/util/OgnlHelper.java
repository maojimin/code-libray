package org.sevenstar.util;
import com.opensymphony.xwork.util.OgnlContextState;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * @author rtm 2008-5-8
 */
public final class OgnlHelper {
	public static void setValue(Object root, String express, Object value) {

 		OgnlValueStack stack = new OgnlValueStack();
  		stack.push(root);
  		OgnlContextState.setCreatingNullObjects(stack.getContext(), true);
		OgnlContextState.setDenyMethodExecution(stack.getContext(), true);
		OgnlContextState.setReportingConversionErrors(stack.getContext(), true);
		try {
 			stack.setValue(express, value);
 		} finally {
			OgnlContextState.setCreatingNullObjects(stack.getContext(), false);
			OgnlContextState.setDenyMethodExecution(stack.getContext(), false);
			OgnlContextState.setReportingConversionErrors(stack.getContext(), false);
		}
 	}

	public static Object getValue(Object root, String express) {
 		OgnlValueStack stack = new OgnlValueStack();
 		stack.push(root);
 		return stack.findValue(express);
	}

	public static void main(String[] args) {
		/*Bean testBean = new Bean();
		setValue(testBean, "parentBean.parentBean.name", "朱朱");
 		//System.out.println(getValue(testBean, "parentBean.parentBean.name"));
 		System.out.println(testBean.getClass().getName());
		Bean testBean = new Bean();
		setValue(testBean,"createDate","2008-04-14");
		System.out.println(testBean.getCreateDate());*/
 	}
}


