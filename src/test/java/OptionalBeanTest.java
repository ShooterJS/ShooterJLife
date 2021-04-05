import com.winway.demo.DemoApplication;
import com.winway.demo.optional.WWCustomerOptionalBean;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class OptionalBeanTest {

    @Test
    public void testCustomerOptional() {
        User user = new User();
        user.setName("hello");
        String value1 = WWCustomerOptionalBean.ofNullable(user)
                .getBean(User::getSchool)
                .getBean(User.School::getAddress)
                .get();
        Assert.assertNull(value1);

        boolean present = WWCustomerOptionalBean.ofNullable(user)
                .getBean(User::getSchool)
                .getBean(User.School::getAddress).isPresent();
        Assert.assertFalse(present);

        String value2 = WWCustomerOptionalBean.ofNullable(user)
                .getBean(User::getSchool)
                .getBean(User.School::getAddress).orElse("没得地址");
        Assert.assertEquals(value2, "没得地址");
        try {
            WWCustomerOptionalBean.ofNullable(user)
                    .getBean(User::getSchool)
                    .getBean(User.School::getAddress).orElseThrow(() -> new RuntimeException("空指针了"));
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "空指针了");
        }
    }

    @Data
    class User {
        private String name;
        private String gender;
        private School school;

        @Data
        class School {
            private String name;
            private String address;
        }
    }

}
