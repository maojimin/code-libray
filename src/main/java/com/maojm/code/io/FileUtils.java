/**
 * 
 */
package com.maojm.code.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * @since 2014年4月26日 下午2:00:42
 */
public class FileUtils {
	private static final Log logger = LogFactory.getLog(FileUtils.class);


    public static boolean isFile(final String path) {
        final File file = new File(path);
        return file.isFile();
    }


    public static boolean isDirectory(final String path) {
        final File dir = new File(path);
        return dir.isDirectory();
    }


    public static String getFileName(final String path) {
        final File file = new File(path);
        if (!file.isFile()) {
            throw new RuntimeException("此路径表达的不是文件");
        }
        return file.getName();
    }


    public static String getParentDir(final String path) {
        final File file = new File(path);
        if (!file.isFile()) {
            throw new RuntimeException("此路径表达的不是文件");
        }
        final File parent = file.getParentFile();
        if (parent.isDirectory()) {
            return parent.getName();
        } else {
            throw new RuntimeException("父目录不是目录");
        }
    }


    public static String getGrandpaDir(final String path) {
        final File file = new File(path);
        if (file.isDirectory()) {
            throw new RuntimeException("此路径表达的不是文件");
        }
        final File parent = file.getParentFile();
        if (parent.isDirectory()) {
            final File grandpa = parent.getParentFile();
            if (grandpa.isDirectory()) {
                return grandpa.getName();
            } else {
                throw new RuntimeException("祖目录不是目录");
            }
        } else {
            throw new RuntimeException("父目录不是目录");
        }
    }


    public static String getFileContent(final String path) throws IOException {
        return getFileContent(new File(path));
    }


    public static String getFileContent(final File tFile) throws IOException,
            FileNotFoundException, UnsupportedEncodingException {
        if (!tFile.isFile()) {
            throw new IOException("不是文件");
        }
        final RandomAccessFile file = new RandomAccessFile(tFile, "r");
        final long fileSize = file.length();
        final byte[] bytes = new byte[(int) fileSize];
        long readLength = 0L;
        while (readLength < fileSize) {
            final int onceLength = file
                    .read(bytes, (int) readLength, (int) (fileSize - readLength));
            if (onceLength > 0) {
                readLength += onceLength;
            } else {
                break;
            }
        }

        IOUtils.closeQuietly(file);
        return new String(bytes, "UTF-8");
    }


    public static File createDirsIfNessary(final String path) {
        final File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }


