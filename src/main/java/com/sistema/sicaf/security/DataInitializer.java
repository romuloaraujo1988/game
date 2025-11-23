package com.sistema.sicaf.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.sistema.sicaf.model.ERole;
import com.sistema.sicaf.model.Role;
import com.sistema.sicaf.model.User;
import com.sistema.sicaf.repository.RoleRepository;
import com.sistema.sicaf.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        // Initialize Roles
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role(null, ERole.ROLE_ADMIN));
            roleRepository.save(new Role(null, ERole.ROLE_MANAGER));
            roleRepository.save(new Role(null, ERole.ROLE_VET));
            roleRepository.save(new Role(null, ERole.ROLE_OPERATOR));
        }

        // Initialize Admin User
        if (!userRepository.existsByUsername("admin")) {
            User user = new User("admin", "admin@sicaf.com", encoder.encode("123456"));
            Set<Role> roles = new HashSet<>();
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
            user.setRoles(roles);
            userRepository.save(user);
        }
    }
}
