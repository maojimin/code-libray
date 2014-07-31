package org.sevenstar.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 调用本地命令
 *
 * @author zxf
 *
 */
public class OSCommandHelper {
	private static Log LOG = LogFactory.getLog(OSCommandHelper.class);
	public static String[] exec(String command) {
		LOG.debug(command +" start");
		List list = new ArrayList();
 		try {
			Process process = Runtime.getRuntime().exec(command);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String tmp;
			while ((tmp = reader.readLine()) != null) {
				list.add(tmp);
			}
			reader.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		LOG.debug(command +" end");
		String[] ss = new String[list.size()];
		System.arraycopy(list.toArray(), 0, ss, 0, ss.length);
		return ss;
	}

	public static void main(String[] args) throws IOException {

		String[] ss = OSCommandHelper.exec("java");
		for(int i=0;i<ss.length;i++){
			System.out.println(ss[i]);
		}
	}
}
