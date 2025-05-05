package henrotaym.env;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import jakarta.transaction.Transactional;


@SpringBootTest
@ActiveProfiles(profiles = "test")
@Transactional
@AutoConfigureMockMvc
public abstract class ApplicationTest {}
