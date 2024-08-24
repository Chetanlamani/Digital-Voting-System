package com.example.election.controller;

import com.example.election.dto.VoteDTO;
import com.example.election.exception.UserNotFoundException;
import com.example.election.service.VoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(path = "/vote")
public class VoteController {
    @Autowired
    private VoteService voteService;

    @GetMapping("/getAllVote")
    public ResponseEntity<List<VoteDTO>> getAllVote() {
        try {
            List<VoteDTO> vote = voteService.getAllVote();
            return new ResponseEntity<>(vote, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getVoteById/{id}")
    public ResponseEntity<VoteDTO> getVoteById(@PathVariable long id) throws UserNotFoundException {
        return ResponseEntity.ok(voteService.getVoteById(String.valueOf(id)));
    }
    @DeleteMapping("/deleteVoteById/{id}")
    public ResponseEntity<Void> deleteVote(@PathVariable long id) throws UserNotFoundException {
        voteService.deleteVote(String.valueOf(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/createVote")
    public ResponseEntity<VoteDTO> createVote(@RequestBody @Valid VoteDTO voteDTO) {
        try {
            VoteDTO createdVote = voteService.createVote(voteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdVote);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateVote/{id}")
    public ResponseEntity<VoteDTO> updateVote(@PathVariable long id, @RequestBody @Valid VoteDTO userDetails) throws UserNotFoundException {
        voteService.getVoteById(String.valueOf(id));
        userDetails.setVoteId(id);
        return new ResponseEntity<>(voteService.updateVote(String.valueOf(id),userDetails), HttpStatus.OK);
    }

    @GetMapping("/getVoteByVoterId/{voterId}")
    public ResponseEntity<List<VoteDTO>> getAllVotesForVoter(@PathVariable long voterId) {
        try {
            List<VoteDTO> votes = voteService.getAllVoteForVoter(voterId);
            return new ResponseEntity<>(votes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getVoteByCandidateId/{candidateId}")
    public ResponseEntity<List<VoteDTO>> getAllVotesCandidate(@PathVariable long candidateId) {
        try {
            List<VoteDTO> votes = voteService.getAllVoteForCandidate(candidateId);
            return new ResponseEntity<>(votes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
