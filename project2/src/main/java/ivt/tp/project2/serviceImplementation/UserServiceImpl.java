package ivt.tp.project2.serviceImplementation;

import ivt.tp.project2.entity.User;
import ivt.tp.project2.entity.UserRole;
import ivt.tp.project2.exception.AuthException;
import ivt.tp.project2.exception.ObjectNotFoundException;
import ivt.tp.project2.model.AuthModel;
import ivt.tp.project2.repository.UserRepository;
import ivt.tp.project2.service.UserRoleService;
import ivt.tp.project2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User saveWithPasswordEncode(User user) {
        Optional<User> userLoginCheck = userRepository.findByLogin(user.getLogin());
        Optional<User> userEmailCheck = userRepository.findByEmail(user.getEmail());

        if (userLoginCheck.isPresent()) {
            throw new AuthException("User with login \"" + user.getLogin() + "\" already exists.");
        } else if (userEmailCheck.isPresent()) {
            throw new AuthException("User with this email is already exists.");
        } else {
            user.setCreationDate(LocalDateTime.now());
            user.setStatus((long) 1);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user = userRepository.save(user);
            user.setCreationDate(LocalDateTime.now());
            user.setPhotoId((long)3);
            UserRole userRole = new UserRole();
            userRole.setRoleName("ROLE_USER");
            userRole.setUser(user);
            userRoleService.save(userRole);
            return user;
        }
    }

    @Override
    public User findByLogin(String username) {
        if(!username.equals("anonymousUser")){
        return userRepository.findByLogin(username).orElseThrow(()
                -> new ObjectNotFoundException("People with that name not found: ", username));}
        else {
            return null;
        }
    }

    @Override
    public String getTokenByAuthModel(AuthModel authModel) {
        String authResult = "";
        User user = findByLogin(authModel.getLogin());
        if (user == null) authResult = "Wrong login or password";
        else {
            if (passwordEncoder.matches(authModel.getPassword(), user.getPassword())) {
                String loginPassPair = user.getLogin() + ":" + authModel.getPassword();
                authResult = "Basic " + Base64.getEncoder().encodeToString(loginPassPair.getBytes());
            } else authResult = "Wrong login or password";
        }
        return authResult;
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (NullPointerException ignored) {
            throw new ObjectNotFoundException("List is empty");
        }
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User with id \"" + id + "\" doesn't exist"));
    }

    @Override
    public String deleteById(Long id) {
        User user = findById(id);
        if (user == null) {
            throw new ObjectNotFoundException("User with id \"" + id + "\" doesn't exist");
        } else {
            userRoleService.deleteById(id);
            userRepository.delete(user);
            return "user with id \"" + id + "\" is deleted";
        }
    }

    @Override
    public List<User> findUsersByPartOfFullName(String name) {
        List<User> users = userRepository.findByFullNameContainingIgnoringCase(name);
        if (users.isEmpty()) {
            throw new ObjectNotFoundException("User with a such name not found");
        } else
            return users;
    }

    @Override
    public User changeStatusById(Long id) {
        User user = findById(id);
        if (user != null && user.getStatus() == 1) {
            user.setStatus(0L);
            return save(user);
        } else if (user != null && user.getStatus() == 0) {
            user.setStatus(1L);
            return save(user);
        } else {
            throw new ObjectNotFoundException("User with id \"" + id + "\" doesn't exist");
        }
    }
}
