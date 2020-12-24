package tim2.CulturalHeritage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import tim2.CulturalHeritage.model.CHType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CHTypeServiceIntegrationTest {

    @Autowired
    private CHTypeService chTypeService;

    @Test
    @Transactional
    @Rollback(true)
    public void add_chTypeOk_true() {
        CHType chType = new CHType();
        chType.setName("naziv");
        int size = chTypeService.findAll().size();

        CHType created = chTypeService.add(chType);
        int newSize = chTypeService.findAll().size();

        assertEquals(created.getId(), 2L);
        assertEquals(chType.getName(), created.getName());
        assertEquals(size + 1, newSize);
    }

}
