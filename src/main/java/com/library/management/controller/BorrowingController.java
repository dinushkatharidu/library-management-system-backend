package com.library.management.controller;

import com.library.management.model.Book;
import com.library.management.model.Borrowing;
import com.library.management.model.Member;
import com.library.management.repository.BookRepository;
import com.library.management.repository.BorrowingRepository;
import com.library.management.repository.MemberRepository;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@RestController
@RequestMapping("/borrow")
@CrossOrigin
public class BorrowingController {

    private final BorrowingRepository borrowingRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    public BorrowingController(BorrowingRepository borrowingRepository,
                          MemberRepository memberRepository,
                          BookRepository bookRepository) {
        this.borrowingRepository = borrowingRepository;
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
    }


    private static final int LATE_FEE_PER_DAY_CENTS = 100;
    private static final int MAX_ACTIVE_BORROWS = 2;
    private static final int BORROW_DAYS = 14;

    @GetMapping("/active")
    public ResponseEntity<?> getActiveLoans (@RequestParam Long memberId) {
        return ResponseEntity.ok(borrowingRepository.findAllByMember_IdAndReturnedAtIsNull(memberId));
    }

    @PostMapping("/borrow")
    public ResponseEntity<?> borrow(@RequestBody BorrowRequest req) {
        Optional<Member> memberOpt = memberRepository.findById(req.getMemberId());
        Optional<Book> bookOpt = bookRepository.findById(req.getBookId());

        if (memberOpt.isEmpty() || bookOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid memberId or bookId");
        }

        Member member = memberOpt.get();
        Book book = bookOpt.get();

        long activeCount = borrowingRepository.countByMember_IdAndReturnedAtIsNull(member.getId());
        if (activeCount >= MAX_ACTIVE_BORROWS) {
            return ResponseEntity.badRequest().body("Borrowing limit reached (2 active loans).");
        }

        if (book.getQuantity() == null || book.getQuantity() <= 0) {
            return ResponseEntity.badRequest().body("Book is not available.");
        }

        LocalDate now = LocalDate.now();
        Borrowing borrowing = new Borrowing();
        borrowing.setMember(member);
        borrowing.setBook(book);
        borrowing.setBorrowedAt(now);
        borrowing.setDueAt(now.plusDays(BORROW_DAYS));
        borrowing.setReturnedAt(null);
        borrowing.setFineCents(0);

        // Update stock
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        Borrowing saved = borrowingRepository.save(borrowing);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/{loanId}/return")
    public ResponseEntity<?> returnLoan(@PathVariable Long loanId) {
        Optional<Borrowing> borrowOpt = borrowingRepository.findById(loanId);
        if (borrowOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid loanId");
        }
        Borrowing borrowing = borrowOpt.get();
        if (borrowing.getReturnedAt() != null) {
            return ResponseEntity.badRequest().body("Loan already returned.");
        }

        LocalDate today = LocalDate.now();
        borrowing.setReturnedAt(today);

        int fine = 0;
        if (today.isAfter(borrowing.getDueAt())) {
            long lateDays = ChronoUnit.DAYS.between(borrowing.getDueAt(), today);
            fine = (int) lateDays * LATE_FEE_PER_DAY_CENTS;
        }
        borrowing.setFineCents(fine);

        // Restore stock
        Book book = borrowing.getBook();
        book.setQuantity((book.getQuantity() == null ? 0 : book.getQuantity()) + 1);
        bookRepository.save(book);

        Borrowing saved = borrowingRepository.save(borrowing);
        return ResponseEntity.ok(saved);
    }

    @Data
    public static class BorrowRequest {
        private Long memberId;
        private Long bookId;
    }
}
