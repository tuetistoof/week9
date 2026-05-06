package org.example;

import org.example.Account;
import org.example.InsufficientFundsException;
import org.example.InvalidFundingAmountException;
import org.example.Transaction;

/**
 * Tài khoản tiết kiệm - thực thi các quy định đặc biệt về giới hạn rút tiền và
 * số dư tối thiểu.
 */
public class SavingsAccount extends Account {
  /** Giới hạn rút tiền tối đa cho mỗi lần rút. */
  private static final double MAX_WITHDRAWAL = 1000.0;

  /** Số dư tối thiểu phải giữ lại trong tài khoản. */
  private static final double MIN_BALANCE = 5000.0;

  /**
   * Khởi tạo tài khoản tiết kiệm.
   *
   * @param accountNumber số tài khoản
   * @param balance số dư ban đầu
   */
  public SavingsAccount(long accountNumber, double balance) {
    super(accountNumber, balance);
  }

  @Override
  public void deposit(double amount) {
    double initialBalance = getBalance();
    try {
      doDepositing(amount);
      double finalBalance = getBalance();
      Transaction transaction = new Transaction(
          Transaction.TYPE_DEPOSIT_SAVINGS, amount, initialBalance, finalBalance);
      addTransaction(transaction);
      System.out.println(
          "Nạp tiền vào tài khoản "
              + getAccountNumber()
              + " thành công: +"
              + String.format("%.2f", amount));
    } catch (InvalidFundingAmountException e) {
      System.err.println("Lỗi nạp tiền: " + e.getMessage());
    }
  }

  @Override
  public void withdraw(double amount) {
    double initialBalance = getBalance();
    try {
      if (amount > MAX_WITHDRAWAL) {
        throw new InvalidFundingAmountException(amount);
      }
      if (initialBalance - amount < MIN_BALANCE) {
        throw new InsufficientFundsException(amount);
      }

      doWithdrawing(amount);
      double finalBalance = getBalance();

      Transaction transaction = new Transaction(
          Transaction.TYPE_WITHDRAW_SAVINGS, amount, initialBalance, finalBalance);
      addTransaction(transaction);

      System.out.println(
          "Rút tiền từ tài khoản tiết kiệm thành công: -"
              + String.format("%.2f", amount)
              + ". Số dư còn lại: "
              + String.format("%.2f", finalBalance));
    } catch (InvalidFundingAmountException | InsufficientFundsException e) {
      System.err.println("Lỗi rút tiền: " + e.getMessage());
    }
  }
}