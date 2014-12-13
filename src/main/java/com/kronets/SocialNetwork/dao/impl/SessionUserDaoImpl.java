package com.kronets.SocialNetwork.dao.impl;

import com.kronets.SocialNetwork.dao.SessionUserDao;
import com.kronets.SocialNetwork.models.SessionUser;
import com.kronets.SocialNetwork.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * @author Volodymyr Grynda
 */
public class SessionUserDaoImpl implements SessionUserDao {
    @Override
    public void insert(SessionUser sessionUser) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(sessionUser);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public SessionUser selectById(long id) throws Exception {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            SessionUser sessionUser =
                    (SessionUser) session.get(SessionUser.class, id);
            return sessionUser;
        } finally {
            if (session!= null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public SessionUser selectBySession(String sessionUserField) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery(
                "SELECT sessionId FROM SessionUser where session = '" + sessionUserField + "'");
        List<Long> sessionId = query.list();


        try {
            if (sessionId.size() != 0) {
                SessionUser sessionUser = (SessionUser) session
                        .get(SessionUser.class, sessionId.get(0));
                return sessionUser;
            } else {
                return null;
            }
        } finally {
            session.close();
        }


    }

    @Override
    public void delete(SessionUser sessionUser) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(sessionUser);
        session.getTransaction().commit();
        session.close();
    }
}
