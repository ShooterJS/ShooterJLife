import com.winway.demo.DemoApplication;
import com.winway.demo.design.strategy.AbstractHandler;
import com.winway.demo.design.strategy.OneHandler;
import com.winway.demo.util.WWObjectUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class JunitTest {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void testStrateagy() {
//        AbstractHandler one = HandlerFactory.getHandlerByName("one");
        AbstractHandler one = applicationContext.getBean(OneHandler.class);
        one.drive();
        one.eat();


       /* AbstractHandler two = HandlerFactory.getHandlerByName("two");
        two.drive();
        two.eat();*/
    }


    @Test
    public void testdefaultIfEmpty(){
        String str ="";
        String s = WWObjectUtil.defaultIfEmpty(str, () -> str.trim(), "哈哈");
        System.out.println("testdefaultIfEmpty:"+s);
        Assert.assertNotNull(s);
    }




}
