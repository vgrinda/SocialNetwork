package com.kronets.SocialNetwork.dao.impl;

import com.kronets.SocialNetwork.dao.PostDao;
import com.kronets.SocialNetwork.models.BackOfficeAdmin;
import com.kronets.SocialNetwork.models.Post;
import com.kronets.SocialNetwork.models.User;
import com.kronets.SocialNetwork.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Posts Dao implementation
 *
 * @author Volodymyr Grynda
 */
public class PostDaoImpl implements PostDao {

    @Override
    public Post selectById(long id) throws Exception {
        Session session = null;
        try {
            session  = HibernateUtil.getSessionFactory().openSession();
            Post post = (Post) session.get(Post.class, id);
            session.close();
            return post;
        } finally {
            if (session!= null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void insert(Post post) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(post);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Post post) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(post);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Post> selectLastWith(User user, int lot) throws Exception {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Post.class);
            criteria.setMaxResults(10);
            criteria.add(Restrictions.eq("user.id", user.getId()));
            criteria.addOrder(Order.desc("time"));
            criteria.setFirstResult(lot * 10);
            List<Post> list = criteria.list();
            session.close();
            return list;
        } finally {
            if (session!= null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public List<Post> selectLastBeckOffWith (List<BackOfficeAdmin> backOfficeAdmins,
                                             int lot) throws Exception {
        Session session = null;
        try {
            int i = 0;
            Long [] arr = new Long[backOfficeAdmins.size()];
            for(BackOfficeAdmin backOff:backOfficeAdmins) {
                arr[i] = backOff.getUser().getId();
                i++;
            }

            session = HibernateUtil.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Post.class);
            criteria.setMaxResults(10);
            criteria.add(Restrictions.in("user.id" , arr));
            criteria.addOrder(Order.desc("time"));
            criteria.setFirstResult(lot * 10);
            List<Post> list = criteria.list();
            session.close();
            return list;
        } finally {
            if (session!= null && session.isOpen()) {
                session.close();
            }
        }
    }
}
