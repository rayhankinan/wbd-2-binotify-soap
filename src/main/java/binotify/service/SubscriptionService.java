package binotify.service;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import binotify.model.DataPagination;
import binotify.repository.SubscriptionRepository;
import binotify.enums.Stat;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public class SubscriptionService {
    private static final SubscriptionRepository SubscriptionRepository = new SubscriptionRepository();

    @WebMethod
    public String createSubscribe(int creator_id, int subscriber_id, String creator_name, String subscriber_name) {
        return SubscriptionRepository.createSubscribe(creator_id, subscriber_id, creator_name, subscriber_name);
    }

    @WebMethod
    public String approveSubscribe(int creator_id, int subscriber_id) {
        String res = SubscriptionRepository.approveSubscribe(creator_id, subscriber_id);
        if (res.equals("Subscription accepted")) {
            try {
                URL url = new URL("http://web-tubes-1:8080/public/subs/update");
                System.out.println(url);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("PUT");
                http.setDoOutput(true);
                http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                String data = "creator_id=" + creator_id + "&subscriber_id=" + subscriber_id + "&status=ACCEPTED" +
                "&soap_key=JLuplGpZTGxLQthAO4LZh0xipE0TU1QjImGp2qIkzQYZ5SxGWWmXHgAjQULTQkI";
                OutputStreamWriter writer = new OutputStreamWriter(http.getOutputStream());
                System.out.println("Sending notification to subscriber");
                writer.write(data);
                writer.flush();
                writer.close();
                System.out.println("Sending notification to subscriber10");
                System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
                http.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                return res;
            }
        }
        return res;
    }

    @WebMethod
    public String rejectSubscribe(int creator_id, int subscriber_id) {
        return SubscriptionRepository.rejectSubscribe(creator_id, subscriber_id);
    }

    @WebMethod
    public DataPagination getAllReqSubscribe(int page, int rows) {
        return SubscriptionRepository.getAllReqSubscribe(page, rows);
    }

    @WebMethod
    public Stat checkStatus(int creator_id, int subscriber_id) {
        return SubscriptionRepository.checkStatus(creator_id, subscriber_id);
    }

    @WebMethod
    public int getPageCount(int rows) {
        return SubscriptionRepository.getPageCount(rows);
    }
}
