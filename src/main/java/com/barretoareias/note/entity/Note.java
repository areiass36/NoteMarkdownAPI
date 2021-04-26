package com.barretoareias.note.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "note")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;
    
    @ManyToMany(fetch = FetchType.EAGER ,cascade = {CascadeType.MERGE})
    @JoinTable(
        name = "labels_in_note", 
        joinColumns = @JoinColumn(name = "note"), 
        inverseJoinColumns = @JoinColumn(name = "label"))
    private List<Label> labelsInNote;

}
