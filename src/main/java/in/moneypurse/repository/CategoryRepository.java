package in.moneypurse.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;

import in.moneypurse.entity.Category;

public interface CategoryRepository extends BaseRepository<Category, Long> {
	
	@EntityGraph(attributePaths = {"parent"})
    Optional<Category> findByPublicId(UUID publicId);

	boolean existsByCode(String code);
	
	@EntityGraph(attributePaths = {"parent"})
	List<Category> findByParentId(Long parentId);

}
