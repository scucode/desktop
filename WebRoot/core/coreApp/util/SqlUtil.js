Ext.define("core.util.SqlUtil",{
	/**
	 * 构建执行的Sql字符串
	 * @param {} updateArray
	 * @param {} modelName
	 * @param {} pkName
	 */
	getUpdateSql:function(updateArray,modelName,pkName){
		var datas=new Array();
		Ext.each(updateArray,function(obj){
			var pkValue="";
			var setArray=new Array();
			var fields=new Array();
			for(var f in obj){
				var value=Ext.value(obj[f],'');
				value=value.replace("'","''");//因为sql语句执行的'有特殊意义，前面加一个单引号标识转义
				if(f==pkName){
					pkValue=obj[pkName];					
				}else{
					fields.push(f);
					if(typeof(value)=="string"){
						setArray.push(f+"='"+value+"'");
					}else if(typeof(value)=="int" || typeof(value)=="float"){
						setArray.push(f+"="+value);
					}else{
						setArray.push(f+"='"+value+"'");
					}
				}
			}
			datas.push("{pkValue:'"+pkValue+"',fields:'"+fields.join(",")+"',sql:\"update "+modelName+" set "+setArray+" where "+pkName+"='"+pkValue+"'\"}")
		});
		return "["+datas.join(",")+"]";
	}
});