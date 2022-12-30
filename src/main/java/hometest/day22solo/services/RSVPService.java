package hometest.day22solo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hometest.day22solo.models.RSVP;
import hometest.day22solo.repositories.RSVPRepository;

@Service
public class RSVPService {

    @Autowired RSVPRepository rsvpRepo;

    //GET METHOD 1 -- search by name
    public List<RSVP> getAllRSVPByName(String q){
        return rsvpRepo.getAllRSVPByName(q);
    }

    //GET METHOD 2 -- searching by email
    public List<RSVP> getByEmail(String emailInput){
        return rsvpRepo.getByEmail(emailInput);
    }

    //PUT METHOD 1 -- update by email
    public boolean updateRSVPByEmail(final RSVP rsvpRow){
        return rsvpRepo.updateRSVPByEmail(rsvpRow);
    }

    //GET METHOD FOR COUNT -- count the number of rows needed
    public Integer getTotalRSVP() {
        return rsvpRepo.getTotalCount();
    }
}
