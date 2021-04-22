import com.alibaba.fastjson.JSON;
import com.shooterj.core.optional.WWCustomerOptionalBean;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;


//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = WebApplication.class)
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

    @Test
    public void testFunc() {
        int i = 3;
        WWCustomerOptionalBean.validateAndApplyFunction(i>2,this::out1,this::out2,"大红灯笼","搞搞搞");
    }

    private Object out1(Object[] objects) {
        System.out.println("out1:"+JSON.toJSON(objects));
        return 1;
    }
    private Object out2(Object[] objects) {
        System.out.println("out2:"+JSON.toJSON(objects));
        return 2;
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
