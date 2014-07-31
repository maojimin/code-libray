package org.sevenstar.component.luence;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
 
import org.sevenstar.component.luence.LuencePage;
import org.sevenstar.component.luence.record.DocumentRecordIndex;
import org.sevenstar.web.context.WebContext;

public class LuceneHelper {
	static {
		if (WebContext.getRequest() != null) {
			try{
			indexDir = WebContext.getRequest().getRealPath(
					"/luenceindex");
			}catch(Throwable e){
				//pass
				indexDir = "D:/luenceindex";
			}
		} else {
			indexDir = "D:/luenceindex";
		}
	   //indexDir = PropertiesHelper.getLuencePath();
	   //indexDir = "D:/workproject/hcmis/webcontext/bbs/luenceindex";
	}

	public  static String indexDir;

	public final static String SUBJECT = "subject";

	public final static String CONTENT = "content";

	public final static String DESCRIPTION = "description";

	public final static String SECURITYVALUE = "securityvalue";

	public final static String URL = "url";

	public final static String ID = "id";

	public final static String TABLE = "table";

	public final static String TABLE_ID = "table_id";

	public final static String TYPE = "type";

	public final static String USERID = "userId";

	public final static String CREATEDATE = "createdate";

	public final static String MODIFYDATE = "modifydate";
	
	public final static String EGOV_NO = "egov_no";
	
	public final static String EGOV_DEPT = "egov_dept";
 
	public static Boolean create = new Boolean(true);

	public static IndexSearcher getIndexSearcher() {
		try {
 			return new IndexSearcher(indexDir);
		} catch (IOException e) {
 			throw new RuntimeException(e);
		}
	}

	public static String getFieldValue(Document doc, String fieldName) {
		Field field = doc.getField(fieldName);
		if (field != null) {
			return field.stringValue();
		}
		return null;
	}

	/**
	 * ????20
	 *
	 * @param queryString
	 * @param page
	 * @return
	 */
	public static List search(String queryString, int page, Sort sort,
			String securityValue, String securityType) {
		return search(queryString, page, 20, sort, securityValue, securityType);
	}
	
	public static List search(String queryString, int page ) {
		return search(queryString, page, 20, null, null);
	}

	public static List search(String queryString, int page,
			String securityValue, String securityType) {
		return search(queryString, page, 20, securityValue, securityType);
	}

	public static LuencePage searchPage(String queryString, int page,
			Sort sort, String securityValue, String securityType) {
		return searchPage(queryString, page, 20, sort, securityValue,
				securityType);
	}

	public static LuencePage searchPage(String queryString, int page,
			String securityValue, String securityType) {
		return searchPage(queryString, page, 20, securityValue, securityType);
	}

	/**
	 * ????,??,???????,?????,????????
	 *
	 * @param queryString
	 *            ?????
	 * @param type
	 *            ?????
	 * @param security
	 *            ????
	 * @param page
	 *            ??
	 * @return ?????
	 */
	public static List search(String queryString, List type, int page,
			Sort sort, String securityValue, String securityType) {
		return search(queryString, type, page, 20, sort, securityValue,
				securityType);
	}
	
	public static List search(String queryString, List type, int page) {
		return search(queryString, type, page, 20, null, null,
				null);
	}

	public static List search(String queryString, List type, int page,
			String securityValue, String securityType) {
		return search(queryString, type, page, 20, null, securityValue,
				securityType);
	}

	public static LuencePage searchPage(String queryString, List type,
			int page, Sort sort, String securityValue, String securityType) {
		return searchPage(queryString, type, page, 20, sort, securityValue,
				securityType);
	}

	public static LuencePage searchPage(String queryString, List type,
			int page, String securityValue, String securityType) {
		return searchPage(queryString, type, page, 20, null, securityValue,
				securityType);
	}

	public static LuencePage searchPage(String queryString, List type,
			LuencePage page, String securityValue, String securityType) {
		if (page == null) {
			page = new LuencePage();
			page.setPagesize(20);
			page.setPage(0);
		}
		return searchPage(queryString, type, page.getPage(),
				page.getPagesize(), null, securityValue, securityType);
	}

