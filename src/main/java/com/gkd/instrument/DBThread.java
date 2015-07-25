package com.gkd.instrument;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.gkd.GKD;
import com.gkd.hibernate.HibernateUtil;
import com.gkd.instrument.callgraph.JmpData;
import com.gkd.instrument.callgraph.Parameter;

public class DBThread implements Runnable {
	public static Logger logger = Logger.getLogger(JmpSocketServer.class);

	public static void main(String args[]) {
		Session session = HibernateUtil.openSession();
		try {
			Class.forName("org.h2.Driver");
			String jdbcString = "jdbc:h2:" + new File(".").getAbsolutePath() + "/jmpDB;TRACE_LEVEL_SYSTEM_OUT=2";
			Connection conn = DriverManager.getConnection(jdbcString);
			PreparedStatement pstmt = conn.prepareStatement(
					"insert into jmpData (cs, date, deep, ds, eax, ebp, ebx, ecx, edi, edx, es, esi, esp, fromAddress, fromAddressDescription, fs, gs, lineNo, segmentEnd, segmentStart, ss, toAddress, toAddressDescription, fromAddress_DW_AT_name, toAddress_DW_AT_name, showForDifferentDeep, what, toAddressSymbol, stack, stackBase) values (?, CURRENT_TIMESTAMP(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			//			int pk = 1;
			//			pstmt.setLong(1, pk);
			pstmt.setLong(1, 0x12345678);
			for (int x = 2; x <= 29; x++) {
				pstmt.setLong(x, x);
			}
			pstmt.addBatch();
			//			pk++;
			//
			//			pstmt.setLong(1, pk);
			pstmt.setLong(1, 0x98765432);
			for (int x = 2; x <= 29; x++) {
				pstmt.setLong(x, x);
			}
			pstmt.addBatch();
			//			pk++;
			pstmt.executeBatch();

			PreparedStatement pstmt2 = conn
					.prepareStatement("insert into parameter (parameterId, name, type, size, location, value, jmpData_jmpDataId) values (null, ?, ?, ?, ?, ?, ?);");
			pstmt2.setString(1, "name1");
			pstmt2.setInt(2, 0);
			for (int x = 3; x <= 5; x++) {
				pstmt2.setLong(x, x);
			}
			pstmt2.setInt(6, 1);
			pstmt2.addBatch();

			pstmt2.setString(1, "name2");
			pstmt2.setInt(2, 0);
			for (int x = 3; x <= 5; x++) {
				pstmt2.setLong(x, x);
			}
			pstmt2.setInt(6, 1);
			pstmt2.addBatch();
			pstmt2.executeBatch();
			Criteria countCriteria = session.createCriteria(JmpData.class);
			countCriteria.setProjection(Projections.rowCount());
			long count = (long) countCriteria.uniqueResult();
			System.out.println("count=" + count);

			////////////////
			Criteria criteria = session.createCriteria(JmpData.class);
			criteria.setMaxResults(50);
			criteria.add(Restrictions.ge("jmpDataId", 0));
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			Iterator<JmpData> iterator = criteria.list().iterator();
			int c = 0;
			while (iterator.hasNext()) {
				JmpData d = iterator.next();
				System.out.println(d.parameters.size());
				for (Parameter p : d.parameters) {
					System.out.println("\t" + p.name);
				}
				c++;
			}
			System.out.println("c=" + c);

			////////////////

			Query query;
			String where1 = "";
			where1 += " and (fromAddressDescription like '%%'";
			where1 += " or toAddressDescription like '%%'";
			where1 += " or fromAddress_DW_AT_name like '%%'";
			where1 += " or toAddress_DW_AT_name like '%%')";
			query = session.createSQLQuery("SELECT a.* from JMPDATA as a where (toAddressSymbol!=null or toAddressSymbol!='')" + where1).addEntity(JmpData.class);

			query.setMaxResults(50);
			query.setFirstResult(0);
			//query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			Iterator<JmpData> iterator2 = query.list().iterator();
			c = 0;
			while (iterator2.hasNext()) {
				JmpData d = iterator2.next();
				System.out.println(d.parameters.size());
				for (Parameter p : d.parameters) {
					System.out.println("\t" + p.name);
				}
				c++;
			}
			System.out.println("c=" + c);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		HibernateUtil.getSessionFactory().close();
	}

	public void run2() {
		final Session session = HibernateUtil.openSession();
		try {
			Class.forName("org.h2.Driver");
			//String jdbcString = "jdbc:h2:" + new File(".").getAbsolutePath() + "/jmpDB";

			Transaction tx = session.beginTransaction();

			while (true) {
				int count = JmpSocketServer.jmpDataVector.size();
				if (count > 0) {

					for (JmpData jmpData : JmpSocketServer.jmpDataVector) {
						session.save(jmpData);
					}
					GKD.instrumentStatusLabel.setText("Jump instrumentation : " + JmpSocketServer.statistic);

					logger.info("writted to db = " + count);
					tx.commit();
				}
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@Override
	public void run() {
		try {
			Class.forName("org.h2.Driver");
			String jdbcString = "jdbc:h2:" + new File(".").getAbsolutePath() + "/jmpDB";
			int pk = 1;
			while (true) {
				int count = JmpSocketServer.jmpDataVector.size();
				if (count > 0) {
					Connection conn = DriverManager.getConnection(jdbcString);
					PreparedStatement pstmt = conn.prepareStatement(
							"insert into jmpData (jmpDataId, cs, date, deep, ds, eax, ebp, ebx, ecx, edi, edx, es, esi, esp, fromAddress, fromAddressDescription, fs, gs, lineNo, segmentEnd, segmentStart, ss, toAddress, toAddressDescription, fromAddress_DW_AT_name, toAddress_DW_AT_name, showForDifferentDeep, what, toAddressSymbol, stack, stackBase) values (?, ?, CURRENT_TIMESTAMP(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

					Vector<JmpData> temp = new Vector<JmpData>();
					int oldPk = pk;
					int noOdDBRecord = 0;
					for (JmpData jmpData : JmpSocketServer.jmpDataVector) {
						pstmt.setLong(1, pk);
						pstmt.setLong(2, jmpData.cs);
						pstmt.setLong(3, jmpData.deep);
						pstmt.setLong(4, jmpData.ds);
						pstmt.setLong(5, jmpData.eax);
						pstmt.setLong(6, jmpData.ebp);
						pstmt.setLong(7, jmpData.ebx);
						pstmt.setLong(8, jmpData.es);
						pstmt.setLong(9, jmpData.edi);
						pstmt.setLong(10, jmpData.edx);
						pstmt.setLong(11, jmpData.es);
						pstmt.setLong(12, jmpData.esi);
						pstmt.setLong(13, jmpData.esp);
						pstmt.setLong(14, jmpData.fromAddress);
						pstmt.setString(15, jmpData.fromAddressDescription);
						pstmt.setLong(16, jmpData.fs);
						pstmt.setLong(17, jmpData.gs);
						pstmt.setLong(18, jmpData.lineNo);
						pstmt.setLong(19, jmpData.segmentEnd);
						pstmt.setLong(20, jmpData.segmentStart);
						pstmt.setLong(21, jmpData.ss);
						pstmt.setLong(22, jmpData.toAddress);
						pstmt.setString(23, jmpData.toAddressDescription);
						pstmt.setObject(24, jmpData.fromAddress_DW_AT_name);
						pstmt.setObject(25, jmpData.toAddress_DW_AT_name);
						pstmt.setObject(26, jmpData.showForDifferentDeep);
						pstmt.setObject(27, jmpData.what);
						pstmt.setObject(28, jmpData.toAddressSymbol);
						pstmt.setObject(29, jmpData.stack);
						pstmt.setObject(30, jmpData.stackBase);
						pstmt.addBatch();

						temp.add(jmpData);

						noOdDBRecord++;
						if (jmpData.toAddressSymbol != null) {
							JmpSocketServer.statistic.noOfRecordWithSymbol++;
						}

						JmpSocketServer.jmpDataVector.remove(jmpData);

						pk++;
					}

					pstmt.executeBatch();

					PreparedStatement pstmt2 = conn
							.prepareStatement("insert into parameter (parameterId, name, type, size, location, value, jmpData_jmpDataId) values (null, ?, ?, ?, ?, ?, ?);");
					for (JmpData jmpData : temp) {
						for (Parameter parameter : jmpData.parameters) {
							pstmt2.setString(1, parameter.name);
							pstmt2.setString(2, parameter.type);
							pstmt2.setInt(3, parameter.size);
							pstmt2.setString(4, parameter.location);
							pstmt2.setLong(5, parameter.value);
							pstmt2.setInt(6, oldPk);
							pstmt2.addBatch();
						}
						oldPk++;
					}
					pstmt2.executeBatch();

					conn.close();
					JmpSocketServer.statistic.noOfDBRecord += noOdDBRecord;
					JmpSocketServer.statistic.noOfCachedRecord = JmpSocketServer.jmpDataVector.size();
					GKD.instrumentStatusLabel.setText("Jump instrumentation : " + JmpSocketServer.statistic);
					logger.info("writted to db = " + count);
				}
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
