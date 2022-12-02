package binotify.service;


import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import binotify.enums.Stat;
import binotify.model.DataPagination;
import binotify.repository.SubscriptionRepository;
import binotify.util.BinotifyAppUtil;

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
                BinotifyAppUtil.acceptSubscription(creator_id, subscriber_id);
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
                BinotifyAppUtil.rejectSubscription(creator_id, subscriber_id);
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
