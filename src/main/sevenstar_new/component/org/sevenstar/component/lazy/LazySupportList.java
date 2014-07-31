/*     */ package org.sevenstar.component.lazy;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.sevenstar.util.BeanHelper;
/*     */ 
/*     */ public class LazySupportList extends ArrayList
/*     */ {
/*     */   private Object targetObject;
/*     */   private String propertyName;
/*  16 */   private boolean hasLoading = false;
/*     */ 
/*  18 */   private Object lockedObject = new Object();
/*     */ 
/*     */   public LazySupportList(Object targetObject, String propertyName) {
/*  21 */     this.targetObject = targetObject;
/*  22 */     this.propertyName = propertyName;
/*     */   }
/*     */ 
/*     */   private void lazyLoading() {
/*  26 */     if (!this.hasLoading) {
/*  27 */       synchronized (this.lockedObject) {
/*  28 */         this.hasLoading = true;
/*     */       }
/*  30 */       List list = (List)BeanHelper.getPropertyValue(this.propertyName, 
/*  31 */         this.targetObject);
/*  32 */       if (list != null)
/*  33 */         for (int i = 0; i < list.size(); i++)
/*  34 */           add(list.get(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   public Iterator iterator()
/*     */   {
/*  41 */     lazyLoading();
/*  42 */     return super.iterator();
/*     */   }
/*     */ 
/*     */   public int size() {
/*  46 */     lazyLoading();
/*  47 */     return super.size();
/*     */   }
/*     */ 
/*     */   public boolean isEmpty() {
/*  51 */     lazyLoading();
/*  52 */     return super.isEmpty();
/*     */   }
/*     */ 
/*     */   public boolean contains(Object o) {
/*  56 */     lazyLoading();
/*  57 */     return super.contains(o);
/*     */   }
/*     */ 
/*     */   public int indexOf(Object o) {
/*  61 */     lazyLoading();
/*  62 */     return super.indexOf(o);
/*     */   }
/*     */ 
/*     */   public int lastIndexOf(Object o) {
/*  66 */     lazyLoading();
/*  67 */     return super.lastIndexOf(o);
/*     */   }
/*     */ 
/*     */   public Object clone() {
/*  71 */     lazyLoading();
/*  72 */     return super.clone();
/*     */   }
/*     */ 
/*     */   public Object[] toArray() {
/*  76 */     lazyLoading();
/*  77 */     return super.toArray();
/*     */   }
/*     */ 
/*     */   public Object get(int i) {
/*  81 */     lazyLoading();
/*  82 */     return super.get(i);
/*     */   }
/*     */ 
/*     */   public Object set(int index, Object element) {
/*  86 */     lazyLoading();
/*  87 */     return super.set(index, element);
/*     */   }
/*     */ 
/*     */   public void add(int index, Object element) {
/*  91 */     lazyLoading();
/*  92 */     super.add(index, element);
/*     */   }
/*     */ 
/*     */   public boolean add(Object element) {
/*  96 */     lazyLoading();
/*  97 */     return super.add(element);
/*     */   }
/*     */ 
/*     */   public Object remove(int index) {
/* 101 */     lazyLoading();
/* 102 */     return super.remove(index);
/*     */   }
/*     */ 
/*     */   public boolean remove(Object o) {
/* 106 */     lazyLoading();
/* 107 */     return super.remove(o);
/*     */   }
/*     */ 
/*     */   public boolean addAll(Collection connection) {
/* 111 */     lazyLoading();
/* 112 */     return super.addAll(connection);
/*     */   }
/*     */ 
/*     */   public boolean addAll(int index, Collection connection) {
/* 116 */     lazyLoading();
/* 117 */     return super.addAll(index, connection);
/*     */   }
/*     */ }

/* Location:           D:\Program Files\JD-GUI\test\SevenStarComponent15.jar
 * Qualified Name:     org.sevenstar.component.lazy.LazySupportList
 * JD-Core Version:    0.6.0
 */