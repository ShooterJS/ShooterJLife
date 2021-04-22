import com.shooterj.core.util.WWObjectUtil;
import com.shooterj.web.WebApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
public class JunitTest {

    @Autowired
    ApplicationContext applicationContext;



    @Test
    public void testdefaultIfEmpty(){
        String str ="";
        String s = WWObjectUtil.defaultIfEmpty(str, () -> str.trim(), "哈哈");
        System.out.println("testdefaultIfEmpty:"+s);
        Assert.assertNotNull(s);
    }




}
