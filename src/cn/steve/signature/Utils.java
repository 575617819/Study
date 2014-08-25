package cn.steve.signature;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.os.Environment;

public class Utils {
	/**
	 * ���ܣ���ǩ������bitmap���浽sd��
	 * 
	 * @param bitmap���������ͼƬ
	 */
	public static void storeInSD(Bitmap bitmap) {
		
		String SDCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date curDate = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
		String currentSystemTime = formatter.format(curDate);
		
		File file = new File(SDCardRoot+"/ǩ��");// Ҫ������ļ����ļ��е�ַ
		
		if (!file.exists()) {
			file.mkdir();
		}
		
		//�ļ�������·��
		File imageFile = new File(file, currentSystemTime + ".png");
		
		//�ļ���д�����ļ��Զ�����������ʽ���浽ָ����·��֮��
		try {
			imageFile.createNewFile();
			
			System.out.println(imageFile);
			
			FileOutputStream fos = new FileOutputStream(imageFile);
			
			if (fos==null) {
				System.out.println("wwwwww");
			}
			if (bitmap==null) {
				System.out.println("kkkkkkk");
			}
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
