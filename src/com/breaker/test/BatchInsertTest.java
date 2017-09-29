package com.breaker.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.breaker.model.DataModel;
import com.breaker.util.DBUtil;

public class BatchInsertTest {
	private Connection connection;
	private PreparedStatement preparedStatement;
	
	public int insertData1(List<DataModel> dataList) throws ClassNotFoundException, SQLException {
		System.out.println("开始执行第一种方法的数据插入...");
		int itemNum = 1000;
		Long begin = new Date().getTime();
		String prefix = "INSERT INTO ncdc VALUES ";
		connection = new DBUtil().getDbCon();
		preparedStatement = connection.prepareStatement("");
		// ±£´æsqlºó×º
		StringBuffer suffix = new StringBuffer();
		// ÉèÖÃÊÂÎñÎª·Ç×Ô¶¯Ìá½»
		connection.setAutoCommit(false);
		int numShiwu = dataList.size() / itemNum + 1;
		int numData = 0;
		System.out.println("即将插入"+dataList.size() + "条数据！");
	
		for (int j = 1; j <= numShiwu; j++) {
	
			for (int i = numData; i < numData + itemNum; i++) {
				if (i == dataList.size()) {
					break;
				}
				
				suffix.append("('" + dataList.get(i).getSTN() + "','" + dataList.get(i).getWBAN() + "','"
						+ dataList.get(i).getYEARMODA() + "','" + dataList.get(i).getTEMP() + "','"
						+ dataList.get(i).getDEWP() + "','" + dataList.get(i).getSLP() + "','"
						+ dataList.get(i).getSTP() + "','" + dataList.get(i).getVISIB() + "','"
						+ dataList.get(i).getWDSP() + "','" + dataList.get(i).getMXSPD() + "','"
						+ dataList.get(i).getGUST() + "','" + dataList.get(i).getMAX() + "','"
						+ dataList.get(i).getMIN() + "','" + dataList.get(i).getPRCP() + "','"
						+ dataList.get(i).getSNDP() + "','" + dataList.get(i).getFRSHTT() + "'),");
			}
		
			String sql = prefix + suffix.substring(0, suffix.length() - 1);
			
			preparedStatement.addBatch(sql);
			
			preparedStatement.executeBatch();
			
			connection.commit();
			
			suffix = new StringBuffer();
			numData += itemNum;
		}

		preparedStatement.close();
		connection.close();

	
		Long end = new Date().getTime();
		
		System.out.println("除去读取文件时间，只算插入数据库的时间为 : " + (end - begin) + " ms");
		return 1;
	}
	
	public int insertData2(List<DataModel> dataList) throws ClassNotFoundException, SQLException {
		
		System.out.println("开始执行第二种方法的数据插入...");
		
		
		Long begin = new Date().getTime();

		String sql = "insert into ncdc values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	
		Connection connection = new DBUtil().getDbCon();

	
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		System.out.println("即将插入"+dataList.size() + "条数据！");
		for (int i = 0; i < dataList.size(); i++) {
			preparedStatement.clearParameters();
			preparedStatement.setString(1, dataList.get(i).getSTN());
			preparedStatement.setString(2, dataList.get(i).getWBAN());
			preparedStatement.setString(3, dataList.get(i).getYEARMODA());
			preparedStatement.setString(4, dataList.get(i).getTEMP());
			preparedStatement.setString(5, dataList.get(i).getDEWP());
			preparedStatement.setString(6, dataList.get(i).getSLP());
			preparedStatement.setString(7, dataList.get(i).getSTP());
			preparedStatement.setString(8, dataList.get(i).getVISIB());
			preparedStatement.setString(9, dataList.get(i).getWDSP());
			preparedStatement.setString(10, dataList.get(i).getMXSPD());
			preparedStatement.setString(11, dataList.get(i).getGUST());
			preparedStatement.setString(12, dataList.get(i).getMAX());
			preparedStatement.setString(13, dataList.get(i).getMIN());
			preparedStatement.setString(14, dataList.get(i).getPRCP());
			preparedStatement.setString(15, dataList.get(i).getSNDP());
			preparedStatement.setString(16, dataList.get(i).getFRSHTT());
			preparedStatement.execute();
		}

	
		preparedStatement.close();

		connection.close();
		

		Long end = new Date().getTime();

		System.out.println("除去读取文件时间，只算插入数据库的时间为 : " + (end - begin) + " ms");

		return 1;

	}
}
