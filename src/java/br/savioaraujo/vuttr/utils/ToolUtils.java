/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.savioaraujo.vuttr.utils;

import br.savioaraujo.vuttr.model.Tag;
import br.savioaraujo.vuttr.model.Tool;
import br.savioaraujo.vuttr.service.TagDAO;
import br.savioaraujo.vuttr.vo.ToolVO;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Efetua operaçoes com a classe Tool</p>
 *
 * @author evaldosavio
 */
public class ToolUtils {

    /**
     * <p> Efetua o parse da lista de Tools para uma lista de ToolVo</p>
     *
     * @param tools : Lista de Tool
     * @return Lista de ToolVO
     */
    public static List<ToolVO> parseToVO(List<Tool> tools) {
        List<ToolVO> list = new ArrayList<ToolVO>();
        for (Tool tool : tools) {
            list.add(new ToolVO(tool));
        }
        return list;
    }

    /**
     * <p> Efetua o parse da lista de Tools para uma lista de ToolVo</p>
     *
     * @param tools : Lista de Tool
     * @return Lista de ToolVO
     */
    public static Tool getInstaceByToolVO(ToolVO toolVO, TagDAO tagDAO) {
        Tool tool = new Tool();
        tool.setTitle(toolVO.getTitle());
        tool.setLink(toolVO.getTitle());
        tool.setDescription(toolVO.getTitle());

        for (String tagName : toolVO.getTags()) {
            Tag tag = tagDAO.find(tagName);
            if (tag == null) {
                tag = new Tag(tagName);
                tagDAO.save(tag);
            }
            tool.getTags().add(tag);
        }

        return tool;
    }
}
