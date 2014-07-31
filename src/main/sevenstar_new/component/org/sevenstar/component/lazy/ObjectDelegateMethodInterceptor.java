/*    */ package org.sevenstar.component.lazy;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import net.sf.cglib.proxy.MethodInterceptor;
/*    */ import net.sf.cglib.proxy.MethodProxy;
/*    */ import org.sevenstar.util.BeanHelper;
/*    */ 
/*    */ public class ObjectDelegateMethodInterceptor
/*    */   implements MethodInterceptor
/*    */ {
/* 11 */   private Object sourceObject = null;
/*    */ 
/* 13 */   private String prefix = null;
/*    */ 
/*    */   private ObjectDelegateMethodInterceptor()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ObjectDelegateMethodInterceptor(Object soruceObject, String prefix) {
/* 20 */     this.sourceObject = soruceObject;
/* 21 */     this.prefix = prefix;
/*    */   }
/*    */ 
/*    */   public Object intercept(Object targetObject, Method method, Object[] params, MethodProxy methodProxy) throws Throwable
/*    */   {
/* 26 */     if (this.sourceObject != null)
/*    */     {
/* 28 */       if (method.getName().startsWith("get")) {
/* 29 */         Object thisValue = methodProxy
/* 30 */           .invokeSuper(targetObject, params);
/* 31 */         if (thisValue == null) {
/* 32 */           if ((this.prefix == null) || ("".equals(this.prefix))) {
/* 33 */             String className = this.sourceObject.getClass().getName();
/* 34 */             if (className.indexOf("$$EnhancerByCGLIB$$") != -1) {
/* 35 */               className = className.substring(0, 
/* 36 */                 className.indexOf("$$EnhancerByCGLIB$$"));
/*    */             }
/* 38 */             Method targetMethod = BeanHelper.getMethod(
/* 39 */               BeanHelper.loadClass(className), method.getName());
/* 40 */             return targetMethod.invoke(this.sourceObject, params);
/*    */           }
/* 42 */           Object prefixObject = BeanHelper.getPropertyValue(
/* 43 */             this.prefix, this.sourceObject);
/* 44 */           if (prefixObject != null) {
/* 45 */             String className = prefixObject.getClass()
/* 46 */               .getName();
/* 47 */             if (className.indexOf("$$EnhancerByCGLIB$$") != -1) {
/* 48 */               className = className.substring(0, 
/* 49 */                 className.indexOf("$$EnhancerByCGLIB$$"));
/*    */             }
/* 51 */             Method targetMethod = BeanHelper.getMethod(
/* 52 */               BeanHelper.loadClass(className), 
/* 53 */               method.getName());
/* 54 */             return targetMethod.invoke(prefixObject, params);
/*    */           }
/*    */         }
/*    */ 
/* 58 */         return thisValue;
/*    */       }
/*    */ 
/* 61 */       if (method.getName().startsWith("set")) {
/* 62 */         if ((this.prefix == null) || ("".equals(this.prefix))) {
/* 63 */           String className = this.sourceObject.getClass().getName();
/* 64 */           if (className.indexOf("$$EnhancerByCGLIB$$") != -1) {
/* 65 */             className = className.substring(0, 
/* 66 */               className.indexOf("$$EnhancerByCGLIB$$"));
/*    */           }
/* 68 */           Method targetMethod = BeanHelper.getMethod(
/* 69 */             BeanHelper.loadClass(className), method.getName());
/* 70 */           targetMethod.invoke(this.sourceObject, params);
/*    */         } else {
/* 72 */           Object prefixObject = BeanHelper.getPropertyValue(this.prefix, 
/* 73 */             this.sourceObject);
/* 74 */           if (prefixObject != null) {
/* 75 */             String className = prefixObject.getClass().getName();
/* 76 */             if (className.indexOf("$$EnhancerByCGLIB$$") != -1) {
/* 77 */               className = className.substring(0, 
/* 78 */                 className.indexOf("$$EnhancerByCGLIB$$"));
/*    */             }
/* 80 */             Method targetMethod = BeanHelper.getMethod(
/* 81 */               BeanHelper.loadClass(className), method.getName());
/* 82 */             targetMethod.invoke(prefixObject, params);
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/* 87 */     return methodProxy.invokeSuper(targetObject, params);
/*    */   }
/*    */ }

/* Location:           D:\Program Files\JD-GUI\test\SevenStarComponent15.jar
 * Qualified Name:     org.sevenstar.component.lazy.ObjectDelegateMethodInterceptor
 * JD-Core Version:    0.6.0
 */