package org.sevenstar.component.luence.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.lucene.index.IndexWriter;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.HtmlPage;

/**
 * 索引html文档
 * 
 * @author rtm
 * 
 */
public class HtmlFileIndex extends AbstractFileIndex {

	public void index(IndexWriter indexWriter, FileRecordIndex fileRecord)
			throws IOException {
		try {

			String content = readTextFile(fileRecord.getFilePath(), "utf-8");

			indexText(indexWriter, fileRecord, getText(content, "utf-8"));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean check(FileRecordIndex file) {
		String fileName = file.getFile().getName().toLowerCase();
		return fileName.endsWith("htm") || fileName.endsWith("html");

	}

	// public static void main(String[] args) throws IOException
	// {
	// FileInputStream in = new FileInputStream(new File(
	// "D:/公司文档/附件三：鸿程系统对外宣传框架结构V2.doc"));
	// WordExtractor extractor = new WordExtractor(in);
	// System.out.println(extractor.getText());
	// in.close();
	// }
	public static void main(String[] args) throws Exception {
		String aFile = "E:/egov/webcontext/portal/yg/detail_tj.htm";

		String content = readTextFile(aFile, "utf-8");

		// System.out.println(test1(content));

		// test2(content);
		// System.out.println("====================================");

		System.out.println(getText(content, "utf-8"));
		// System.out.println("====================================");

		// test4(content);
		// System.out.println("====================================");

		// test5(aFile);
		// SSystem.out.println("====================================");

	}

	/**
	 * 读取文件的方式来分析内容. filePath也可以是一个Url.
	 * 
	 * @param resource
	 *            文件/Url
	 */
	public static void test5(String resource) throws Exception {
		Parser myParser = new Parser(resource);

		// 设置编码
		myParser.setEncoding("GBK");

		HtmlPage visitor = new HtmlPage(myParser);

		myParser.visitAllNodesWith(visitor);

		String textInPage = visitor.getTitle();

		System.out.println(textInPage);
	}

	/**
	 * 得到普通文本和链接的内容.
	 * 
	 * 使用了过滤条件.
	 */
	public static String getText(String content, String encode)
			throws ParserException {
		Parser myParser;
		NodeList nodeList = null;
		StringBuilder result = new StringBuilder();
		myParser = Parser.createParser(content, encode);

		NodeFilter textFilter = new NodeClassFilter(TextNode.class);
		NodeFilter linkFilter = new NodeClassFilter(LinkTag.class);

		// 暂时不处理 meta
		// NodeFilter metaFilter = new NodeClassFilter(MetaTag.class);

		OrFilter lastFilter = new OrFilter();
		lastFilter.setPredicates(new NodeFilter[] { textFilter, linkFilter });

		nodeList = myParser.parse(lastFilter);

		Node[] nodes = nodeList.toNodeArray();
		String line = "";

		for (int i = 0; i < nodes.length; i++) {
			Node anode = (Node) nodes[i];

			if (anode instanceof TextNode) {
				TextNode textnode = (TextNode) anode;
				// line = textnode.toPlainTextString().trim();
				line = textnode.getText();
			} else if (anode instanceof LinkTag) {
				LinkTag linknode = (LinkTag) anode;

				line = linknode.getLink();
				// @todo 过滤jsp标签:可以自己实现这个函数
				// line = StringFunc.replace(line, "<%.*%>", "");
			}

			if (isTrimEmpty(line))
				continue;

			// System.out.println(line);
			result.append(line);
		}

		return result.toString();
	}

	/**
	 * 读取一个文件到字符串里.
	 * 
	 * @param sFileName
	 *            文件名
	 * @param sEncode
	 *            String
	 * @return 文件内容
	 */
	public static String readTextFile(String sFileName, String sEncode) {
		StringBuffer sbStr = new StringBuffer();

		BufferedReader ins = null;
		try {
			File ff = new File(sFileName);
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					ff), sEncode);
			ins = new BufferedReader(read);
			String dataLine = "";
			while (null != (dataLine = ins.readLine())) {
				sbStr.append(dataLine);
				sbStr.append("\r\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ins != null) {
				try {
					ins.close();
				} catch (Throwable e) {
					// throw new Exception(e);
				}
			}
		}

		return sbStr.toString();
	}

	/**
	 * 去掉左右空格后字符串是否为空
	 * 
	 * @param astr
	 *            String
	 * @return boolean
	 */
	public static boolean isTrimEmpty(String astr) {
		if ((null == astr) || (astr.length() == 0)) {
			return true;
		}
		if (isBlank(astr.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 字符串是否为空:null或者长度为0.
	 * 
	 * @param astr
	 *            源字符串.
	 * @return boolean
	 */
	public static boolean isBlank(String astr) {
		if ((null == astr) || (astr.length() == 0)) {
			return true;
		} else {
			return false;
		}
	}

}
