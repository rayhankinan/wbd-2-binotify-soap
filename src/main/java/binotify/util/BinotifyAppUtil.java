package binotify.util;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class BinotifyAppUtil {
    public static final String SOAP_KEY = Dotenv.load().get("SOAP_KEY", "1234567890");

    public static void acceptSubscription(int creator_id, int subscriber_id) throws IOException {
        URL url = new URL("http://host.docker.internal:8080/public/subs/update");

        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("PUT");
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        String data = String.format("creator_id=%d&subscriber_id=%d&status=ACCEPTED&soap_key=%s", creator_id, subscriber_id, BinotifyAppUtil.SOAP_KEY);

        OutputStreamWriter writer = new OutputStreamWriter(http.getOutputStream());
        writer.write(data);
        writer.flush();
        writer.close();

        http.disconnect();
    }

    public static void rejectSubscription(int creator_id, int subscriber_id) throws IOException {
        URL url = new URL("http://host.docker.internal:8080/public/subs/update");

        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("PUT");
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        String data = String.format("creator_id=%d&subscriber_id=%d&status=REJECTED&soap_key=%s", creator_id, subscriber_id, BinotifyAppUtil.SOAP_KEY);

        OutputStreamWriter writer = new OutputStreamWriter(http.getOutputStream());
        writer.write(data);
        writer.flush();
        writer.close();

        http.disconnect();
    }
}
