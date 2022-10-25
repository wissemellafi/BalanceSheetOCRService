package com.amenbank.bilan_ocr.service;

import com.amenbank.bilan_ocr.entity.Role;

import java.util.List;

public interface IRoleService {
    List<Role> findAll();
    Role findById(Integer id);
    Role save(Role role);
    void deleteById(Integer id);
    Role findByRole(String role);
}
