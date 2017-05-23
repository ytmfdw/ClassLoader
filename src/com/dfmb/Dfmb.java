package com.dfmb;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/5/23 0023.
 */
public class Dfmb {

	public static boolean isReady() {
		// URL dir = Dfmb.class.getResource("/CipherDongFangMeiBao.class");
		// InputStream
		// is=this.getClass().getResourceAsStream("/resource/res.txt");
		try {
			Class<?> cls = loader.loadClass("com.dfmb.DongFangMeiBao");
			ISystem instance = (ISystem) cls.newInstance();
			System.out.println("get isReady:" + instance.isReady());
			System.out.println("get isReady cls:" + instance.isReady(cls));
			System.out
					.println("get isSystemReady cls:" + instance.isSystemOk());
			return instance.isSystemOk();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	static ClassLoader loader = new ClassLoader() {
		@Override
		public Class<?> findClass(String clsName) {

			System.out.println("findClass() name = " + clsName);
			InputStream is = this.getClass().getResourceAsStream(
					"/CipherDongFangMeiBao.class");
			byte[] data = MyCipher.deCihperClass(is);
			File file = new File(clsName);
			/*
			 * File tmp = new File(file.getParent(), "CipherDeCipher.class");
			 * try { OutputStream out = new FileOutputStream(tmp);
			 * out.write(data); out.flush(); out.close(); } catch (Exception e)
			 * { e.printStackTrace(); }
			 */
			String className = "com.dfmb.DongFangMeiBao";
			// String claName = file.getName();
			System.out.println("claName=" + className);
			Class<?> clz = defineClass(className, data, 0, data.length);
			return clz;
		}
	};
}
