package com.desktop.service;

import java.util.List;

import com.desktop.model.extjs.JSONTreeNode;

/**
 * Service层接口定义
 * 
 * @author wenyou <br />
 *         2013年11月18日 下午3:55:00
 */
public interface CommonService {

	/**
	 * 根据id查询当前实体
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
	 * 根据HQL查询条件查询的总记录
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
	 * @return
	 */
	public Object update(Object entity);

	/**
	 * 表单更新方法
	 * 
	 * @param entity
	 * @return
	 */
	// public Object formUpdate(Object entity);

	/**
	 * 删除一个实体
	 * 
	 * @param entity
	 */
	public void delete(Object entity);

	/**
	 * 删除多个实体
	 * 
	 * @param clazz
	 * @param idName
	 * @param ids
	 */
	public void deleteBatchById(Class<?> clazz, String idName, String ids);

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
	 * 根据HQL查询实体列表
	 * 
	 * @param hql
	 * @return
	 */
	public List<?> queryByHql(String hql);

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
	 * @param c
	 * @return
	 */
	public List<?> queryBySql(String sql, Class<?> c);

	/**
	 * hql分页查询
	 * 
	 * @param hql
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<?> queryByHql(String hql, Integer start, Integer limit);

	/**
	 * 执行hql语句
	 * 
	 * @param hql
	 * @return
	 */
	public Long executeHql(String hql);

	/**
	 * 批量更新HQL语句
	 * 
	 * @param updateSqls
	 */
	public void executeBatchHql(String[] updateSqls);

	/**
	 * 构建树形对象
	 * 
	 * @param list
	 * @param rootId
	 * @return
	 */
	public JSONTreeNode buildJSONTreeNode(List<JSONTreeNode> list, String rootId);

	/**
	 * 得到树形查询的集合
	 * 
	 * @param rootId
	 * @param tableName
	 * @param whereSql
	 * @param template
	 * @return
	 */
	public List<JSONTreeNode> getTreeList(String rootId, String tableName,
			String whereSql, JSONTreeNode template);

}
