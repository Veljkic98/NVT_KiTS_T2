package tim2.CulturalHeritage.controler;

import org.springframework.beans.factory.annotation.Autowired;

import tim2.CulturalHeritage.service.AuthenticatedUserService;

public class AuthenticatedUserControler {

    @Autowired
    private AuthenticatedUserService authenticatedUserService;
    
}
