/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.escola.converters;

import br.com.escola.modelo.Turma;
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
@Named("turmaConverter")
@ApplicationScoped
public class TurmaConverter implements Converter<Turma> {
    @PersistenceContext
    EntityManager em;

    @Override
    public Turma getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            Long id = Long.valueOf(value);
            return em.find(Turma.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Turma value) {
        if (value == null || value.getId() == null) {
            return null;
        } else {
            return String.valueOf(value.getId());
        }
    }
    
    
}
