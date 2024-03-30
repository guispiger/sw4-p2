/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package br.com.escola.beans;

import br.com.escola.modelo.Professor;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author guispiger
 */
@Named(value = "profBean")
@SessionScoped
public class ProfessorBean implements Serializable {

    @PersistenceContext
    EntityManager em;

    @Resource
    UserTransaction utx;

    private Professor professor;
    private boolean editando = false;

    public ProfessorBean() {
        professor = new Professor();
    }

    public String confirmar() {
        try {
            utx.begin();
            if (!editando) {
                em.persist(professor);
            } else {
                professor = em.merge(professor);
            }
            utx.commit();
            editando = false;
            professor = new Professor();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Professor> getListaProfessores() {
        try {
            return em.createQuery("SELECT p FROM Professor p ORDER BY p.nome").getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Professor>();
        }
    }

    public String excluir(Professor p) {
        try {
            utx.begin();
            p = em.merge(p);
            em.remove(p);
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Professor não exclu&iacute;do",
                            "Não foi possível excluir o Professor."));
        }

        return null;
    }

    public String editar(Professor p) {
        this.editando = true;
        professor = p;
        return null;
    }
    
    public List<SelectItem> getItensProfessor(){
        ArrayList<SelectItem> itens = new ArrayList<>();
        try {
            List<Professor> professores = em.createQuery("SELECT p FROM Professor p ORDER BY p.nome").getResultList();
            
            itens.add(new SelectItem(null, "Selecione um Professor"));
            for (Professor professor : professores) {
                itens.add(new SelectItem(professor, professor.getNome()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itens;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

}
