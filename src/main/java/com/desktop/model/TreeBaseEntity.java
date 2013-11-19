package com.desktop.model;

import javax.persistence.MappedSuperclass;

import com.desktop.annotation.NodeType;
import com.desktop.constant.TreeNodeType;

/**
 * 基础业务实体扩展，树形结构的扩展
 * 
 * @author wenyou <br />
 *         2013年11月19日 上午10:12:55
 */
@MappedSuperclass
public class TreeBaseEntity extends BaseEntity {

	@NodeType(type = TreeNodeType.LAYER)
	private Integer layer;

	@NodeType(type = TreeNodeType.NODEINFO)
	private String nodeInfo;

	@NodeType(type = TreeNodeType.LEAF)
	private String nodeType;

	@NodeType(type = TreeNodeType.NODEINFOTYPE)
	private String nodeInfoType;

	public Integer getLayer() {
		return layer;
	}

	public void setLayer(Integer layer) {
		this.layer = layer;
	}

	public String getNodeInfo() {
		return nodeInfo;
	}

	public void setNodeInfo(String nodeInfo) {
		this.nodeInfo = nodeInfo;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getNodeInfoType() {
		return nodeInfoType;
	}

	public void setNodeInfoType(String nodeInfoType) {
		this.nodeInfoType = nodeInfoType;
	}

}
