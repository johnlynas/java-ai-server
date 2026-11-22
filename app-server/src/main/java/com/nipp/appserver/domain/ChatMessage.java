package com.nipp.appserver.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

/**
 * Persisted chat message, stored in the local H2 database.
 */
@Entity
@Table(name = "chat_messages")
public class ChatMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String userId;

	@Column(nullable = false, length = 4000)
	private String content;

	@Column(nullable = false)
	private Instant createdAt;

	protected ChatMessage() {
		// JPA
	}

	public ChatMessage(String userId, String content) {
		this.userId = userId;
		this.content = content;
		this.createdAt = Instant.now();
	}

	public Long getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public String getContent() {
		return content;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
