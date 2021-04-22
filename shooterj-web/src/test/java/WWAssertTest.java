import com.google.common.collect.Maps;
import com.shooterj.core.validator.wwasert.WWAssert;
import lombok.Data;
import org.junit.Test;

import java.util.HashMap;


public class WWAssertTest {

    @Test
    public void notNullTest() {
        String a = null;
        User user = null;
//        WWAssert.notNull(user,"用户不能为空");
//        WWAssert.notNull(user,() -> new AuthException("权限不足"));
        HashMap<Object, Object> objectObjectHashMap = Maps.newHashMap();
        WWAssert.notEmpty(objectObjectHashMap, "this is {0} for {1}", "a", "空集合");

    }

    @Data
    class User {
        private String name;
        private Integer age;
    }
}
