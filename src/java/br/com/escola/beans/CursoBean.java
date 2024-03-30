/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package br.com.escola.beans;

import br.com.escola.modelo.Curso;
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
@Named(value = "cursoBean")
@SessionScoped
public class CursoBean implements Serializable {

    @PersistenceContext
    EntityManager em;

    @Resource
    UserTransaction utx;

    private Curso curso;

    private boolean editando = false;

    public CursoBean() {
        curso = new Curso();
    }

    public String confirmar() {
        try {
            utx.begin();
            if (!editando) {
                em.persist(curso);
            } else {
                curso = em.merge(curso);
            }
            utx.commit();
            editando = false;
            curso = new Curso();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String editar(Curso c) {
        curso = c;
        editando = true;

        return null;
    }

    public String excluir(Curso c) {
        try {
            utx.begin();
            c = em.merge(c);
            em.remove(c);
            utx.commit();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Curso n&atilde;o exclu&iacute;do", "N&atilde;o foi poss&iacute;vel excluir o Curso. " + c.getNome() + ". Devem haver turmas relacionadas."));
        }
        return null;
    }
    
    public List<Curso> getListarCursos(){
        try {
            return em.createQuery("SELECT c FROM Curso c ORDER BY c.nome").getResultList();
        }catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Curso>();
        }
    }
    
    public List<SelectItem> getItensCursos(){
        ArrayList<SelectItem> itens = new ArrayList<>();
        try {
            List<Curso> cursos = em.createQuery("SELECT c FROM Curso c ORDER BY c.nome").getResultList();
            
            itens.add(new SelectItem(null, "Selecione um Curso"));
            for (Curso curso : cursos) {
                itens.add(new SelectItem(curso, curso.getNome()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itens;
    }
    
    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

}
