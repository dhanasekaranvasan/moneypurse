package in.moneypurse.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.moneypurse.dto.ApiResponse;
import in.moneypurse.dto.request.CreateCategoryRequest;
import in.moneypurse.dto.request.UpdateCategoryRequest;
import in.moneypurse.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	@PostMapping
	public ResponseEntity<ApiResponse<?>> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.of(HttpStatus.CREATED,
				"Category created successfully...!", categoryService.createCategory(request)));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<?>> getAllCategories() {
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Fetch all categories successfully...!",
				categoryService.getAllCategories()));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<?>> updateCategory(@PathVariable UUID id, @Valid @RequestBody UpdateCategoryRequest request) {
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Category updated successfully...!",
				categoryService.updateCategory(id, request)));
	}

}
