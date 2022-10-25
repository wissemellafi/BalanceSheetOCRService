package com.amenbank.bilan_ocr;

import com.amenbank.bilan_ocr.entity.Role;
import com.amenbank.bilan_ocr.entity.User;
import com.amenbank.bilan_ocr.repository.RoleRepository;
import com.amenbank.bilan_ocr.service.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BilanOcrApplicationTests {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private IUserService userService;

    @Test
    void createRoles() {
        var admin = new Role();
        admin.setRole("ADMIN");

        var user = new Role();
        user.setRole("USER");

        roleRepository.saveAll(List.of(admin, user));
    }

    @Test
    void createAdmin() {
        var adminRole = roleRepository.findByRole("ADMIN");

        var user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setEnabled(true);

        Assertions.assertTrue(adminRole.isPresent());

        user.setRole(adminRole.get());

        userService.save(user);
    }
}
