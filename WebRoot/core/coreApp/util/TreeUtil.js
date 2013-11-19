Ext.define("core.util.TreeUtil",{
	/**
	 *递归获取节点的信息
	 */
	eachChildNode:function(node){
		var self=this;
		var list=new Ext.util.MixedCollection();
		list.add(node.get("id"),node);
		node.eachChild(function(n){
			var tempList=self.eachChildNode(n);
			tempList.eachKey(function(key){
				list.add(key,tempList.get(key));	
			});
		});
		return list;
	}
});