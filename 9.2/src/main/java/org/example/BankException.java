package org.example;

/**
 * Ngoại lệ chung trong hệ thống ngân hàng.
 */
public class BankException extends Exception {

  /**
   * Khởi tạo một BankException mới với thông báo lỗi cụ thể.
   *
   * @param message thông báo chi tiết về nguyên nhân gây ra ngoại lệ
   */
  public BankException(String message) {
    super(message);
  }
}