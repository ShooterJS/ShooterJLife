import com.shooterj.JasyptApplication;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 作者:ShooterJ
 * 邮箱:prd-jiangshuai@winwayworld.com
 * 日期:2021/8/12
 */
@SpringBootTest(classes = JasyptApplication.class)
@RunWith(SpringRunner.class)
public class JasyptTest {

    @Autowired
    private StringEncryptor stringEncryptor;

    public String encrypt(String content) {
        String encryptStr = stringEncryptor.encrypt(content);
        System.out.println("加密后的内容：" + encryptStr);
        return encryptStr;
    }

    public String decrypt(String content) {
        String decryptStr = stringEncryptor.decrypt(content);
        System.out.println("解密后的内容：" + decryptStr);
        return decryptStr;
    }

    @Test
    public void testJasypt(){
        encrypt("Mas_2019#wy");
    }

}
