$(function(){
	//表格数据初始化
	$('#grid').datagrid({
		url:'report_orderTrend.action',
		columns:[[
		          {field:'month',title:'月份',width:100},
		          {field:'money',title:'销售金额',width:100}
		          ]],
		singleSelect:true,
		//表格数据加载完毕后的事件
		onLoadSuccess:function(data){
			
//			alert(JSON.stringify(data.rows));
//			showPie(data.rows);  //把数据放到highchart中
			
			
			var values = new Array();
			
			for (var i = 0; i < data.rows.length; i++) {
				values[i] = data.rows[i].money;
			}
			showLine(values);
//			[7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
		}
	});
	
	//条件查询
	$('#searchBtn').bind('click',function(){
		var formdata= $('#searchForm').serializeJSON();
		$('#grid').datagrid('load',formdata);	
	});
	

});
//展示折线图
function showLine(value){
	$('#container').highcharts({
        title: {
            text: '销售金额趋势分析',
            x: -20 //center
        },
        subtitle: {
            text: '',
            x: -20
        },
        xAxis: {
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
                'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
        },
        yAxis: {
            title: {
                text: '销售额（元）'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '元' 
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: '销售额',
            data: value
        }]
    });
}

