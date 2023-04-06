package kg.edu.alatoo.demo.repository;

import kg.edu.alatoo.demo.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,String> {
}
