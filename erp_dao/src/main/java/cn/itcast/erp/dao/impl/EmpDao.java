package cn.itcast.erp.dao.impl;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import cn.itcast.erp.dao.IEmpDao;
import cn.itcast.erp.entity.Emp;
/**
 * 员工数据访问类
 * @author Administrator
 *
 */
public class EmpDao extends BaseDao<Emp> implements IEmpDao {

	/**
	 * 通过用户名和密码查找用户
	 * @param username
	 * @param pwd
	 * @return
	 */
	public Emp findByUsernameAndPwd(String username,String pwd){
		
		List<Emp> emps = (List<Emp>) this.getHibernateTemplate().find(" from Emp where username=? and pwd=?", username,pwd);
		if(emps!=null&&emps.size()>0){
			return emps.get(0);
		}else{
			return null;
		}
		
	}
	
	
	/**
	 * 构建查询条件
	 * @param dep1
	 * @param dep2
	 * @param param
	 * @return
	 */
	public DetachedCriteria getDetachedCriteria(Emp emp1,Emp emp2,Object param){
		DetachedCriteria dc=DetachedCriteria.forClass(Emp.class);
		if(emp1!=null){
			if(emp1.getUsername()!=null &&  emp1.getUsername().trim().length()>0)
			{
				dc.add(Restrictions.like("username", emp1.getUsername(), MatchMode.ANYWHERE));			
			}
			if(emp1.getPwd()!=null &&  emp1.getPwd().trim().length()>0)
			{
				dc.add(Restrictions.like("pwd", emp1.getPwd(), MatchMode.ANYWHERE));			
			}
			if(emp1.getName()!=null &&  emp1.getName().trim().length()>0)
			{
				dc.add(Restrictions.like("name", emp1.getName(), MatchMode.ANYWHERE));			
			}
			if(emp1.getEmail()!=null &&  emp1.getEmail().trim().length()>0)
			{
				dc.add(Restrictions.like("email", emp1.getEmail(), MatchMode.ANYWHERE));			
			}
			if(emp1.getTele()!=null &&  emp1.getTele().trim().length()>0)
			{
				dc.add(Restrictions.like("tele", emp1.getTele(), MatchMode.ANYWHERE));			
			}
			if(emp1.getAddress()!=null &&  emp1.getAddress().trim().length()>0)
			{
				dc.add(Restrictions.like("address", emp1.getAddress(), MatchMode.ANYWHERE));			
			}
			if(emp1.getGender()!=null){
				dc.add(Restrictions.eq("gender", emp1.getGender()));
			}
			if(emp1.getBirthday()!=null){
				dc.add(Restrictions.ge("birthday", emp1.getBirthday())) ;  //ge = great  equal
			}
			if(emp1.getDep()!=null&&emp1.getDep().getUuid()!=null){
				dc.add(Restrictions.eq("dep", emp1.getDep()));    // 等同于 dc.add(Restrictions.eq("dep.uuid", emp1.getDep().getUuid()));
			}
		
		}		
		if(emp2!=null){
			if(emp2.getBirthday()!=null){
				dc.add(Restrictions.le("birthday", emp2.getBirthday())) ;  //le = less  equal
			}
		}
		return dc;
	}
	
	
}

