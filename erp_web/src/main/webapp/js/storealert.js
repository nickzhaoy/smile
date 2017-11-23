$(function(){
	$("#grid").datagrid({
		url:'storealert_list.action',
		columns:[[
		          {field:'uuid',title:'商品编号',width:100},
		          {field:'name',title:'商品名称',width:100},
		          {field:'storenum',title:'库存数量',width:100},
		          {field:'outnum',title:'待发货数量',width:100}
		          ]],
	   singleSelect:true		
	})
	
	
	$("#sendMailBtn").bind('click',function(){
		$.ajax({
			url:'storealert_sendMail.action',
			dataType:'json',
			success:function(value){
				$.messager.alert('提示',value.message);
			}
		})
		
		
		
		
		
	});
	
})