import com.shooterj.core.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= Application.class)
@Transactional
public class BaseTestCase {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
//	@Resource
//	protected IdGenerator idGenerator;
	
	@Test
	public void Test(){
		logger.debug("X7 base test initialize.");
	}
}
