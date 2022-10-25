package com.amenbank.bilan_ocr.service;

import com.amenbank.bilan_ocr.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    Page<User> findAll(Pageable pageable);
    User findById(Integer id);
    User save(User user);
    User update(User user);
    void deleteById(Integer id);
    boolean existsByUsername(String username);
    User findByUsername(String username);
    User changePassword(Integer id, String password);
}
