package com.breaker.test;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import com.breaker.model.DataModel;

public class ReadFileTest {

	public List<DataModel> readFiles(List<String> fileList) {

		List<DataModel> dataList = new ArrayList<>();

		try {
			for (int j = 0; j < fileList.size(); j++) {
				System.out.println("正在读取第" + (j + 1) + "个文件...");
				GZIPInputStream gzipIS = new GZIPInputStream(new FileInputStream(fileList.get(j)));
				BufferedInputStream zin = new BufferedInputStream(gzipIS);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buf = new byte[1024];
				int len = 0;
				while ((len = zin.read(buf, 0, 1024)) != -1) {
					baos.write(buf, 0, len);
				}
				baos.toByteArray();
				String result = baos.toString("UTF-8");
				ArrayList<String> allData = new ArrayList<String>();
				
				//将每个文件包里的所有数据存进数据模型
				for (int i = 1; i < result.split("\n").length; i++) {
					String[] items = result.split("\n")[i].split("\\s+");
					DataModel dataModel = new DataModel();
					dataModel.setSTN(items[0]);
					dataModel.setWBAN(items[1]);
					dataModel.setYEARMODA(items[2]);
					dataModel.setTEMP(items[3]);
					dataModel.setDEWP(items[5]);
					dataModel.setSLP(items[7]);
					dataModel.setSTP(items[9]);
					dataModel.setVISIB(items[11]);
					dataModel.setWDSP(items[13]);
					dataModel.setMXSPD(items[15]);
					dataModel.setGUST(items[16]);
					dataModel.setMAX(items[17]);
					dataModel.setMIN(items[18]);
					dataModel.setPRCP(items[19]);
					dataModel.setSNDP(items[20]);
					dataModel.setFRSHTT(items[21]);
					dataList.add(dataModel);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("文件读取中出错！请检查问题。");
			e.printStackTrace();
		}
		return dataList;
		
	}

	public List<String> scanFile(String filepath) {
		List<String> fileArray = new ArrayList<>();
		File file = new File(filepath);
		if (!file.isDirectory()) {
			// filepath 不是一个目录，是一个文件直接添加到fileArray;
			fileArray.add(filepath);
		} else if (file.isDirectory()) {
			// filepath 是一个目录，继续剥离目录下一层;

			// 获取filepath该目录下的所有文件名的数组；
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File readfile = new File(filepath + "\\" + filelist[i]);
				if (!readfile.isDirectory()) {
					// filelist[i]不是一个目录，是一个文件直接添加到fileArray;
					fileArray.add(filepath + "\\" + filelist[i]);

				} else if (readfile.isDirectory()) {
					// filelist[i] 是一个目录，当前考虑简单，就不继续剥离目录下一层了;

				}
			}

		}
		return fileArray;
	}
}
