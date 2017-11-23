package cn.itcast.erp.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.itcast.erp.dao.IReportDao;

public class ReportDao extends HibernateDaoSupport implements IReportDao {

	public List orderReport() {
		String hql="select new Map( gt.name as name, sum(od.money) as y  )from "+
				" Goodstype gt,  Goods g,     Orderdetail od,     Orders o "+
				" where gt=g.goodstype and g.uuid=od.goodsuuid "+
				 " and o=od.orders and o.type='2' " +
				" group by gt.name";
		return this.getHibernateTemplate().find(hql);
	}
	
	public List orderReport(Date date1,Date date2) {
		String hql="select new Map( gt.name as name, sum(od.money) as y  )from "+
				" Goodstype gt,  Goods g,     Orderdetail od,     Orders o "+
				" where gt=g.goodstype and g.uuid=od.goodsuuid "+
				 " and o=od.orders and o.type='2' and o.createtime>= ? and o.createtime<=?"+
				" group by gt.name";
		return this.getHibernateTemplate().find(hql,date1,date2);
	}
	
	/**
	 * 销售趋势
	 * @param year
	 * @param month
	 * @return
	 */
	public Double orderTrend(int year ,int month){
		
		String hql=" select sum(totalmoney) as totalmoney  from Orders where month(createtime)=? "+
				" and year(createtime)=? ";
		List<Double> list = (List<Double>) this.getHibernateTemplate().find(hql, month,year);
		if(list.get(0)==null){
			return 0.0;
		}else{
			return list.get(0);
		}
		
	}
	
}
