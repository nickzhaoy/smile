package cn.itcast.erp.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.itcast.erp.dao.IStorealertDao;
import cn.itcast.erp.entity.Storealert;

public class StorealertDao extends HibernateDaoSupport implements IStorealertDao {

	@Override
	public List<Storealert> getStorealertList() {
		
		return (List<Storealert>) this.getHibernateTemplate().find(" from Storealert where storenum<outnum ");
	}

}
