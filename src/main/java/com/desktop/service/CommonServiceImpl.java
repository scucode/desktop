package com.desktop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.desktop.constant.NodeType;
import com.desktop.dao.CommonDao;
import com.desktop.model.extjs.JSONTreeNode;
import com.desktop.utils.EntityUtil;
import com.desktop.utils.ModelUtil;
import com.desktop.utils.StringUtil;

/**
 * Service层实现类
 * 
 * @author wenyou
 *         <p>
 *         2013年9月25日 下午5:06:20
 */
@Service("commonService")
@Transactional
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
	public Object formUpdate(Object entity) {
		String keyName = ModelUtil.getClassPkName(entity.getClass());
		String keyValue = (String) EntityUtil.getPropertyValue(entity, keyName);
		// 获取当前需要更新的实体
		Object obj = commonDao.findById(entity.getClass(), keyValue);
		obj = EntityUtil.copyNewField(obj, entity);
		obj = commonDao.update(obj);
		return obj;
	}

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

	@Override
	public JSONTreeNode buildJSONTreeNode(List<JSONTreeNode> list, String rootId) {
		JSONTreeNode root=new JSONTreeNode();
		for(JSONTreeNode node:list){
			if(!(StringUtil.isNotEmpty(node.getParent()) && !node.getId().equals(rootId))){
				root=node;
				list.remove(node);
				break;
			}
		}
		createTreeChildren(list, root);
		return root;
	}

	@Override
	public List<JSONTreeNode> getTreeList(String rootId, String tableName,
			String whereSql, JSONTreeNode template) {
		List<JSONTreeNode> chilrens=new ArrayList<JSONTreeNode>();
		StringBuffer sql=new StringBuffer("select ");
		sql.append("t."+template.getId()+",");
		sql.append("t."+template.getText()+",");
		sql.append("t."+template.getCode()+",");
		sql.append("t."+template.getNodeType()+",");
		sql.append("t."+template.getNodeInfo()+",");
		sql.append("t."+template.getNodeInfoType()+",");
		sql.append("t."+template.getParent()+",");
		sql.append("t.orderIndex ");
		if(StringUtil.isNotEmpty(template.getIcon())){
			sql.append(",t."+template.getIcon());
		}
		if(StringUtil.isNotEmpty(template.getHref())){
			sql.append(",t."+template.getHref());
		}
		sql.append(" from "+tableName+" t where 1=1");
		if(StringUtil.isNotEmpty(whereSql)){
			sql.append(whereSql);
		}
		sql.append(" start with t."+template.getId()+"='"+rootId+"' CONNECT BY t."+template.getParent()+"= PRIOR t."+template.getId()+" ");
		sql.append(" order by t."+template.getParent()+",t.orderIndex");
		List<?> alist=commonDao.queryBySql(sql.toString());
		for(int i=0;i<alist.size();i++){
			Object[] obj=(Object[]) alist.get(i);
			JSONTreeNode node=new JSONTreeNode();
			node.setId((String)obj[0]);
			node.setText((String)obj[1]);
			node.setCode((String)obj[2]);
			if(NodeType.LEAF.equalsIgnoreCase((String)obj[3])){
				node.setLeaf(true);
			}else{
				node.setLeaf(false);
			}
			node.setNodeInfo((String)obj[4]);
			node.setNodeInfoType((String)obj[5]);
			node.setParent((String)obj[6]);
			if(StringUtil.isNotEmpty((obj[7])+"")){
				node.setOrderIndex(Integer.parseInt(obj[7]+""));
			}
			if(StringUtil.isNotEmpty(template.getIcon())){
				node.setIcon((String)obj[8]);
				if(StringUtil.isNotEmpty(template.getHref())){
					node.setDisabled(Boolean.parseBoolean(obj[9].toString()));
				}
			}else{
				if(StringUtil.isNotEmpty(template.getIcon())){
					node.setDisabled(Boolean.parseBoolean(obj[8].toString()));
				}
			}
			chilrens.add(node);			
		}
		return chilrens;
	}
	
	private void createTreeChildren(List<JSONTreeNode> childrens,JSONTreeNode root){
		String parentId=root.getId();
		for(int i=0;i<childrens.size();i++){
			JSONTreeNode node=childrens.get(i);
			if(StringUtil.isNotEmpty(node.getParent()) && node.getParent().equals(parentId)){
				root.getChildren().add(node);
				createTreeChildren(childrens, node);
			}
			if(i==childrens.size()-1){
				if(root.getChildren().size()<1){
					root.setLeaf(false);
				}
				return;
			}
		}
	}

}
