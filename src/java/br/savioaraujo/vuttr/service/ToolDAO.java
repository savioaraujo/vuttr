/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.savioaraujo.vuttr.service;

import br.savioaraujo.vuttr.basic.service.GenericDAOImpl;
import br.savioaraujo.vuttr.model.Tool;
import br.savioaraujo.vuttr.utils.Utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * <p> DAO para a classe Tag </p>
 *
 * @author evaldosavio
 */
@Stateless
public class ToolDAO extends GenericDAOImpl<Tool> {

    /**
     * <p> Consulta as Tools no sistema, caso seja informado o parametro tags a
     * consulta ira filtrar as Tools que possuam aquela tag </p>
     *
     * @param tags : Tag para efetuar a consulta
     * @return Lista contendo todas as Tools que possuam todas as tags listadas,
     * caso nao seja informado as tags a consulta retornara todas as Tools
     */
    public List<Tool> findByTags(String[] tags) {
        String sql = "SELECT tool "
                + " FROM Tool tool "
                + " LEFT JOIN tool.tags tag"
                + " WHERE tool.active = TRUE";
        Map<String, String> params = new HashMap<String, String>();
        if (tags != null) {
            int paramNumber = 0;
            for (String tag : tags) {
                sql += " AND lower(tag.name) = :param" + paramNumber;
                params.put("param" + paramNumber++, tag);
            }
        }
        Query query = getEntityManager().createQuery(sql);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }

    public Tool verifyExists(Tool tool) {
        String sql = "SELECT tool FROM Tool tool WHERE tool.active = TRUE AND lower(tool.title) = :title";
        Query query = getEntityManager().createQuery(sql);
        query.setParameter("title", tool.getTitle().trim().toLowerCase());
        List<Tool> result = query.getResultList();
        if (Utils.isEmpty(result)) {
            return tool;
        } else {
            return result.get(0);
        }
    }
}
