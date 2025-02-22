/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dal;
import java.util.List;
import model.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ADMIN
 */
public class AcountDBContextTest {
    private AcountDBContext accountDB;
    private Account testAccount;
    
    @Before
    public void setUp() {
        // Thiết lập kết nối cơ sở dữ liệu và chèn tài khoản kiểm thử
        accountDB = new AcountDBContext();
        testAccount = new Account();
        testAccount.setUser("testUser");
        testAccount.setPass("testPass");
        testAccount.setIsSell(0);
        testAccount.setIsAdmin(0);
        testAccount.setActive(true);
        accountDB.insertAccount(testAccount.getUser(), testAccount.getPass());
    }

    @After
    public void tearDown() {
        // Dọn dẹp tài khoản kiểm thử sau mỗi kiểm thử
        accountDB = new AcountDBContext();
        Account accountToDelete = accountDB.checkAccountExist(testAccount.getUser());
        if (accountToDelete != null) {
            accountToDelete.setActive(false); // Cập nhật trực tiếp thuộc tính active
            accountDB.updateAccount(accountToDelete);
        }
    }
    public AcountDBContextTest() {
    }

    @Test
    public void testGetAllAccount() {
        List<Account> accounts = accountDB.getAllAccount();
        assertNotNull(accounts);
        assertTrue(accounts.size() > 0);
    }

    @Test
    public void testLogin() {
        Account account = accountDB.login(testAccount.getUser(), testAccount.getPass());
        assertNotNull(account);
        assertEquals(testAccount.getUser(), account.getUser());
    }

    @Test
    public void testCheckAccountExistByUserPass() {
        Account account = accountDB.checkAccountExistByUserPass(testAccount.getUser(), testAccount.getPass());
        assertNotNull(account);
        assertEquals(testAccount.getUser(), account.getUser());
    }

    @Test
    public void testCheckAccountExist() {
        Account account = accountDB.checkAccountExist(testAccount.getUser());
        assertNotNull(account);
        assertEquals(testAccount.getUser(), account.getUser());
    }

    @Test
    public void testInsertAccount() {
        Account newAccount = new Account();
        newAccount.setUser("newUser");
        newAccount.setPass("newPass");
        newAccount.setIsSell(0);
        newAccount.setIsAdmin(0);
        newAccount.setActive(true);
        
        accountDB.insertAccount(newAccount.getUser(), newAccount.getPass());
        
        Account insertedAccount = accountDB.checkAccountExist(newAccount.getUser());
        assertNotNull(insertedAccount);
        assertEquals(newAccount.getUser(), insertedAccount.getUser());
    }

    @Test
    public void testGetAccountById() {
        Account account = accountDB.getAccountById(testAccount.getUid());
        assertNotNull(account);
        assertEquals(testAccount.getUser(), account.getUser());
    }

    @Test
    public void testUpdateAccount() {
        testAccount.setActive(false);
        accountDB.updateAccount(testAccount);
        Account updatedAccount = accountDB.getAccountById(testAccount.getUid());
        assertNotNull(updatedAccount);
        assertFalse(updatedAccount.isActive());
    }

    @Test
    public void testMain() {
        // Kiểm thử này chỉ chạy phương thức main để đảm bảo không có ngoại lệ nào bị ném ra
        AcountDBContext.main(new String[]{});
    }

    @Test
    public void testUpDatePassWord() {
        String newPass = "newTestPass";
        accountDB.UpDatePassWord(newPass, testAccount.getUser());
        Account updatedAccount = accountDB.login(testAccount.getUser(), newPass);
        assertNotNull(updatedAccount);
        assertEquals(testAccount.getUser(), updatedAccount.getUser());
        assertEquals(newPass, updatedAccount.getPass());
    }
    
}
