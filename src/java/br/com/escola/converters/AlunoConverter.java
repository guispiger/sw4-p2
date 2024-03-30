/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.escola.converters;

import br.com.escola.modelo.Aluno;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author guispiger
 */
@Named("alunoConverter")
@ApplicationScoped
public class AlunoConverter implements Converter<Aluno> {
    
    @PersistenceContext
    EntityManager em;

    @Override
    public Aluno getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            String cpf = String.valueOf(value);
            return em.find(Aluno.class, cpf);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Aluno value) {
        if (value == null || value.getCpf() == null) {
            return null;
        } else {
            return String.valueOf(value.getCpf());
        }
    }
    
    
}
