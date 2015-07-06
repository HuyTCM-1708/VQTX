package codereport.daocontroller;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import codereport.entity.User;

/**
 * @author HuyTCM
 *
 */
public class UserController implements Serializable {

    /** .*/
    private static final long serialVersionUID = 1L;
    /** .*/
    private static final Logger LOGGER = Logger.getLogger("UserController");
    /** .*/
    private EntityManagerFactory emf = null;
    
    /**
     * Constructor userController.
     * @param emf 
     *
     */
    public UserController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    /**
     * @return entity manager
     */
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    /**
     * Create new user.
     * @param user 
     * @throws Exception  
     */
    public void create(User user) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception ex) {
            throw new Exception(ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    /**
     * Edit user information.
     * @param user 
     * @throws Exception 
     */
    public void edit(User user) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            user = em.merge(user);
            em.getTransaction().commit();
        } catch (Exception ex) {
            
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    /**
     * Delete user.
     * @param account 
     * @throws Exception 
     */
    public void destroy(String account) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user;
            try {
                user = em.getReference(User.class, account);
                user.getUsername();
            } catch (Exception ex) {
                throw new Exception(ex);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    /**
     * Check login.
     * @param username - username.
     * @param password - password.
     * @return user if user is existed, otherwise return null.
     */
    public User checkLogin(String username, String password) {
        EntityManager em = getEntityManager();
        
        String jqpl = "User.checkLogin";
        Query query = em.createNamedQuery(jqpl);
        query.setParameter("username", username);
        query.setParameter("password", password);
        
        try {
            User user = (User) query.getSingleResult();
            return user;
        } catch (Exception ex) {
            return null;
        }
    }
    /**
     * Find user by account.
     * @param account 
     * @return user
     */
    public User findUser(String account) {
        EntityManager em = getEntityManager();
        return em.find(User.class, account);
    }
    /**
     *Get user entity.
     * @return list users
     */
    public List<User> findUserEntity() {
        EntityManager em = getEntityManager();
        try {
            String jqpl = "User.findAll";
            Query query = em.createNamedQuery(jqpl);
            @SuppressWarnings("unchecked")
            List<User> listUser = query.getResultList();
            return listUser;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return null;
        }
    }
}
