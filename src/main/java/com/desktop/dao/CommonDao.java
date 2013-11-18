package com.desktop.dao;

import java.util.List;

/**
 * DAO模块接口
 * 
 * @author wenyou <br />
 *         2013年11月18日 下午3:08:01
 */
public interface CommonDao {

	/**
	 * 根据ID查询当前实体
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public Object findById(Class<?> clazz, String id);

	/**
	 * 查询所有实体集合
	 * 
	 * @param clazz
	 * @return
	 */
	public List<?> findAll(Class<?> clazz);

	/**
	 * 根据HQL查询条件查询总记录
	 * 
	 * @param hql
	 * @return
	 */
	public Integer getCount(String hql);

	/**
	 * 查询当前页信息
	 * 
	 * @param clazz
	 * @param whereSql
	 * @param from
	 * @param size
	 * @return
	 */
	public List<?> findByPage(final Class<?> clazz, final String whereSql,
			final int from, final int size);

	/**
	 * 添加实体
	 * 
	 * @param entity
	 * @return
	 */
	public Object save(Object entity);

	/**
	 * 更新实体
	 * 
	 * @param entity
	 */
	public Object update(Object entity);

	/**
	 * 删除一个实体
	 * 
	 * @param entity
	 */
	public void delete(Object entity);

	/**
	 * 使用hql查询获取一条记录
	 * 
	 * @param hql
	 * @return
	 */
	public Object getEntityByHql(String hql);

	/**
	 * 执行一条sql语句
	 * 
	 * @param sql
	 * @return
	 */
	public Long executeSql(String sql);

	/**
	 * 执行hql语句
	 * 
	 * @param hql
	 * @return
	 */
	public Long executeHql(String hql);

	/**
	 * 根据HQL查询实体列表
	 * 
	 * @param hql
	 * @return
	 */
	public List<?> queryByHql(String hql);

	/**
	 * 根据HQL分页查询
	 * 
	 * @param hql
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<?> queryByHql(String hql, Integer start, Integer limit);

	/**
	 * 根据SQL查询实体列表
	 * 
	 * @param sql
	 * @return
	 */
	public List<?> queryBySql(String sql);

	/**
	 * 根据SQL查询实体列表
	 * 
	 * @param sql
	 * @return
	 */
	public List<?> queryBySql(String sql, Class<?> c);

}
