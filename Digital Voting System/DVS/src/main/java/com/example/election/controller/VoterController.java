package com.example.election.controller;

import com.example.election.dto.*;
import com.example.election.exception.UserNotFoundException;
import com.example.election.resource.Role;
import com.example.election.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/voter")
public class VoterController {
    @Autowired
    private VoterService voterService;


    @PostMapping("/createVoter")
    public ResponseEntity<?> createVoter(@RequestBody @Valid VoterDTO voterDTO) {
        try {
            if (voterDTO.getAge() < 18) {
                return new ResponseEntity<>("Age must be Greater than or Equal to 18",HttpStatus.BAD_REQUEST);
            } else if (voterDTO.getUserEntity().getRole()!= Role.VOTER) {

                return new ResponseEntity<>("give the correct user id",HttpStatus.BAD_REQUEST);

            }
            VoterDTO createdVoter = voterService.createVoter(voterDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdVoter);
        }
            catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @PutMapping("/updateVoter/{id}")
    public ResponseEntity<VoterDTO> updateVoter(@PathVariable long id, @RequestBody @Valid VoterDTO userDetails) throws UserNotFoundException {
        voterService.getVoterById(String.valueOf(id));
        userDetails.setVoterId(id);
        return new ResponseEntity<>(voterService.updateVoter(String.valueOf(id),userDetails), HttpStatus.OK);
    }

    @DeleteMapping("/deleteVoterById/{id}")
    public ResponseEntity<Void> deleteVoter(@PathVariable long id) throws  UserNotFoundException {
        voterService.deleteVoter(String.valueOf(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getVoterById/{id}")
    public ResponseEntity<VoterDTO> getVoterById(@PathVariable long id) throws  UserNotFoundException {
        return ResponseEntity.ok(voterService.getVoterById(String.valueOf(id)));
    }


}

