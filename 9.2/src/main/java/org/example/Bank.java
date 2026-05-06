package org.example;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/**
 * Quản lý thông tin ngân hàng và danh sách khách hàng.
 */
public class Bank {
  private final Logger logger = Logger.getLogger(Bank.class.getName());

  private static final String ID_NUMBER_REGEX = "\\d{9}";
  private static final String WHITESPACE_REGEX = "\\s+";
  private static final String ACCOUNT_TYPE_CHECKING = "CHECKING";
  private static final String ACCOUNT_TYPE_SAVINGS = "SAVINGS";
  private List<Customer> customerList;

  /**
   * Khởi tạo một đối tượng Bank mới với danh sách khách hàng rỗng.
   */
  public Bank() {
    this.customerList = new ArrayList<>();
  }

  public List<Customer> getCustomerList() {
    return customerList;
  }

  /**
   * Thiết lập danh sách khách hàng cho ngân hàng.
   *
   * @param customerList Danh sách khách hàng mới, nếu null sẽ tự động khởi tạo danh sách rỗng.
   */
  public void setCustomerList(List<Customer> customerList) {
    this.customerList = Objects.requireNonNullElseGet(customerList, ArrayList::new);
  }

  /**
   * Ham nay rat dai và khó doc
   */
  public void readCustomerList(InputStream inputStream) {
    if (inputStream == null) {
      logger.warning("InputStream truyền vào bị null.");
      return;
    }
    logger.info(() -> "Bắt đầu đọc dữ liệu...");
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      String line;
      Customer currentCustomer = null;
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (line.isEmpty()) { // Vi phạm: Lồng nested if quá sâu
          continue;
        }

        int last = line.lastIndexOf(' ');
        if (last <= 0) {
          continue;
        }

        String token = line.substring(last + 1).trim();
        if (token.matches(ID_NUMBER_REGEX)) {
          String name = line.substring(0, last).trim();
          currentCustomer = new Customer(Long.parseLong(token), name);
          customerList.add(currentCustomer);
          logger.info(() -> "Đã thêm khách hàng: " + name);
        } else {
          if (currentCustomer != null) {
            processAccountLine(line, currentCustomer);
          }
        }
      }
    } catch (Exception e) { // Vi phạm: Catch Exception chung chung
      System.out.println("Error: " + e.getMessage());
    }
  }

  private void processAccountLine(String line, Customer customer) {
    String[] parts = line.split(Bank.WHITESPACE_REGEX);
    if (parts.length < 3) {
      return;
    }

    try {
      long accountNumber = Long.parseLong(parts[0]);
      String accountType = parts[1];
      double balance = Double.parseDouble(parts[2]);

      if (Bank.ACCOUNT_TYPE_CHECKING.equalsIgnoreCase(accountType)) {
        customer.addAccount(new CheckingAccount(accountNumber, balance));
      } else if (Bank.ACCOUNT_TYPE_SAVINGS.equalsIgnoreCase(accountType)) {
        customer.addAccount(new SavingsAccount(accountNumber, balance));
      }
    } catch (NumberFormatException e) {
      logger.log(Level.WARNING, "Dữ liệu tài khoản không hợp lệ: " + line, e);
    }
  }

  /**
   * Lấy thông tin tất cả khách hàng sắp xếp theo ID tăng dần.
   *
   * @return Chuỗi thông tin khách hàng, phân tách bằng dấu xuống dòng.
   */
  public String getCustomersInfoByIdOrder() {
    return customerList.stream()
        .sorted((c1, c2) -> Long.compare(c1.getIdNumber(), c2.getIdNumber()))
        .map(Customer::getCustomerInfo)
        .collect(Collectors.joining("\n"));
  }

  /**
   * Lấy thông tin tất cả khách hàng sắp xếp theo Tên (nếu trùng thì xếp theo ID).
   *
   * @return Chuỗi thông tin khách hàng, phân tách bằng dấu xuống dòng.
   */
  public String getCustomersInfoByNameOrder() {
    return customerList.stream()
        .sorted((c1, c2) -> {
          int nameCompare = c1.getFullName().compareTo(c2.getFullName());
          return nameCompare != 0 ? nameCompare : Long.compare(c1.getIdNumber(), c2.getIdNumber());
        })
        .map(Customer::getCustomerInfo)
        .collect(Collectors.joining("\n"));
  }
}