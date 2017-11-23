package cn.itcast.erp.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.struts2.ServletActionContext;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IReportBiz;
import cn.itcast.erp.entity.Emp;

public class ReportAction extends BaseAction<Emp> {
	
	private IReportBiz reportBiz;
	
	private Date date1;
	
	private Date date2;
	
	
	private int year;
	
	public int getYear() {
		return year;
	}



	public void setYear(int year) {
		this.year = year;
	}



	public Date getDate1() {
		return date1;
	}



	public void setDate1(Date date1) {
		this.date1 = date1;
	}



	public Date getDate2() {
		return date2;
	}



	public void setDate2(Date date2) {
		this.date2 = date2;
	}



	public void setReportBiz(IReportBiz reportBiz) {
		this.reportBiz = reportBiz;
	}
	
	/**
	 * 销售报表趋势分析
	 */
	public void orderTrend(){
		List<Map<String, Object>> list = reportBiz.orderTrend(year);
		String jsonString = JSON.toJSONString(list);
		write(jsonString);
	}



	/**
	 * 销售报表
	 */
	public void orderReport(){
		List orderReport = reportBiz.orderReport(date1, date2);
		String jsonString = JSON.toJSONString(orderReport);
		write(jsonString);
	}
	/**
	 * 销售图表
	 */
	public void orderChart(){
		List<Map<String,Object>> list = reportBiz.orderReport(date1, date2);
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (Map map : list) {
			dataset.setValue(map.get("name")+"",Double.parseDouble(map.get("y")+""));
		}
//		title 标题, dataset 数据集, legend  图例, tooltips 是否有提示, urls 是否有页面跳转
		JFreeChart chart = ChartFactory.createPieChart("销售图表", dataset, true, false, false);
		try {
			ServletOutputStream outputStream = ServletActionContext.getResponse().getOutputStream();
			ChartUtilities.writeChartAsJPEG(outputStream, chart, 500, 400);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		dataset.setValue("水果类", 50000);
		dataset.setValue("蔬菜类", 80000);
		dataset.setValue("日用品类", 100000);
		
//		title 标题, dataset 数据集, legend  图例, tooltips 是否有提示, urls 是否有页面跳转
		JFreeChart chart = ChartFactory.createPieChart("JFRee示例", dataset, true, false, false);
		
		ChartUtilities.saveChartAsJPEG(new File("d:\\chart1.jpeg"), chart, 400, 300);
//		ChartUtilities.writeChartAsJPEG(out, chart, width, height);
		
	}

}
