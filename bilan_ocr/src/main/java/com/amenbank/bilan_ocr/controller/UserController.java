package com.amenbank.bilan_ocr.controller;

import com.amenbank.bilan_ocr.dto.page.PageDto;
import com.amenbank.bilan_ocr.dto.user.UserDto;
import com.amenbank.bilan_ocr.dto.user.UserRequest;
import com.amenbank.bilan_ocr.dto.user.UserResponse;
import com.amenbank.bilan_ocr.entity.Role;
import com.amenbank.bilan_ocr.entity.User;
import com.amenbank.bilan_ocr.service.IRoleService;
import com.amenbank.bilan_ocr.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final IUserService userService;
    private final IRoleService roleService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(IUserService userService, IRoleService roleService, ModelMapper modelMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<PageDto<UserResponse>> getUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        var usersPage = userService.findAll(PageRequest.of(page, size));

        var users = usersPage.getContent()
                .stream().map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());

        var pageData = new PageDto<>(usersPage.getTotalElements(), users);

        return ResponseEntity.ok(pageData);
    }

    @GetMapping("{id}")
    public UserResponse getUser(@PathVariable Integer id) {
        return modelMapper.map(userService.findById(id), UserResponse.class);
    }

    @PostMapping
    public UserResponse saveUser(@Valid @RequestBody UserRequest userRequest) {
        var role = fetchRole(userRequest.getRole());
        var user = modelMapper.map(userRequest, User.class);
        user.setRole(role);

        return modelMapper.map(userService.save(user), UserResponse.class);
    }

    @PutMapping("{id}")
    public UserResponse updateUser(@PathVariable Integer id, @RequestBody UserDto userRequest) {
        var role = fetchRole(userRequest.getRole());
        var user = modelMapper.map(userRequest, User.class);
        user.setId(id);
        user.setRole(role);

        return modelMapper.map(userService.update(user), UserResponse.class);
    }

    @PutMapping("/change-password/{id}")
    public UserResponse changePassword(@PathVariable Integer id, @RequestBody() String password) {
        return modelMapper.map(userService.changePassword(id, password), UserResponse.class);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteById(id);
    }

    private Role fetchRole(String role) {
        return roleService.findByRole(role);
    }
}
