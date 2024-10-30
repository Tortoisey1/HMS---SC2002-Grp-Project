// package management;

// import java.io.*;
// import java.sql.Date;
// import java.util.ArrayList;

// import entities.Account;
// import merged.Role;

// public class AccountDataManager implements DataManager<Account, String> {

//     private ArrayList<Account> accountList;
//     private static DataManager<Account, String> accountDataManager;
//     private final String filePath;

//     AccountDataManager() {
//         filePath = "account.csv";
//         accountList = new ArrayList<Account>();
//         try {
//             retrieveAll();
//         } catch (IOException e) {
//             System.out.println(e);
//         }

//     }

//     public static DataManager<Account, String> getInstance() {
//         if (accountDataManager == null) {
//             accountDataManager = new AccountDataManager();
//         }
//         return accountDataManager;
//     }

//     @Override
//     public Account retrieve(String accountId) {
//         for (Account user : accountList) {
//             if (user.getAccount_Id().equals(accountId)) {
//                 return user;
//             }
//         }
//         return null;
//     }

//     @Override
//     public void update(Account newAccount) {
//         int count = 0;
//         try {
//             for (Account temp : accountList) {
//                 if (temp.getAccount_Id().equals(newAccount.getAccount_Id())) {
//                     accountList.set(count, newAccount);
//                     writeAll();
//                     System.out.println("Updated details");
//                 }
//                 count++;
//             }
//         } catch (IOException e) {
//             System.out.println(e);
//         }
//     }

//     @Override
//     public void writeAll() throws IOException {
//         BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
//         String[] header = { "Account_Id", "firstLogin", "Password", "Role" };

//         for (String h : header) {
//             bw.write(h);
//             bw.write(",");
//         }
//         for (Account temp : accountList) {
//             bw.newLine();
//             bw.write(temp.getAccount_Id());
//             bw.write(",");
//             bw.write(String.valueOf(temp.isFirstLogin()));
//             bw.write(",");
//             bw.write(temp.getPassword());
//             bw.write(",");
//             bw.write(temp.getRole().toString());

//         }
//         bw.flush();
//         bw.close();

//     }

//     @Override
//     public ArrayList<Account> getList() {
//         return accountList;
//     }

//     @Override
//     public boolean delete(Account account) {
//         return false;
//     }

//     @Override
//     public boolean add(Account T1) {
//         return false;
//     }

//     @Override
//     public void retrieveAll() throws IOException {
//         String line = "";
//         BufferedReader br = new BufferedReader(new FileReader(filePath));
//         int count = 0;
//         while ((line = br.readLine()) != null) {
//             if (count > 0) {
//                 String[] data = line.split(",");
//                 accountList.add(new Account(data[0].trim(),
//                         Boolean.parseBoolean(data[1].trim()),
//                         data[2].trim(),
//                         Role.valueOf(data[3].trim())));
//             }
//             count++;
//         }
//         br.close();

//     }

// }
