package binotify.service;

import java.sql.SQLException;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import binotify.model.Subscribe;
import binotify.enums.Stat;
import binotify.controller.SubscribeController;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public class SubscribeService {
    private static final SubscribeController subscribceController = new SubscribeController();

    @WebMethod
    public String createSubscribe(int creator_id, int subscriber_id) {
        return subscribceController.createSubscribe(creator_id, subscriber_id);
    }

    @WebMethod
    public String approveSubscribe(int creator_id, int subscriber_id, Stat status) throws SQLException {
        return subscribceController.approveSubscribe(creator_id, subscriber_id, status);
    }

    @WebMethod
    public List<Subscribe> getAllReqSubscribe() throws SQLException {
        return subscribceController.getAllReqSubscribe();
    }

    @WebMethod
    public Stat checkStatus(int creator_id, int subscriber_id) throws SQLException {
        return subscribceController.checkStatus(creator_id, subscriber_id);
    }
}
