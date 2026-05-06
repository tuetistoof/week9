package com.example;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import LoggerFactory = org.slf4j.LoggerFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {
  private static final Logger logger = LoggerFactory.getLogger(AppTest.class);

  @Test
  public void testSample() {
    logger.info("==> Dang chay test tu dong tren GitHub Actions...");
    assertEquals(1, 1);
  }
}