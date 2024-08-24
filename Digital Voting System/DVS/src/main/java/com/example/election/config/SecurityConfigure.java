package com.example.election.config;

import com.example.election.filter.JwtAuthenticationFilter;
import com.example.election.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigure {
    @Autowired
    private  UserService userService;
    @Autowired
    private   JwtAuthenticationFilter authenticationFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http )throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req->req.requestMatchers("login/**","register/**")
                        .permitAll()
                        .requestMatchers("voter/createVoter/**","voter/updateVoter/**","voter/deleteVoterById/**",
                                "voter/getVoterById/**","election/getAllElections/**","admin/getAllCandidate/**",
                                "vote/getVoteByVoterId/**","vote/createVote/**","vote/updateVote/**",
                                "vote/deleteVoteById/**", "notification/getNotificationByVoterId**")
                        .hasAuthority("VOTER")
                        .requestMatchers("admin/createAdmin/**","admin/getAllAdmin/**","admin/getAdminById/{id}/**",
                                "admin/updateAdmin/**","admin/deleteAdmin/**","admin/getAllVoter/**",
                                "admin/getAllCandidate/**","candidate/deleteCandidate/**","election/getAllElections/**",
                                "election/getElectionById/**","election/createElection/**","election/updateElection/**",
                                "election/deleteElection/**","notification/getNotificationById/**","notification/getAllNotification/**",
                                "notification/create/**","notification/update/**","notification/delete/{id}/**","vote/getAllVote/**",
                                "vote/deleteVoteById/**","voter/deleteVoterById/**")
                        .hasAuthority("ADMIN")
                        .requestMatchers("candidate/createCandidate/**","candidate/update/**","candidate/deleteCandidate/**",
                                "candidate/getCandidateById/**","election/getAllElections/**","vote/getVoteByCandidateId/**")
                        .hasAuthority("CANDIDATE")
                        .anyRequest()
                        .authenticated())
                .userDetailsService(userService)
                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception{
        return configuration.getAuthenticationManager();

    }
}
