package com.workshop.model;

import java.beans.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;
@Component
@Entity
@Table(name = "AdresType")
public class AdresType implements java.io.Serializable{
	private int adres_type_id;
    private String adres_type;
    //private static String[] types = {"Bezorgadres", "Factuuradres", "Bezoekadres"};
    
    public AdresType(){}
    
    public AdresType(int id) {
		this.adres_type_id = id;
	}

	@Id
    public int getAdres_type_id(){
        return this.adres_type_id;
    }
    
    public void setAdres_type_id(int id){
        this.adres_type_id = id;
    }
    
    @Column
    public String getAdres_type(){
        return this.adres_type;
    }
    
    public void setAdres_type(String adres_type){
        this.adres_type = adres_type;
    }
    /*
    public void setAdres_type(int adres_type){
        this.adres_type = types[adres_type];
    }
    
    @Transient
    public String[] getAllTypes(){
        return types;
    }
    
    private void setAllTypes(String[] allTypes){
        types = allTypes;
    }*/
}
