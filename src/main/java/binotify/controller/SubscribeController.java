package binotify.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import binotify.model.Subscribe;
import binotify.enums.Stat;

public class SubscribeController {
    public final Connection conn = new Database().getConnection();

    public String createSubscribe(int creator_id, int subscriber_id) {
        try {
            Statement smt = this.conn.createStatement();
            String sql = "insert into Subscription(creator_id, subscriber_id, status) values(" + creator_id + ", " + subscriber_id + ", 'PENDING')";
            smt.executeUpdate(sql);
            return "Subscription created, wait for approval";
        } catch (Exception e){
            e.printStackTrace();
            return "Error creating subscription";
        }
    }

    public String approveSubscribe(int creator_id, int subscriber_id) throws SQLException {
        try {
            ResultSet rs = this.conn.createStatement()
            .executeQuery("SELECT * from Subscription where id=" + creator_id + " and subscriber_id=" + subscriber_id);
            rs.next();
            Stat currentStatus = Stat.valueOf(rs.getString("status"));
            if (currentStatus == Stat.PENDING) {
                this.conn.createStatement()
                    .executeUpdate("UPDATE Subscription SET status='ACCEPTED' where creator_id=" + creator_id + " and subscriber_id=" + subscriber_id);
                return "Subscription accepted";
            } else if (currentStatus == Stat.ACCEPTED) {
                return "Subscription already accepted";
            }
            else {
                return "Subscription already rejected";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error rejecting subscription";
        }
    }

    public String rejectSubscribe(int creator_id, int subscriber_id) throws SQLException {
        try {
            ResultSet rs = this.conn.createStatement()
            .executeQuery("SELECT * from Subscription where creator_id=" + creator_id + " and subscriber_id=" + subscriber_id);
            rs.next();
            Stat currentStatus = Stat.valueOf(rs.getString("status"));
            if (currentStatus == Stat.PENDING) {
                this.conn.createStatement()
                    .executeUpdate("UPDATE Subscription SET status='REJECTED' where creator_id=" + creator_id + " and subscriber_id=" + subscriber_id);
                return "Subscription rejected";
            } else if (currentStatus == Stat.ACCEPTED) {
                return "Subscription already accepted";
            }
            else {
                return "Subscription already rejected";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error approving subscription";
        } 
    }

    public List<Subscribe> getAllReqSubscribe() throws SQLException {
        try {
            List<Subscribe> req = new ArrayList<>();
            ResultSet rs = this.conn.createStatement()
                    .executeQuery("SELECT * from Subscription where status='PENDING'");
            while(rs.next()){
                Subscribe s = new Subscribe();
                s.setCreator(rs.getInt("creator_id"));
                s.setSubscriber(rs.getInt("subscriber_id"));
                s.setStatus(Stat.valueOf(rs.getString("status")));
                req.add(s);
            }
            return req;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }

    public Stat checkStatus(int creator_id, int subscriber_id) throws SQLException {
        try {
            ResultSet rs = this.conn.createStatement()
            .executeQuery("SELECT status FROM Subscription WHERE creator_id=" + creator_id + " and subscriber_id=" + subscriber_id);
            rs.next();
            if (rs.getString("status") == null) {
                return null;
            }
            return Stat.valueOf(rs.getString("status"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    } 
}
