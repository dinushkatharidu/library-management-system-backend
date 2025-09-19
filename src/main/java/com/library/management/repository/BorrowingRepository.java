package com.library.management.repository;

import com.library.management.model.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {
    long countByMember_IdAndReturnedAtIsNull(Long memberId);
    List<Borrowing> findAllByMember_IdAndReturnedAtIsNull(Long memberId);
}
