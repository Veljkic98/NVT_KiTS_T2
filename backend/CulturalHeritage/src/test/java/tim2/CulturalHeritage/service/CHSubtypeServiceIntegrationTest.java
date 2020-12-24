package tim2.CulturalHeritage.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import tim2.CulturalHeritage.model.CHSubtype;
import tim2.CulturalHeritage.model.CHType;
import tim2.CulturalHeritage.repository.CHSubtypeRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static tim2.CulturalHeritage.constants.CHSubtypeConstants.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CHSubtypeServiceIntegrationTest {

    @Autowired
    CHSubtypeRepository chSubtypeRepository;

    @Autowired
    CHTypeService chTypeService;

    @Autowired
    CulturalHeritageService culturalHeritageService;

    @Autowired
    CHSubtypeService chSubtypeService;

    @Test
    public void testFindAll(){

        List<CHSubtype> list = chSubtypeService.findAll();
        assertEquals(list.size(), SUBTYPES_ALL);

    }

    @Test
    public void testFindOneFound(){
        CHSubtype found = chSubtypeService.findById(EXIST_SUBTYPE_ID);
        assertEquals(found.getId(), EXIST_SUBTYPE_ID);
    }

    @Test
    public void testFindOneNotFound(){
        CHSubtype found = chSubtypeService.findById(NONEXIST_SUTYPE_ID);
        assertNull(found);
    }

    @Test
    public void testSaveOneValid(){
        CHSubtype subtype = new CHSubtype();
        subtype.setName(NEW_VALID_SUBTYPE_NAME);
        CHType type = new CHType();
        type.setId(EXIST_TYPE_ID);
        subtype.setChtype(type);

        CHSubtype saved = chSubtypeService.add(subtype);
        assertEquals(NEW_VALID_SUBTYPE_NAME, saved.getName());

        chSubtypeService.deleteById(saved.getId());
    }

    @Test
    public void testSaveOneInvalidName(){
        CHSubtype subtype = new CHSubtype();
        subtype.setName(NEW_INVALID_SUBTYPE_NAME);
        CHType type = new CHType();
        type.setId(EXIST_TYPE_ID);
        subtype.setChtype(type);

        assertThrows(DataIntegrityViolationException.class,() -> {
            chSubtypeService.add(subtype);
        });
    }

    @Test
    public void testSaveOneInvalidCHTypeId(){
        CHSubtype subtype = new CHSubtype();
        CHType type = new CHType();
        type.setId(NONEXIST_TYPE_ID);
        subtype.setChtype(type);

        subtype.setName(NEW_VALID_SUBTYPE_NAME);
        assertThrows(DataIntegrityViolationException.class,() -> {
            chSubtypeService.add(subtype);
        });
    }
}