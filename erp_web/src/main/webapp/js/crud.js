
var method="";//保存提交的方法名称 
$(function(){
	
	if(typeof(listParam) =='undefined'){   //判断 参数是否定义
		listParam ="";
	}
	
	//表格数据初始化
	$('#grid').datagrid({
		url:name+'_listByPage.action'+listParam,
		columns:columns,
		singleSelect:true,
		pagination:true,
		toolbar: [{
			iconCls: 'icon-add',
			text:'增加',
			handler: function(){				
				$('#editWindow').window('open');
				$('#editForm').form('clear');
				method="add";
			}
		},{
			iconCls: 'icon-save',
			text:'导出',
			handler: function(){				
//				window.open("supplier_export.action")
				var formdata= $('#searchForm').serializeJSON();
				$.download("supplier_export.action?t1.type="+Request['type'],formdata);
			}
		},{
			iconCls: 'icon-tip',
			text:'导入',
			handler: function(){				
				$("#importWindow").window('open');
			}
		}]

	});
	
	//条件查询
	$('#btnSearch').bind('click',function(){
		var formdata= $('#searchForm').serializeJSON();
		$('#grid').datagrid('load',formdata);		
	});
	
	//数据导入
	$('#importBtn').bind('click',function(){
		$.ajax({
			url:'supplier_doImport.action',
			type:'post',
			data: new FormData($("#importForm")[0]),
			processData:false,
			contentType:false,
			dataType:'json',
			success:function(value){
				if(value.success){
					$("#importWindow").window('close');
					$('#grid').datagrid('reload');		
				}
				$.messager.alert("提示",value.message);
			}
		})
		
		
		
	});
	
	//保存
	$('#btnSave').bind('click',function(){
//		$('#editForm').form('validate')   判断表单中的验证框是否都已通过验证
		if(!$('#editForm').form('validate')){
			//如果返回false就是说明有的组件未验证通过
			return;
		}
		var formdata= $('#editForm').serializeJSON();	
		
		if(typeof(saveParam) =='undefined'){
			saveParam ="";
		}
		
		$.ajax({
			url:name+'_'+method+'.action'+saveParam,
			data:formdata,
			dataType:'json',
			type:'post',
			success:function(value){
				
				if(value.success){
					$('#editWindow').window('close');
					$('#grid').datagrid('reload');
				}
				$.messager.alert('提示',value.message);				
			}
			
		});
		
		
	});
	
	
});

/**
 * 删除 
 */
function dele(id){
	
	$.messager.confirm('提示','确定要删除此记录吗？',function(r){
		if(r)
		{
			$.ajax({
				url:name+'_delete.action?id='+id,
				dataType:'json',
				success:function(value){
					if(value.success){
						$('#grid').datagrid('reload');
					}
					$.messager.alert('提示',value.message);
				}
			});		
		}	
	});	
}

/**
 * 编辑
 */
function edit(id){
	
	$('#editWindow').window('open');
	$('#editForm').form('clear');
	$('#editForm').form('load',name+'_get.action?id='+id);	
	method="update";
}