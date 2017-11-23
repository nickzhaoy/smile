package cn.itcast.erp.dao.impl;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import cn.itcast.erp.dao.IStoreoperDao;
import cn.itcast.erp.entity.Storeoper;
/**
 * 仓库操作记录数据访问类
 * @author Administrator
 *
 */
public class StoreoperDao extends BaseDao<Storeoper> implements IStoreoperDao {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 构建查询条件
	 * @param dep1
	 * @param dep2
	 * @param param
	 * @return
	 */
	public DetachedCriteria getDetachedCriteria(Storeoper storeoper1,Storeoper storeoper2,Object param){
		DetachedCriteria dc=DetachedCriteria.forClass(Storeoper.class);
		if(storeoper1!=null){
			if(storeoper1.getType()!=null &&  storeoper1.getType().trim().length()>0)
			{
				dc.add(Restrictions.like("type", storeoper1.getType(), MatchMode.ANYWHERE));			
			}
			if(storeoper1.getGoodsuuid()!=null){
				dc.add(Restrictions.eq("goodsuuid", storeoper1.getGoodsuuid()));
			}
			if(storeoper1.getStoreuuid()!=null){
				dc.add(Restrictions.eq("storeuuid", storeoper1.getStoreuuid()));
			}
			if(storeoper1.getEmpuuid()!=null){
				dc.add(Restrictions.eq("empuuid", storeoper1.getEmpuuid()));
			}
		
			if(storeoper1.getOpertime()!=null){
				dc.add(Restrictions.ge("opertime", storeoper1.getOpertime()));
			}
		}		
		if(storeoper2!=null){
			if(storeoper2.getOpertime()!=null){
				String dateStr = sdf.format(storeoper2.getOpertime())+" 23:59:59";
				try {
					dc.add(Restrictions.le("opertime", sdf1.parse(dateStr)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return dc;
	}
	
	
}

