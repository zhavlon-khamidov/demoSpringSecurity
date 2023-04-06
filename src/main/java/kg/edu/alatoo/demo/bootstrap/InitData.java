package kg.edu.alatoo.demo.bootstrap;

import kg.edu.alatoo.demo.model.Role;
import kg.edu.alatoo.demo.model.User;
import kg.edu.alatoo.demo.repository.RoleRepository;
import kg.edu.alatoo.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitData implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public InitData(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public void createUsers() {

        Role userRole = new Role("USER");
        Role adminRole = new Role("ADMIN");

        roleRepository.saveAll(List.of(userRole, adminRole));

        User user = new User();
        user.setUsername("user");
        user.setPassword(encoder.encode("user"));
        user.getRoles().add(userRole);
        userRepository.save(user);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(encoder.encode("admin"));
        admin.getRoles().add(adminRole);
        userRepository.save(admin);
    }


    @Override
    public void run(String... args) throws Exception {
        log.info("InitData running");
        createUsers();
    }
}
