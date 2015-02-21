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
			int pk = 1;
			while (true) {
				int count = JmpSocketServer.jmpDataVector.size();
				if (count > 0) {
					Connection conn = DriverManager.getConnection(jdbcString);
					PreparedStatement pstmt = conn
							.prepareStatement("insert into jmpData (jmpDataId, cs, date, deep, ds, eax, ebp, ebx, ecx, edi, edx, es, esi, esp, fromAddress, fromAddressDescription, fs, gs, lineNo, segmentEnd, segmentStart, ss, toAddress, toAddressDescription, fromAddress_DW_AT_name, toAddress_DW_AT_name, showForDifferentDeep, what) values (?, ?, CURRENT_TIMESTAMP(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

					Vector<JmpData> temp = new Vector<JmpData>();
					int oldPk = pk;
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
						pstmt.addBatch();

						temp.add(jmpData);

						JmpSocketServer.statistic.noOfDBRecord++;
						if (jmpData.toAddressDescription != null) {
							JmpSocketServer.statistic.noOfRecordWithSymbol++;
						}

						JmpSocketServer.jmpDataVector.remove(jmpData);

						pk++;
					}

					pstmt.executeBatch();

					PreparedStatement pstmt2 = conn.prepareStatement("insert into parameter (parameterId, name, jmpData_jmpDataId) values (null, ?, ?);");
					for (JmpData jmpData : temp) {
						for (Parameter parameter : jmpData.parameters) {
							pstmt2.setString(1, parameter.name);
							pstmt2.setInt(2, oldPk);
							pstmt2.addBatch();
						}
						oldPk++;
					}
					pstmt2.executeBatch();

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
