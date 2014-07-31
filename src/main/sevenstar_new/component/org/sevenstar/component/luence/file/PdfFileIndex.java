package org.sevenstar.component.luence.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


import org.apache.lucene.index.IndexWriter;
import org.pdfbox.cos.COSDocument;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

/**
 * 索引pdf文件
 *
 * @author rtm
 *
 */
public class PdfFileIndex extends AbstractFileIndex {
	public void index(IndexWriter indexWriter, FileRecordIndex fileRecord)
			throws IOException {
		COSDocument cosDoc = getCosDocument(fileRecord.getFile());
		try {
			if (cosDoc.isEncrypted()) {
				/**
				 * 加密过的,解密
				 */
				// encryptionCosDoc(cosDoc);
				/**
				 * 加密过的不做处理
				 */
				return;
			}
			PDFTextStripper stripper = new PDFTextStripper();
			String pdfText = stripper.getText(new PDDocument(cosDoc));
 			indexText(indexWriter, fileRecord, pdfText);
			/*
			 * PDDocument pdDoc = new PDDocument(cosDoc); PDDocumentInformation
			 * docInfo = pdDoc.getDocumentInformation(); String author =
			 * docInfo.getAuthor(); String title = docInfo.getTitle(); String
			 * keywords = docInfo.getKeywords(); String summary =
			 * docInfo.getSubject();
			 */
		} finally {
			closeCosDoc(cosDoc);
			// closePDoc(pdDoc);
		}

	}

	/**
	 * 解密
	 *
	 * @param cosDoc
	 */
	/*
	 * private void encryptionCosDoc(COSDocument cosDoc) { DocumentEncryption
	 * decryptor = new DocumentEncryption(cosDoc); try {
	 * decryptor.decryptDocument(PASSWORD); } catch (CryptographyException e) {
	 * closeCosDoc(cosDoc); e.printStackTrace(); throw new
	 * ApplicationException(e.getMessage()); } catch (IOException e) {
	 * closeCosDoc(cosDoc); e.printStackTrace(); throw new
	 * ApplicationException(e.getMessage()); } catch (InvalidPasswordException
	 * e) { closeCosDoc(cosDoc); e.printStackTrace(); throw new
	 * ApplicationException(e.getMessage()); } }
	 */

	private COSDocument getCosDocument(File file) {
		COSDocument cosDoc = null;
		try {
			PDFParser parser = new PDFParser(new FileInputStream(file));
			parser.parse();
			cosDoc = parser.getDocument();
		} catch (IOException e) {
			e.printStackTrace();
			closeCosDoc(cosDoc);
			throw new RuntimeException(e);
		}
		return cosDoc;
	}

	private void closeCosDoc(COSDocument cosDoc) {
		if (cosDoc != null) {
			try {
				cosDoc.close();
			} catch (IOException e1) {
				e1.printStackTrace();
				throw new RuntimeException(e1);
			}
		}
	}

	private void closePDoc(PDDocument pdDoc) {
		if (pdDoc != null) {
			try {
				pdDoc.close();
			} catch (IOException e1) {
				e1.printStackTrace();
				throw new RuntimeException(e1);
			}
		}
	}


	public boolean check(FileRecordIndex file) {
		if (file.getFile().getName().toLowerCase().endsWith("pdf")) {
			return true;
		}
		return false;
	}

}
