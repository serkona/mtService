package com.example.mtservice.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Entity
@Data
@Table(name="accounts")
@RequiredArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    private Long id;

    private Long balance;


}
