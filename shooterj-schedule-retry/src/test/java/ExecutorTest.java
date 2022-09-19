import com.google.common.collect.Maps;
import com.shooterj.retry.core.RefundExecutor;
import com.shooterj.retry.scheduler.ExecutorJobScheduler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class ExecutorTest {

    @Autowired
    private ExecutorJobScheduler scheduler;

    public void test1(){
        Map<String, Object> params = Maps.newHashMap();
        scheduler.scheduleRetry(RefundExecutor.class, params, 0, 10000);
    }

}
