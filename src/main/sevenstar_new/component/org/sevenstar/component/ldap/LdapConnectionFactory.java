package org.sevenstar.component.ldap;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thoughtworks.xstream.XStream;

import netscape.ldap.LDAPAttribute;
import netscape.ldap.LDAPAttributeSet;
import netscape.ldap.LDAPConnection;
import netscape.ldap.LDAPEntry;
import netscape.ldap.LDAPException;
import netscape.ldap.LDAPModification;
import netscape.ldap.util.ConnectionPool;

/**
 *
 * @author zxf
 *
 */
public class LdapConnectionFactory {
	private static Log LOG = LogFactory.getLog(LdapConnectionFactory.class);
	private static ConnectionPool POOL;
	private static LdapParam PARAM = null;
	static {
		XStream xstream = new XStream();
		xstream.alias("ldapparam", LdapParam.class);
		PARAM = (LdapParam) xstream
				.fromXML(LdapConnectionFactory.class.getClassLoader()
						.getResourceAsStream("ldap.xml"));
		try {
			POOL = new ConnectionPool(PARAM.getMinConn(), PARAM.getMaxConn(),
					PARAM.getServer(), PARAM.getPort(), PARAM.getDn(), PARAM
							.getPassword());
		} catch (LDAPException e) {
			throw new RuntimeException(e);
		}
	}

	public static LDAPConnection getConnection() {
		return POOL.getConnection();
	}

	public static boolean authenticate(String uid,  String password) {
		LDAPConnection conn = null;
		try {
			conn = getConnection();
			conn.authenticate("uid=" + uid + "," + PARAM.getOu(), password);
			return true;//
		} catch (LDAPException e) {
 			LOG.error(e);
 			e.printStackTrace();
 			System.out.println(e.errorCodeToString());
			if (e.getLDAPResultCode() == 49) {
				throw new RuntimeException("密码错误");
			} else if (e.getLDAPResultCode() == 32) {
				throw new RuntimeException("用户名错误");
			} else {
				throw new RuntimeException(e);
			}
		} finally {
			if (conn != null) {
				POOL.close(conn);
			}
		}
	}

	/**
	 * cn: first Name sn: last Name dn: "uid=" + uid + "," + ou
	 * ("zxf","ou=mat,dc=ctzj,dc=net")
	 *
	 * @param uid
	 * @param ou
	 * @param password
	 */
	public static boolean addUser(String uid,String cn,String sn ,String password) {
		LDAPAttributeSet attributeSet = new LDAPAttributeSet();
		attributeSet.add(new LDAPAttribute("cn", cn));
		attributeSet.add(new LDAPAttribute("sn", sn));
		attributeSet.add(new LDAPAttribute("objectClass", new String[] { "top",
				"person", "organizationalPerson", "inetorgperson" }));
		attributeSet.add(new LDAPAttribute("uid", uid));
		attributeSet.add(new LDAPAttribute("userPassword", password));
		LDAPEntry entry = new LDAPEntry("uid=" + uid + "," +  PARAM.getOu(), attributeSet);
		LDAPConnection conn = null;
		try {
			conn = getConnection();
			conn.add(entry);
			return true;
		} catch (LDAPException e) {
			LOG.error(e);
			if (e.getLDAPResultCode() == 68) {
				throw new RuntimeException("用户名已存在");
			}else if (e.getLDAPResultCode() == 50) {
				throw new RuntimeException("无权限添加用户");
			} else if (e.getLDAPResultCode() == 19) {
				throw new RuntimeException("密码格式不正确，必须包含数字与字母，8位以上");
			} else {
				throw new RuntimeException(e);
			}
		} finally {
			POOL.close(conn);
		}
	}

	public static boolean deleteUser(String uid) {
		LDAPConnection conn = null;
		try {
			conn = getConnection();
			conn.delete("uid=" + uid + "," +  PARAM.getOu());
			return true;
		} catch (LDAPException e) {
			LOG.error(e);
			if (e.getLDAPResultCode() == 32) {
				throw new RuntimeException("用户名错误");
			} else if (e.getLDAPResultCode() == 50) {
				throw new RuntimeException("无权限删除用户");
			} else {
				throw new RuntimeException(e);
			}
		} finally {
			POOL.close(conn);
		}
	}

	public static boolean modifyUser(String uid, String newpassword) {
		LDAPConnection conn = null;
		try {
			LDAPAttribute newpLDAP = new LDAPAttribute("userPassword",
					newpassword);
			LDAPModification mod = new LDAPModification(
					LDAPModification.REPLACE, newpLDAP);
			conn = getConnection();
			conn.modify("uid=" + uid + "," +  PARAM.getOu(), mod);
			return true;
		} catch (LDAPException e) {
			LOG.error(e);
			if (e.getLDAPResultCode() == 32) {
				throw new RuntimeException("用户名错误");
			} else if (e.getLDAPResultCode() == 50) {
				throw new RuntimeException("无权限修改用户");
			} else {
				throw new RuntimeException(e);
			}
		} finally {
			POOL.close(conn);
		}
	}

	public static void main(String[] args) throws LDAPException {
	 //	deleteUser("zxf");
	 	// addUser("1000","1000","1000","11zzzzzz");
	  	System.out.println(LdapConnectionFactory.authenticate("test2","555555555"));
		//System.out.println(LdapConnectionFactory.authenticate("zxf","z1ddd23d"));
		// modifyUser("zxff", "ou=mat,dc=ctzj,dc=net", "1234");
		// System.out.println(LdapConnectionFactory.authenticate("zxff","ou=mat,dc=ctzj,dc=net","1234"));
		//System.out.println(LdapConnectionFactory.authenticate("HZTEST10","ou=mat,dc=ctzj,dc=net","12345678"));
	}

}

class LdapParam {
	private String server;
	private int port;
	private String dn;
	private String password;
	private String ou;
	private int minConn;
	private int maxConn;

	public String getOu() {
		return ou;
	}

	public void setOu(String ou) {
		this.ou = ou;
	}

	public int getMinConn() {
		return minConn;
	}

	public void setMinConn(int minConn) {
		this.minConn = minConn;
	}

	public int getMaxConn() {
		return maxConn;
	}

	public void setMaxConn(int maxConn) {
		this.maxConn = maxConn;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
