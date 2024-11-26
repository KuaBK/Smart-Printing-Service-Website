package hcmut.spss.be.service.impl;

import hcmut.spss.be.dtos.request.UpdateUserRequest;
import hcmut.spss.be.dtos.response.UpdateUserResponse;
import hcmut.spss.be.dtos.response.UserInfoResponse;
import hcmut.spss.be.entity.user.Role;
import hcmut.spss.be.entity.user.User;
import hcmut.spss.be.repository.UserRepository;
import hcmut.spss.be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void updateUserRole(Long userId, String roleName) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new RuntimeException("User not found"));
        Role role = Role.valueOf(roleName);
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public List<UserInfoResponse> getAllUsers() {
        List<User> accounts = userRepository.findAll();
        return accounts.stream().map(this::fromUser).collect(Collectors.toList());
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with email: " + email)));
    }

    @Override
    public UpdateUserResponse deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        userRepository.delete(user);
        return new UpdateUserResponse(
                userId,
                user.getUsername(),
                false,
                "User with id " + userId + " has been deleted successfully."
        );
    }


    @Override
    public UpdateUserResponse updateUserStatus(UpdateUserRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

        if (request.isEnable() == user.isEnabled()) {
            String currentStatus = user.isEnabled() ? "enabled" : "disabled";
            throw new IllegalStateException("User is already " + currentStatus + ".");
        }

        user.setEnabled(request.isEnable());
        userRepository.save(user);

        String status = user.isEnabled() ? "enabled" : "disabled";
        return UpdateUserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .enabled(user.isEnabled())
                .message("User with id " + user.getUserId() + " has been " + status + " successfully.")
                .build();
    }

    public UserInfoResponse fromUser(User user) {
        return UserInfoResponse.builder()
                .id(user.getUserId())
                .userName(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .mssv(user.getMssv())
                .numOfPrintingPages(user.getNumOfPrintingPages())
                .phone(user.getPhoneNumber())
                .avtUrl(user.getAvatarUrl())
                .role(user.getRole().name())
                .build();
    }
}
