package com.oceantech.backend.controller;

import com.oceantech.backend.entity.CreditCard;
import com.oceantech.backend.repository.CreditCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user/card")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CreditCardController {

    private final CreditCardRepository creditCardRepository;

    @GetMapping
    public ResponseEntity<List<CreditCard>> getCards() {
        return ResponseEntity.ok(creditCardRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<CreditCard> createCard(@RequestBody CreditCard card) {
        return ResponseEntity.ok(creditCardRepository.save(card));
    }

    @PutMapping
    public ResponseEntity<CreditCard> updateCard(@RequestBody CreditCard card) {
        if (!creditCardRepository.existsById(card.getId())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(creditCardRepository.save(card));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        creditCardRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}