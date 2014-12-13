package com.kronets.SocialNetwork.dao.impl;

import com.kronets.SocialNetwork.dao.PrivateMessageDao;
import com.kronets.SocialNetwork.models.PrivateMessage;
import com.kronets.SocialNetwork.models.User;
import com.kronets.SocialNetwork.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Private messages Dao implementation
 *
 * @author Volodymyr Grynda
 */
public class PrivateMessageDaoImpl implements PrivateMessageDao {
    @Override
    public PrivateMessage selectById(long msgId) throws Exception {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            PrivateMessage privateMessage =
                    (PrivateMessage) session.get(PrivateMessage.class, msgId);
            return privateMessage;
        } finally {
            if (session!= null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void update(PrivateMessage privateMessage) throws Exception {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(privateMessage);
            session.getTransaction().commit();
        } finally {
            if (session!= null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void insert(PrivateMessage privateMessage) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(privateMessage);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(PrivateMessage privateMessage) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(privateMessage);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<PrivateMessage> selectReceivedNextWith(User user, int lot)
    throws Exception {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(PrivateMessage.class);
            criteria.setMaxResults(10);
            criteria.add(Restrictions.eq("receiverUser.id", user.getId()));
            criteria.addOrder(Order.desc("time"));
            criteria.setFirstResult(lot * 10);
            List list = criteria.list();
            return list;
        } finally {
            if (session!= null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public List<PrivateMessage> selectSentNextWith(User user, int lot)
    throws Exception {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(PrivateMessage.class);
            criteria.setMaxResults(10);
            criteria.add(Restrictions.eq("sentUser.id", user.getId()));
            criteria.addOrder(Order.desc("time"));
            criteria.setFirstResult(lot * 10);
            List list = criteria.list();
            session.close();
            return list;
        } finally {
            if (session!= null && session.isOpen()) {
                session.close();
            }
        }
    }
}
