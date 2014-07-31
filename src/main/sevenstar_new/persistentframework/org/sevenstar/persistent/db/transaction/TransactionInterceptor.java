package org.sevenstar.persistent.db.transaction;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.sevenstar.util.BeanHelper;
import org.sevenstar.web.action.Action;
import org.sevenstar.web.interceptor.Interceptor;
import org.sevenstar.web.invocation.Invocation;

public class TransactionInterceptor
  implements Interceptor
{
  private Map paramMap;

  public Object intercept(Invocation invocation)
  {
    Action action = invocation.getAction();
    String methodName = invocation.getMethodName();
    Method method = BeanHelper.getMethod(action.getClass(), methodName);
    Object result = null;
    if ((method != null) && (method.isAnnotationPresent(SSTransaction.class))) {
      TransactionFactory.startTransaction();
      try {
        result = invocation.invoke();
        TransactionFactory.commitTransaction();
      } finally {
        TransactionFactory.endTransaction();
      }
    } else {
      result = invocation.invoke();
    }
    return result;
  }

  public Map getParamMap() {
    if (this.paramMap == null) {
      this.paramMap = new HashMap();
    }
    return this.paramMap;
  }

  public void setParamMap(Map map) {
    this.paramMap = map;
  }
}