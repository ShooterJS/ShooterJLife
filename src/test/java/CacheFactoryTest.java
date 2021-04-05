import com.winway.demo.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class CacheFactoryTest {


    @Test
    public void test1(){
        /*Log log = CacheFactory.get();
        Exception e = new Exception("test Exception");
        log.error("我是错误消息", e);*/
    }
}
