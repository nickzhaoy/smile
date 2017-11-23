package cn.itcast.erp.biz.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.itcast.erp.biz.IReportBiz;
import cn.itcast.erp.dao.IReportDao;

public class ReportBiz implements IReportBiz {

	private IReportDao  reportDao;
	
	public void setReportDao(IReportDao reportDao) {
		this.reportDao = reportDao;
	}

	@Override
	public List orderReport() {
		return reportDao.orderReport();
	}

	public List orderReport(Date date1,Date date2){
		if(date1==null||date2==null){
			return reportDao.orderReport();
		}else{
			return reportDao.orderReport(date1, date2);
		}
		
	}

	/**
	 * 趋势分析
	 * @param year
	 * @return
	 */
	public List<Map<String,Object>> orderTrend(int year){
		List<Map<String,Object>> maps = new ArrayList<Map<String,Object>>();
		if(year==0){
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			year = c.get(c.YEAR);
		}
		
		
		for (int i = 1; i <= 12; i++) {
			Map<String,Object> map = new HashMap<String,Object>();
			Double money = reportDao.orderTrend(year, i);
			map.put("month", i+"月");
			map.put("money", money);
			maps.add(map);
		}
		return maps;
	}
		
		
}
