$(function(){
	
	//表格数据初始化
	$('#grid').datagrid({
		url:'emp_listByPage.action',
		columns:[[
		  		    {field:'uuid',title:'编号',width:100},
		  		    {field:'username',title:'登陆名',width:100},
		  		    {field:'name',title:'真实姓名',width:100},
		  		    {field:'gender',title:'性别',width:100,formatter:function(value,row,index){
		  		    	if(value=='1'){
		  		    		return '男';
		  		    	}if(value==0){
		  		    		return '女';
		  		    	}
		  		    }},
		  		    {field:'email',title:'EMAIL',width:100},
		  		    {field:'tele',title:'电话',width:100},
		  		    {field:'address',title:'地址',width:100},
		  		    {field:'birthday',title:'出生年月日',width:100,formatter:function(value){
//	 	  		    	在此引用了date.js的方法
		  		    	return  new Date(value).Format('yyyy-MM-dd');
		  		    }},
		  		    {field:'dep',title:'部门',width:100,formatter:function(value){
//	 	  		    	value 在这里就是dep对象
		  		    	return value.name;
		  		    }},
//	 	  		  value  当前行所在列的值,row 当前行的值,index 当前行的索引值
				    {field:'-',title:'操作',width:200,formatter:function(value,row,index)
				    	{
				    		return "<a href='#' onclick='edit("+row.uuid+")'>重置密码</a>";
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
	
	//保存
	$('#btnSave').bind('click',function(){
//		$('#editForm').form('validate')   判断表单中的验证框是否都已通过验证
		var formdata= $('#editForm').serializeJSON();	
//		{"id":xx,"pwd":sss}
		$.ajax({
			url:'emp_updatePwd_reset.action',
			data:formdata,
			dataType:'json',
			type:'post',
			success:function(value){
				if(value.success){
					$('#editWindow').window('close');
//					$('#grid').datagrid('reload');
				}
				$.messager.alert('提示',value.message);				
			}
			
		});
		
		
	});
	
	
});


/**
 * 编辑
 */
function edit(id){
	$('#editWindow').window('open');
	$('#editForm').form('clear');
	$("#id").val(id);
}