package com.example;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {
  private static final Logger logger = LoggerFactory.getLogger(AppTest.class);

  @Test
  void testSample() {
    logger.info("==> Dang thực hiện chạy test tích hợp CI/CD...");

    // Ban đầu để là ĐÚNG để kiểm tra hệ thống hoạt động ổn định
    assertEquals(1, 1);
  }
}