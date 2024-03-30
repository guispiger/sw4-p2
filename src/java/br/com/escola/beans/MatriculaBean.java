/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package br.com.escola.beans;

import br.com.escola.modelo.Aluno;
import br.com.escola.modelo.Curso;
import br.com.escola.modelo.Matricula;
import br.com.escola.modelo.Turma;
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
@Named(value = "matriculaBean")
@SessionScoped
public class MatriculaBean implements Serializable {

    @PersistenceContext
    EntityManager em;

    @Resource
    UserTransaction utx;

    private Matricula matricula;

    private boolean editando = false;

    public MatriculaBean() {
        matricula = new Matricula();
    }

    public String confirmar() {
        if (matricula.getTurma().getVagas() == 0) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Essa turma já está lotada",
                            "Não há mais vagas nessa turma."));
        } else {
            try {
                utx.begin();
                if (!editando) {
                    em.persist(matricula);
                } else {
                    matricula = em.merge(matricula);
                }
                utx.commit();
                editando = false;
                matricula = new Matricula();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String editar(Matricula m) {
        matricula = m;
        editando = true;

        return null;
    }

    public String excluir(Matricula m) {
        try {
            utx.begin();
            m = em.merge(m);
            em.remove(m);
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Matricula não exclu&iacute;da",
                            "Não foi possível excluir a matricula."));
        }
        return null;
    }

    public List<Matricula> getListaMatriculas() {
        try {
            return em.createQuery("SELECT m FROM Matricula m ORDER BY m.data").getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Matricula>();
        }
    }

    public String buscarAluno() {
        if (matricula.getAluno() == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Aluno com não localizado!",
                            "Por favor cadastre antes de matricula-lo"));
            return null;
        } else {
            return "matricula.jsp";
        }
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

}
