/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.savioaraujo.vuttr.model;

import br.savioaraujo.vuttr.basic.model.BasicEntity;
import com.google.gson.annotations.Expose;
import javax.persistence.Entity;

/**
 * <p>Classe para montar a base com todas as tags que possam ser cadastradas
 * </p> <p> Principal motivo eh evitar o cadastrado em duplicidade</p>
 *
 * @author evaldosavio
 */
@Entity
public class Tag extends BasicEntity {

    @Expose
    private String name;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.name != null ? this.name.hashCode() : 0);
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
        final Tag other = (Tag) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
