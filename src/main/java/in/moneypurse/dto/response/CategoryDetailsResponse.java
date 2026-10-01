package in.moneypurse.dto.response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

import in.moneypurse.entity.enums.CategoryType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CategoryDetailsResponse(UUID id, String code, String name, String description, CategoryType type,
		UUID parentId, List<CategoryDetailsResponse> subCategories, Boolean isSystem, Boolean isActive,
		Instant createdAt, Instant updatedAt) {

}
