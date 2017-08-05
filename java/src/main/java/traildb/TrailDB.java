package traildb;

import java.util.UUID;
import java.util.HashMap;
import java.io.FileNotFoundException;

public class TrailDB {
	public enum TDB_OPT_KEY {
		TDB_OPT_ONLY_DIFF_ITEMS,
		TDB_OPT_EVENT_FILTER,
		TDB_OPT_CURSOR_EVENT_BUFFER_SIZE,
	}

	public enum TDB_OPT_VALUE {
		TDB_OPT_ONLY_DIFF_ITEMS,
		TDB_OPT_EVENT_FILTER,
		TDB_OPT_CURSOR_EVENT_BUFFER_SIZE,
	}

	public String root;
	public String[] fieldNames;
	public HashMap<String, Integer> fieldMap;

	private long db;

	public TrailDB(String root) throws FileNotFoundException {
		this.root = root;
		init(root);
		long n = this.numFields();
		this.fieldNames = new String[(int) n];
		this.fieldMap = new HashMap<String, Integer>();
		for (int i = 0; i < n; i++) {
			this.fieldNames[i] = this.getFieldName(i);
			this.fieldMap.put(this.fieldNames[i], i);
		}
	}

	private native void init(String root);

	public native void dontNeed();

	public native void willNeed();

	public native long numTrails();

	public native long numEvents();

	public native long numFields();

	public native long minTimestamp();

	public native long maxTimestamp();

	public native long version();

	// Options

	public native void setOpt(TDB_OPT_KEY key, int value); // TODO: Fix this

	public native int getOpt(TDB_OPT_KEY key); // TODO: Fix this

	public native void setTrailOpt(long trailId, TDB_OPT_KEY key, int value); // TODO: Fix this

	public native int getTrailOpt(long trailId, TDB_OPT_KEY key); // TODO: Fix this

	// Items

	public native int lexiconSize(int field);

	public native int getField(String fieldName);

	public native String getFieldName(int field);

	public native TrailDBItem getItem(int field, String value);

	public native void close();

	// UUID's

	public native UUID getUUID(int trailId);

	public native void getTrailId(UUID uuid);

	private static native void initIDs();

	static {
		System.loadLibrary("TraildbJavaNative");
		initIDs();
	}
}
