package cn.itcast.erp.biz;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IReportBiz {
	/**
	 * 销售报表统计
	 * @return
	 */
	public List orderReport();
	
	public List orderReport(Date date1,Date date2);
	
	/**
	 * 趋势分析
	 * @param year
	 * @return
	 */
	public List<Map<String,Object>> orderTrend(int year);
}
