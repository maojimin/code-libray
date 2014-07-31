package org.sevenstar.persistent.db.jdbc.conn;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

public class ConnectionManager extends Thread
{
  public List connectionList = new LinkedList();

  public void regisgerConnection(Connection conn) { synchronized (this.connectionList) {
      ConnectionDecorator cd = new ConnectionDecorator(conn);
      this.connectionList.add(cd);
    }
  }

  public void run() {
    while (true)
      try {
        Thread.sleep(10000L);
        long currenttime = System.currentTimeMillis();
        synchronized (this.connectionList) {
        	
        	if(connectionList != null){
			    for(int i=0;i<connectionList.size();i++){
			    	ConnectionDecorator cd = (ConnectionDecorator)connectionList.get(i);
			        if(cd.isClosed()){
			        	connectionList.remove(i);
			        	i--;
			        }else{
			        	if(((currenttime - cd.registtime)/1000 )>= (60 * 5)){
				    		/**
							 * 超过5分钟未关闭的就关闭连接，记录未关闭连接
							 */
				    		cd.close();
				    		connectionList.remove(i);
				    		i--;
				    	}
			        }
			    }
			}
        	
          /*if (this.connectionList != null) {
            int i = 0; continue;
            ConnectionDecorator cd = (ConnectionDecorator)this.connectionList.get(i);
            if (cd.isClosed()) {
              this.connectionList.remove(i);
              i--;
            } else {
              if ((currenttime - cd.registtime) / 1000L < 300L) {
                continue;
              }
              cd.close();
              this.connectionList.remove(i);
              i--;
            }
            i++; if (i < this.connectionList.size()) {
              continue;
            }
          }*/
        }

      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
  }
}