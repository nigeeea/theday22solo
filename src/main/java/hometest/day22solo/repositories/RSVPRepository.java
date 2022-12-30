package hometest.day22solo.repositories;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import hometest.day22solo.models.RSVP;
import hometest.day22solo.models.RSVPTotalCountMapper;

import static hometest.day22solo.repositories.Queries.*;

@Repository
public class RSVPRepository {

    @Autowired JdbcTemplate jdbcTemplate;

    //GET METHOD 1 -- searching by name -- if no param return all the rows in json format
    public List<RSVP> getAllRSVPByName(String q){

        //create an empty list
        final List<RSVP> rsvps= new LinkedList<>();

        //create am empty sql rowset
        SqlRowSet rs = null;

        //print out the query entered by the user to check that it has gone through to this stage at least
        System.out.println("This is the query name entered by the user--> "+q);

        //query for the rowsets
        if(q==null){
            rs=jdbcTemplate.queryForRowSet(SQL_SELECT_ALL_RSVPROWS);
        }

        else{ rs=jdbcTemplate.queryForRowSet(SQL_SELECT_RSVP_BY_NAME, q);}

        //turn each rowset into an RSVP object and add it into the RSVP List
        while(rs.next()){
            rsvps.add(RSVP.createFromSQLRowSetToRSVP(rs));
        }

        return rsvps;

    }

    //GET METHOD 2 -- searching by email
    public List<RSVP> getByEmail(String emailInput){
        
        final List<RSVP> listzxc = new LinkedList<>();
        
        SqlRowSet rs = null;

        System.out.println("this is the email input by the user--> "+emailInput);

        if(emailInput==null){
            rs = jdbcTemplate.queryForRowSet(SQL_SELECT_ALL_RSVPROWS);
        }
        else{
            rs=jdbcTemplate.queryForRowSet(SQL_SELECT_RSVP_BY_EMAIL, emailInput);
        }

        //turn each rowset into a list
        while(rs.next()){
            listzxc.add(RSVP.createFromSQLRowSetToRSVP(rs));
        }

        return listzxc;

    }

    //PUT METHOD 1 -- updating a single row by email
    //returns a boolean true or false depending if operation is completed successfully or not -- see the > 0 part
    public boolean updateRSVPByEmail(final RSVP rsvpRow){
        return jdbcTemplate.update(SQL_UPDATE_RSVP_BY_EMAIL,
                rsvpRow.getName(),
                rsvpRow.getPhone(),
                new Timestamp(rsvpRow.getConfirmationDate().toDateTime().getMillis()),
                rsvpRow.getComments(),
                rsvpRow.getEmail()) > 0;
    }
    

    //GET COUNT TOTAL METHOD -- count the total number of rows in the table
    public Integer getTotalCount(){
        List<RSVP> rsvpList = jdbcTemplate.query(SQL_COUNT_TOTAL, new RSVPTotalCountMapper(), new Object[] {});

        return rsvpList.get(0).getTotalCount();
    }
}
