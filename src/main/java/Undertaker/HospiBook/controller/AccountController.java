package Undertaker.HospiBook.controller;

import Undertaker.HospiBook.component.JwtUtil;
import Undertaker.HospiBook.model.entities.Person;
import Undertaker.HospiBook.service.AccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.catalina.webresources.JarWarResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "accounts")
@SecurityRequirement(name = "bearerAuth")
public class AccountController {
    private final AccountService accountService;
    private final JwtUtil jwtUtil;

    public AccountController(AccountService accountService, JwtUtil jwtUtil) {
        this.accountService = accountService;
        this.jwtUtil = jwtUtil;
    }

    @PutMapping(path = "{email}/lock")
    public ResponseEntity<Person> lockUser(@PathVariable String email){
        return ResponseEntity.ok(this.accountService.lockOrUnlock(email, false));
    }

    @PutMapping(path = "{email}/unlock")
    public ResponseEntity<Person> unlockUser(@PathVariable String email){
        return ResponseEntity.ok(this.accountService.lockOrUnlock(email, true));
    }

    @GetMapping("/check/{email}")
    public ResponseEntity<Boolean> isUserOnline (@PathVariable String email) {
        return ResponseEntity.ok(this.jwtUtil.isUserOnline(email));
    }

    @GetMapping("/online-users")
    public ResponseEntity<Set<String>> getOnlineUsers () {
        return ResponseEntity.ok(this.jwtUtil.getOnlineUsers());
    }
}
