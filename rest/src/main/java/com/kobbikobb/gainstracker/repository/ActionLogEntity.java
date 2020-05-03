package com.kobbikobb.gainstracker.repository;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "action_logs")
@Getter
@Setter
public class ActionLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double value;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "actionId"))
    ActionEntity action;
}
