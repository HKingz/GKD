package com.gkd.instrument;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import com.gkd.instrument.callgraph.JmpData;

public class DBThread implements Runnable {
	public static Logger logger = Logger.getLogger(JmpSocketServer.class);

	@Override
	public void run() {
		try {
			Class.forName("org.h2.Driver");
			while (true) {
				int count = JmpSocketServer.jmpDataVector.size();
				logger.debug(">>>>>>>>>>>>>>> count=" + count);
				if (count > 0) {
					Connection conn = DriverManager.getConnection("jdbc:h2:" + new File(".").getAbsolutePath() + "/jmpDB");
					PreparedStatement pstmt = conn
							.prepareStatement("insert into jmpData (jmpDataId, cs, date, deep, ds, eax, ebp, ebx, ecx, edi, edx, es, esi, esp, fromAddress, fromAddressDescription, fs, gs, lineNo, segmentEnd, segmentStart, ss, toAddress, toAddressDescription, what, fromAddress_DW_AT_name, toAddress_DW_AT_name, showForDifferentDeep) values (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

					for (JmpData jmpData : JmpSocketServer.jmpDataVector) {
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
						pstmt.setObject(25, jmpData.fromAddress_DW_AT_name);
						pstmt.setObject(26, jmpData.toAddress_DW_AT_name);
						pstmt.setObject(27, jmpData.showForDifferentDeep);
						pstmt.addBatch();

						JmpSocketServer.jmpDataVector.remove(jmpData);
					}

					logger.info("writted to db = " + count);
					pstmt.executeBatch();
					conn.close();
				}
				Thread.sleep(10000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
