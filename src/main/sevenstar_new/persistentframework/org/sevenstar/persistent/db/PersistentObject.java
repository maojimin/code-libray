package org.sevenstar.persistent.db;

import java.io.Serializable;
import java.util.List;
import org.sevenstar.persistent.db.ibatis.IbatisPage;

public class PersistentObject extends PageParam
{
  public void insert()
  {
    PersistentFactory.insert(this);
  }

  public void insert(String sqlmapfile) {
    PersistentFactory.insert(this, sqlmapfile);
  }

  public void delete() {
    PersistentFactory.delete(this);
  }

  public void delete(String sqlmapfile) {
    PersistentFactory.delete(this, sqlmapfile);
  }

  public void deleteEqual() {
    PersistentFactory.deleteEqual(this);
  }

  public void deleteEqual(String sqlmapfile) {
    PersistentFactory.deleteEqual(this, sqlmapfile);
  }

  public void update() {
    PersistentFactory.update(this);
  }

  public void update(String sqlmapfile) {
    PersistentFactory.update(this, sqlmapfile);
  }

  public void updateNull() {
    PersistentFactory.updateNull(this);
  }

  public void updateNull(String sqlmapfile) {
    PersistentFactory.updateNull(this, sqlmapfile);
  }

  public void insertOrUpdate() {
    PersistentFactory.insertOrUpdate(this);
  }

  public void insertOrUpdate(String sqlmapfile) {
    PersistentFactory.insertOrUpdate(this, sqlmapfile);
  }

  public Object load() {
    return PersistentFactory.load(this);
  }

  public Object load(String sqlmapfile) {
    return PersistentFactory.load(this, sqlmapfile);
  }

  public boolean exist() {
    return PersistentFactory.exist(this);
  }

  public boolean updateExist(Serializable id) {
    return PersistentFactory.updateExist(this, id);
  }

  public int count() {
    return PersistentFactory.count(this);
  }

  public int count(String sqlmapfile) {
    return PersistentFactory.count(this, sqlmapfile);
  }

  public int countEqual() {
    return PersistentFactory.countEqual(this);
  }

  public int countEqual(String sqlmapfile) {
    return PersistentFactory.countEqual(this, sqlmapfile);
  }

  public boolean exist(String sqlmapfile) {
    return PersistentFactory.exist(this, sqlmapfile);
  }

  public boolean updateExist(Serializable id, String sqlmapfile) {
    return PersistentFactory.updateExist(this, id, sqlmapfile);
  }

  public Object loadEqual() {
    return PersistentFactory.loadEqual(this);
  }

  public Object loadEqual(String sqlmapfile) {
    return PersistentFactory.loadEqual(this, sqlmapfile);
  }

  public List select() {
    return PersistentFactory.select(this);
  }

  public List select(String sqlmapfile) {
    return PersistentFactory.select(this, sqlmapfile);
  }

  public List select(int maxsize) {
    return PersistentFactory.select(this, maxsize);
  }

  public List select(int maxsize, String sqlmapfile) {
    return PersistentFactory.select(this, sqlmapfile, maxsize);
  }

  public List selectEqual() {
    return PersistentFactory.selectEqual(this);
  }

  public List selectEqual(String sqlmapfile) {
    return PersistentFactory.selectEqual(this, sqlmapfile);
  }

  public List selectEqual(int maxsize) {
    return PersistentFactory.selectEqual(this, maxsize);
  }

  public List selectEqual(int maxsize, String sqlmapfile) {
    return PersistentFactory.selectEqual(this, sqlmapfile, maxsize);
  }

  public Object selectEqualSingle() {
    return PersistentFactory.selectEqualSingle(this);
  }

  public Object selectEqualSingle(String sqlmapfile) {
    return PersistentFactory.selectEqualSingle(this, sqlmapfile);
  }

  public Object selectSingle() {
    return PersistentFactory.selectSingle(this);
  }

  public Object selectSingle(String sqlmapfile) {
    return PersistentFactory.selectSingle(this, sqlmapfile);
  }

  public List selectAll() {
    return PersistentFactory.selectAll(getClass());
  }

  public List selectAll(String sqlmapfile) {
    return PersistentFactory.selectAll(getClass(), sqlmapfile);
  }

  public IbatisPage getSelectPage(IbatisPage page) {
    return PersistentFactory.getSelectPage(this, page);
  }

  public IbatisPage getSelectPage(IbatisPage page, String sqlmapfile) {
    return PersistentFactory.getSelectPage(this, page, sqlmapfile);
  }

  public List queryPageList(IbatisPage page) {
    return PersistentFactory.queryPageList(this, page);
  }

  public List queryPageList(IbatisPage page, String sqlmapfile) {
    return PersistentFactory.queryPageList(this, page, sqlmapfile);
  }
}