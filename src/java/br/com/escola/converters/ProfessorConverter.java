/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.escola.converters;

import br.com.escola.modelo.Professor;
import javax.enterprise.context.ApplicationScoped;
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
@Named("profConverter")
@ApplicationScoped
public class ProfessorConverter implements Converter<Professor> {
    
    @PersistenceContext
    EntityManager em;

    @Override
    public Professor getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            String cpf = String.valueOf(value);
            return em.find(Professor.class, cpf);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Professor value) {
        if (value == null || value.getCpf() == null) {
            return null;
        } else {
            return String.valueOf(value.getCpf());
        }
    }
    
    
}
