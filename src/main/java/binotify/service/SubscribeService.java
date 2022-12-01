package binotify.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import binotify.model.DataPagination;
import binotify.repository.SubscribeRepository;
import binotify.enums.Stat;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public class SubscribeService {
    private static final SubscribeRepository subscribeRepository = new SubscribeRepository();

    @WebMethod
    public String createSubscribe(int creator_id, int subscriber_id, String creator_name, String subscriber_name) {
        return subscribeRepository.createSubscribe(creator_id, subscriber_id, creator_name, subscriber_name);
    }

    @WebMethod
    public String approveSubscribe(int creator_id, int subscriber_id) {
        return subscribeRepository.approveSubscribe(creator_id, subscriber_id);
    }

    @WebMethod
    public String rejectSubscribe(int creator_id, int subscriber_id) {
        return subscribeRepository.rejectSubscribe(creator_id, subscriber_id);
    }

    @WebMethod
    public DataPagination getAllReqSubscribe(int page, int rows) {
        return subscribeRepository.getAllReqSubscribe(page, rows);
    }

    @WebMethod
    public Stat checkStatus(int creator_id, int subscriber_id) {
        return subscribeRepository.checkStatus(creator_id, subscriber_id);
    }

    @WebMethod
    public int getPageCount(int rows) {
        return subscribeRepository.getPageCount(rows);
    }
}
