/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.savioaraujo.vuttr.basic.service;

import br.savioaraujo.vuttr.basic.model.BasicEntity;
import br.savioaraujo.vuttr.basic.interfaces.DAO;
import br.savioaraujo.vuttr.utils.ReflectionUtils;
import br.savioaraujo.vuttr.utils.Utils;
import java.lang.reflect.Field;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * <p> Implementaçao das operaçoes basicas de um DAO </p>
 *
 * @author evaldosavio
 * @param <T>
 */
public abstract class GenericDAOImpl<T extends BasicEntity> implements DAO<T> {

    public String innerJoin;
    protected final Class klass;
    private String nameSql;
    @PersistenceContext(name = "vuttr")
    private EntityManager entityManager;

    public GenericDAOImpl() {
        klass = ReflectionUtils.getGenericClass(getClass());
        String name = klass.getSimpleName();
        nameSql = name.substring(0, 1).toLowerCase() + name.substring(1, name.length());
    }

    /**
     * <p> Carrega a determinada entidade do banco. </p>
     *
     * @param bean
     * @return
     */
    @Override
    public T load(String id) {
        if (Utils.isEmpty(id)) {
            return null;
        }

        try {
            return load(Long.parseLong(id));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * <p> Carrega a determinada entidade do banco. </p>
     *
     * @param bean
     * @return
     */
    @Override
    public T load(T bean) {
        if (bean == null) {
            return null;
        } else if (bean.getId() == null) {
            return null;
        }
        final T beanLoaded = load(bean.getId());

        return beanLoaded;
    }

    /**
     * <p> Carrega a determinada entidade do banco. </p>
     *
     * @param id
     * @return
     */
    @Override
    public T load(Long id) {
        if (id == null) {
            return null;
        }
        Query query = getEntityManager().createQuery(String.format("SELECT b FROM %s b WHERE b.id = :id and b.active = true", klass.getSimpleName()));
        query.setParameter("id", id);
        List<T> result = query.getResultList();
        if (Utils.isEmpty(result)) {
            return null;
        }
        T load = result.get(0);
        getEntityManager().refresh(load);
        return load;
    }

    /**
     * <p> Efetua o load de os dados do banco</p>
     *
     * @return
     */
    public List<T> findAll() {
        Query query = getEntityManager().createQuery(String.format("SELECT b FROM %s b WHERE b.active = true ORDER BY b.id", klass.getSimpleName()));
        List<T> result = query.getResultList();
        return result;
    }

    /**
     * <p> Remove permanentemente uma dado bean </p>
     *
     * @param bean
     */
    @Override
    public void delete(T bean) {
        getEntityManager().remove(bean);
    }

    /**
     * <p> Efetua a inativação de um dado bean. </p>
     *
     * @param bean
     */
    @Override
    public void inactivate(T bean) {
        bean.setActive(false);
        update(bean);
    }

    /**
     * <p> Método reponsável por efetuar a persitencia e atualização do bean no
     * banco. </p>
     *
     * @param bean
     */
    @Override
    public void save(T bean) {
        getEntityManager().persist(bean);
        getEntityManager().flush();
    }

    /**
     * <p> Varifica a existencia de uma entidade com o mesmo valor no campo</p>
     *
     * @param bean
     * @param field
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    protected boolean exists(T bean, Field field) throws IllegalArgumentException, IllegalAccessException {
        Query query = getEntityManager().createQuery(String.format("SELECT b FROM %s b WHERE b.%s = :%s and b.active = true", klass.getSimpleName(), field.getName(), field.getName()));
        query.setParameter(field.getName(), field.get(bean));
        List<T> result = query.getResultList();
        return (!Utils.isEmpty(result) && bean.getId() == null)
                || (!Utils.isEmpty(result) && !result.get(0).getId().equals(bean.getId()));
    }

    /**
     * <p> Método responsável por atualizar um bean </p>
     *
     * @param bean
     */
    @Override
    public T update(T bean) {
        getEntityManager().merge(bean);
        return load(bean);
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }
}
