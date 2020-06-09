package ku.opensrcsw.MBot;

public class TimeTracker {
	private static long time1;
	
	public static void initTime() {
		time1 = System.currentTimeMillis();
	}
	
	public static long getTime() {
		long time2 = System.currentTimeMillis();
		long time = time2-time1; 
		time1 = time2;
		return time;
	}
}
