package tim2.CulturalHeritage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim2.CulturalHeritage.service.CHTypeService;

@RestController
@RequestMapping("/api/ch-type")
public class CHTypeController {

    @Autowired
    private CHTypeService chTypeService;

}
