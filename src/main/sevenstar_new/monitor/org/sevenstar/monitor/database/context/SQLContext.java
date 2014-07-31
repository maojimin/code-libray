package org.sevenstar.monitor.database.context;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SQLContext {
	private static ThreadLocal SQL = new ThreadLocal();
	private static Log LOG = LogFactory.getLog(SQLContext.class);
	private static Pattern P = Pattern.compile("\t|\r|\n");

	public static void push(String sql) {
		Matcher m = P.matcher(sql);
		sql = m.replaceAll("");
	/*	while (sql.indexOf("  ") != -1) {
			sql = sql.replaceAll("  ", " ");
		}*/
		sql = sql.trim();
		List list = (List) SQL.get();
		if (list == null) {
			list = new ArrayList();
			SQL.set(list);
		}
		list.add(sql);
		LOG.debug("push:" + sql);
	}

	public static void main(String[] args) {
		String sql = "\t\t insert into              dd\r\n";
		Matcher m = P.matcher(sql);
		sql = m.replaceAll("");
		sql = sql.trim();
 		System.out.println(sql);
	}

	public static String pop() {
		List list = (List) SQL.get();
		if (list == null || list.size() == 0) {
			return null;
		}
		String sql = (String) list.get(list.size() - 1);
		list.remove(list.size() - 1);
		LOG.debug("pop:" + sql);
		return sql;
	}

	public static List getAllSql() {
		List list = (List) SQL.get();
		if (list == null) {
			return new ArrayList();
		}
		return list;
	}

	public static void clear() {
		SQL.set(null);
	}
}
