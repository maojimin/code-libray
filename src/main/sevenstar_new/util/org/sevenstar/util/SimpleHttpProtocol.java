package org.sevenstar.util;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SimpleHttpProtocol {

	private static Log LOG = LogFactory.getLog(SimpleHttpProtocol.class);

	private final static String TYPESTRING = "java.lang.String";
	private final static String TYPEINTEGER = "java.lang.Integer";
	private final static String TYPELONG = "java.lang.Long";
	private final static String TYPEDOUBLE = "java.lang.Double";
	private final static String TYPEFLOAT = "java.lang.Float";
	private final static String TYPECharacter = "java.lang.Character";
	private final static String TYPEBoolean = "java.lang.Boolean";
	private final static String TYPELIST = "java.util.List";
	private final static String TYPEMAP = "java.util.Map";
	private final static String TYPEDATE = "java.util.Date";

	// {{{<<<java.util.ListDDDIII>>>zIIIzxc }}}}
	private final static String LEFT = "AZSASW";
	private final static String RIGHT ="ZCDFRF";



	private final static String TYPELEFT = "HGHJH";
	private final static String TYPERIGTH ="OIOPO";

	private final static String TYPE_FIELD_SEQ = "HGZSW";
	private final static String FIELD_VALUE_SEQ = "BVNTR";

	public final static String ERROR = "EEERRRRRRRREEEE";

	public static String objectToString(Object ob) {
		if (ob == null) {
			return null;
		}
		if (isBasicType(ob.getClass())) {
			LOG.debug(String.valueOf(ob));
			if (Date.class.equals(ob.getClass())) {
				return LEFT + TYPELEFT + ob.getClass().getName() + TYPERIGTH
						+ dateToStringCommon((Date) ob) + RIGHT;
			} else {
				return LEFT + TYPELEFT + ob.getClass().getName() + TYPERIGTH
						+ ob + RIGHT;
			}
		}
		if (Date.class.equals(ob.getClass())) {
			return LEFT + TYPELEFT + ob.getClass().getName() + TYPERIGTH
					+ dateToStringCommon((Date) ob) + RIGHT;
		}
		if (ob instanceof StringBuffer) {
			LOG.debug(String.valueOf(ob));
			return LEFT + TYPELEFT + ob.getClass().getName() + TYPERIGTH + ob
					+ RIGHT;
		}
		if (ob instanceof List) {
			List list = (List) ob;
			if (list.size() == 0) {
				return null;
			}
			String result = null;
			String fieldSeq = getRandomString10();
			for (int i = 0; i < list.size(); i++) {
				String os = objectToString(list.get(i));
				if (os != null) {
					if (result == null) {
						result = LEFT + TYPELEFT + ob.getClass().getName()
								+ TYPE_FIELD_SEQ + fieldSeq + TYPERIGTH + os;
					} else {
						result = result + fieldSeq + os;
					}
				}
			}
			if (result != null) {
				result = result + RIGHT;
			}
			LOG.debug(result);
			return result;
		}
		if (ob instanceof Map) {
			Map map = (Map) ob;
			if (map.size() == 0) {
				return null;
			}
			String result = null;
			Iterator iter = map.keySet().iterator();
			String fieldSeq = getRandomString10();
			String fieldValueSeq = getRandomString10();
			while (iter.hasNext()) {
				Object key = iter.next();
				Object value = map.get(key);
				if (value == null) {
					continue;
				}
				String os = objectToString(value);
				if (os != null) {
					if (result == null) {
						result = LEFT + TYPELEFT + ob.getClass().getName()
								+ TYPE_FIELD_SEQ + fieldSeq + FIELD_VALUE_SEQ
								+ fieldValueSeq + TYPERIGTH + key +fieldValueSeq +  os;
					} else {
						result = result + fieldSeq + key + fieldValueSeq + os;
					}
				}
			}
			if (result != null) {
				result = result + RIGHT;
			}
			LOG.debug(result);
			return result;
		}
		// 自定义类
		Field[] fields = ob.getClass().getDeclaredFields();
		if (fields.length == 0) {
			return null;
		}
		String result = null;
		String fieldSeq = getRandomString10();
		String fieldValueSeq = getRandomString10();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			Object value = BeanHelper.getPropertyValue(field.getName(), ob);
			if (value == null) {
				continue;
			}
			String os = objectToString(value);
			if (os != null) {
				if (result == null) {
					result = LEFT + TYPELEFT + ob.getClass().getName()
							+ TYPE_FIELD_SEQ + fieldSeq + FIELD_VALUE_SEQ
							+ fieldValueSeq + TYPERIGTH + field.getName() + fieldValueSeq+ os;
				} else {
					result = result + fieldSeq + field.getName()
							+ fieldValueSeq + os;
				}
			}
		}
		if (result != null) {
			result = result + RIGHT;
		}
		LOG.debug(result);
		return result;
	}

	private static Date stringToDateCommon(String date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return df.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	private static String dateToStringCommon(Date date) {
		if (date == null)
			return null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}

	public static Object stringToObject(String str) {
		if (str == null || str.equals(ERROR)) {
			return null;
		}
		str = str.substring(LEFT.length());
		str = str.substring(0, str.length() - RIGHT.length());

		String type = str.substring(TYPELEFT.length());
		type = type.substring(0, type.indexOf(TYPERIGTH));
		String value = str.substring(str.indexOf(TYPERIGTH)
				+ TYPERIGTH.length());// ????????

		String fieldSeq = null;
		String fieldValueSeq = null;

		if (type.indexOf(TYPE_FIELD_SEQ) != -1) {
			String[] types = type.split(TYPE_FIELD_SEQ);
			type = types[0];
			if (types[1].indexOf(FIELD_VALUE_SEQ) != -1) {
				String[] typess = types[1].split(FIELD_VALUE_SEQ);
				fieldSeq = typess[0];
				fieldValueSeq = typess[1];
			} else {
				fieldSeq = types[1];
			}
		}

		if (type.equals(Date.class.getName())) {
			return stringToDateCommon(value);
		}
		if (type.equals(String.class.getName())) {
			return value;
		}
		if (type.equals(Integer.class.getName())) {
			return Integer.valueOf(value);
		}
		if (type.equals(Long.class.getName())) {
			return Long.valueOf(value);
		}
		if (type.equals(Float.class.getName())) {
			return Float.valueOf(value);
		}
		if (type.equals(Double.class.getName())) {
			return Double.valueOf(value);
		}
		if (type.equals(Boolean.class.getName())) {
			return Boolean.valueOf(value);
		}
		if (type.equals(Character.class.getName())) {
			return value;
		}
		if (type.equals(List.class.getName()) || type.equals(ArrayList.class.getName())) {
			List list = new ArrayList();
			String[] ss = value.split(fieldSeq);
			for (int i = 0; i < ss.length; i++) {
				Object ob = stringToObject(ss[i]);
				list.add(ob);
			}
			return list;
		}

		if (type.equals(Map.class.getName()) || type.equals(HashMap.class.getName())) {
			Map map = new HashMap();
			String[] ss = value.split(fieldSeq);
			for (int i = 0; i < ss.length; i++) {
				String[] ssm = ss[i].split(fieldValueSeq);
				Object ob = stringToObject(ssm[1]);
				map.put(ssm[0], ob);
			}
			return map;
		}
		String[] ss = value.split(fieldSeq);
		Object ob = BeanHelper.newInstance(type);
		for (int i = 0; i < ss.length; i++) {
			String[] ssm = ss[i].split(fieldValueSeq);
			Object obj = stringToObject(ssm[1]);
			if (obj != null) {
				OgnlHelper.setValue(ob, ssm[0], obj);
			}
		}
		return ob;
	}

	public static void main(String[] args) {


		BeanBean childbean2 = new BeanBean();
		childbean2.setId(new Long(3));
		childbean2.setModify_date(new Date());
		childbean2.setVa(new Float(4.5));

		Bean childbean1 = new Bean();
		childbean1.setId(new Long(2));
		childbean1.setCreateDate(new Date());
	// 	childbean1.setList(new ArrayList());
	//	 childbean1.getList().add(childbean2);



		// String str = listToStringOneLevel(bean.getList());

		Map map = new HashMap();
		Map childMap = new HashMap();
		map.put("childMap", childMap);
		map.put("str", "ssssssss");
		childMap.put("bean1", childbean1);

 	 	String str = objectToString(map);
 	 	long start = System.currentTimeMillis();
	 	Map ddd =  (Map)stringToObject(str);

         System.out.println(System.currentTimeMillis() - start);
 	}

	private static String getType(Class klass) {
		if (String.class.equals(klass)) {
			return TYPESTRING;
		}
		if (Integer.class.equals(klass)) {
			return TYPEINTEGER;
		}
		if (Float.class.equals(klass)) {
			return TYPEFLOAT;
		}
		if (Long.class.equals(klass)) {
			return TYPELONG;
		}
		if (Double.class.equals(klass)) {
			return TYPEDOUBLE;
		}
		if (Date.class.equals(klass)) {
			return TYPEDATE;
		}
		if (Character.class.equals(klass)) {
			return TYPECharacter;
		}
		if (Boolean.class.equals(klass)) {
			return TYPEBoolean;
		}
		if (Date.class.equals(klass)) {
			return TYPEDATE;
		}
		return null;
	}

	private static String getRandomString10() {
	/*	long seed = 1;
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < 30; i++) {
			seed *= 30;
		}
		Random random = new Random(seed);
		long randomNum = random.nextLong(); */
		StringBuffer result = new StringBuffer();
		String strRandomNum = String.valueOf((int)(Math.random()*1000));
		int len = strRandomNum.length();
		// 位数不够补0[在前面补0]
		for (int i = len; i < 10; i++) {
			result.append("0");
		}
		result.append(strRandomNum);
		return result.toString();
	}

	private static List basicTypeList = new ArrayList();
	static {
		basicTypeList.add(String.class);
		basicTypeList.add(Float.class);
		basicTypeList.add(Double.class);
		basicTypeList.add(Long.class);
		basicTypeList.add(Integer.class);
		basicTypeList.add(Character.class);
		basicTypeList.add(Boolean.class);
		basicTypeList.add(Date.class);
	}

	private static boolean isBasicType(Class klass) {
		return basicTypeList.contains(klass);
	}
}

class BeanBean {
	private Long id;
	private Date modify_date;
	private String desc;
	private Float va;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getModify_date() {
		return modify_date;
	}

	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Float getVa() {
		return va;
	}

	public void setVa(Float va) {
		this.va = va;
	}

}

class Bean {
	private String name;
	private Long id;
	private List list;
	private Date createDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}

