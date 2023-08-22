package com.axis.bank.controllers;

import com.axis.bank.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import com.axis.bank.entities.SupportTicket;
import com.axis.bank.repositories.SupportTicketRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/tickets")
public class SupportTicketController {
    @Autowired
    private SupportTicketRepository supportTicketRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<SupportTicket> createSupportTicket(@RequestBody SupportTicket supportTicket) {
        supportTicket.setStatus("Open");
        SupportTicket createdTicket = supportTicketRepository.save(supportTicket);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
    }

    @GetMapping("/{id}/{accountId}")
    public ResponseEntity<List<SupportTicket>> getSupportTicketsByAccountID(@PathVariable String accountId) {
        List<SupportTicket> supportTickets = supportTicketRepository.findByAccountId(accountId);
        return ResponseEntity.ok(supportTickets);
    }

    @PutMapping("/{id}")
    public SupportTicket updateTicket(@PathVariable Long id, @RequestBody SupportTicket supportTicket) {
        supportTicket.setId(id);
        return supportTicketRepository.save(supportTicket);
    }
    @GetMapping("/{id}")
    public Optional<SupportTicket> getTicket(@PathVariable Long id) {
        return supportTicketRepository.findById(id);
    }
    @GetMapping
    public ResponseEntity<List<SupportTicket>> getTicketsByStatus(@RequestParam(name = "status", required = false) String status) {
        List<SupportTicket> filteredTickets;

        if (status != null) {
            filteredTickets = supportTicketRepository.findByStatus(status);
        } else {
            filteredTickets = supportTicketRepository.findAll();
        }
        return ResponseEntity.ok(filteredTickets);
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Long id) {
        supportTicketRepository.deleteById(id);
    }
}

