package hcmut.spss.be.controller;

import hcmut.spss.be.dtos.request.UpdateUserRequest;
import hcmut.spss.be.dtos.response.ApiResponse;
import hcmut.spss.be.dtos.response.UpdateUserResponse;
import hcmut.spss.be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdateUserResponse>> deleteUser(@PathVariable Long id) {
        UpdateUserResponse response = userService.deleteUser(id);
        return ResponseEntity.ok(
                ApiResponse.<UpdateUserResponse>builder()
                        .status(200)
                        .message("Success")
                        .data(response)
                        .build()
        );
    }

    @PostMapping("/status")
    public ResponseEntity<?> updateUserStatus(@RequestBody UpdateUserRequest request) {
        try {
            UpdateUserResponse response = userService.updateUserStatus(request);
            return ResponseEntity.ok(ApiResponse.<UpdateUserResponse>builder()
                    .status(200)
                    .message("Success")
                    .data(response)
                    .build());
        } catch (IllegalStateException ex) {
            return ResponseEntity.badRequest().body(ApiResponse.<String>builder()
                    .status(400)
                    .message("Error")
                    .data(ex.getMessage())
                    .build());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).body(ApiResponse.<String>builder()
                    .status(404)
                    .message("Error")
                    .data(ex.getMessage())
                    .build());
        }
    }
}
