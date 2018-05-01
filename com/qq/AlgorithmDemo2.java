package com.qq;

import java.io.File;

public class AlgorithmDemo2 {

	public static void main(String[] args) {

		String filePath = "/Users/chenmingqiu/algorithmdemo";
		LoopQueue<File> loopQueue = new LoopQueue<File>();
		loopQueue.push(new File(filePath));
		
		while(loopQueue.size() > 0) {
			File file = loopQueue.poll();
			System.out.println(file.getPath());
			String[] files = file.list();
			for(String fPath : files) {
				File fi = new File(file.getPath() + File.separator + fPath);
				if(fi.isDirectory()) {
					loopQueue.push(fi);
				} else {
					System.out.println(fi.getPath());
				}
			}
		}

	}
	
	//遍历文件夹下面的所有文件
	public static void TraverseFiles(String filePath) {
		File file = new File(filePath);
		if(file.isDirectory()) {
			System.out.println(file.getPath());
			String[] files = file.list();
			for(String fPath : files) {
				TraverseFiles(file.getPath() +File.separator + fPath);
			}
		} else {
			System.out.println(file.getAbsolutePath());
		}
	}
	
}
