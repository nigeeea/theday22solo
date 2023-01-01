package hometest.day22solo.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class RSVPTotalCountMapper implements RowMapper<RSVP>{

    //GET COUNT METHOD 1
    @Override
    public RSVP mapRow(ResultSet rs, int rowNum) throws SQLException {
        RSVP r = new RSVP();
        r.setTotalCount(rs.getInt("total"));
        return r;
    }
    
}
