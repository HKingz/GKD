package com.gkd.instrument;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import com.gkd.instrument.callgraph.JmpData;

public class DBThread implements Runnable {
	//	Connection conn;
	//	Statement stat;
	//	PreparedStatement pstmt = null;
	//	private static Session session = HibernateUtil.openSession();
	public static Logger logger = Logger.getLogger(JmpSocketServer.class);

	@Override
	public void run() {
		try {
			int count = 0;
			while (true) {
				//				Transaction tx = session.beginTransaction();
				//				Iterator<JmpData> iterator = JmpSocketServer.jmpDataVector.iterator();
				Class.forName("org.h2.Driver");
				Connection conn = DriverManager.getConnection("jdbc:h2:" + new File(".").getAbsolutePath() + "/jmpDB");
				PreparedStatement pstmt = conn
						.prepareStatement("insert into jmpData (jmpDataId, cs, date, deep, ds, eax, ebp, ebx, ecx, edi, edx, es, esi, esp, fromAddress, fromAddressDescription, fs, gs, lineNo, segmentEnd, segmentStart, ss, toAddress, toAddressDescription, what) values (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

				for (JmpData jmpData : JmpSocketServer.jmpDataVector) {
					//				while (iterator.hasNext()) {
					//					JmpData jmpData = iterator.next();
					//session.save(jmpData);
					//					iterator.remove();

					pstmt.setLong(1, jmpData.cs);
					pstmt.setDate(2, new java.sql.Date(jmpData.date.getTime()));
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
					pstmt.setObject(24, jmpData.what);

					pstmt.addBatch();

					JmpSocketServer.jmpDataVector.remove(jmpData);
					count++;
					if (count % 100000 == 0) {
						logger.info("writted to db = " + count + ", " + JmpSocketServer.jmpDataVector.size());
					}
				}
				pstmt.executeBatch();
				conn.close();
				//				tx.commit();
				Thread.sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
