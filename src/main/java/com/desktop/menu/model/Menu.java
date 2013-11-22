package com.desktop.menu.model;

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

@Entity
@GenericGenerator(name = "systemUUID", strategy = "uuid")
public class Menu extends TreeBaseEntity {

	@NodeType(type = TreeNodeType.ID)
	@FieldInfo(name = "主键", type = "ID")
	private String menuId;
	@NodeType(type = TreeNodeType.TEXT)
	@FieldInfo(name = "菜单名称")
	private String menuName;
	@NodeType(type = TreeNodeType.CODE)
	@FieldInfo(name = "模块编码")
	private String moduleCode;
	@NodeType(type = TreeNodeType.ICON)
	@FieldInfo(name = "图标")
	private String icon;
	@FieldInfo(name = "大图标")
	@NodeType(type = TreeNodeType.BIGICON)
	private String bigIcon;
	@NodeType(type = TreeNodeType.PARENT)
	@FieldInfo(name = "父节点")
	private Menu parent;
	private Set<Menu> children = new HashSet<Menu>();

	@Id
	@GeneratedValue(generator = "systemUUID")
	@Column(length = 50)
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getBigIcon() {
		return bigIcon;
	}

	public void setBigIcon(String bigIcon) {
		this.bigIcon = bigIcon;
	}

	@JsonIgnore
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT")
	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = { CascadeType.REMOVE })
	@LazyCollection(LazyCollectionOption.TRUE)
	public Set<Menu> getChildren() {
		return children;
	}

	public void setChildren(Set<Menu> children) {
		this.children = children;
	}

}
