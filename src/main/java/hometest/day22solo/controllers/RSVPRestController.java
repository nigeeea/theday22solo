package hometest.day22solo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hometest.day22solo.models.RSVP;
import hometest.day22solo.services.RSVPService;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@RestController
@RequestMapping(path = "/api/getrsvp", produces = MediaType.APPLICATION_JSON_VALUE)
public class RSVPRestController {

    @Autowired 
    private RSVPService rsvpSvc;

    //GET METHOD 1 -- searching by name
    @GetMapping()
    public ResponseEntity<String> getAllCustomers(@RequestParam(required = false) String q){
        //querying the database and returning each rowset as an RSVP object in a List of RSVP Objects
        List<RSVP> rsvpListzxc = rsvpSvc.getAllRSVPByName(q);

        //Building the result
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        //for each rsvp object in the rsvps list, convert each RSVP Object into a JSON Object and add it to the json array
        for(RSVP rsvpObject:rsvpListzxc)
            arrBuilder.add(rsvpObject.fromRVSPtoJSON());

        //build the array
        jakarta.json.JsonArray result = arrBuilder.build();

        //print it out in the terminal just to check
            System.out.println("This is the result that should be returned--> "+result.toString());

        
            //if empty, AKA query name does not exist, or if there is some sort of error, return an empty http response
            if (rsvpListzxc.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{'error_codezxc': " + HttpStatus.NOT_FOUND + "'}");
            }

            //else return a response with the result
            else{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toString());
            }

        }
        
        //GET METHOD 2 -- searching by email
        @GetMapping(path = "/email")
        public ResponseEntity<String> getByEmail(@RequestParam(required = false) String emailInput){

            //querying the database and returning each rowset as an RSVP object in a List of RSVP Objects
        List<RSVP> rsvpListzxc = rsvpSvc.getByEmail(emailInput);

        //Building the result
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        //for each rsvp object in the rsvps list, convert each RSVP Object into a JSON Object and add it to the json array
        for(RSVP rsvpObject:rsvpListzxc)
            arrBuilder.add(rsvpObject.fromRVSPtoJSON());

        //build the array
        jakarta.json.JsonArray result = arrBuilder.build();

        //print it out in the terminal just to check
            System.out.println("This is the result that should be returned--> "+result.toString());

        
            //if empty, AKA query name does not exist, or if there is some sort of error, return an empty http response
            if (rsvpListzxc.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{'error_codezxczxc': " + HttpStatus.NOT_FOUND + "'}");
            }

            //else return a response with the result
            else{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toString());
            }
        }

        //PUT METHOD 1 -- update the row in the rsvp table with the specified email input
        @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> updateRSVPTable(@RequestBody String jsonString){

            //create an empty rsvp object, create a boolean, create an empty json object
            RSVP rsvp = null;
            boolean rsvpResult = false;
            JsonObject resp;

            //convert the json input into an rsvp object
            try {
                rsvp = RSVP.createStringJSONtoRsvp(jsonString);
                
            } 
            //if there is an error in parsing the json input, return an error message immediately
            catch (Exception e) {
                e.printStackTrace();
                resp = Json.createObjectBuilder()
                        .add("error", e.getMessage())
                        .build();
                return ResponseEntity.badRequest().body(resp.toString());
            }

            //try to update with the query, if successful, the update RSVPByEmail method will return true, else will return false
            rsvpResult = rsvpSvc.updateRSVPByEmail(rsvp);

            //create the response body for the response entity
            resp = Json.createObjectBuilder()
                    .add("updates", rsvpResult)
                    .build();

            //create and return the response entity
            return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(resp.toString());

        }


        //GET COUNT METHOD 1 - correct way i guess
        @GetMapping(path = "/count")
        public ResponseEntity<String> countRSVP() {
            JsonObject resp;
            // Query the database for the rsvps
            Integer totalCntRsvps = rsvpSvc.getTotalRSVP();
    
            resp = Json.createObjectBuilder()
                    .add("total_cnt", totalCntRsvps)
                    .build();
    
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(resp.toString());
    
        }

        //GET COUNT METHOD 2 - the cheat way lol but easier
        @GetMapping(path = "/countalternative")
        public ResponseEntity<String> countRSVPAlternative(){

            List<RSVP> rsvpList = rsvpSvc.getAllRSVPByName(null);

            Integer numberOfRows = rsvpList.size();
            System.out.println(numberOfRows);

            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            JsonObject finalCount = objectBuilder.add("count", numberOfRows).build();

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(finalCount.toString());
        }

        //POST METHOD 1 - insert a row - if the email in the json body used to post already exist, update instead
        @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> postRowInTable(@RequestBody String jsonBodyForPosting){

            RSVP rsvp = null;
            RSVP rsvpResult = null;
            JsonObject resp;

            try {
                rsvp = RSVP.createStringJSONtoRsvp(jsonBodyForPosting);
            } 
            catch (Exception e) {
                e.printStackTrace();
                resp = Json.createObjectBuilder()
                        .add("error", e.getMessage())
                        .build();
                    return ResponseEntity.badRequest().body(resp.toString());
            }

            //now take the created rsvp object and try to insert it into the table and store the it in rsvpResult as well
            rsvpResult = rsvpSvc.insertRSVP(rsvp);

            //create a new json object to use as the http response
            resp = Json.createObjectBuilder()
                    .add("rsvpRowID", rsvpResult.getId())
                    .build();

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(resp.toString());
        }

        //POST METHOD 2 -- easy version done by myself
        @PostMapping(path = "/nigelpost", consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> easyPost(@RequestBody String jsonBodyString){

            String response = "";

            //try to update the post, if it doesnt work store the error message in the response
            try {
                boolean updatedStatus = rsvpSvc.easyPost(jsonBodyString);
                response = String.valueOf(updatedStatus);    
            } catch (Exception e) {
                e.printStackTrace();
                response = e.getMessage();
                return ResponseEntity.badRequest().body(response);
                
            }
            
            JsonObject returnObject = Json.createObjectBuilder().add("responseToMe", response).build();


            return ResponseEntity.status(HttpStatus.CREATED)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(returnObject.toString());
        }
        
}

