package binotify.repository;

import binotify.model.*;
import binotify.enums.Stat;
import binotify.util.HibernateUtil;

import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class SubscriptionRepository {
    public String createSubscribe(int creator_id, int subscriber_id, String creator_name, String subscriber_name) {
        try {
            Subscription subscribe = new Subscription();
            subscribe.setCreatorID(creator_id);
            subscribe.setSubscriberID(subscriber_id);
            subscribe.setCreatorName(creator_name);
            subscribe.setSubscriberName(subscriber_name);

            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();
            session.save(subscribe);
            session.getTransaction().commit();

            return "Subscription created, wait for approval";

        } catch (Exception e){
            e.printStackTrace();

            return "Error creating subscription";
        }
    }

    public String approveSubscribe(int creator_id, int subscriber_id) {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();
            Subscription subscribe = session.get(Subscription.class, new Subscription(creator_id, subscriber_id));

            if (subscribe == null) {
                session.getTransaction().commit();
                return "Subscription not found";
            }

            Stat currentStatus = subscribe.getStatus();

            if (currentStatus == Stat.PENDING) {
                subscribe.setStatus(Stat.ACCEPTED);
                session.save(subscribe);
                session.getTransaction().commit();

                return "Subscription accepted";
            } else if (currentStatus == Stat.ACCEPTED) {
                session.getTransaction().commit();
                return "Subscription already accepted";
            } else {
                session.getTransaction().commit();
                return "Subscription already rejected";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error approving subscription";
        }
    }

    public String rejectSubscribe(int creator_id, int subscriber_id) {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();
            Subscription subscribe = session.get(Subscription.class, new Subscription(creator_id, subscriber_id));

            if (subscribe == null) {
                session.getTransaction().commit();
                return "Subscription not found";
            }

            Stat currentStatus = subscribe.getStatus();

            if (currentStatus == Stat.PENDING) {
                subscribe.setStatus(Stat.REJECTED);
                session.save(subscribe);
                session.getTransaction().commit();

                return "Subscription rejected";
            } else if (currentStatus == Stat.ACCEPTED) {
                session.getTransaction().commit();
                return "Subscription already accepted";
            } else {
                session.getTransaction().commit();
                return "Subscription already rejected";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error rejecting subscription";
        } 
    }

    public DataPagination getAllReqSubscribe(int page, int rows) {
        try {
            DataPagination data = new DataPagination();

            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Subscription> criteria = builder.createQuery(Subscription.class);
            Root<Subscription> root = criteria.from(Subscription.class);
            Predicate predicate = builder.equal(root.get("status"), Stat.PENDING);
            criteria.select(root).where(predicate);
            TypedQuery<Subscription> query = session.createQuery(criteria);

            List<Subscription> subscribes = query.setFirstResult((page - 1) * rows).setMaxResults(rows).getResultList();
            data.setData(subscribes);

            int pageCount = this.getPageCount(rows);
            data.setPageCount(pageCount);

            session.getTransaction().commit();

            return data;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getPageCount(int rows) {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
            Root<Subscription> root = criteria.from(Subscription.class);
            Predicate predicate = builder.equal(root.get("status"), Stat.PENDING);
            criteria.select(builder.count(root)).where(predicate);
            TypedQuery<Long> query = session.createQuery(criteria);

            long count = query.getSingleResult();

            return (int) Math.ceil((double) count / rows);

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Stat checkStatus(int creator_id, int subscriber_id) {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();
            Subscription subscribe = session.get(Subscription.class, new Subscription(creator_id, subscriber_id));
            session.getTransaction().commit();

            if (subscribe == null) {
                return Stat.NODATA;
            }
            return subscribe.getStatus();

        } catch (Exception e) {
            e.printStackTrace();
            return Stat.NODATA;
        }
    } 
}
