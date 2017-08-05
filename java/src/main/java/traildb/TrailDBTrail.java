package traildb;

import traildb.TrailDBItem;

public class TrailDBTrail
{
	public long timestamp;
	public long numItems;

	private long items; // Points to beginning of items
	private long db;

	public String getItem(int i) {
		if (i >= numItems || i < 0) {
			throw new IndexOutOfBoundsException("getItem(" + i + ") but numItems in event is" + numItems);
		}
		return native_getItem(i);
	}

	private native String native_getItem(int i);

	static {
		System.loadLibrary("TraildbJavaNative");
	}
}
