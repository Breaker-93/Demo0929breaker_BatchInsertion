package com.breaker.test;

import java.sql.SQLException;
import java.util.List;

import com.breaker.model.DataModel;

public class MainTest {
	private static List<DataModel> dataList;
	private static List<String> fileList;
	
	private static ReadFileTest readFileTest;
	private static BatchInsertTest batchInsertTest;
	
	public static void main(String[] args) {
		String absolutePath = "E://TestFile/";
		readFileTest =new ReadFileTest();
		fileList = readFileTest.scanFile(absolutePath);
		dataList =readFileTest.readFiles(fileList);
		
		try {
			batchInsertTest = new BatchInsertTest();
			int result = batchInsertTest.insertData2(dataList);
			if (result==1) {
				System.out.println("大功告成，插入结束！");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
