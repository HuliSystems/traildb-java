
import traildb.*;

import java.io.FileNotFoundException;

public class Wikipedia {
	public static long SESSION_LIMIT = 30 * 60;
	public static void sessions(TrailDB tdb) {
		System.out.print("Number of events: " + tdb.numEvents());
		TrailDBCursor cursor = new TrailDBCursor(tdb);
		long n = tdb.numTrails();
		long totalSessions = 0;
		long totalEvents = 0;

		for (long i = 0; i < n; i++) {
			TrailDBEvent event;
			if (i % 16384 == 0) {
				cursor.getTrail(i);
				System.out.println(cursor.getTrailLength());
			}
			cursor.getTrail(i);

			event = cursor.next();

			long prevTime = event.timestamp;
			long numSessions = 1;
			long numEvents = 1;
			while ((event = cursor.next()) != null) {
				if (event.timestamp - prevTime > SESSION_LIMIT)
					numSessions++;
				prevTime = event.timestamp;
				numEvents++;
			}
			totalSessions += numSessions;
			totalEvents += numEvents;
		}
		System.out.println("Trails: " + n + " Sessions: " + totalSessions + " Events: " + totalEvents);
	}

	public static void main(String[] args) throws FileNotFoundException {
		TrailDB tdb = new TrailDB("wikipedia-history-small.tdb");
		sessions(tdb);
	}
}