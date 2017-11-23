
$(function(){
	//表格数据初始化
	$('#grid').datagrid({
		url:'storeoper_listByPage.action',
		columns:[[
		            {field:'uuid',title:'编号',width:100},
		  		    {field:'empuuid',title:'员工',width:100,formatter:function(value,row,index){
		  		    	return ajax(value,'emp_get.action?id=','empuuid'+index,'t.name');
		  		    	
		  		    }},
		  		    {field:'opertime',title:'操作日期',width:100,formatter:function(value){
		  		    	return new Date(value).Format('yyyy-MM-dd');
		  		    }},
		  		    {field:'storeuuid',title:'仓库',width:100,formatter:function(value,row,index){
		  		    	return ajax(value,'store_get.action?id=','storeuuid'+index,'t.name');
		  		    	
		  		    }},
		  		    {field:'goodsuuid',title:'商品',width:100,formatter:function(value,row,index){
		  		    	return ajax(value,'goods_get.action?id=','goodsuuid'+index,'t.name');
		  		    }},
		  		    {field:'num',title:'数量',width:100},
		  		    {field:'type',title:'类型',width:100,formatter:function(value){
		  		    	if(value==1){
		  		    		return "入库";
		  		    	}if(value==2){
		  		    		return "出库";
		  		    	}
		  		    }}
				          ]],
		singleSelect:true,
		pagination:true

	});
	
	//条件查询
	$('#btnSearch').bind('click',function(){
		var formdata= $('#searchForm').serializeJSON();
		$('#grid').datagrid('load',formdata);		
	});
});