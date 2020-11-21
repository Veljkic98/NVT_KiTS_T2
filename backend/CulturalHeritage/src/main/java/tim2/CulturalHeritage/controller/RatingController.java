package tim2.CulturalHeritage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim2.CulturalHeritage.service.RatingService;

@RestController
@RequestMapping("/api/rating")
public class RatingController {
    
    @Autowired
    private RatingService ratingService;

}
