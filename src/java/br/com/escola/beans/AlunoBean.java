/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package br.com.escola.beans;

import br.com.escola.modelo.Aluno;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author guispiger
 */
@Named(value = "alunoBean")
@SessionScoped
public class AlunoBean implements Serializable {

    @PersistenceContext
    EntityManager em;

    @Resource
    UserTransaction utx;

    private Aluno aluno;

    private boolean editando = false;

    public AlunoBean() {
        aluno = new Aluno();
    }

    public String confirmar() {
        try {
            utx.begin();
            if (!editando) {
                em.persist(aluno);
            } else {
                aluno = em.merge(aluno);
            }
            utx.commit();
            editando = false;
            aluno = new Aluno();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Aluno> getListaAlunos() {
        try {
            return em.createQuery("SELECT a FROM Aluno a ORDER BY a.nome").getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Aluno>();
        }
    }

    public String editar(Aluno a) {
        aluno = a;
        editando = true;
        return null;
    }

    public String excluir(Aluno a) {
        try {
            utx.begin();
            a = em.merge(a);
            em.remove(a);
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Aluno não exclu&iacute;do",
                            "Não foi possível excluir o Aluno: CPF(" + a.getCpf() + "); Nome: " + a.getNome()));
        }

        return null;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

}
