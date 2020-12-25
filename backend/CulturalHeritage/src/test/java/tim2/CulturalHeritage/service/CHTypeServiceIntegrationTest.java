package tim2.CulturalHeritage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tim2.CulturalHeritage.constants.CHTypeConstants.*;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
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
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void add_chTypeOk_true() {

        CHType chType = new CHType();
        chType.setName(NAME);

        CHType created = chTypeService.add(chType);

        assertEquals(created.getId(), NEW_CHTYPE_ID);
        assertEquals(chType.getName(), created.getName());
    }

    @Test(expected = DataIntegrityViolationException.class)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void add_chTypeNameExists_DataIntegrityViolationException() {

        CHType chType = new CHType();
        chType.setName(NAME_EXISTS);

        chTypeService.add(chType);
    }

    @Test(expected = DataIntegrityViolationException.class)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void add_chTypeNameNull_DataIntegrityViolationException() {

        CHType chType = new CHType();
        // chType.setName(NAME_EXISTS);

        chTypeService.add(chType);
    }

}
