package in.moneypurse.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import in.moneypurse.entity.BaseEntity;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, ID> extends JpaRepository<T, ID> {

	Optional<T> findByPublicId(UUID publicId);

	default Optional<T> findByPublicId(String publicId) {
		if (publicId == null || publicId.isBlank()) {
			return Optional.empty();
		}
		try {
			return findByPublicId(UUID.fromString(publicId));
		} catch (IllegalArgumentException e) {
			return Optional.empty();
		}
	}

}
