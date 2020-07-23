/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.savioaraujo.vuttr.model;

import br.savioaraujo.vuttr.basic.model.BasicEntity;
import br.savioaraujo.vuttr.utils.Utils;
import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>Classe para cadastrado das ferramentas</p>
 *
 * @author evaldosavio
 */
@Entity
@Table(name = "tool")
@XmlRootElement
public class Tool extends BasicEntity {

    @Column
    private String title;
    @Column
    private String link;
    @Column
    private String description;
    @Expose
    @OneToMany
    @JoinTable(name = "tool_tags", inverseJoinColumns =
    @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

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

    public List<Tag> getTags() {
        if (Utils.isEmpty(this.tags)) {
            this.tags = new ArrayList<Tag>();
        }
        return this.tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (this.title != null ? this.title.hashCode() : 0);
        hash = 29 * hash + (this.link != null ? this.link.hashCode() : 0);
        hash = 29 * hash + (this.description != null ? this.description.hashCode() : 0);
        hash = 29 * hash + (this.tags != null ? this.tags.hashCode() : 0);
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
        final Tool other = (Tool) obj;
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
        return "Tool{" + "title=" + title + ", link=" + link + ", description=" + description + ", tags=" + tags + '}';
    }
}
