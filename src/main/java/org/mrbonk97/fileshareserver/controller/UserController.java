package org.mrbonk97.fileshareserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrbonk97.fileshareserver.controller.request.ChangeUsernameRequest;
import org.mrbonk97.fileshareserver.model.User;
import org.mrbonk97.fileshareserver.service.UserService;
import org.mrbonk97.fileshareserver.utils.CookieUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> getUserInfo(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        log.info("유저: {} 자신 정보 조회", user.getId());
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/me")
    public void deleteUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        log.info("유저: {} 회원 탈퇴", user.getId());
        userService.deleteUser(user);
    }

    @PatchMapping("/me/change-name")
    public ResponseEntity<User> changeName(@RequestBody ChangeUsernameRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        userService.changeName(request.getUsername(), user);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/me/sign-out")
    public ResponseEntity<String> signOut() {
        log.info("유저 로그아웃");
        ResponseCookie cookie = CookieUtils.deleteRefreshToken();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE,cookie.toString())
                .body("로그아웃 완료");
    }
}
