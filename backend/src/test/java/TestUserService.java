import com.pusulait.airsqreen.Application;
import com.pusulait.airsqreen.domain.dto.security.UserDTO;
import com.pusulait.airsqreen.service.security.UserService;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.api.Randomizer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by benan on 5/14/2017.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TestUserService {


    @Autowired
    private UserService userService;

    Randomizer<UserDTO> userRandomizer = new Randomizer<UserDTO>() {
        private EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build();
        @Override
        public UserDTO getRandomValue() {
            return enhancedRandom.nextObject(UserDTO.class);
        }
    };

    @Test
    public void saveUser(){
        UserDTO userDTO = userRandomizer.getRandomValue();

        userService.save(userDTO);
    }
}
