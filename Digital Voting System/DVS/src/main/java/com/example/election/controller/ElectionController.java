package com.example.election.controller;

import com.example.election.dto.ElectionDTO;
import com.example.election.exception.UserNotFoundException;
import com.example.election.service.ElectionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(path = "/election")
public class ElectionController {
    @Autowired
    private ElectionService electionService;
    @GetMapping("/getAllElections")
    public ResponseEntity<List<ElectionDTO>> getAllElections() {
        try {
            List<ElectionDTO> elections = electionService.getAllElections();
            return new ResponseEntity<>(elections, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getElectionById/{id}")
    public ResponseEntity<ElectionDTO> getElectionById(@PathVariable long id) throws UserNotFoundException {
        return ResponseEntity.ok(electionService.getElectionById(String.valueOf(id)));
    }

    @PostMapping("/createElection")
    public ResponseEntity<ElectionDTO> createElection(@RequestBody @Valid ElectionDTO electionDTO) {
        try {
            ElectionDTO createdElection = electionService.createElection(electionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdElection);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/updateElection/{id}")
    public ResponseEntity<ElectionDTO> updateElection(@PathVariable long id, @RequestBody @Valid ElectionDTO userDetails) throws UserNotFoundException {
        electionService.getElectionById(String.valueOf(id));
        userDetails.setElectionId(id);
        return new ResponseEntity<>(electionService.updateElection(String.valueOf(id),userDetails), HttpStatus.OK);
    }
    @DeleteMapping("/deleteElection/{id}")
    public ResponseEntity<Void> deleteElection(@PathVariable long id) throws UserNotFoundException {
        electionService.deleteElection(String.valueOf(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
