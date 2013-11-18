package com.desktop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desktop.dao.CommonDao;

/**
 * Service层实现类
 * 
 * @author wenyou
 *         <p>
 *         2013年9月25日 下午5:06:20
 */
@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	private CommonDao commonDao;

	@Override
	public Object findById(Class<?> clazz, String id) {
		return commonDao.findById(clazz, id);
	}

	@Override
	public List<?> findAll(Class<?> clazz) {
		return commonDao.findAll(clazz);
	}

	@Override
	public Integer getCount(String hql) {
		return commonDao.getCount(hql);
	}

	@Override
	public List<?> findByPage(Class<?> clazz, String whereSql, int from,
			int size) {
		return commonDao.findByPage(clazz, whereSql, from, size);
	}

	@Override
	public Object save(Object entity) {
		return commonDao.save(entity);
	}

	@Override
	public Object update(Object entity) {
		return commonDao.update(entity);
	}

//	@Override
//	public Object formUpdate(Object entity) {
//		String keyName = ModelUtil.getClassPkName(entity.getClass());
//		String keyValue = (String) EntityUtil.getPropertyValue(entity, keyName);
//		// 获取当前需要更新的实体
//		Object obj = commonDao.findById(entity.getClass(), keyValue);
//		obj = EntityUtil.copyNewField(obj, entity);
//		obj = commonDao.update(obj);
//		return obj;
//	}

	@Override
	public void delete(Object entity) {
		commonDao.delete(entity);
	}

	@Override
	public void deleteBatchById(Class<?> clazz, String idName, String ids) {
		// 查询实体
		List<?> models = commonDao.queryByHql(" from " + clazz.getName()
				+ " where " + idName + " in (" + ids + ")");
		// 循环删除相应的实体
		for (Object obj : models) {
			commonDao.delete(obj);
		}
	}

	@Override
	public Object getEntityByHql(String hql) {
		return commonDao.getEntityByHql(hql);
	}

	@Override
	public Long executeSql(String sql) {
		return commonDao.executeSql(sql);
	}

	@Override
	public List<?> queryByHql(String hql) {
		return commonDao.queryByHql(hql);
	}

	@Override
	public List<?> queryBySql(String sql) {
		return commonDao.queryBySql(sql);
	}

	@Override
	public List<?> queryBySql(String sql, Class<?> c) {
		return commonDao.queryBySql(sql, c);
	}

	@Override
	public List<?> queryByHql(String hql, Integer start, Integer limit) {
		return commonDao.queryByHql(hql, start, limit);
	}

	@Override
	public Long executeHql(String hql) {
		return commonDao.executeHql(hql);
	}

	@Override
	public void executeBatchHql(String[] updateSqls) {
		for (String sql : updateSqls) {
			commonDao.executeHql(sql);
		}

	}

}
