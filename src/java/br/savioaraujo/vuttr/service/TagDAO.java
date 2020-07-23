/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.savioaraujo.vuttr.service;

import br.savioaraujo.vuttr.basic.service.GenericDAOImpl;
import br.savioaraujo.vuttr.model.Tag;
import br.savioaraujo.vuttr.utils.Utils;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * <p> DAO para a classe Tool </p>
 *
 * @author evaldosavio
 */
@Stateless
public class TagDAO extends GenericDAOImpl<Tag> {

    public Tag find(String name) {
        String sql = "SELECT tag FROM Tag tag WHERE tag.active = TRUE ";
        if (!Utils.isEmpty(name)) {
            sql += " AND lower(tag.name) = :name";
        }
        Query query = getEntityManager().createQuery(sql);
        if (!Utils.isEmpty(name)) {
            query.setParameter("name", name.trim().toLowerCase());
        }
        List<Tag> result = query.getResultList();
        if (!Utils.isEmpty(result)) {
            return result.get(0);
        } else {
            return null;
        }
    }
}
