
package org.sevenstar.component.luence;

import java.io.IOException;

/**
 * 索引记录接口
 * @author <a href="mailto:hhongq@hotmail.com">ponder</a>
 */
public interface IIndexWriter {

	/**
	 * 写索引
	 */
	abstract public void writeIndex() throws IOException;
}
