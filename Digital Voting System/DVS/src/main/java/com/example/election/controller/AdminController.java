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

import java.util.List;
@RestController
@RequestMapping(path = "/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/getAllAdmin")
    public ResponseEntity<List<AdminDTO>> getAllAdmin() {
        try {
            List<AdminDTO> admin = adminService.getAllAdmin();
            return new ResponseEntity<>(admin, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAdminById/{id}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable long id) throws UserNotFoundException {
        return ResponseEntity.ok(adminService.getAdminById(String.valueOf(id)));
    }

    @PostMapping("/createAdmin")
    public ResponseEntity<?> createAdmin(@RequestBody @Valid AdminDTO adminDTO) {
        try {
            if (adminDTO.getAge() < 18) {
                return new ResponseEntity<>("Age must be Greater than or Equal to 18", HttpStatus.BAD_REQUEST);
            } else if (adminDTO.getUserEntity().getRole()!= Role.ADMIN) {

                return new ResponseEntity<>("give the correct user id",HttpStatus.BAD_REQUEST);

            }
            AdminDTO createdAdmin = adminService.createAdmin(adminDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAdmin);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/updateAdmin/{id}")
    public ResponseEntity<AdminDTO> updateAdmin(@PathVariable long id, @RequestBody @Valid AdminDTO userDetails) throws UserNotFoundException {
        adminService.getAdminById(String.valueOf(id));
        userDetails.setAdminId(id);
        return new ResponseEntity<>(adminService.updateAdmin(String.valueOf(id), userDetails), HttpStatus.OK);
    }

    @DeleteMapping("/deleteAdmin/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable long id) throws UserNotFoundException {
        adminService.deleteAdmin(String.valueOf(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Autowired
    private VoterService voterService;
    @GetMapping("/getAllVoter")
    public ResponseEntity<List<VoterDTO>> getAllVoters() {
        try {
            List<VoterDTO> voter = voterService.getAllVoter();
            return new ResponseEntity<>(voter, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Autowired
    private CandidateService candidateService;
    @GetMapping("/getAllCandidate")
    public ResponseEntity<List<CandidateDTO>> getAllCandidate() {
        try {
            List<CandidateDTO> candidate = candidateService.getAllCandidates();
            return new ResponseEntity<>(candidate, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
  
    
  




  


  





