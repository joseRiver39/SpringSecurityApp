package com.App.persistence.repository;

import com.App.persistence.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ANTONIO
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>{
    
    Optional<UserEntity> findUserEntityByUserName(String username);
}
