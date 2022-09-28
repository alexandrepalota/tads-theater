package dao;

import exception.DbException;
import exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import java.util.List;

public class DAO<T> {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private Class<T> clazz;

    static {
        try {
            emf = Persistence.createEntityManagerFactory("theaterPu");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DAO(Class<T> clazz) {
        this.clazz = clazz;
        em = emf.createEntityManager();
    }

    public DAO() {
        this(null);
    }

    public DAO<T> openTransaction() {
        em.getTransaction().begin();
        return this;
    }

    public DAO<T> closeTransaction() {
        em.getTransaction().commit();
        return this;
    }

    public DAO<T> rollbackTransaction() {
        em.getTransaction().rollback();
        return this;
    }

    public DAO<T> include(T entity) {
        em.persist(entity);
        return this;
    }

    public DAO<T> includeAtomic(T entity) {
        this.openTransaction();
        try {
            include(entity);
        } catch (Exception e) {
            this.rollbackTransaction();
            throw new DbException("Error inserting " + clazz.getSimpleName());
        }
        this.closeTransaction();
        return this;
    }

    public DAO<T> update(T entity) {
        em.merge(entity);
        return this;
    }

    public DAO<T> updateAtomic(T entity) {
        return this.openTransaction().update(entity).closeTransaction();
    }

    public T findById(Object id) {
        T entity = this.em.find(this.clazz, id);
        if (entity == null) throw new NotFoundException(String.format("%s with ID=%s was not found", clazz.getSimpleName(), id.toString()));
        return entity;
    }

    public List<T> findAll() {
        if (clazz == null) {
            throw new UnsupportedOperationException("Class is null");
        }
        String jpql = "select e from " + clazz.getName() + " e";
        TypedQuery<T> query = em.createQuery(jpql, clazz);
        return query.getResultList();
    }

    public DAO<T> delete(Object id) {
        T entity = findById(id);
        em.remove(entity);
        return this;
    }

    public DAO<T> deleteAtomic(Object id) {
        this.openTransaction();
        try {
            this.delete(id);
        } catch (NotFoundException e) {
            this.rollbackTransaction();
            throw e;
        } catch (RollbackException e) {
            this.rollbackTransaction();
            throw e;
        } catch (Exception e) {
            this.rollbackTransaction();
            System.out.println(e.getClass().getName());
            throw new DbException(e.getMessage());
        }
        return this.closeTransaction();
    }

    public List<T> searchMany(String queryName,  Object... params) {
        TypedQuery<T> query = em.createNamedQuery(queryName, clazz);
        for (int i = 0; i < params.length; i += 2) {
            query.setParameter(params[i].toString(), params[i + 1]);
        }
        return query.getResultList();
    }

    public T searchOne(String nomeConsulta,  Object... params) {
        List<T> list = searchMany(nomeConsulta, params);
        return list.isEmpty() ? null : list.get(0);
    }

    public void close() {
        em.close();
    }
}