    public static File createFileIfNessary(final String path) throws IOException {
        final File file = new File(path);
        final File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            createDirsIfNessary(parentFile.getAbsolutePath());
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (final Exception e) {
                logger.error("创建文件失败, path=" + path, e);
            }
        }
        return file;
    }


    /**
     * copy from commons-io . Compares the contents of two files to determine if
     * they are equal or not.
     * <p>
     * This method checks to see if the two files are different lengths or if
     * they point to the same file, before resorting to byte-by-byte comparison
     * of the contents.
     * <p>
     * Code origin: Avalon
     * 
     * @param file1 the first file
     * @param file2 the second file
     * @return true if the content of the files are equal or they both don't
     *         exist, false otherwise
     * @throws IOException in case of an I/O error
     * @see org.apache.commons.io.FileUtils
     */
    public static boolean contentEquals(final File file1, final File file2) throws IOException {
        final boolean file1Exists = file1.exists();
        if (file1Exists != file2.exists()) {
            return false;
        }

        if (!file1Exists) {
            // two not existing files are equal
            return true;
        }

        if (file1.isDirectory() || file2.isDirectory()) {
            // don't want to compare directory contents
            throw new IOException("Can't compare directories, only files");
        }

        if (file1.length() != file2.length()) {
            // lengths differ, cannot be equal
            return false;
        }

        if (file1.getCanonicalFile().equals(file2.getCanonicalFile())) {
            // same file
            return true;
        }

        InputStream input1 = null;
        InputStream input2 = null;
        try {
            input1 = new FileInputStream(file1);
            input2 = new FileInputStream(file2);
            return IOUtils.contentEquals(input1, input2);

        } finally {
            IOUtils.closeQuietly(input1);
            IOUtils.closeQuietly(input2);
        }
    }


    public static void copyFile(final File srcFile, final File destFile) throws IOException {
        copyFile(srcFile, destFile, true);
    }


    public static void copyFile(final File srcFile, final File destFile,
                                final boolean preserveFileDate) throws IOException {
        if (srcFile == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (destFile == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (srcFile.exists() == false) {
            throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
        }
        if (srcFile.isDirectory()) {
            throw new IOException("Source '" + srcFile + "' exists but is a directory");
        }
        if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {
            throw new IOException("Source '" + srcFile + "' and destination '" + destFile
                    + "' are the same");
        }
        if ((destFile.getParentFile() != null) && (destFile.getParentFile().exists() == false)) {
            if (destFile.getParentFile().mkdirs() == false) {
                throw new IOException("Destination '" + destFile + "' directory cannot be created");
            }
        }
        if (destFile.exists() && (destFile.canWrite() == false)) {
            throw new IOException("Destination '" + destFile + "' exists but is read-only");
        }
        doCopyFile(srcFile, destFile, preserveFileDate);
    }


    private static void doCopyFile(final File srcFile, final File destFile,
                                   final boolean preserveFileDate) throws IOException {
        if (destFile.exists() && destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' exists but is a directory");
        }

        final FileInputStream input = new FileInputStream(srcFile);
        try {
            final FileOutputStream output = new FileOutputStream(destFile);
            try {
                IOUtils.copy(input, output);
            } finally {
                IOUtils.closeQuietly(output);
            }
        } finally {
            IOUtils.closeQuietly(input);
        }

        if (srcFile.length() != destFile.length()) {
            throw new IOException("Failed to copy full contents from '" + srcFile + "' to '"
                    + destFile + "'");
        }
        if (preserveFileDate) {
            destFile.setLastModified(srcFile.lastModified());
        }
    }


    public static FileOutputStream openOutputStream(final File file, final boolean append)
            throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (file.canWrite() == false) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            final File parent = file.getParentFile();
            if (parent != null) {
                if (!parent.mkdirs() && !parent.isDirectory()) {
                    throw new IOException("Directory '" + parent + "' could not be created");
                }
            }
        }
        return new FileOutputStream(file, append);
    }


    public static void writeStringToFile(final File file, final String data,
                                         final Charset encoding, final boolean append)
            throws IOException {
        OutputStream out = null;
        try {
            out = openOutputStream(file, append);
            IOUtils.write(data, out, encoding);
            out.close(); // don't swallow close Exception if copy completes normally
        } finally {
            IOUtils.closeQuietly(out);
        }
    }


    public static void write(final File file, final CharSequence data) throws IOException {
        write(file, data, Charset.defaultCharset(), false);
    }


    /**
     * 写入文件,父目录或文件不存在时会创建.文件已存在时会被覆盖
     * 
     * @param file
     * @param data
     * @param encoding
     * @param append
     * @throws IOException
     */
    public static void write(final File file, final CharSequence data, final String encoding,
                             final boolean append) throws IOException {
        write(file, data, IOUtils.toCharset(encoding), append);
    }


    /**
     * 写入文件,父目录或文件不存在时会创建.文件已存在时会被覆盖
     * 
     * @param file
     * @param data
     * @param encoding
     * @param append
     * @throws IOException
     */
    public static void write(final File file, final CharSequence data, final Charset encoding,
                             final boolean append) throws IOException {
        final String str = data == null ? null : data.toString();
        writeStringToFile(file, str, encoding, append);
    }


    /**
     * 写入文件,父目录或文件不存在时会创建.文件已存在时会被覆盖
     * 
     * @param file
     * @param data
     * @param encoding
     * @throws IOException
     */
    public static void write(final File file, final CharSequence data, final String encoding)
            throws IOException {
        write(file, data, encoding, false);
    }

}