	/**
	 * ????
	 *
	 * @param queryString
	 *            ????
	 * @param page
	 *            ??
	 * @param pagesize
	 *            ???????????
	 * @return List ????
	 */
	public static List search(String queryString, int page, int pagesize,
			Sort sort, String securityValue, String securityType) {
		return search(queryString, null, page, pagesize, sort, securityValue,
				securityType);
	}

	public static List search(String queryString, int page, int pagesize,
			String securityValue, String securityType) {
		return search(queryString, null, page, pagesize, null, securityValue,
				securityType);
	}

	public static LuencePage searchPage(String queryString, LuencePage page,
			Sort sort, String securityValue, String securityType) {
		if (page == null) {
			page = new LuencePage();
			page.setPagesize(15);
			page.setPage(0);
		}
		return searchPage(queryString, null, page.getPage(),
				page.getPagesize(), sort, securityValue, securityType);
	}

	public static LuencePage searchPage(String queryString, int page,
			int pagesize, Sort sort, String securityValue, String securityType) {
		return searchPage(queryString, null, page, pagesize, sort,
				securityValue, securityType);
	}

	public static LuencePage searchPage(String queryString, int page,
			int pagesize, String securityValue, String securityType) {
		return searchPage(queryString, null, page, pagesize, null,
				securityValue, securityType);
	}

	public static LuencePage searchPage(String queryString, LuencePage page,
			String securityValue, String securityType) {
		if (page == null) {
			page = new LuencePage();
			page.setPagesize(15);
			page.setPage(0);
		}
		return searchPage(queryString, null, page.getPage(),
				page.getPagesize(), null, securityValue, securityType);
	}

	/**
	 * ????
	 *
	 * @param queryString
	 *            ????
	 * @param type
	 *            ??
	 * @param security
	 *            ????
	 * @param page
	 *            ??
	 * @param pagesize
	 *            ???????????
	 * @return List ????
	 */
	public static List search(String queryString, List type, int page,
			int pagesize, Sort sort, String securityValue, String securityType) {
		if (pagesize == 0) {
			throw new RuntimeException("pagesize cann't be 0");
		}
		IndexSearcher indexSearcher = getIndexSearcher();
		try {
			return queryHits(queryString, type, indexSearcher, page, pagesize,
					sort, securityValue, securityType);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			closeIndexSearcher(indexSearcher);
		}
	}

	public static LuencePage searchPage(String queryString, List type,
			int page, int pagesize, String securityValue, String securityType) {
		return searchPage(queryString, type, page, pagesize, null,
				securityValue, securityType);
	}

	public static LuencePage searchPage(String queryString, List type,
			int page, int pagesize, Sort sort, String securityValue,
			String securityType) {
		if (pagesize == 0) {
			pagesize = 20;
		}
		IndexSearcher indexSearcher = getIndexSearcher();
		try {
			return queryHitsPage(queryString, type, indexSearcher, page,
					pagesize, sort, securityValue, securityType);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			closeIndexSearcher(indexSearcher);
		}
	}

	public static LuencePage searchPage(Query query, int page, int pagesize,
			Sort sort) {
		if (pagesize == 0) {
			throw new RuntimeException("pagesize ????");
		}
		IndexSearcher indexSearcher = getIndexSearcher();
		try {
			return queryHitsPage(query, indexSearcher, page, pagesize, sort);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			closeIndexSearcher(indexSearcher);
		}
	}

	/**
	 * ????
	 *
	 * @param queryString
	 *
	 * @param type
	 *
	 * @param security
	 *
	 * @param
	 * @return List ???????????
	 */

	/**
	 * ????????????
	 *
	 * @param queryString
	 *            ????
	 * @param type
	 *            ??
	 * @param catalogIdList
	 *            ????
	 * @param security
	 *            ????
	 * @param sort
	 *            ??
	 * @param isDeleted
	 *            ????
	 * @return List ?????
	 */
	public static List search(String queryString, List type, Sort sort,
			String securityValue, String securityType) {
		IndexSearcher indexSearcher = getIndexSearcher();
		try {
			BooleanQuery query = getQuery(queryString, type, securityValue,
					securityType);
			StandardAnalyzer analyzer = new StandardAnalyzer();
			Highlighter highlighter = new Highlighter(new QueryScorer(query));
			if (sort == null) {
				sort = new Sort(CREATEDATE, true);
			}
			Hits hits = indexSearcher.search(query, sort);
			List list = new ArrayList();
			for (int i = 0; i < hits.length(); i++) {
				Document doc = hits.doc(i);
				DocumentRecordIndex record = new DocumentRecordIndex(doc);
				if (record.getContent() != null) {
					TokenStream tokenStream = analyzer.tokenStream(CONTENT,
							new StringReader(record.getContent()));
					String hight = highlighter.getBestFragment(tokenStream,
							record.getContent());
					if (hight != null) {
						record.setContent(hight);
					}
					record.setContent(record.getContent());
				}
				if (record.getSubject() != null) {
					TokenStream tokenStream = analyzer.tokenStream(SUBJECT,
							new StringReader(record.getSubject()));
					String hight = highlighter.getBestFragment(tokenStream,
							record.getSubject());
					if (hight != null) {
						record.setSubject(hight);
					}
				}
				list.add(record);
			}
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeIndexSearcher(indexSearcher);
		}
	}

