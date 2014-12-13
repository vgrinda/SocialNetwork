package com.kronets.SocialNetwork.dao.impl;

import com.kronets.SocialNetwork.dao.GroupDao;
import com.kronets.SocialNetwork.models.Group;
import com.kronets.SocialNetwork.models.User;
import com.kronets.SocialNetwork.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * @author Volodymyr Grynda
 */
public class GroupDaoImpl implements GroupDao {
    @Override
    public Group selectById(long id) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Group groupE = (Group) session.get(Group.class, id);
        session.close();
        return groupE;
    }

    @Override
    public void insert(Group group) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.save(group);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Group group) throws Exception {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            session.beginTransaction();

            session.update(group);

            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void delete(Group group) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.delete(group);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Group> selectByGroupName(String groupName, int lot)
    throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Group.class);

        criteria.setFirstResult(lot * 10);
        criteria.setMaxResults(10);
        criteria.add(Restrictions.eq("name", groupName));

        List<Group> list = criteria.list();
        session.close();
        return list;
    }

    @Override
    public long selectCount(long groupId) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Criteria criteria = session.createCriteria(User.class);
        criteria.createAlias("groups", "groupsAlias");
        criteria.add(Restrictions.eq("groupsAlias.groupId", groupId));
        Long result = (Long) criteria.setProjection(Projections.rowCount())
                                     .uniqueResult();
        session.close();
        return result;
    }
}
