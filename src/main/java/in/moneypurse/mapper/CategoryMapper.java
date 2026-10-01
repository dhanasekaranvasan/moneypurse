package in.moneypurse.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import in.moneypurse.dto.request.CreateCategoryRequest;
import in.moneypurse.dto.response.CategoryDetailsResponse;
import in.moneypurse.entity.Category;

@Component
public class CategoryMapper {

	public Category toEntity(CreateCategoryRequest request) {
		return Category.create(request.name(), request.description(), request.type(), false, true);
	}

	public CategoryDetailsResponse toDto(Category category, List<Category> categories) {
		if (category == null) {
			return null;
		}

		List<CategoryDetailsResponse> subCategories = categories.stream()
				.map(cat -> new CategoryDetailsResponse(cat.getPublicId(), cat.getCode(), cat.getName(), null, null,
						cat.getParent() != null ? cat.getParent().getPublicId() : null, null, null, cat.isActive(),
						null, null))
				.toList();

		return new CategoryDetailsResponse(category.getPublicId(), category.getCode(), category.getName(),
				category.getDescription(), category.getType(),
				category.getParent() != null ? category.getParent().getPublicId() : null, subCategories,
				category.isSystem(), category.isActive(), category.getCreatedAt(), category.getUpdatedAt());
	}

}