	/**
	 * ????,?????????
	 *
	 * @param queryString
	 *            ????
	 * @param type
	 *            ??
	 * @param security
	 *            ????
	 * @return List ???????????
	 */
	public static int count(String queryString, List type,
			String securityValue, String securityType) {
		IndexSearcher indexSearcher = getIndexSearcher();
		try {
			BooleanQuery query = getQuery(queryString, type, securityValue,
					securityType);
			StandardAnalyzer analyzer = new StandardAnalyzer();
			// Highlighter highlighter = new Highlighter(new
			// QueryScorer(query));
			Hits hits = indexSearcher.search(query);
			return hits.length();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeIndexSearcher(indexSearcher);
		}
	}

	private static LuencePage queryHitsPage(Query query,
			IndexSearcher indexSearcher, int page, int pagesize, Sort sort)
			throws ParseException, IOException {
		LuencePage luencepage = new LuencePage();
		if (sort == null) {
			sort = new Sort(CREATEDATE, true);
		}
		Hits hits = indexSearcher.search(query, sort);
		StandardAnalyzer analyzer = new StandardAnalyzer();
		Highlighter highlighter = new Highlighter(new QueryScorer(query));
		highlighter.setTextFragmenter(new SimpleFragmenter(1000));

		int totalsize = hits.length();
		int pagetotal = totalsize / pagesize;
		if (pagetotal * pagesize < totalsize) {
			pagetotal = pagetotal + 1;
		}
		if (page > pagetotal) {
			page = pagetotal;
		}
		if (page <= 0) {
			/**
			 * ?????
			 */
			if (pagetotal == 0) {
				page = 0;
			} else {
				page = 1;
			}
		}
		int firstrownum = (page - 1) * pagesize;
		if (firstrownum < 0) {
			firstrownum = 0;
		}
		int lastrownum = firstrownum + pagesize;
		if (lastrownum >= totalsize) {
			lastrownum = totalsize;
		}
		luencepage.setTotal(totalsize);
		luencepage.setPagetotal(pagetotal);
		luencepage.setPagesize(pagesize);
		luencepage.setPage(page);
		List list = new ArrayList();
		for (int i = firstrownum; i < lastrownum; i++) {
			// ?????????
			if (lastrownum == 0) {
				break;
			}
			Document doc = hits.doc(i);
			DocumentRecordIndex record = new DocumentRecordIndex(doc);
			if (record.getContent() != null) {
				TokenStream tokenStream = analyzer.tokenStream(CONTENT,
						new StringReader(record.getContent()));
				String hight = highlighter.getBestFragment(tokenStream, record
						.getContent());
				if (hight != null) {
					record.setContent(hight);
				}
				record.setContent(record.getContent());
			}
			if (record.getSubject() != null) {
				TokenStream tokenStream = analyzer.tokenStream(SUBJECT,
						new StringReader(record.getSubject()));
				String hight = highlighter.getBestFragment(tokenStream, record
						.getSubject());
				if (hight != null) {
					record.setSubject(hight);
				}
			}
			list.add(record);
		}
		luencepage.setDataList(list);
		return luencepage;
	}

