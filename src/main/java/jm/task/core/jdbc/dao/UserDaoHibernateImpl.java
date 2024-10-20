package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import jm.task.core.jdbc.util.Util;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @SuppressWarnings("deprecation")
    @Override
    public void createUsersTable() {

        Transaction transaction = null;
    
        try (Session session = Util.getSessionFactory().openSession();){
            transaction = session.beginTransaction();
    
            String sql = "CREATE TABLE IF NOT EXISTS Users (id bigint AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) NOT NULL, lastName VARCHAR(255) NOT NULL, age tinyint NOT NULL)";
            
            session.createNativeQuery(sql).executeUpdate();
    
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @SuppressWarnings("deprecation")
    @Override
    public void dropUsersTable() {

        Session session = null;
        Transaction transaction = null;
        
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
        
            String sql = "DROP TABLE IF EXISTS Users";
            Query query = session.createNativeQuery(sql); //Удаление таблиц напрямую не поддерживается, используется нативный запрос
            query.executeUpdate();  
        
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close(); 
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void saveUser(String name, String lastName, byte age) {
       
        User user = new User(name, lastName, age);

        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            session.close();
            System.out.println(name+" добавлен в базу данных");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @SuppressWarnings("deprecation")
    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("delete from User where id = :userId")
                    .setParameter("userId", id)
                    .executeUpdate();
    
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

   
    @Override
    public List<User> getAllUsers() {
        List <User> users = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            users = session.createQuery("FROM User", User.class).list(); // Имя класса, не таблицы
        }catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
