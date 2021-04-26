package com.barretoareias.note.repository;

import java.util.Optional;

import com.barretoareias.note.entity.Note;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long>{
    
    Optional<Note> findByTitle(String title);
}
