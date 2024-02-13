package com.example.app2.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Accessors(fluent = false)
@SuperBuilder
@Data
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {
}
