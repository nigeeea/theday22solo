package hometest.day22solo.models;

import java.io.ByteArrayInputStream;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormat;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class RSVP {

    private Integer id;
    private String name;
    private String email;
    private String phone;
    private DateTime confirmationDate;
    private String comments;
    private Integer totalCount;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public DateTime getConfirmationDate() {
        return this.confirmationDate;
    }

    public void setConfirmationDate(DateTime confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    //this method takes in a rowset and returns the rowset in RSVP object form - data transfer object
    public static RSVP createFromSQLRowSetToRSVP(SqlRowSet rs){
        RSVP r = new RSVP();
        r.setId(rs.getInt("id"));
        r.setName(rs.getString("name"));
        r.setEmail(rs.getString("email"));
        r.setPhone(rs.getString("phone"));
        r.setConfirmationDate(new DateTime(
            DateTimeFormat.forPattern("dd/MM/yyyy")
                    .parseDateTime(rs.getString("confirmation_date"))));
        r.setComments(rs.getString("comments"));

        return r;
    }

    //this method will convert an RSVP Object to JSON format
    public JsonObject fromRVSPtoJSON(){
        return Json.createObjectBuilder()
                .add("id", getId())
                .add("name", getName())
                .add("email", getEmail())
                .add("phone", getPhone())
                .add("confirmation_date", getConfirmationDate() != null ? getConfirmationDate().toString() : "")
                .add("comments", getComments())
                .build();
    }


    //these two below methods are used in conjunction to take the json object and convert it into an rsvp object
    public static RSVP createStringJSONtoRsvp(String jsonStr) throws Exception {
        //take a json string and puts it into a json reader -- not read yet
        JsonReader reader = Json.createReader(
                new ByteArrayInputStream(jsonStr.getBytes()));
        //json reader reads the json string into a json object 
        //then the create method takes the json object and turns it into an rsvp object 
        return create(reader.readObject());
    }

    private static RSVP create(JsonObject readObject) {
        final RSVP rsvp = new RSVP();
        rsvp.setName(readObject.getString("name"));
        rsvp.setEmail(readObject.getString("email"));
        rsvp.setPhone(readObject.getString("phone"));
        rsvp.setConfirmationDate(new DateTime(Instant.parse(readObject.getString("confirmation_date"))));
        rsvp.setComments(readObject.getString("comments"));
        return rsvp;
    }
}
