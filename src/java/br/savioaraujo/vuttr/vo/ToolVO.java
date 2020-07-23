/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.savioaraujo.vuttr.vo;

import br.savioaraujo.vuttr.model.Tag;
import br.savioaraujo.vuttr.model.Tool;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>VO para padronizar a formataçao de um objeto Tool</p>
 *
 * @author evaldosavio
 */
public class ToolVO {

    private Long id;
    private String title;
    private String link;
    private String description;
    private List<String> tags;

    public ToolVO(Tool tool) {
        this(tool.getId(), tool.getTitle(), tool.getLink(), tool.getDescription(), null);
        this.tags = new ArrayList< String>();
        for (Tag tag : tool.getTags()) {
            this.tags.add(tag.getName());
        }

    }

    public ToolVO(Long id, String title, String link, String description, List<String> tags) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 89 * hash + (this.title != null ? this.title.hashCode() : 0);
        hash = 89 * hash + (this.link != null ? this.link.hashCode() : 0);
        hash = 89 * hash + (this.description != null ? this.description.hashCode() : 0);
        hash = 89 * hash + (this.tags != null ? this.tags.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ToolVO other = (ToolVO) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.title == null) ? (other.title != null) : !this.title.equals(other.title)) {
            return false;
        }
        if ((this.link == null) ? (other.link != null) : !this.link.equals(other.link)) {
            return false;
        }
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
            return false;
        }
        if (this.tags != other.tags && (this.tags == null || !this.tags.equals(other.tags))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ToolVO{" + "id=" + id + ", title=" + title + ", link=" + link + ", description=" + description + ", tags=" + tags + '}';
    }
}
