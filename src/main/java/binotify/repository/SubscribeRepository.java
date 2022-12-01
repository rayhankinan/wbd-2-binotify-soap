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

public class SubscribeRepository {
    public String createSubscribe(int creator_id, int subscriber_id, String creator_name, String subscriber_name) {
        try {
            Subscribe subscribe = new Subscribe();
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

        } catch (Exception e) {
            return "Error creating subscription";
        }
    }

    public String approveSubscribe(int creator_id, int subscriber_id) {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();
            Subscribe subscribe = session.get(Subscribe.class, new Subscribe(creator_id, subscriber_id));

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
            return "Error rejecting subscription";
        }
    }

    public String rejectSubscribe(int creator_id, int subscriber_id) {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();
            Subscribe subscribe = session.get(Subscribe.class, new Subscribe(creator_id, subscriber_id));

            if (subscribe == null) {
                session.getTransaction().commit();
                return "Subscription not found";
            }

            Stat currentStatus = subscribe.getStatus();

            if (currentStatus == Stat.PENDING) {
                subscribe.setStatus(Stat.REJECTED);
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
            return "Error approving subscription";
        } 
    }

    public DataPagination getAllReqSubscribe(int page, int rows) {
        try {
            DataPagination data = new DataPagination();

            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Subscribe> criteria = builder.createQuery(Subscribe.class);
            Root<Subscribe> root = criteria.from(Subscribe.class);
            Predicate predicate = builder.equal(root.get("status"), Stat.PENDING);
            criteria.select(root).where(predicate);
            TypedQuery<Subscribe> query = session.createQuery(criteria);

            List<Subscribe> subscribes = query.setFirstResult((page - 1) * rows).setMaxResults(rows).getResultList();
            data.setData(subscribes);

            int pageCount = this.getPageCount(rows);
            data.setPageCount(pageCount);

            session.getTransaction().commit();

            return data;

        } catch (Exception e) {
            return null;
        }
    }

    public int getPageCount(int rows) {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
            Root<Subscribe> root = criteria.from(Subscribe.class);
            Predicate predicate = builder.equal(root.get("status"), Stat.PENDING);
            criteria.select(builder.count(root)).where(predicate);
            TypedQuery<Long> query = session.createQuery(criteria);

            long count = query.getSingleResult();

            return (int) Math.ceil((double) count / rows);

        } catch (Exception e) {
            return 0;
        }
    }

    public Stat checkStatus(int creator_id, int subscriber_id) {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();
            Subscribe subscribe = session.get(Subscribe.class, new Subscribe(creator_id, subscriber_id));
            session.getTransaction().commit();

            if (subscribe == null) {
                return Stat.NODATA;
            }
            return subscribe.getStatus();

        } catch (Exception e) {
            return Stat.NODATA;
        }
    } 
}
