package tim2.CulturalHeritage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim2.CulturalHeritage.service.CHSubtypeService;

@RestController
@RequestMapping("/api/ch-subtype")
public class CHSubtypeController {

    @Autowired
    private CHSubtypeService chSubtypeService;
    
}
