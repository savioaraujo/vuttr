/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.savioaraujo.vuttr.basic.interfaces;

import br.savioaraujo.vuttr.basic.model.BasicEntity;

/**
 * <p> Abstração de um DAO. </p>
 *
 * @author evaldosavio
 * @param <T>
 */
public interface DAO<T extends BasicEntity> {

    public T load(Long id);

    public T load(T bean);

    public T load(String id);

    public void save(T bean);

    public T update(T bean);

    public void inactivate(T bean);

    public void delete(T bean);
}
