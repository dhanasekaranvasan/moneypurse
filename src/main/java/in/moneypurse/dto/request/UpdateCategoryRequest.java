package in.moneypurse.dto.request;

import java.util.UUID;

import in.moneypurse.entity.enums.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateCategoryRequest(@NotBlank(message = "Category name is required") @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters") String name,

		@Size(max = 255, message = "Description cannot exceed 255 characters") String description,

		@NotNull(message = "Category type is required") CategoryType type, UUID parentId, @NotNull(message = "Category active status is required") Boolean isActive) {}