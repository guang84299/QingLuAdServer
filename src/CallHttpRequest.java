import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CountDownLatch;

public class CallHttpRequest implements Runnable {
	public static int successRequest = 0;
	public static int failRequest = 0;
	public static int timeOutRequest = 0;
	private final String hostString = "http://120.25.87.115:";
	private String port;
	private String puductPartURL;

	private CountDownLatch begin;
	private CountDownLatch end;

	CallHttpRequest( String puductPartURL, CountDownLatch begin,
			CountDownLatch end) {
		this.port = port;
		this.puductPartURL = puductPartURL;
		this.begin = begin;
		this.end = end;
	}

	private String makeFullURL() {
		return puductPartURL;

	}

	private static synchronized void incrementSuccessCount() {
		successRequest++;
	}

	private static synchronized void incrementFailCount() {
		failRequest++;
	}

	private static synchronized void incrementTimeOutCount() {
		timeOutRequest++;
	}

	public void run() {
		String urlStr = makeFullURL();
		URL url;
		try {
			begin.await();
			url = new URL(urlStr);
			URLConnection URLconnection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) URLconnection;
			httpConnection.setConnectTimeout(10000);
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				incrementSuccessCount();
			} else {
				incrementFailCount();
			}
		} catch (SocketTimeoutException e) {
			incrementTimeOutCount();
		} catch (Exception e) {
		} finally {
			end.countDown();
		}

	}

}
