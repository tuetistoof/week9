package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Lớp trừu tượng đại diện cho một tài khoản ngân hàng.
 * Cung cấp các thuộc tính và phương thức cơ bản để quản lý số dư và giao dịch.
 *
 * @author YourName
 * @version 1.0
 */
public abstract class Account {

  private static final Logger logger = Logger.getLogger(Account.class.getName());

  private static final String CHECKING_TYPE = "CHECKING";
  private static final String SAVING_TYPE = "SAVINGS";

  private long accountNumber;
  private double balance;
  protected List<Transaction> transactions;

  /**
   * Khởi tạo một tài khoản mới với số tài khoản và số dư ban đầu.
   *
   * @param accountNumber số tài khoản duy nhất
   * @param balance số dư ban đầu của tài khoản
   */
  public Account(long accountNumber, double balance) {
    this.accountNumber = accountNumber;
    this.balance = balance;
    this.transactions = new ArrayList<>();
  }

  public long getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(long accountNumber) {
    this.accountNumber = accountNumber;
  }

  public double getBalance() {
    return balance;
  }

  protected void setBalance(double balance) {
    this.balance = balance;
  }

  public List<Transaction> getTransactionList() {
    return transactions;
  }

  /**
   * Cập nhật danh sách giao dịch. Nếu danh sách truyền vào null,
   * một danh sách trống mới sẽ được khởi tạo.
   *
   * @param transactionList danh sách giao dịch mới
   */
  public void setTransactionList(List<Transaction> transactionList) {
    this.transactions = Objects.requireNonNullElseGet(transactionList, ArrayList::new);
  }

  /**
   * Nạp một số tiền vào tài khoản.
   *
   * @param amount số tiền cần nạp
   */
  public abstract void deposit(double amount);

  /**
   * Rút một số tiền từ tài khoản.
   *
   * @param amount số tiền cần rút
   */
  public abstract void withdraw(double amount);

  /**
   * Thực hiện xử lý nghiệp vụ nạp tiền và cập nhật số dư.
   *
   * @param amount số tiền hợp lệ lớn hơn 0
   * @throws InvalidFundingAmountException nếu số tiền nhỏ hơn hoặc bằng 0
   */
  protected void doDepositing(double amount) throws InvalidFundingAmountException {
    if (amount <= 0) {
      throw new InvalidFundingAmountException(amount);
    }
    balance += amount;
  }

  /**
   * Thực hiện xử lý nghiệp vụ rút tiền và cập nhật số dư.
   *
   * @param amount số tiền hợp lệ lớn hơn 0 và nhỏ hơn hoặc bằng số dư hiện tại
   * @throws InvalidFundingAmountException nếu số tiền nhỏ hơn hoặc bằng 0
   * @throws InsufficientFundsException nếu số tiền rút vượt quá số dư tài khoản
   */
  protected void doWithdrawing(double amount)
      throws InvalidFundingAmountException, InsufficientFundsException {
    if (amount <= 0) {
      throw new InvalidFundingAmountException(amount);
    }
    if (amount > balance) {
      throw new InsufficientFundsException(amount);
    }
    balance -= amount;
  }

  /**
   * Thêm một giao dịch vào lịch sử giao dịch của tài khoản.
   *
   * @param transaction giao dịch cần thêm, bỏ qua nếu null
   */
  public void addTransaction(Transaction transaction) {
    if (transaction != null) {
      transactions.add(transaction);
    }
  }

  /**
   * Lấy chuỗi văn bản chứa toàn bộ lịch sử giao dịch của tài khoản.
   *
   * @return chuỗi lịch sử giao dịch được định dạng theo dòng
   */
  public String getTransactionHistory() {
    StringBuilder sb = new StringBuilder();
    sb.append("Lịch sử giao dịch của tài khoản ").append(accountNumber).append(":\n");

    for (int i = 0; i < transactions.size(); i++) {
      sb.append(transactions.get(i).getTransactionSummary());
      if (i < transactions.size() - 1) {
        sb.append("\n");
      }
    }

    logger.info(() -> "[DEBUG] Đã lấy lịch sử cho tài khoản: " + accountNumber);
    return sb.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Account)) {
      return false;
    }
    Account other = (Account) obj;
    return this.accountNumber == other.accountNumber;
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountNumber);
  }
}