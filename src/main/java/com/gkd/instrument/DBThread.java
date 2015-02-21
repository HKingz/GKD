package com.gkd.instrument;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gkd.GKD;
import com.gkd.hibernate.HibernateUtil;
import com.gkd.instrument.callgraph.JmpData;
import com.gkd.instrument.callgraph.Parameter;

public class DBThread implements Runnable {
	public static Logger logger = Logger.getLogger(JmpSocketServer.class);

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
			while (true) {
				int count = JmpSocketServer.jmpDataVector.size();
				if (count > 0) {
					Connection conn = DriverManager.getConnection(jdbcString);
					PreparedStatement pstmt = conn
							.prepareStatement("insert into jmpData (jmpDataId, cs, date, deep, ds, eax, ebp, ebx, ecx, edi, edx, es, esi, esp, fromAddress, fromAddressDescription, fs, gs, lineNo, segmentEnd, segmentStart, ss, toAddress, toAddressDescription, fromAddress_DW_AT_name, toAddress_DW_AT_name, showForDifferentDeep, what) values (null, ?, CURRENT_TIMESTAMP(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

					Vector<int[]> parametersIds = new Vector<int[]>();
					Vector<JmpData> temp = new Vector<JmpData>();
					for (JmpData jmpData : JmpSocketServer.jmpDataVector) {
						pstmt.setLong(1, jmpData.cs);
						pstmt.setLong(2, jmpData.deep);
						pstmt.setLong(3, jmpData.ds);
						pstmt.setLong(4, jmpData.eax);
						pstmt.setLong(5, jmpData.ebp);
						pstmt.setLong(6, jmpData.ebx);
						pstmt.setLong(7, jmpData.es);
						pstmt.setLong(8, jmpData.edi);
						pstmt.setLong(9, jmpData.edx);
						pstmt.setLong(10, jmpData.es);
						pstmt.setLong(11, jmpData.esi);
						pstmt.setLong(12, jmpData.esp);
						pstmt.setLong(13, jmpData.fromAddress);
						pstmt.setString(14, jmpData.fromAddressDescription);
						pstmt.setLong(15, jmpData.fs);
						pstmt.setLong(16, jmpData.gs);
						pstmt.setLong(17, jmpData.lineNo);
						pstmt.setLong(18, jmpData.segmentEnd);
						pstmt.setLong(19, jmpData.segmentStart);
						pstmt.setLong(20, jmpData.ss);
						pstmt.setLong(21, jmpData.toAddress);
						pstmt.setString(22, jmpData.toAddressDescription);
						pstmt.setObject(23, jmpData.fromAddress_DW_AT_name);
						pstmt.setObject(24, jmpData.toAddress_DW_AT_name);
						pstmt.setObject(25, jmpData.showForDifferentDeep);
						pstmt.setObject(26, jmpData.what);
						pstmt.addBatch();

						JmpSocketServer.statistic.noOfDBRecord++;
						if (jmpData.toAddressDescription != null) {
							JmpSocketServer.statistic.noOfRecordWithSymbol++;
						}
						temp.addElement(jmpData);
						JmpSocketServer.jmpDataVector.remove(jmpData);
					}

					logger.info("s1");
					ResultSet rs = pstmt.getGeneratedKeys();
					int jmpDataIds[] = pstmt.executeBatch();
					int x = 0;
					PreparedStatement pstmt2 = conn.prepareStatement("insert into parameter (parameterId, name, jmpData_jmpDataId) values (null, ?, ?);");
					for (JmpData jmpData : temp) {
						for (Parameter parameter : jmpData.parameters) {
							pstmt2.setString(1, parameter.name);
							pstmt2.setInt(2, jmpDataIds[x]);
							pstmt2.addBatch();
						}
						x++;
					}
					logger.info("s3");
					pstmt2.executeBatch();
					logger.info("s4");
					conn.close();

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
