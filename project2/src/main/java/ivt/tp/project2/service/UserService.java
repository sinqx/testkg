package ivt.tp.project2.service;

import ivt.tp.project2.entity.User;
import ivt.tp.project2.model.AuthModel;

import java.util.List;

public interface UserService {
    User saveWithPasswordEncode(User user) throws Exception;
    User save(User user);
    List<User> getAllUsers();
    User findById(Long id);
    User findByLogin(String username);
    String getTokenByAuthModel(AuthModel authModel);
    String deleteById(Long id);
    List<User> findUsersByPartOfFullName(String name);
    User changeStatusById(Long id);
}
