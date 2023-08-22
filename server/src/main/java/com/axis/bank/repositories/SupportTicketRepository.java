package com.axis.bank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.axis.bank.entities.SupportTicket;
public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {
    List<SupportTicket> findByAccountId(String accountId);
    List<SupportTicket> findByStatus(String status);
}
