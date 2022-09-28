package service;

import dao.DAO;
import exception.BusinessException;
import exception.NotFoundException;
import model.Session;

import javax.persistence.RollbackException;
import java.util.List;

public class SessionService {

    private DAO<Session> sessionDAO;

    private static SessionService instance;

    private SessionService() {
        this.sessionDAO = new DAO<>(Session.class);
    }

    public static SessionService getInstance() {
        if (instance == null) {
            instance = new SessionService();
        }
        return instance;
    }

    public void save(Session session) {
        if (session.getId() != null) {
            sessionDAO.updateAtomic(session);
        } else {
            sessionDAO.includeAtomic(session);
        }
    }

    public Session findById(Long id) {
        return sessionDAO.findById(id);
    }

    public List<Session> findAll() {
        return sessionDAO.findAll();
    }

    public void remove(Long id) {
        try {
            sessionDAO.deleteAtomic(id);
        } catch (NotFoundException e) {
            throw e;
        } catch (RollbackException e) {
            throw new BusinessException("Entidade possui vínculos");
        }
    }

}