	private static LuencePage queryHitsPage(String queryString, List type,
			IndexSearcher indexSearcher, int page, int pagesize, Sort sort,
			String securityValue, String securityType) throws ParseException,
			IOException {
		LuencePage luencepage = new LuencePage();
		BooleanQuery query = getQuery(queryString, type, securityValue,
				securityType);
		if (sort == null) {
			sort = new Sort(CREATEDATE, true);
		}
		Hits hits = indexSearcher.search(query, sort);
		StandardAnalyzer analyzer = new StandardAnalyzer();
		Highlighter highlighter = new Highlighter(new QueryScorer(query));
		highlighter.setTextFragmenter(new SimpleFragmenter(1000));

		int totalsize = hits.length();
		int pagetotal = totalsize / pagesize;
		if (pagetotal * pagesize < totalsize) {
			pagetotal = pagetotal + 1;
		}
		if (page > pagetotal) {
			page = pagetotal;
		}
		if (page <= 0) {
			/**
			 * ?????
			 */
			if (pagetotal == 0) {
				page = 0;
			} else {
				page = 1;
			}
		}
		int firstrownum = (page - 1) * pagesize;
		if (firstrownum < 0) {
			firstrownum = 0;
		}
		int lastrownum = firstrownum + pagesize;
		if (lastrownum >= totalsize) {
			lastrownum = totalsize;
		}
		luencepage.setTotal(totalsize);
		luencepage.setPagetotal(pagetotal);
		luencepage.setPagesize(pagesize);
		luencepage.setPage(page);
		List list = new ArrayList();
		for (int i = firstrownum; i < lastrownum; i++) {
			// ?????????
			if (lastrownum == 0) {
				break;
			}
			Document doc = hits.doc(i);
			DocumentRecordIndex record = new DocumentRecordIndex(doc);
			if (record.getContent() != null) {
				TokenStream tokenStream = analyzer.tokenStream(CONTENT,
						new StringReader(record.getContent()));
				String hight = highlighter.getBestFragment(tokenStream, record
						.getContent());
				if (hight != null) {
					record.setContent(hight);
				}
				record.setContent(record.getContent());
			}
			if (record.getSubject() != null) {
				TokenStream tokenStream = analyzer.tokenStream(SUBJECT,
						new StringReader(record.getSubject()));
				String hight = highlighter.getBestFragment(tokenStream, record
						.getSubject());
				if (hight != null) {
					record.setSubject(hight);
				}
			}

			 
 
			list.add(record);
		}
		luencepage.setDataList(list);
		return luencepage;
	}

	private static List queryHits(String queryString, List type,
			IndexSearcher indexSearcher, int page, int pagesize, Sort sort,
			String securityValue, String securityType) throws ParseException,
			IOException {
		BooleanQuery query = getQuery(queryString, type, securityValue,
				securityType);
		if (sort == null) {
			sort = new Sort(CREATEDATE, true);
		}
		Hits hits = indexSearcher.search(query);
		StandardAnalyzer analyzer = new StandardAnalyzer();
		// Highlighter highlighter = new Highlighter(new QueryScorer(query));
		// highlighter.setTextFragmenter(new SimpleFragmenter(1000));
		int totalsize = hits.length();
		int pagetotal = totalsize / pagesize;
		if (pagetotal * pagesize < totalsize) {
			pagetotal = pagetotal + 1;
		}
		if (page > pagetotal) {
			page = pagetotal;
		}
		if (page <= 0) {
			/**
			 * ?????
			 */
			if (pagetotal == 0) {
				page = 0;
			} else {
				page = 1;
			}
		}
		int firstrownum = (page - 1) * pagesize;
		if (firstrownum < 0) {
			firstrownum = 0;
		}
		int lastrownum = firstrownum + pagesize;
		if (lastrownum >= totalsize) {
			lastrownum = totalsize;
		}
		List list = new ArrayList();
		for (int i = firstrownum; i < lastrownum; i++) {
			// ?????????
			if (lastrownum == 0) {
				break;
			}
			Document doc = hits.doc(i);
			DocumentRecordIndex record = new DocumentRecordIndex(doc);
			// if (record.getContent() != null) {
			// TokenStream tokenStream = analyzer.tokenStream(CONTENT,
			// new StringReader(record.getContent()));
			// //String hight = highlighter.getBestFragment(tokenStream, record
			// // .getContent());
			// //if (hight != null) {
			// //11.21????,?highlighter?? start
			// //record.setContent(hight);
			// // record.setContent(record.getContent());
			// //end
			// //}
			// }
			// if (record.getSubject() != null) {
			// TokenStream tokenStream = analyzer.tokenStream(SUBJECT,
			// new StringReader(record.getSubject()));
			// String hight = highlighter.getBestFragment(tokenStream, record
			// .getSubject());
			// if (hight != null) {
			// // 11.21????,?highlighter?? start
			// //record.setSubject(hight);
			// record.setSubject(record.getSubject());
			//
			// }
			// }
			list.add(record);
		}

		return list;
	}

