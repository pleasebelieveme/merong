package org.example.merong.domain.user.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import org.example.merong.common.base.BaseEntity;
import org.example.merong.domain.songs.entity.Song;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
		name = "users",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = {"email"})
		}
)
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String email;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Song> songs;

	@Column
	private boolean isDeleted;

	public void withdraw() {
		this.name = "deleted user" + UUID.randomUUID();
		this.isDeleted = true;
	}

	public void changeProfileInformation(String name, String newPassword) {

		QUser qUser = QUser.user;
		this.name = name;
		this.password = newPassword;
	}

	public User(String email, String name, String password) {
		this.email = email;
		this.name = name;
		this.password = password;
	}

}
