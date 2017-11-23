var isEditingIndex =-1;  //记录的是表格中当前编辑状态行的索引号
$(function(){
  $("#supplier").combogrid({
	  url:'supplier_list.action?t1.type='+Request['type'],
	  columns:[[
			  {field:'uuid',title:'编号',width:100},
			  {field:'name',title:'名称',width:100},
			  {field:'address',title:'地址',width:100},
			  {field:'contact',title:'联系人',width:100},
			  {field:'tele',title:'电话',width:100},
			  {field:'email',title:'EMAIL',width:100}
	            ]],
	            
	  textField:'name',
	  idField:'uuid',
	  panelWidth:650,
	  mode:'remote'
  })
	
	
	
	$("#addGrid").datagrid({
		fitColumns:true,
		columns:[[
				  {field:'goodsuuid',title:'商品编号',width:100,editor:{type:'numberbox',options:{disabled:true}}},
				  {field:'goodsname',title:'商品名称',width:100,editor:{type:'combobox',options:{
					  url:'goods_list.action',textField:'name',valueField:'name',
						  onSelect:function(record){
							  var priceEditor = $("#addGrid").datagrid('getEditor',{index:isEditingIndex,field:'price'});
							  if(Request['type']==1){
							   $(priceEditor.target).val(record.inprice);
							  }
							  if(Request['type']==2){
								   $(priceEditor.target).val(record.outprice);
							    }
							  var goodsuuidEditor = $("#addGrid").datagrid('getEditor',{index:isEditingIndex,field:'goodsuuid'});
							  $(goodsuuidEditor.target).val(record.uuid);
							  cal();
						  } 
				  
				  }}},
				  
//				  editor 列属性 ，要对某列上放一个编辑框  type：要放的编辑框类型  options：编辑框的属性
				  {field:'price',title:'价格',width:100,editor:{type:'numberbox',options:{min:0,precision:2,disabled:true}}},
				  {field:'num',title:'数量',width:100,editor:{type:'numberbox',options:{min:0}}},
				  {field:'money',title:'金额',width:100,editor:{type:'numberbox',options:{min:0,precision:2,disabled:true}}},
				  {field:'-',title:'操作',width:200,formatter:function(value,row,index)
				    	{
				    		return "<a href='#' onclick='dele("+index+")'>删除</a>";
				    	}}
				     ]],
				 singleSelect:true,
				 toolbar:[
				          {
				        	iconCls:'icon-add',
				        	text:'添加',
				        	handler:function(){
				        		$("#addGrid").datagrid('endEdit',isEditingIndex);  //结束编辑行 方法名  ：endEdit ，参数：行索引号
				        		$("#addGrid").datagrid('appendRow',{'num':0,'money':'0.00'}); //动态添加一行数据，把 num和money列初始化值
				        		isEditingIndex = $("#addGrid").datagrid("getRows").length-1;  // $("#addGrid").datagrid("getRows").length   grid表格中的数据量 
				        		$("#addGrid").datagrid('beginEdit',isEditingIndex);//开启编辑行 方法名  ：beginEdit ，参数：行索引号
				        		bindEvent();
				        	}
				          }
				          ],
				onClickRow:function(rowIndex,rowData){  //datagrid单击行事件 带两个参数  行索引  行数据
					$("#addGrid").datagrid('endEdit',isEditingIndex);
					$("#addGrid").datagrid('beginEdit',rowIndex);
					isEditingIndex = rowIndex;  // isEditingIndex这个变量要始终保存的是当前编辑行的索引号
					bindEvent();
				}
			});
  
  $("#saveBtn").bind('click',function(){
	  
	  $("#addGrid").datagrid('endEdit',isEditingIndex);
	  var formData=$("#editForm").serializeJSON();
//	  formData 格式 {"t.supplieruuid":ID值}
	  
	  var orderdetailsJSON = JSON.stringify($("#addGrid").datagrid('getRows'));
	  
//	  alert(orderdetailsJSON);
	  
	  formData['orderdetailsJSON']=orderdetailsJSON;
//	  {"t.supplieruuid":ID值 , "orderdetailsJSON":}
//	  alert(JSON.stringify(formData));
	  
	  
	  $.ajax({
		  url:'orders_add.action?t.type='+Request['type'],
		  type:'post',
		  data:formData,
		  dataType:'json',
		  success:function(value){
			  if(value.success){
//				  1清空表格数据
//				  {total:0,rows：[]}
				  $("#addGrid").datagrid('loadData', {total:0,rows:[]});
//				  2总计数清零
				  $("#sum").html('0.00');
				  
				  $("#addWindow").window('close');
				  
				  $("#grid").datagrid('reload')
				  
			  }
			  $.messager.alert('提示',value.message);
		  }
	  })
  })
  
  
  
})

