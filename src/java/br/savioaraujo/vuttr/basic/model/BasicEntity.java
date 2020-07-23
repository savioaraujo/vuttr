package br.savioaraujo.vuttr.basic.model;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * <p>Contém os atributos necessários para se criar uma entidade persistente</p>
 *
 * @author evaldosavio
 */
@XmlAccessorType(XmlAccessType.FIELD)
@MappedSuperclass
public abstract class BasicEntity implements Serializable {

    private static final long serialVersionUID = 2806421523585360625L;
    @XmlTransient
    private boolean active;
    @XmlTransient
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long id;

    public BasicEntity() {
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BasicEntity other = (BasicEntity) obj;
        if (this.active != other.active) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.active ? 1 : 0);
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    public String logStr() {
        return getClass().getSimpleName().concat(" [ Id : ").concat(String.valueOf(getId())).concat(" ]");
    }
}
