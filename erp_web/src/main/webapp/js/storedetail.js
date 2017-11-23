
$(function(){
	//表格数据初始化
	$('#grid').datagrid({
		url:'storedetail_listByPage.action',
		columns:[[
		  		    {field:'uuid',title:'编号',width:100},
		  		    {field:'storeuuid',title:'仓库',width:100,formatter:function(value,row,index){
		  		    	return ajax(value,'store_get.action?id=','storeuuid'+index,'t.name');
		  		    	
		  		    }},
		  		    {field:'goodsuuid',title:'商品',width:100,formatter:function(value,row,index){
		  		    	return ajax(value,'goods_get.action?id=','goodsuuid'+index,'t.name');
		  		    }},
		  		    {field:'num',title:'数量',width:100}
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