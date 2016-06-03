package com.workshop.dao;

import java.io.Serializable;
import java.util.List;


import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface GenericDao <T, ID extends Serializable> extends Repository<T, ID>{ 
    
   List<T> findAll();
   void delete(long id);
   T findOne(ID id);
   <S extends T> S save(S entity);
}

