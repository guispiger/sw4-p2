/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package br.com.escola.beans;

import br.com.escola.modelo.Turma;
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
@Named(value = "turmaBean")
@SessionScoped
public class TurmaBean implements Serializable {
    @PersistenceContext
    EntityManager em;

    @Resource
    UserTransaction utx;

    private Turma turma;
    
    private boolean editando = false;
    
    public TurmaBean() {
        turma = new Turma();
    }

    public String confirmar(){
        int ch = turma.getCurso().getCargaHoraria();
        Double valorHora = turma.getProfessor().getValorHora();
        int vagas = turma.getVagas();
        turma.setValor((ch * (100 + valorHora) * 2.5)/vagas);
        try {
            utx.begin();
            if (!editando) {
                em.persist(turma);
            } else {
                turma = em.merge(turma);
            }
            utx.commit();
            editando = false;
            turma = new Turma();                   
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String editar(Turma t){
        turma = t;
        editando = true;
        
        return null;
    }
    
    public String excluir(Turma t){
        try {
            utx.begin();
            t = em.merge(t);
            em.remove(t);
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Turma não exclu&iacute;da",
                            "Não foi possível excluir a Turma: " + t.getId() + "."));
        }
        return null;
    }
    
    public List<Turma> getListaTurmas(){
        try {
            return em.createQuery("SELECT t FROM Turma t WHERE t.vagas > 0 ORDER BY t.id").getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Turma>();
        }
    }
    
    public List<SelectItem> getItensTurma(){
        ArrayList<SelectItem> itens = new ArrayList<>();
        try {
            List<Turma> turmas = em.createQuery("SELECT t FROM Turma t ORDER BY t.id").getResultList();
            
            itens.add(new SelectItem(null, "Selecione uma Turma"));
            for (Turma turma : turmas) {
                itens.add(new SelectItem(turma, turma.getId() + '-' + turma.getCurso().getNome()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itens;
    }
    
    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }
    
    
    
}
