package com.desktop.action;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.desktop.constant.TreeVeriable;
import com.desktop.model.TreeBaseEntity;
import com.desktop.model.extjs.JSONTreeNode;
import com.desktop.service.CommonService;
import com.desktop.utils.JsonBuilder;
import com.desktop.utils.ModelUtil;
import com.desktop.utils.StringUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 通用的基础Action类封装
 * 
 * @author wenyou <br />
 *         2013年11月18日 下午4:43:51
 */
public abstract class BaseAction extends ActionSupport implements
		ModelDriven<Object>, ServletResponseAware, ServletRequestAware,
		Serializable {

	private static final long serialVersionUID = -620965937227955611L;
	/** 日志输出对象 */
	private static Logger logger = Logger.getLogger(BaseAction.class);
	/** 请求对象 */
	protected HttpServletRequest request;
	/** 返回对象 */
	protected HttpServletResponse response;
	/** 项目部署的WebRoot路径 */
	public static String webrootAbsPath;
	/** 项目部署的class路径 */
	public static String absClassPath;
	/** Json工具类 */
	protected static JsonBuilder jsonBuilder;
	/** 服务层 */
	@Autowired
	protected CommonService commonService;

	/** ------------变量声明------------- */
	/** 实体全名称 */
	protected String modelName;
	/** 上传文件字段值 */
	protected String uploadFields;
	/** 表名 */
	protected String tableName;
	/** 主键名 */
	protected String pkName;
	/** 主键值 */
	protected String pkValue;
	/** 第几页 */
	protected int start = 0;
	/** 每页几条 */
	protected int limit = 30;
	/** 排序 */
	protected String sort;
	/** 查询条件 */
	protected String whereSql = "";
	/** 排序条件 */
	protected String orderSql = "";
	/** 主键值列表 */
	protected String ids;
	/** 传输字符串 */
	protected String strData;
	/** 为了json排除的字段 */
	protected String excludes; // checked

	protected String node;

	static {
		jsonBuilder = JsonBuilder.getInstance();
	}

	/** ------------开始封装通用方法------------------ */
	/**
	 * 默认的读取方法
	 */
	public void load() {
		logger.debug("默认的读取方法");
	}

	/**
	 * 默认的保存方法
	 */
	public void doSave() {

	}

	/**
	 * 默认的更新
	 */
	public void doUpdate() {

	}

	/**
	 * 默认的删除方法
	 */
	public void doRemove() {

	}

	/**
	 * 默认的表格的更新方法
	 */
	public void doUpdateList() {

	}

	/**
	 * 根据实体主键值获取实体信息
	 */
	public void getInfoById() {

	}

	/**
	 * 默认的读取树形方法
	 */
	public void getTree() {
		Object model = getModel();
		if (model != null && model instanceof TreeBaseEntity) {
			Class<?> c = model.getClass();
			// 得到根节点ID
			if (!(StringUtil.isNotEmpty(node) && node
					.equalsIgnoreCase(TreeVeriable.ROOT))) {
				node = TreeVeriable.ROOT;
			}
			//得到表名
			String entityName=c.getSimpleName();
			//得到模版类
			JSONTreeNode template=ModelUtil.getJSONTreeNodeTemplate(c);
			//递归查询出集合
			List<JSONTreeNode> lists=commonService.getTreeList(node, entityName, whereSql, template);
			//构建成树形节点对象
			JSONTreeNode root=commonService.buildJSONTreeNode(lists, node);
			if(node.equalsIgnoreCase(TreeVeriable.ROOT)){
				strData=jsonBuilder.buildList(root.getChildren(), excludes);
			}else{
				List<JSONTreeNode> alist=new ArrayList<JSONTreeNode>();
				alist.add(root);
				strData=jsonBuilder.buildList(root.getChildren(), excludes);	
			}
			toWrite(strData);
		}
	}

	protected void toWrite(String contents) {
		if (ModelUtil.isNotNull(response)) {
			response.setContentType("text/html;charset=UTF-8;");
			Writer writer = null;
			try {
				response.setCharacterEncoding("UTF-8");
				writer = response.getWriter();
				writer.write(contents);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					writer.flush();
					writer.close();
					response.flushBuffer();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 构建创建的实体信息
	 * 
	 * @param model
	 */
	// protected void buildModelCreateInfo(BaseEntity entity){
	// //登录人信息获取
	//
	// //登录人所属部门获取
	// entity.setCreateTime(DateUtil.formatDateTime(new Date()));
	//
	// }
	/**
	 * 构建修改的实体信息
	 * 
	 * @param model
	 */
	// protected void buildModelModifyInfo(BaseEntity entity){
	// //登录人信息获取
	//
	// //登录人所属部门获取
	// entity.setModifyTime(DateUtil.formatDateTime(new Date()));
	// }

	/** ------------结束封装通用方法------------------ */

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getUploadFields() {
		return uploadFields;
	}

	public void setUploadFields(String uploadFields) {
		this.uploadFields = uploadFields;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getPkName() {
		return pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	public String getPkValue() {
		return pkValue;
	}

	public void setPkValue(String pkValue) {
		this.pkValue = pkValue;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getWhereSql() {
		return whereSql;
	}

	public void setWhereSql(String whereSql) {
		this.whereSql = whereSql;
	}

	public String getOrderSql() {
		return orderSql;
	}

	public void setOrderSql(String orderSql) {
		this.orderSql = orderSql;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getStrData() {
		return strData;
	}

	public void setStrData(String strData) {
		this.strData = strData;
	}

	public String getExcludes() {
		return excludes;
	}

	public void setExcludes(String excludes) {
		this.excludes = excludes;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

}
