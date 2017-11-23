var isEndingIndex;  //入库的数据索引号
var type=1;
$(function(){
	type =Request['type'];
	
	var url='orders_listByPage.action';
	if(Request['type']==1){   //采购订单
		url+='?t1.type=1';
	}
	
	if(Request['type']==2){   //销售订单
		url+='?t1.type=2';
	}
	
	
	if(Request['type']==1&&Request['operation']=='myorders'){   //我的采购订单
		$("#addBtn").show();  //显示  
		url='orders_myListByPage?t1.type=1';
	}
	
	if(Request['type']==2&&Request['operation']=='myorders'){   //我的销售订单
		$("#addBtn").linkbutton({
			text:'销售订单录入'
		})
		$("#addBtn").show();  //显示  
		url='orders_myListByPage?t1.type=2';
	}
	
	if(Request['operation']=='check'&&Request['type']==1){  //采购订单审核
		url="orders_listByPage.action?t1.type=1&t1.state=0"
		$("#checkBtn").show();  //显示  
	}
	
	if(Request['operation']=='start'&&Request['type']==1){  //采购订单确认
		url="orders_listByPage.action?t1.type=1&t1.state=1"
		$("#startBtn").show();  //显示  
	}
	
	if(Request['operation']=='instore'&&Request['type']==1){  //采购订单入库
		url="orders_listByPage.action?t1.type=1&t1.state=2"
		$("#instoreBtn").show();  //显示  
	}
	
	if(Request['operation']=='outstore'&&Request['type']==2){  //销售订单出库
		url="orders_listByPage.action?t1.type=2&t1.state=0"
			$("#instoreBtn").linkbutton({
				text:'出库'
			})
		$("#instoreBtn").show();  //显示  
	}
	
	//表格数据初始化
	$('#grid').datagrid({
		fitColumns:true,
		url:url,
		columns:getColumns(),
		singleSelect:true,
		pagination:true,
		onDblClickRow:function(rowIndex,rowData){
			$("#orderdetailsWindow").window('open');
			
			$("#uuid").html(rowData.uuid);
			$("#sn").html(rowData.sn);
			
//			$("#supplier"+rowIndex).html()  直接从datagrid中的span中取值
			$("#supplieruuid").html( $("#supplieruuid"+rowIndex).html() );  //从span取到值赋给td
			$("#state").html(getState(rowData.state));
			$("#creater").html($("#creater"+rowIndex).html() );
			$("#checker").html($("#checker"+rowIndex).html() );
			$("#starter").html($("#starter"+rowIndex).html() );
			$("#ender").html($("#ender"+rowIndex).html() );
			$("#createtime").html(new Date(rowData.createtime).Format('yyyy-MM-dd'));
			$("#checktime").html(new Date(rowData.checktime).Format('yyyy-MM-dd'));
			$("#starttime").html(new Date(rowData.starttime).Format('yyyy-MM-dd'));
			$("#endtime").html(new Date(rowData.endtime).Format('yyyy-MM-dd'));
			
			$("#orderdetailsGrid").datagrid({
				fitColumns:true,
				data:rowData.orderdetails,
				columns:[[
				  		    {field:'goodsuuid',title:'商品编号',width:100},
				  		    {field:'goodsname',title:'商品名称',width:100},
				  		    {field:'price',title:'价格',width:100},
				  		    {field:'num',title:'数量',width:100},
				  		    {field:'money',title:'金额',width:100},
				  		    {field:'state',title:'状态',width:100,formatter:function(value){
				  		    	if(type==1){
				  		    		if(value==0){
					  		    		return '未入库';
					  		    	}
					  		    	if(value==1){
					  		    		return '已入库';
					  		    	}
				  		    	}if(type==2){
				  		    		if(value==0){
					  		    		return '未出库';
					  		    	}
					  		    	if(value==1){
					  		    		return '已出库';
					  		    	}
				  		    	}
				  		    	
				  		    }}
				          ]],
				   singleSelect:true,
				   onDblClickRow:function(index,row){
					   
					   isEndingIndex = index;
					   $("#orderdetailsInstoreWindow").window('open');
					   $("#goodsuuid").html(row.goodsuuid);
					   $("#goodsname").html(row.goodsname);
					   $("#num").html(row.num);
					   $("#id").val(row.uuid);  //订单项编号
				   }
			})
			
		}

	});
	
	//条件查询
	$('#btnSearch').bind('click',function(){
		var formdata= $('#searchForm').serializeJSON();
		$('#grid').datagrid('load',formdata);		
	});
	
	//查看物流信息
	$('#waybillBtn').bind('click',function(){
		
		$("#waybillWindow").window('open');
		
		$("#waybillGrid").datagrid({
			url:'orders_waybillList.action?sn='+$("#sn").html(),
			columns:[[
			          {field:'exedate',title:'日期',width:100},
			          {field:'exetime',title:'时间',width:100},
			          {field:'info',title:'信息',width:100}
			          ]],
			singleSelect:true
		})
		
		
	});
	
	

	//订单导出
	$('#exportBtn').bind('click',function(){
		var formdata= $('#searchForm').serializeJSON();
		$.download('orders_export.action?id='+$("#uuid").html(),formdata);
		
	});
	
	
	
	//弹出添加窗口
	$('#addBtn').bind('click',function(){
		if(type==2){
			$("#_supplier").html("客户");
			$("#addWindow").window({
				title:'销售订单'
			})
		}
		$("#addWindow").window('open');
		
		
	});
	
	
	//订单项入库
	$('#instoreBtn').bind('click',function(){
		var url='orderdetail_doInstore.action';
		if(type==2){
			url='orderdetail_doOutstore.action';
		}
		var formdata= $('#instoreForm').serializeJSON();  //{"storeuuid":XXX，"id":XXX}
		$.ajax({
			url:url,
			type:'post',
			data:formdata,
			dataType:'json',
			success:function(value){
				if(value.success){
//					1、关闭小窗口
					$("#orderdetailsInstoreWindow").window('close');
//					2、修改订单项的状态   
//					修改完订单状态后重新加载小表格的数据
					$("#orderdetailsGrid").datagrid('getRows')[isEndingIndex].state=1;  //所有行数据
					var gridData = $("#orderdetailsGrid").datagrid('getData');
					$("#orderdetailsGrid").datagrid('loadData',gridData);
//					3、循环遍历所有的订单项，如果所有的订单项状态都是已入库 load大表格数据
					var flag = true;
					var rowsData = $("#orderdetailsGrid").datagrid('getRows');
					for (var i = 0; i < rowsData.length; i++) {
						if(rowsData[i].state==0){
							flag = false;   //有未入库的   不需要刷新大表格
							break;
						}
					}
					if(flag){
						$("#grid").datagrid('reload');
					}
				}
				$.messager.alert('提示',value.message);
			}
		})
	});
	
	
	
	//订单审核
	$('#checkBtn').bind('click',function(){
		$.messager.confirm('提示','确认要审核通过吗？',function(r){
			if(r){
				$.ajax({
					url:'orders_doCheck.action?id='+$("#uuid").html(),
					dataType:'json',
					success:function(value){
						if(value.success){
							$("#orderdetailsWindow").window('close');	
							$('#grid').datagrid('reload');		
						}
						$.messager.alert('提示',value.message);
					}
				})	
			}
		})
	});
	
	//订单审核
	$('#startBtn').bind('click',function(){
		$.messager.confirm('提示','确定要确认吗？',function(r){
			if(r){
				$.ajax({
					url:'orders_doStart.action?id='+$("#uuid").html(),
					dataType:'json',
					success:function(value){
						if(value.success){
							$("#orderdetailsWindow").window('close');	
							$('#grid').datagrid('reload');		
						}
						$.messager.alert('提示',value.message);
					}
				})	
			}
		})
	});
	
	
	
	
	
});