/**
 * 绑定事件
 */
function  bindEvent(){
	var priceEditor = $("#addGrid").datagrid('getEditor',{index:isEditingIndex,field:'price'});
	 $(priceEditor.target).bind('keyup',function(){
		 cal();
	 });
	var numEditor = $("#addGrid").datagrid('getEditor',{index:isEditingIndex,field:'num'});
	$(numEditor.target).bind('keyup',function(){
		cal();
	}); 
	
}

//价格*数量
function cal(){
	var priceEditor = $("#addGrid").datagrid('getEditor',{index:isEditingIndex,field:'price'});
	var price = $(priceEditor.target).val(); 
	var numEditor = $("#addGrid").datagrid('getEditor',{index:isEditingIndex,field:'num'});
	var num = $(numEditor.target).val(); 
	var moneyEditor = $("#addGrid").datagrid('getEditor',{index:isEditingIndex,field:'money'});
	 $(moneyEditor.target).val((price*num).toFixed(2));    //toFixed()  保留小数位
	 sum();
}
//动态删除行
function dele(index){
//	在获取表格数据之前把 可编辑状态那行的数量 数据赋给表格
//	var numEditor = $("#addGrid").datagrid('getEditor',{index:isEditingIndex,field:'num'});
//	var num = $(numEditor.target).val();    //  $(numEditor.target)  相当于   $("#ID值")
////	$("#addGrid").datagrid('getRows') 获取表格所有数据  格式是：[{},{}]
//	alert(JSON($("#addGrid").datagrid('getRows')));
//	$("#addGrid").datagrid('getRows')[isEditingIndex].num = num;   // 把从编辑框获取到的数量值，赋给表格中的那一行的对象

	$("#addGrid").datagrid('endEdit',isEditingIndex);
	$("#addGrid").datagrid('deleteRow',index);
	
//	重新加载数据
//	1、获取表格数据
	var gridData = $("#addGrid").datagrid('getData');  //获取表格数据 数据的格式{total:,rows:[{},{}]}
	
//	2、把刚才获取到的表格数据重新加载到datagrid里
	$("#addGrid").datagrid('loadData',gridData);  //重新加载数据  加载的数据格式是：


	total();
	
	
	
}
//合计
function sum(){
//	在获取表格数据之前把 可编辑状态那行的金额 数据赋给表格
	var moneyEditor = $("#addGrid").datagrid('getEditor',{index:isEditingIndex,field:'money'});
	var money = $(moneyEditor.target).val();    //  $(numEditor.target)  相当于   $("#ID值")
//	$("#addGrid").datagrid('getRows') 获取表格所有数据  格式是：[{},{}]
	$("#addGrid").datagrid('getRows')[isEditingIndex].money = money;   // 把从编辑框获取到的数量值，赋给表格中的那一行的对象
	
	
//	$("#addGrid").datagrid('endEdit',isEditingIndex);
	
	total();
	
}
function total(){
	var totalMoney=0;
	var rowData = $("#addGrid").datagrid('getRows') ;
	for (var i = 0; i < rowData.length; i++) {
		totalMoney+=  parseFloat( $("#addGrid").datagrid('getRows')[i].money,2);
	}
	$("#sum").html(totalMoney)
}
