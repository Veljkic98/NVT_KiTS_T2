package tim2.CulturalHeritage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tim2.CulturalHeritage.constants.CHTypeConstants.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
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

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testDeleteValid(){
        chTypeService.deleteById(TYPE_ID_WITHOUT_SUBTYPES);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testDeleteInvalidId(){
        chTypeService.deleteById(TYPE_NONEXIST_ID);
    }

    @Test(expected = DataIntegrityViolationException.class)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testDeleteInvalidTypeReferenced(){
        chTypeService.deleteById(TYPE_ID_WITH_SUBTYPES);
    }

    @Test
    public void testFindAllPageable(){
        PageRequest p = PageRequest.of(0, PAGE_SIZE);
        Page<CHType> res = chTypeService.findAll(p);

        assertEquals(PAGE_SIZE, res.getTotalElements());

    }
}