function  getState(value){
	if(type==1){
		if(value==0){
	  		return '未审核';
	  	}
	  	if(value==1){
	  		return '已审核';
	  	}
	  	if(value==2){
	  		return '已确认';
	  	}
	  	if(value==3){
	  		return '已完成';
	  	}	
	}
	if(type==2){
		if(value==0){
	  		return '未出库';
	  	}
	  	if(value==3){
	  		return '已完成';
	  	}	
	}
	
}
function getColumns(){
	if(type==1){
		return [[
		  		    {field:'uuid',title:'编号',width:100},
		  		    {field:'createtime',title:'生成日期',width:100,formatter:function(value){
		  		    	return new Date(value).Format('yyyy-MM-dd');
		  		    }},
		  		    {field:'checktime',title:'检查日期',width:100,formatter:function(value){
		  		    	return new Date(value).Format('yyyy-MM-dd');
		  		    }},
		  		    {field:'starttime',title:'开始日期',width:100,formatter:function(value){
		  		    	return new Date(value).Format('yyyy-MM-dd');
		  		    }},
		  		    {field:'endtime',title:'结束日期',width:100,formatter:function(value){
		  		    	return new Date(value).Format('yyyy-MM-dd');
		  		    }},
//		  		    {field:'type',title:'订单类型',width:100},
		  		    {field:'creater',title:'下单员',width:100,formatter:function(value,row,index){
		  		    	return ajax(value,'emp_get.action?id=',"creater"+index,'t.name');
		  		    }},
		  		    {field:'checker',title:'审查员',width:100,formatter:function(value,row,index){
		  		    	return ajax(value,'emp_get.action?id=',"checker"+index,'t.name');
		  		    }},
		  		    {field:'starter',title:'采购员',width:100,formatter:function(value,row,index){
		  		    	return ajax(value,'emp_get.action?id=',"starter"+index,'t.name');
		  		    }},
		  		    {field:'ender',title:'库管员',width:100,formatter:function(value,row,index){
		  		    	return ajax(value,'emp_get.action?id=',"ender"+index,'t.name');
		  		    }},
		  		    {field:'supplieruuid',title:'供应商',width:100,formatter:function(value,row,index){
		  		    	return ajax(value,'supplier_get.action?id=',"supplieruuid"+index,'t.name');
		  		    }},
		  		    {field:'totalmoney',title:'总金额',width:100},
		  		    {field:'state',title:'订单状态',width:100,formatter:function(value){
		  		    	return getState(value);
		  		    }}
				          ]]
		
	}
	if(type==2){
		return [[
		 {field:'uuid',title:'编号',width:100},
		    {field:'createtime',title:'生成日期',width:100,formatter:function(value){
		    	return new Date(value).Format('yyyy-MM-dd');
		    }},
		    {field:'endtime',title:'结束日期',width:100,formatter:function(value){
		    	return new Date(value).Format('yyyy-MM-dd');
		    }},
//		    {field:'type',title:'订单类型',width:100},
		    {field:'creater',title:'下单员',width:100,formatter:function(value,row,index){
		    	return ajax(value,'emp_get.action?id=',"creater"+index,'t.name');
		    }},
		    {field:'ender',title:'库管员',width:100,formatter:function(value,row,index){
		    	return ajax(value,'emp_get.action?id=',"ender"+index,'t.name');
		    }},
		    {field:'supplieruuid',title:'客户',width:100,formatter:function(value,row,index){
		    	return ajax(value,'supplier_get.action?id=',"supplieruuid"+index,'t.name');
		    }},
		    {field:'totalmoney',title:'总金额',width:100},
		    {field:'state',title:'订单状态',width:100,formatter:function(value){
		    	return getState(value);
		    }}
		          ]]
	}
	
	
}
