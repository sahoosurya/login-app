package com.example.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/")
    public String home() {
        return "<h2>Login API Running</h2>";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password) {

        String sql = "SELECT COUNT(*) FROM users WHERE username=? AND password=?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username, password);

        if (count != null && count > 0) {
            return "Login Successful";
        }
        return "Invalid Credentials";
    }
}

