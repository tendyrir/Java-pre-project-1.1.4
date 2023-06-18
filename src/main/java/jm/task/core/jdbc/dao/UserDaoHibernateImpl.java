package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {


    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (SessionFactory sessionFactory = Util.connectToMySqlDatabaseHibernate();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS Users ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "name VARCHAR(50) NOT NULL,"
                    + "lastName VARCHAR(100) NOT NULL,"
                    + "age TINYINT UNSIGNED NOT NULL CHECK (age >= 0))";
            Query query = session.createNativeQuery(sql);
            query.executeUpdate();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (SessionFactory sessionFactory = Util.connectToMySqlDatabaseHibernate();
            Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS Users;";
            Query query = session.createNativeQuery(sql);
            query.executeUpdate();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (SessionFactory sessionFactory = Util.connectToMySqlDatabaseHibernate();
            Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();

        } catch (Exception e) {
                e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        SessionFactory sessionFactory = Util.connectToMySqlDatabaseHibernate();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        User user = session.get(User.class, id);
        session.remove(user);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (SessionFactory sessionFactory = Util.connectToMySqlDatabaseHibernate();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
                Root<User> root = criteriaQuery.from(User.class);
                criteriaQuery.select(root);
                userList = session.createQuery(criteriaQuery).getResultList();
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
            } finally {
                session.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (SessionFactory sessionFactory = Util.connectToMySqlDatabaseHibernate();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                String hql = "DELETE FROM User";
                Query query = session.createQuery(hql);
                query.executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
            } finally {
                session.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
