package com.btvn.projectfinal.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LoginAttemptService {

    // Số lần sai tối đa
    private static final int MAX_ATTEMPTS = 5;

    // Lưu số lần sai theo username
    private final ConcurrentHashMap<String, AtomicInteger> attempts = new ConcurrentHashMap<>();

    // Gọi khi đăng nhập THẤT BẠI
    public void loginFailed(String username) {
        attempts.computeIfAbsent(username, k -> new AtomicInteger(0))
                .incrementAndGet();
    }

    // Gọi khi đăng nhập THÀNH CÔNG → reset về 0
    public void loginSucceeded(String username) {
        attempts.remove(username);
    }

    // Kiểm tra có bị khóa không
    public boolean isBlocked(String username) {
        AtomicInteger count = attempts.get(username);
        return count != null && count.get() >= MAX_ATTEMPTS;
    }

    // Lấy số lần đã sai
    public int getAttempts(String username) {
        AtomicInteger count = attempts.get(username);
        return count != null ? count.get() : 0;
    }

    // Số lần còn lại
    public int getRemainingAttempts(String username) {
        return MAX_ATTEMPTS - getAttempts(username);
    }
}