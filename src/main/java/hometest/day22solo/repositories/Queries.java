package hometest.day22solo.repositories;

public class Queries {

    //read all records
    public static final String SQL_SELECT_ALL_RSVPROWS = "select id, name, email, phone, DATE_FORMAT(confirmation_date, \"%d/%m/%Y\") as confirmation_date, comments from rsvp";
    
    //read all records with the specified name
    public static final String SQL_SELECT_RSVP_BY_NAME = "select id, email, name, phone, DATE_FORMAT(confirmation_date, \"%d/%m/%y\") as confirmation_date, comments from rsvp where name = ?";

    //read all records with the specified email
    public static final String SQL_SELECT_RSVP_BY_EMAIL = "select id, email, name, phone, DATE_FORMAT(confirmation_date, \"%d/%m/%y\") as confirmation_date, comments from rsvp where email = ?";
    
    //count the total number of rowsets in the tabll
    public static final String SQL_COUNT_TOTAL = "select count(*) as total from rsvp";
    
    //read all values with the specific string in the name e.g. search em will return emily and emil information


    //update rowset whith specified email
    public static final String SQL_UPDATE_RSVP_BY_EMAIL = "update rsvp set name= ?, phone= ?, confirmation_date= ?, comments= ? where email =?";

    //insert new record into the table
    public  static final String SQL_INSERT_RSVP = "insert into rsvp(name, email, phone, confirmation_date, comments) values (?,?,?,?,?)";


}
