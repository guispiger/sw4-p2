/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.escola.converters;

import br.com.escola.modelo.Curso;
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
@Named("cursoConverter")
@ApplicationScoped
public class CursoConverter implements Converter<Curso> {
    @PersistenceContext
    EntityManager em;

    @Override
    public Curso getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            Long id = Long.valueOf(value);
            return em.find(Curso.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Curso value) {
        if (value == null || value.getId() == null) {
            return null;
        } else {
            return String.valueOf(value.getId());
        }
    }
    
    
}
