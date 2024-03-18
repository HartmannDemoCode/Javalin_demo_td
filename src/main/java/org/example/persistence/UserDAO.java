package org.example.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.exceptions.EntityNotFoundException;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
public class UserDAO implements ISecurityDAO{

    private EntityManagerFactory emf;
    public UserDAO(EntityManagerFactory _emf) {
        this.emf = _emf;
    }

    @Override
    public User createUser(String username, String password) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User user = new User(username, password);
        Role userRole = em.find(Role.class, "user");
        if (userRole == null) {
            userRole = new Role("user");
            em.persist(userRole);
        }
        user.addRole(userRole);
        em.persist(user);
        em.getTransaction().commit();
        em.close();
        return user;
    }
    public User verifyUser(String username, String password) throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, username);
        if (user == null)
            throw new EntityNotFoundException("No user found with username: " + username);
        if (!user.verifyUser(password))
            throw new EntityNotFoundException("Wrong password");
        return user;
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        UserDAO dao = new UserDAO(emf);
//        User user = dao.createUser("Henrik", "1234");
//        System.out.println(user.getUsername());
        try {
            User verifiedUser = dao.verifyUser("Henrik", "1234");
            System.out.println(verifiedUser.getUsername());
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Role createRole(String role) {
        //return null;

        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public User addRoleToUser(String username, String role) {
        //return null;

        throw new UnsupportedOperationException("Not implemented yet");
    }
}
