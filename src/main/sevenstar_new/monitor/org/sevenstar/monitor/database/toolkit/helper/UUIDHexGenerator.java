package org.sevenstar.monitor.database.toolkit.helper;

public class UUIDHexGenerator  extends UUIDGenerator implements IDGenerator{

	private String sep = "";

	public String generate() {
		return new StringBuffer(36)
		.append( format( getIP() ) ).append(sep)
		.append( format( getJVM() ) ).append(sep)
		.append( format( getHiTime() ) ).append(sep)
		.append( format( getLoTime() ) ).append(sep)
		.append( format( getCount() ) )
		.toString();
	}



	protected String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace( 8-formatted.length(), 8, formatted );
		return buf.toString();
	}

	protected String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace( 4-formatted.length(), 4, formatted );
		return buf.toString();
	}

	public static void main( String[] args ) throws Exception {

		UUIDHexGenerator gen = new UUIDHexGenerator();
		UUIDHexGenerator gen2 = new UUIDHexGenerator();

		for ( int i=0; i<10; i++) {
			String id = (String) gen.generate();
			System.out.println( id + ": " +  id.length() );
			String id2 = (String) gen2.generate();
			System.out.println( id2 + ": " +  id2.length() );
		}
	}

}
