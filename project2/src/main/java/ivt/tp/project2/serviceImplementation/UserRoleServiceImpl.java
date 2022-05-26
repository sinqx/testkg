package ivt.tp.project2.serviceImplementation;

import ivt.tp.project2.entity.User;
import ivt.tp.project2.entity.UserRole;
import ivt.tp.project2.model.RoleModel;
import ivt.tp.project2.repository.UserRoleRepository;
import ivt.tp.project2.service.UserRoleService;
import ivt.tp.project2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;

    private UserService userService; //Без аннотации Autowired - потому что ругается на зациклинность. Проблема новой версии Spring

    @Override
    public UserRole save(@Lazy UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    @Override
    public UserRole save(RoleModel userRoleModel) {
        UserRole userRole = new UserRole();
        userRole.setRoleName(userRoleModel.getRoleName());
        User user = userService.findById(userRoleModel.getUserId());

        if(user == null) throw new IllegalArgumentException("Пользователь с ID " + userRoleModel.getUserId() + " не найден");
        userRole.setUser(user);

        return userRoleRepository.save(userRole);
    }

    @Override
    public UserRole findById(Long id) {
        return userRoleRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        userRoleRepository.deleteById(id);
    }
}