	private static BooleanQuery getQuery(String queryString, List type,
			String securityValue, String securityType) throws ParseException {
		// ??????
		BooleanQuery totalQuery = new BooleanQuery();
		boolean hasQuery = false;
        if(type == null || type.size() == 0){
        	type = new ArrayList();
        	type.add(SUBJECT);
        	type.add(CONTENT);
        }
		// ????
		if (type != null && type.size() > 0) {
			// ???????
			BooleanQuery allTypeQuery = new BooleanQuery();
			// ????????
			for (int i = 0; i < type.size(); i++) {
				String typevalue = (String) type.get(i);
				if (!"".equals(typevalue)) {
					QueryParser  queryParser = new QueryParser(typevalue, new StandardAnalyzer());
					Query typeQuery = queryParser.parse(queryString);
					allTypeQuery.add(new BooleanClause(typeQuery,BooleanClause.Occur.SHOULD ));
					hasQuery = true;
				}
			}
			if (hasQuery) {
				totalQuery.add(new BooleanClause(allTypeQuery,BooleanClause.Occur.MUST ));
			}
		}
		if (securityValue != null && !"".equals(securityValue)
				&& securityType != null && !"".equals(securityType)) {
			QueryParser queryParser =   new QueryParser(securityType, new StandardAnalyzer());
			Query securityQuery = queryParser.parse(securityValue);
			totalQuery.add(new BooleanClause(securityQuery,BooleanClause.Occur.MUST));
		}
		return totalQuery;
	}

	public static void closeIndexSearcher(IndexSearcher indexSearcher) {
		try {
			if (indexSearcher != null) {
				indexSearcher.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public static IndexWriter getIndexWriter() {
		try {
			File file = new File(indexDir);
			if (create.booleanValue() && file.exists() && file.isDirectory()
					&& file.listFiles().length < 3) {
				synchronized (create) {
					create = new Boolean(false);
				}
				return new IndexWriter(indexDir, new StandardAnalyzer(), true,IndexWriter.MaxFieldLength.UNLIMITED);
			} else {
				return new IndexWriter(indexDir, new StandardAnalyzer(), false,IndexWriter.MaxFieldLength.UNLIMITED);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void closeIndexWriter(IndexWriter indexWriter) {
		try {
			if (indexWriter != null) {
				indexWriter.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public static void closeIndexReader(IndexReader indexReader) {
		try {
			if (indexReader != null) {
				indexReader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public static IndexReader getIndexReader() {
		try {
			return IndexReader.open(indexDir);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public static void delete(String table_id) {
		delete(TABLE_ID, table_id);
	}

	/**
	 * ??????????
	 *
	 * @param name
	 * @param value
	 */
	public static void delete(String name, String value) {
		IndexWriter indexWriter = null;
		try {
			//indexReader = getIndexReader();
			indexWriter = getIndexWriter();
			QueryParser  queryParser = new QueryParser(name, new StandardAnalyzer());
			Query typeQuery = null;
			try {
				typeQuery = queryParser.parse(value);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		 //	Hits hits = getIndexSearcher().search(typeQuery);
		// 	int length = hits.length();
		// 	indexWriter.deleteDocuments(typeQuery);
			indexWriter.deleteDocuments(new Term(name, value));
			//indexReader.delete(new Term(name, value));
			File file = new File(indexDir);
			if (create.booleanValue() && file.exists() && file.isDirectory()
					&& file.listFiles().length < 3) {
				synchronized (create) {
					create = new Boolean(true);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			closeIndexWriter(indexWriter);
		}
	}

	/**
	 * optimize???????,?????????optimize??
	 */
	public static void optimize() {
		IndexWriter indexWriter = null;
		try {
			indexWriter = getIndexWriter();
			indexWriter.optimize();
		} catch (IOException e) {
 			throw new RuntimeException(e);
		} finally {
			closeIndexWriter(indexWriter);
		}
	}

}
