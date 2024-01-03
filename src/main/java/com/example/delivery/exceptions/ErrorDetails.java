package com.example.delivery.exceptions;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ErrorDetails {

	@Id
	private LocalDateTime localDateTime;

	@Column
	private String message;

	@Column
	private String details;
}
