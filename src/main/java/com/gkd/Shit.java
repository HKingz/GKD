package com.gkd;

import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.gkd.hibernate.HibernateUtil;
import com.gkd.instrument.callgraph.JmpData;

public class Shit {

	public static void main(String[] args) {
		Session session = HibernateUtil.openSession();
		Criteria criteria = session.createCriteria(JmpData.class);
		criteria.setMaxResults(10);
		criteria.setFirstResult(0);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		Iterator<JmpData> iterator = criteria.list().iterator();
		System.out.println(iterator.hasNext());
		while (iterator.hasNext()) {
			JmpData d = iterator.next();
			System.out.println("id=" + d.jmpDataId);
			System.out.println("size=" + d.parameters.size());
			System.out.println(d.parameters.get(0).name);
			System.out.println(d.parameters.get(1).name);
		}
		session.close();
		HibernateUtil.getSessionFactory().close();
	}

}
