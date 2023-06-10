package rs.raf.demo.user;

import rs.raf.demo.dto.update.UserUpdateDto;
import rs.raf.demo.security.AuthService;
import rs.raf.demo.enums.ReqException;
import rs.raf.demo.models.User;
import rs.raf.demo.security.SecurityService;
import rs.raf.demo.user.repository.UserRepository;

import javax.inject.Inject;
import java.util.List;

public class UserService {

    @Inject
    private UserRepository userRepository;
    @Inject
    private AuthService authService;

    public User addOne(User user) throws ReqException {
        User existingUser = this.userRepository.getOne(user.getEmail());
        if(existingUser != null){
            throw new ReqException("User with same email already exists", 400);
        }
        user.setPassword(this.authService.hashPassword(user.getPassword()));
        return this.userRepository.addOne(user);
    }

    public List<User> getAll() { return this.userRepository.getAll(); }

    public User getOne(Integer id) { return this.userRepository.getOne(id); }

    public void deleteOne(Integer id) { this.userRepository.deleteOne(id);}

    public User getOne(String email, String password) { return this.userRepository.getOne(email, password); }

    public void updateOne(Integer id, UserUpdateDto user){ this.userRepository.updateOne(id, user); }
}
