package com.desktop.rbac.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.desktop.annotation.FieldInfo;
import com.desktop.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 权限管理实体类
 * 
 * @author wenyou <br />
 *         2013年11月18日 下午10:47:46
 */
@Entity
@GenericGenerator(name = "systemUUID", strategy = "uuid")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Permission extends BaseEntity {
	
	@FieldInfo(name = "主键", type = "ID")
	private String perId;
	
	@FieldInfo(name = "权限编码")
	private String perCode;// 主要存放对应的主键值
	
	@FieldInfo(name = "权限类型")
	private String perType; // 主要确定权限类型 菜单权限，按钮权限
	
	@FieldInfo(name = "权限路径")
	private String perPath;
	
	private Set<Role> roles = new HashSet<Role>();

	@Id
	@GeneratedValue(generator = "systemUUID")
	@Column(length = 50)
	public String getPerId() {
		return perId;
	}

	public void setPerId(String perId) {
		this.perId = perId;
	}

	public String getPerCode() {
		return perCode;
	}

	public void setPerCode(String perCode) {
		this.perCode = perCode;
	}

	public String getPerType() {
		return perType;
	}

	public void setPerType(String perType) {
		this.perType = perType;
	}

	public String getPerPath() {
		return perPath;
	}

	public void setPerPath(String perPath) {
		this.perPath = perPath;
	}

	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY,cascade={CascadeType.ALL})
	@JoinTable(name = "ROLE_PERM", joinColumns = {
			@JoinColumn(name = "perId") },
			inverseJoinColumns = { @JoinColumn(name = "roleId")
	})
	@LazyCollection(LazyCollectionOption.TRUE)
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
