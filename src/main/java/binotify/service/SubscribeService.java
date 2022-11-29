package binotify.service;

import java.sql.SQLException;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import binotify.model.Subscribe;
import binotify.repository.SubscribeRepository;
import binotify.enums.Stat;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public class SubscribeService {
    private static final SubscribeRepository subscribceRepository = new SubscribeRepository();

    @WebMethod
    public String createSubscribe(int creator_id, int subscriber_id) {
        return subscribceRepository.createSubscribe(creator_id, subscriber_id);
    }

    @WebMethod
    public String approveSubscribe(int creator_id, int subscriber_id) throws SQLException {
        return subscribceRepository.approveSubscribe(creator_id, subscriber_id);
    }

    @WebMethod
    public String rejectSubscribe(int creator_id, int subscriber_id) throws SQLException {
        return subscribceRepository.rejectSubscribe(creator_id, subscriber_id);
    }

    @WebMethod
    public List<Subscribe> getAllReqSubscribe() throws SQLException {
        return subscribceRepository.getAllReqSubscribe();
    }

    @WebMethod
    public Stat checkStatus(int creator_id, int subscriber_id) throws SQLException {
        return subscribceRepository.checkStatus(creator_id, subscriber_id);
    }
}
