$(function(){
	//表格数据初始化
	$('#grid').datagrid({
		url:'report_orderReport.action',
		columns:[[
		          {field:'name',title:'商品类别',width:100},
		          {field:'y',title:'销售金额',width:100}
		          ]],
		singleSelect:true,
		//表格数据加载完毕后的事件
		onLoadSuccess:function(data){
			showPie(data.rows);  //把数据放到highchart中
		}
	});
	
	//条件查询
	$('#searchBtn').bind('click',function(){
		var formdata= $('#searchForm').serializeJSON();
//		formdata={"date1":xxxxx,"date2":cccccc}
		formdata['date2'] = formdata['date2']+" 23:59:59";  //对第二个日期加时分秒
		$('#grid').datagrid('load',formdata);	
		$("#chart").attr('src',"report_orderChart.action?date1="+formdata['date1']+"&date2="+formdata['date2']);
	});
	

});
//展示pie图
function showPie(value){
	 $('#container').highcharts({
	        chart: {
	            type: 'pie',
	            options3d: {
	                enabled: true,
	                alpha: 45,
	                beta: 0
	            }
	        },
	        title: {
	            text: '销售图表'
	        },
	        tooltip: {
	            pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                depth: 35,
	                dataLabels: {
	                    enabled: true,
	                    format: '{point.name}'
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: '销售额',
//	            [{"money":1899,"name":"调味品"},{"money":7279,"name":"水果"}]
	            data: value
	        }]
	    });
}

