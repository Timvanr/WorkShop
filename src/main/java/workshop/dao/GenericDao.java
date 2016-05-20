package workshop.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericDao <T, ID extends Serializable> extends CrudRepository<T, ID>{ 
    
   List<T> findAll();
   void delete(T deleted);
   T findOne(ID id);
   <S extends T> S save(S entity);
}

