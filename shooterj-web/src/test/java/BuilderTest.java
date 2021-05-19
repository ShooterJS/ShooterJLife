import com.shooterj.core.util.Builder;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class BuilderTest {

    @Test
    public void testbuildMap() {
        HashMap<String, Object> params = Builder.of(HashMap<String, Object>::new)
                .with(HashMap::put, "customerId", 1)
                .with(HashMap::put, "behaviorCode", "meeting")
                .with(HashMap::put, "randomString", ThreadLocalRandom.current().nextLong())
                .with(HashMap::put, "createDate", LocalDate.now())
                .build();
        System.out.println(params);
    }


}
