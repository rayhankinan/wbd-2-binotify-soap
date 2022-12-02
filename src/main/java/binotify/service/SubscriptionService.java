package binotify.service;


import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import io.github.cdimascio.dotenv.Dotenv;

import binotify.enums.Stat;
import binotify.model.DataPagination;
import binotify.repository.SubscriptionRepository;

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
                String SOAP_KEY = Dotenv.load().get("SOAP_KEY", "1234567890");
                URL url = new URL("http://host.docker.internal:8080/public/subs/update");
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("PUT");
                http.setDoOutput(true);
                http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                String data = "creator_id=" + creator_id + "&subscriber_id=" + subscriber_id + "&status=ACCEPTED" +
                "&soap_key=" + SOAP_KEY;
                OutputStreamWriter writer = new OutputStreamWriter(http.getOutputStream());
                writer.write(data);
                writer.flush();
                writer.close();
                System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
                http.disconnect();
            } catch (Exception e) {
                return res;
            }
        }

        return res;
    }

    @WebMethod
    public String rejectSubscribe(int creator_id, int subscriber_id) {
        String res =  SubscriptionRepository.rejectSubscribe(creator_id, subscriber_id);

        if (res.equals("Subscription rejected")) {
            try {
                String SOAP_KEY = Dotenv.load().get("SOAP_KEY", "1234567890");
                URL url = new URL("http://host.docker.internal:8080/public/subs/update");
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("PUT");
                http.setDoOutput(true);
                http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                String data = "creator_id=" + creator_id + "&subscriber_id=" + subscriber_id + "&status=REJECTED" +
                "&soap_key=" + SOAP_KEY;
                OutputStreamWriter writer = new OutputStreamWriter(http.getOutputStream());
                writer.write(data);
                writer.flush();
                writer.close();
                System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
                http.disconnect();
            } catch (Exception e) {
                return res;
            }
        }

        return res;
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
