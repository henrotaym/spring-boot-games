package henrotaym.env;

import henrotaym.env.enums.ProfileName;
import jakarta.transaction.Transactional;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = {ProfileName.TEST, ProfileName.WEB})
@Transactional
@AutoConfigureMockMvc
public abstract class ApplicationTest {}
