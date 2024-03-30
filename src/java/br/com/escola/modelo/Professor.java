/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.escola.modelo;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author aluno
 */
@Entity
public class Professor extends Pessoa implements Serializable {

    private double valorHora;

    public Double getValorHora() {
        return valorHora;
    }

    public void setValorHora(Double valorHora) {
        this.valorHora = valorHora;
    }

}
