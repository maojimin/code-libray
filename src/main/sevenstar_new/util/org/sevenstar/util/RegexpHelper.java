package org.sevenstar.util;

import org.apache.oro.text.GlobCompiler;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Matcher;

/**
 * @author rtm 2008-5-8
 */
public final class RegexpHelper {
	private static String separator = ",";

	/**
	 * 通配符匹配
	 * 
	 * @param str
	 * @param patternstr
	 * @return
	 */
	public final static boolean isGlobmatches(String str, String patternstr) {
		if (patternstr == null || "".equals(patternstr)) {
			return false;
		}
		GlobCompiler compiler = new GlobCompiler();
		PatternMatcher matcher = new Perl5Matcher();
		if (patternstr.indexOf(separator) == -1) {
			try {
				Pattern pattern = compiler.compile(patternstr);
				return matcher.matches(str, pattern);
			} catch (MalformedPatternException e) {
				throw new RuntimeException(e);
			}
		} else {
			String[] patterns = patternstr.split(separator);
			for (int i = 0; i < patterns.length; i++) {
				try {
					Pattern pattern = compiler.compile(patterns[i]);
					boolean result = matcher.matches(str, pattern);
					if(result){
						return result;
					}
				} catch (MalformedPatternException e) {
					throw new RuntimeException(e);
				}
			}
			return false;
		}
	}

	public static void main(String[] args) {
		System.out.println(RegexpHelper.isGlobmatches("sdfdf.jar", "*.jar"));
		System.out.println(RegexpHelper.isGlobmatches("sdfdfjar", "*.jar"));
	}
}
