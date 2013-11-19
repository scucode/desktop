package com.desktop.rbac.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.desktop.annotation.FieldInfo;
import com.desktop.annotation.NodeType;
import com.desktop.constant.TreeNodeType;
import com.desktop.model.TreeBaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 部门管理实体类
 * 
 * @author wenyou <br />
 *         2013年11月18日 下午10:45:09
 */
@Entity
@GenericGenerator(name = "systemUUID", strategy = "uuid")
public class Department extends TreeBaseEntity {

	@NodeType(type = TreeNodeType.ID)
	@FieldInfo(name = "主键", type = "ID")
	private String deptId;

	@NodeType(type = TreeNodeType.TEXT)
	@FieldInfo(name = "部门名称")
	private String deptName;

	@NodeType(type = TreeNodeType.CODE)
	@FieldInfo(name = "部门编码")
	private String deptCode;

	@NodeType(type = TreeNodeType.PARENT)
	private Department parent;
	private Set<Department> children = new HashSet<Department>();

	private Set<EndUser> users = new HashSet<EndUser>();

	@Id
	@GeneratedValue(generator = "systemUUID")
	@Column(length = 50)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@JsonIgnore
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT")
	public Department getParent() {
		return parent;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = { CascadeType.REMOVE })
	@LazyCollection(LazyCollectionOption.TRUE)
	public Set<Department> getChildren() {
		return children;
	}

	public void setChildren(Set<Department> children) {
		this.children = children;
	}

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "department", cascade = { CascadeType.MERGE })
	@LazyCollection(LazyCollectionOption.TRUE)
	public Set<EndUser> getUsers() {
		return users;
	}

	public void setUsers(Set<EndUser> users) {
		this.users = users;
	}

}
