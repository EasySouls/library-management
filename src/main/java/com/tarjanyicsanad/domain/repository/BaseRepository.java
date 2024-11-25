package com.tarjanyicsanad.domain.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

/**
 * A base repository class that provides basic CRUD operations for entities.
 * @param <T> the entity type
 * @param <D> the type of the entity's id
 * @param <E> the type of exception that can be thrown
 */
public abstract class BaseRepository<T, D, E extends Exception> {
    private final Class<T> entityClass;
    protected final EntityManager entityManager;

    protected BaseRepository(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    /**
     * Finds an entity by its id.
     * @param id the id of the entity to find
     * @return the entity with the given id, or null if not found
     */
    public T findById(D id) {
        return entityManager.find(entityClass, id);
    }

    /**
     * Finds all entities of the repository's entity type.
     * @return a list of all entities
     */
    public List<T> findAll() {
        return entityManager.createQuery("FROM " + entityClass.getName(), entityClass).getResultList();
    }

    /**
     * Saves the given entity to the repository.
     * @param entity the entity to save
     */
    public void save(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            // Check if the entity is already managed
            if (!entityManager.contains(entity)) {
                entity = entityManager.merge(entity);
            }

            entityManager.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }

    /**
     * Updates the given entity in the repository.
     * @param entity the entity to update
     */
    public void update(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }

    /**
     * Deletes the given entity from the repository.
     * Guarantees to roll back the transaction if an exception is thrown.
     * @param entity the entity to delete
     */
    public void delete(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }

    /**
     * Deletes the entity with the given id from the repository.
     * @param id the id of the entity to delete
     */
    public void deleteById(D id) {
        T entity = findById(id);
        if (entity != null) {
            delete(entity);
        }
    }
}
