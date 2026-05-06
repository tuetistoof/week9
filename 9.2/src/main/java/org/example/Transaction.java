package org.example;

import java.util.Locale;

/**
 * Đại diện cho một giao dịch ngân hàng.
 * Lưu trữ thông tin về loại giao dịch, số tiền, và số dư trước/sau giao dịch.
 */
public class Transaction {
  /** Loại giao dịch: gửi tiền vào tài khoản vãng lai. */
  public static final int TYPE_DEPOSIT_CHECKING = 1;

  /** Loại giao dịch: rút tiền từ tài khoản vãng lai. */
  public static final int TYPE_WITHDRAW_CHECKING = 2;

  /** Loại giao dịch: gửi tiền vào tài khoản tiết kiệm. */
  public static final int TYPE_DEPOSIT_SAVINGS = 3;

  /** Loại giao dịch: rút tiền từ tài khoản tiết kiệm. */
  public static final int TYPE_WITHDRAW_SAVINGS = 4;

  private int type;
  private double amount;
  private double initialBalance;
  private double finalBalance;

  /**
   * Khởi tạo một giao dịch mới.
   *
   * @param type loại giao dịch
   * @param amount số tiền giao dịch
   * @param initialBalance số dư trước giao dịch
   * @param finalBalance số dư sau giao dịch
   */
  public Transaction(int type, double amount, double initialBalance, double finalBalance) {
    this.type = type;
    this.amount = amount;
    this.initialBalance = initialBalance;
    this.finalBalance = finalBalance;
  }

  /**
   * Lấy loại giao dịch.
   *
   * @return loại giao dịch
   */
  public int getType() {
    return type;
  }

  /**
   * Thiết lập loại giao dịch.
   *
   * @param type loại giao dịch
   */
  public void setType(int type) {
    this.type = type;
  }

  /**
   * Lấy số tiền giao dịch.
   *
   * @return số tiền
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Thiết lập số tiền giao dịch.
   *
   * @param amount số tiền
   */
  public void setAmount(double amount) {
    this.amount = amount;
  }

  /**
   * Lấy số dư trước giao dịch.
   *
   * @return số dư ban đầu
   */
  public double getInitialBalance() {
    return initialBalance;
  }

  /**
   * Thiết lập số dư trước giao dịch.
   *
   * @param initialBalance số dư ban đầu
   */
  public void setInitialBalance(double initialBalance) {
    this.initialBalance = initialBalance;
  }

  /**
   * Lấy số dư sau giao dịch.
   *
   * @return số dư cuối cùng
   */
  public double getFinalBalance() {
    return finalBalance;
  }

  /**
   * Thiết lập số dư sau giao dịch.
   *
   * @param finalBalance số dư cuối cùng
   */
  public void setFinalBalance(double finalBalance) {
    this.finalBalance = finalBalance;
  }

  /**
   * Chuyển đổi loại giao dịch thành chuỗi mô tả.
   *
   * @param transactionType loại giao dịch
   * @return mô tả loại giao dịch
   */
  public static String getTypeString(int transactionType) {
    switch (transactionType) {
      case TYPE_DEPOSIT_CHECKING:
        return "Nạp tiền vãng lai";
      case TYPE_WITHDRAW_CHECKING:
        return "Rút tiền vãng lai";
      case TYPE_DEPOSIT_SAVINGS:
        return "Nạp tiền tiết kiệm";
      case TYPE_WITHDRAW_SAVINGS:
        return "Rút tiền tiết kiệm";
      default:
        return "Không rõ";
    }
  }

  /**
   * Lấy tóm tắt thông tin giao dịch.
   *
   * @return chuỗi mô tả giao dịch
   */
  public String getTransactionSummary() {
    String typeDescription = getTypeString(type);
    String initialBalanceStr = String.format(Locale.US, "%.2f", initialBalance);
    String amountStr = String.format(Locale.US, "%.2f", amount);
    String finalBalanceStr = String.format(Locale.US, "%.2f", finalBalance);

    return String.format(
        "- Kiểu giao dịch: %s. Số dư ban đầu: $%s. Số tiền: $%s. Số dư cuối: $%s.",
        typeDescription, initialBalanceStr, amountStr, finalBalanceStr);
  }
}