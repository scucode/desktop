package com.desktop.dao;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.desktop.utils.StringUtil;

/**
 * 通用dao实现类
 * 
 * @author wenyou <br />
 *         2013年11月18日 下午3:17:14
 */
@Repository
public class CommonDaoImpl implements CommonDao {

	private HibernateTemplate hibernateTemplate;

	@Autowired
	private SessionFactory sessionFactory;

	private static Logger logger = Logger.getLogger(HibernateTemplate.class);

	@Override
	public Object findById(Class<?> clazz, String id) {
		return hibernateTemplate.get(clazz, id);
	}

	@Override
	public List<?> findAll(Class<?> clazz) {
		return hibernateTemplate.find("from " + clazz.getName());
	}

	@Override
	public Integer getCount(String hql) {
		Integer c = 0;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		Object count = query.uniqueResult();
		if (null != count && StringUtil.isInteger(count.toString())) {
			c = Integer.parseInt(count.toString());
		}
		return c;
	}

	@Override
	public List<?> findByPage(final Class<?> clazz, final String whereSql,
			final int from, final int size) {
		return (List<?>) hibernateTemplate
				.execute(new HibernateCallback<Object>() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session
								.createQuery(
										"from " + clazz.getName()
												+ " where 1=1 " + whereSql)
								.setFirstResult(from).setMaxResults(size)
								.list();
					}
				});
	}

	@Override
	public Object save(Object entity) {
		hibernateTemplate.save(entity);
		logger.debug("Save entity success!");
		return entity;
	}

	@Override
	public Object update(Object entity) {
		hibernateTemplate.update(entity);
		logger.debug("Update entity success!");
		return entity;
	}

	@Override
	public void delete(Object entity) {
		hibernateTemplate.delete(entity);
		logger.debug("Delete entity success!");
	}

	@Override
	public Object getEntityByHql(String hql) {
		List<?> datas = hibernateTemplate.find(hql);
		if (datas != null && datas.size() > 0) {
			if (datas.size() == 1) {
				return datas.get(0);
			} else {
				logger.error("获取数据大于一条");
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public Long executeSql(String sql) {
		Long c = 0L;
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		Object count = query.executeUpdate();
		if (null != count && StringUtil.isInteger(count.toString())) {
			c = Long.parseLong(count.toString());
		}
		return c;
	}

	@Override
	public Long executeHql(String hql) {
		return new Long(hibernateTemplate.bulkUpdate(hql));
	}

	@Override
	public List<?> queryByHql(String hql) {
		return hibernateTemplate.find(hql);
	}

	@Override
	public List<?> queryByHql(String hql, Integer start, Integer limit) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if (limit > 0) {
			query.setFirstResult(start);
			query.setMaxResults(limit);
		}
		return query.list();
	}

	@Override
	public List<?> queryBySql(String sql) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		List<?> list = query.list();
		return list;
	}

	@Override
	public List<?> queryBySql(String sql, Class<?> c) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.addEntity(c);
		List<?> list = query.list();
		return list;
	}

	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}
