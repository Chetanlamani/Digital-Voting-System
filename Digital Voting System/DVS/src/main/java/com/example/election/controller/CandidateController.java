package com.example.election.controller;

import com.example.election.dto.CandidateDTO;


import com.example.election.exception.UserNotFoundException;
import com.example.election.resource.Role;
import com.example.election.service.CandidateService;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping(path = "/candidate")
public class CandidateController {
    @Autowired
    private CandidateService candidateService;


    @PostMapping("/createCandidate")
    public ResponseEntity<?> createCandidate(@RequestBody @Valid CandidateDTO candidateDTO) {
        try {
            if (candidateDTO.getAge() < 25) {
                return new ResponseEntity<>("age must be Greater and Equal to 25",HttpStatus.BAD_REQUEST);
            }else if (candidateDTO.getUserEntity().getRole()!= Role.CANDIDATE) {

                return new ResponseEntity<>("give the correct user id",HttpStatus.BAD_REQUEST);

            }
            CandidateDTO createdUser = candidateService.createCandidate(candidateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @PutMapping("/update/{id}")
    public ResponseEntity<CandidateDTO> updateCandidate(@PathVariable long id, @RequestBody @Valid CandidateDTO userDetails) throws UserNotFoundException {
        candidateService.getCandidateById(String.valueOf(id));
        userDetails.setCandidateId(id);
        return new ResponseEntity<>(candidateService.updateCandidate(String.valueOf(id), userDetails), HttpStatus.OK);
    }
    @DeleteMapping("/deleteCandidate/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable long id) throws UserNotFoundException {
        candidateService.deleteCandidate(String.valueOf(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getCandidateById/{id}")
    public ResponseEntity<CandidateDTO> getCandidateById(@PathVariable long id) throws UserNotFoundException {
        return ResponseEntity.ok(candidateService.getCandidateById(String.valueOf(id)));
    }
    


}


