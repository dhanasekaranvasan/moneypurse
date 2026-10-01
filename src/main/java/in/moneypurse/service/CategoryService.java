package in.moneypurse.service;

import java.util.List;
import java.util.UUID;

import in.moneypurse.dto.request.CreateCategoryRequest;
import in.moneypurse.dto.request.UpdateCategoryRequest;
import in.moneypurse.dto.response.CategoryDetailsResponse;
import jakarta.validation.Valid;

public interface CategoryService {
	
	CategoryDetailsResponse createCategory(CreateCategoryRequest request);

	List<CategoryDetailsResponse> getAllCategories();

	CategoryDetailsResponse updateCategory(UUID id, @Valid UpdateCategoryRequest request);

}
