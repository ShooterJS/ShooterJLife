import com.shooterj.cache.CacheApplication;
import com.shooterj.cache.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= CacheApplication.class)
public class CahceTest {
//    @Resource
//    ICache<String> cache;

    @Resource
    RedisService redisService;

    /**
     * cache的增删改查
     * @throws InterruptedException
     */
    @Test
    public void testCache() throws InterruptedException {
        String key = "name";
        String name = "张三";
        String newName = "李四";
        // 添加
        redisService.add(key, name);
        // 是否包含key
        assertTrue(redisService.containKey(key));
        // 取值
        String keyVal = (String) redisService.getByKey(key,String.class);
        assertEquals(keyVal, name);
        // 更新值
        redisService.add(key, newName);
        String newKeyVal = (String) redisService.getByKey(key,String.class);
        assertEquals(newKeyVal, newName);
        // 删除
        redisService.delByKey(key);
        assertFalse(redisService.containKey(key));

    }


}
