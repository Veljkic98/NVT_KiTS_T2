package tim2.CulturalHeritage.service;

import java.security.AccessControlException;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import tim2.CulturalHeritage.model.AuthenticatedUser;
import tim2.CulturalHeritage.model.Rating;
import tim2.CulturalHeritage.repository.RatingRepository;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Page<Rating> findAll(Pageable pageable) {
        return ratingRepository.findAll(pageable);
    }

    @Override
    public Rating findById(Long id) {
        return ratingRepository.findById(id).orElse(null);
    }

    @Override
    public Rating add(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public Rating update(Rating rating) throws AccessControlException{
        //if id is not found in the db
        if(null== ratingRepository.findById(rating.getId()).orElse(null)){
            throw new EntityNotFoundException();
        }
        //if one user is trying to update other user's rating
        AuthenticatedUser userFromDB = ratingRepository.findById(rating.getId()).orElse(null).getAuthenticatedUser();
        if(rating.getAuthenticatedUser().getId() != userFromDB.getId()){
            throw new AccessControlException("not allowed");
        }
        return ratingRepository.save(rating);
    }

    @Override
    public void deleteById(Long id) throws AccessControlException {
        //if id is not found in the db
        if(null == ratingRepository.findById(id).orElse(null)){
            throw new EntityNotFoundException();
        }

        //if one user is trying to delete other user's rating
        AuthenticatedUser currentUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthenticatedUser userFromDB = ratingRepository.findById(id).orElse(null).getAuthenticatedUser();
        if(currentUser.getId() != userFromDB.getId()){
            throw new AccessControlException("not allowed");
        }
        ratingRepository.deleteById(id);
    }

}
