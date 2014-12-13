package com.kronets.SocialNetwork.dao.impl;

import com.kronets.SocialNetwork.dao.PasswordDao;
import com.kronets.SocialNetwork.models.Password;
import com.kronets.SocialNetwork.util.HibernateUtil;
import org.hibernate.Session;

/**
 * @author Volodymyr Grynda
 */
public class PasswordDaoImpl implements PasswordDao {

    @Override
    public Password selectById(long id) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Password password = (Password) session.get(Password.class, id);
        session.close();
        return password;
    }

    @Override
    public void update(Password password) throws Exception {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            session.update(password);
            session.getTransaction().commit();
        } finally {
             if (session!= null && session.isOpen()) {
                 session.close();
             }
        }
    }

}
