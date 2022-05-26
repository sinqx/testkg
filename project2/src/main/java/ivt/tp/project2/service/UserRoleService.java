package ivt.tp.project2.service;


import ivt.tp.project2.entity.UserRole;
import ivt.tp.project2.model.RoleModel;

public interface UserRoleService {
    UserRole save(UserRole userRole);
    UserRole save(RoleModel userRoleModel);
    UserRole findById(Long id);
    void deleteById(Long id);
}
