package kr.yh.movie.repository.screen;

import kr.yh.movie.config.QuerydslConfig;
import kr.yh.movie.repository.screen.ScreenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslConfig.class)
public class ScreenRepositoryTest {
    @Autowired
    ScreenRepository screenRepository;
}