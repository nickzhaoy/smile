package cn.itcast.erp.dao;

import java.util.Date;
import java.util.List;
/**
 * 报表
 * @author syl
 *
 */
public interface IReportDao {

	/**
	 * 销售报表统计
	 * @return
	 */
	public List orderReport();
	
	public List orderReport(Date date1,Date date2);
	
	/**
	 * 销售趋势
	 * @param year
	 * @param month
	 * @return
	 */
	public Double orderTrend(int year ,int month);
}
