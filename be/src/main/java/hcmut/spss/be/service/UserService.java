package hcmut.spss.be.service;

import hcmut.spss.be.dtos.request.UpdateUserRequest;
import hcmut.spss.be.dtos.response.PrintJobResponse;
import hcmut.spss.be.dtos.response.UpdateUserResponse;
import hcmut.spss.be.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void updateUserRole(Long userId, String roleName);

    List<User> getAllUsers();

    User findByUsername(String username);

    Optional<User> findByEmail(String email);

    UpdateUserResponse deleteUser(Long userId);

    UpdateUserResponse updateUserStatus(UpdateUserRequest request);
}