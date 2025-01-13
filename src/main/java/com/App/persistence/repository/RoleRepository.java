
package com.App.persistence.repository;

import com.App.persistence.entity.RoleEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author ANTONIO
 */
public interface RoleRepository  extends CrudRepository<RoleEntity, Long>{
    
    List<RoleEntity> findRoleEntitiesByRoleEnumIn(List<String> rolename);
    
}
