/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spring.hibernate.tuto.springHibernate.controller;

/**
 *
 * @author pc
 */

import com.spring.hibernate.tuto.springHibernate.exception.ResourceNotFoundException;
import com.spring.hibernate.tuto.springHibernate.model.entrepot;
import com.spring.hibernate.tuto.springHibernate.repository.entrepotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EntrepotController {
    @Autowired
    entrepotRepository noteRepository;

    // Get All Notes
    @GetMapping("/notes")
    public List<entrepot> getAllNotes() {
        return noteRepository.findAll();
    }
    // Create a new Note
    @PostMapping("/notes")
    public entrepot createNote(@Valid @RequestBody entrepot note) {
        return noteRepository.save(note);
    }
    // Get a Single Note
    @GetMapping("/notes/{id}")
    public entrepot getNoteById(@PathVariable(value = "id") Long noteId) {
        return noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
    }
    // Update a Note
    @PutMapping("/notes/{id}")
    public entrepot updateNote(@PathVariable(value = "id") Long noteId,
                                            @Valid @RequestBody entrepot noteDetails) {

        entrepot note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());

        entrepot updatedNote = noteRepository.save(note);
        return updatedNote;
    }
    // Delete a Note
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId) {
    entrepot note = noteRepository.findById(noteId)
            .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

    noteRepository.delete(note);

    return ResponseEntity.ok().build();
}
}
