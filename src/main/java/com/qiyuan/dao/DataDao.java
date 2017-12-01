package com.qiyuan.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



@Repository("dataDao")
public class DataDao {

	private SessionFactory sessionFactory;


	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}


	/**
	 * 添加对象记录
	 *
	 * @param
	 */
	public void addObject(Object o) {
//		Session session=getSession();
//		Transaction tx=getSession().beginTransaction();
		getSession().save(o);
//		tx.commit();
	}

	/**
	 * 删除数据库中指定对象
	 *
	 * @param
	 */
	public void deleteObject(Object o) {
		getSession().delete(o);
	}

	/**
	 * 更新数据库中指定对象
	 *
	 * @param
	 */
	public void updateObject(Object o) {
		getSession().update(o);
	}

	public int executeBySql(String sql){
		SQLQuery createSQLQuery = getSession().createSQLQuery(sql);
		int result = createSQLQuery.executeUpdate();
		return result;
	}

	public String executesBySql(String sql){//返回某个字段
		@SuppressWarnings("unchecked")
		List<String> list = getSession().createSQLQuery(sql).list();
		if(list.size()>0){
			return String.valueOf(list.get(0));
		}else{
			return "";
		}

	}


//	public List<?> excuteCardBySql(String sql){
//		SQLQuery createSQLQuery =getSession().createSQLQuery(sql).addEntity(CardInfo.class);
//		createSQLQuery.setString("account", account);
//		List<?> list = createSQLQuery.list();
//		return list;
//	}

	/**
	 * 更新数据库中指定对象，如果对象记录不存在，则插入新的
	 *
	 * @param
	 */
	public void saveOrUpdateObject(Object o) {
		getSession().saveOrUpdate(o);
	}

	/**
	 * 获取整张表的记录集合
	 *
	 * @param
	 * @return 对象列表集合
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getAllObject(Class<T> c) {
		return getSession().createQuery("from " + c.getName()).list();
	}

	/**
	 * 根据id查询对象记录
	 *
	 * @param
	 * @param
	 * @return 查询对象结果
	 */
	@SuppressWarnings("unchecked")
	public <T> T getObjectById(Class<T> c, Serializable id) {
		return (T) getSession().get(c, id);
	}

	/**
	 * 根据id删除对象记录
	 *
	 * @param
	 * @param
	 */
	public <T> void deleteObjectById(Class<T> c, Serializable id) {
		deleteObject(getObjectById(c, id));
	}

	/**
	 * 条件检索对象数据集
	 *
	 * @param
	 * @param
	 * @param
	 * @return 检索结果数据集
	 */
	@SuppressWarnings("rawtypes")
	public List<?> getObjectsViaParam(String hql, String[] params, Object... p) {
		Query query = getSession().createQuery(hql);
		if (params != null) {
			for (int i = 0; i < p.length; i++) {
				if(p[i] instanceof List){
					query.setParameterList(params[i], (Collection) p[i]);
				}else{
					query.setParameter(params[i], p[i]);
				}
			}
		}
		return query.list();
	}

	/**
	 * 条件检索对象数据集(省略参数名)
	 *
	 * @deprecated 使用 {@link DataDao#getObjectsViaParam(String, String[], Object...)}
	 * @param hql
	 * @param p
	 * @return 检索结果数据集
	 */
	public List<?> getObjectByCondition(String hql, Object... p) {
		String[] params = new String[p.length];
		for (int i = 0; i < p.length; i++) {
			params[i] = ":p" + i;
			hql = hql.replaceFirst("？", params[i]);
		}
		return getObjectsViaParam(hql, params, p);
	}

	/**
	 * @deprecated 按条件查询数据条数,尽量使用{@link DataDao#getCount(String, Object...)}
	 * @param hql
	 *            查询hql语句
	 * @param p
	 *            条件值
	 * @return 查询条数
	 */
	public int getQueryCount(String hql, Object... p) {
		return getObjectByCondition(hql, p).size();
	}

	/**
	 * 按条件查询数据条数
	 *
	 * @param hql
	 *            查询hql语句，select count 检索
	 * @param p
	 *            条件值
	 * @return 查询条数
	 */
	public long getCount(String hql, Object... p) {
		return (Long) getObjectByCondition(hql, p).listIterator().next();
	}

	/**
	 * 分页查询数据集
	 *
	 * @deprecated 使用 {@link DataDao#pageQueryViaParam(String, Integer, Integer, String[], Object...)}
	 * @param hql
	 *            查询hql语句
	 * @param pageSize
	 *            每页条数
	 * @param page
	 *            当前页数，从1开始
	 * @param p
	 *            条件值
	 * @return 检索结果数据集
	 */
	public List<?> pageQuery(String hql, Integer pageSize, Integer page, Object... p) {
		String[] params = new String[p.length];
		for (int i = 0; i < p.length; i++) {
			params[i] = ":p" + i;
			hql = hql.replaceFirst("？", params[i]);
		}
		return pageQueryViaParam(hql, pageSize, page, params, p);
	}

	/**
	 * 分页查询数据集
	 *
	 * @param hql
	 *            查询hql语句
	 * @param pageSize
	 *            每页条数
	 * @param page
	 *            当前页数，从1开始
	 * @param params
	 *            条件名称数组
	 * @param p
	 *            条件值
	 * @return 检索结果数据集
	 */
	public List<?> pageQueryViaParam(String hql, Integer pageSize, Integer page, String[] params, Object... p) {
		Query query = getSession().createQuery(hql);
		if (p != null) {
			for (int i = 0; i < p.length; i++) {
				query.setParameter(params[i], p[i]);
			}
		}
		if (pageSize != null && pageSize > 0 && page != null && page > 0) {
			query.setFirstResult((page - 1) * pageSize).setMaxResults(pageSize);
		}
		return query.list();
	}

	/**
	 * 按条件查询单条数据
	 *
	 * @deprecated 使用 {@link DataDao#getFirstObjectViaParam(String, String[], Object...)}
	 * @param hql
	 *            查询hql语句
	 * @param p
	 *            条件值
	 * @return 检索结果数据集
	 */
	public Object getFirstObject(String hql, Object... p) {
		String[] params = new String[p.length];
		for (int i = 0; i < p.length; i++) {
			params[i] = ":p" + i;
			hql = hql.replaceFirst("？", params[i]);
		}
		return getFirstObjectViaParam(hql, params, p);
	}

	/**
	 * 按条件查询单条数据
	 *
	 * @param hql
	 *            查询hql语句
	 * @param params
	 *            条件名称数组
	 * @param p
	 *            条件值
	 * @return 检索结果数据集
	 */
	public Object getFirstObjectViaParam(String hql, String[] params, Object... p) {
		List<?> list = getObjectsViaParam(hql, params, p);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 按条件批量删除数据
	 *
	 * @deprecated 使用
	 * @param hql
	 *            删除hql语句，delete from 开头
	 * @param p
	 *            条件值
	 */
	public void deleteObjectByCondition(String hql, Object... p) {
		String[] params = new String[p.length];
		for (int i = 0; i < p.length; i++) {
			params[i] = ":p" + i;
			hql = hql.replaceFirst("？", params[i]);
		}
		deleteObjectsViaParam(hql, params, p);
	}

	/**
	 * 按条件批量删除数据
	 *
	 * @param hql
	 *            删除hql语句，delete from 开头
	 * @param params
	 *            条件名称数组
	 * @param p
	 *            条件值
	 */
	public void deleteObjectsViaParam(String hql, String[] params, Object... p) {
		Query query = getSession().createQuery(hql);
		if (p != null) {
			for (int i = 0; i < p.length; i++) {
				query.setParameter(params[i], p[i]);
			}
		}
		query.executeUpdate();
	}

	/**
	 * 用SQL文批量删除数据集
	 *
	 * @param sql
	 *            删除sql语句
	 */
	public void deleteBySql(String sql) {
		SQLQuery query = getSession().createSQLQuery(sql);
		query.executeUpdate();
	}

	/**
	 * 获取所有数据
	 * @param sql
	 * @return
	 */
	public  List<?> getALLData(String sql){
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		return sqlQuery.list();
	}

	/**
	 * 获得数据的count
	 * @param sql
	 * @param obj
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Integer getInfoCount(String sql,Object...obj) throws  IllegalArgumentException{
		checkIsLegal(sql,obj);
		Query query = setConditionParam(sql,obj);
		Object o = query.uniqueResult();
		return o==null?null:(Integer)o;
	}

	/**
	 * 检查参数是否合法
	 * @param sql
	 * @param obj
	 * @throws IllegalArgumentException
	 */
	private  void  checkIsLegal(String sql,Object...obj) throws IllegalArgumentException{

		if ("".equals(sql)|| null==sql){
			throw  new  IllegalArgumentException("sql语句不能为null or 空字符串");
		}

		if (null==obj || "".equals(obj)){
			throw  new  IllegalArgumentException("条件参数不能为null or 空字符串");
		}
		if (obj.length == 0){
			throw  new  IllegalArgumentException("条件参数不能为0");
		}
	}

	/**
	 * 赋值条件参数
	 * @param sql
	 * @param obj
	 */
	private  Query  setConditionParam(String sql,Object...obj) throws IllegalArgumentException{
		Query query = getSession().createQuery(sql);
		if ( null==query) {
			throw  new  IllegalArgumentException("query不能为null");
		}

		for (int i = 0; i <obj.length ; i++) {
			final  Object val=obj[i];
			if (val instanceof  String){
				query.setString(i, (String) val);
			}else  if (val instanceof Integer ){
				query.setInteger(i, (Integer) val);
			}else  if (val instanceof  Long){
				query.setLong(i,(Long) val);
			}else if (val instanceof Date){
				query.setDate(i,(Date) val);
			}else {
				throw  new  IllegalArgumentException("参数必须是String or Integer or Long  or Date类型");
			}

		}
		return  query;
	}
}
