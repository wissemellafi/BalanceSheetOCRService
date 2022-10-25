package com.amenbank.bilan_ocr.service;

import com.amenbank.bilan_ocr.entity.User;
import com.amenbank.bilan_ocr.exception.DuplicatedEntityException;
import com.amenbank.bilan_ocr.exception.NotFoundException;
import com.amenbank.bilan_ocr.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    }

    @Override
    public User save(User user) {
        if (existsByUsername(user.getUsername()))
            throw new DuplicatedEntityException("Found another user with username " + user.getUsername());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        var currentUser = findById(user.getId());

        if (!currentUser.getUsername().equals(user.getUsername()) && existsByUsername(user.getUsername())) {
            throw new DuplicatedEntityException("Found another user with username " + user.getUsername());
        }

        currentUser.setUsername(user.getUsername());
        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setRole(user.getRole());
        currentUser.setEnabled(user.isEnabled());

        return userRepository.save(currentUser);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User with username " + username + " not found"));
    }

    @Override
    public User changePassword(Integer id, String password) {
        var user = findById(id);

        user.setPassword(passwordEncoder.encode(password));

        return userRepository.save(user);
    }
}
