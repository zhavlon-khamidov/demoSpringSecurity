package kg.edu.alatoo.demo.services;

import kg.edu.alatoo.demo.UserExistsException;
import kg.edu.alatoo.demo.config.CustomUserDetails;
import kg.edu.alatoo.demo.model.User;
import kg.edu.alatoo.demo.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUserDetails(
                userRepository.findByUsername(username).orElse(new User()));
    }


    public User createUser(User user) throws RuntimeException, UserExistsException {
        if (user.getId() != null) throw new RuntimeException("in oder to create user id must not to be defined");
        user.setPassword(encoder.encode(user.getPassword()));
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserExistsException(user.getUsername());
        }
    }
}
