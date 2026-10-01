package in.moneypurse.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.moneypurse.dto.request.CreateCategoryRequest;
import in.moneypurse.dto.request.UpdateCategoryRequest;
import in.moneypurse.dto.response.CategoryDetailsResponse;
import in.moneypurse.entity.Category;
import in.moneypurse.entity.CategoryCode;
import in.moneypurse.exception.ResourceAlreadyExistsException;
import in.moneypurse.exception.ResourceNotFoundException;
import in.moneypurse.mapper.CategoryMapper;
import in.moneypurse.repository.CategoryRepository;
import in.moneypurse.service.CategoryService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;
	private final CategoryMapper mapper;

	@Override
	@Transactional
	public CategoryDetailsResponse createCategory(CreateCategoryRequest request) {
		CategoryCode code = CategoryCode.fromName(request.name());
		if (categoryRepository.existsByCode(code.value())) {
			throw new ResourceAlreadyExistsException("Category", "name", request.name());
		}
		Category category = mapper.toEntity(request);
		category.setCode(code);
		if (request.parentId() != null) {
			Category parent = categoryRepository.findByPublicId(request.parentId())
					.orElseThrow(() -> new ResourceNotFoundException("Category", "parentId", request.parentId()));
			category.setParent(parent);
		}
		category = categoryRepository.save(category);
		List<Category> subCategories = categoryRepository.findByParentId(category.getId());
		return mapper.toDto(category, subCategories);
	}

	@Transactional(readOnly = true)
	@Override
	public List<CategoryDetailsResponse> getAllCategories() {
		List<Category> allCategories = categoryRepository.findAll();

		Map<UUID, CategoryDetailsResponse> dtoMap = allCategories.stream()
				.map(cat -> new CategoryDetailsResponse(cat.getPublicId(), cat.getCode(), cat.getName(),
						cat.getDescription(), cat.getType(),
						cat.getParent() != null ? cat.getParent().getPublicId() : null, new ArrayList<>(),
						cat.isSystem(), cat.isActive(), cat.getCreatedAt(), cat.getUpdatedAt()))
				.collect(Collectors.toMap(CategoryDetailsResponse::id, dto -> dto));

		List<CategoryDetailsResponse> rootNodes = new ArrayList<>();

		for (CategoryDetailsResponse node : dtoMap.values()) {
			if (node.parentId() == null) {
				rootNodes.add(node);
			} else {
				CategoryDetailsResponse parentNode = dtoMap.get(node.parentId());
				if (parentNode != null) {
					parentNode.subCategories().add(node);
				}
			}
		}

		return rootNodes;
	}

	@Override
	public CategoryDetailsResponse updateCategory(UUID id, UpdateCategoryRequest request) {
		Category category = categoryRepository.findByPublicId(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		CategoryCode newCode = CategoryCode.fromName(request.name());
		if (!category.getCode().equals(newCode.value()) && categoryRepository.existsByCode(newCode.value())) {
			throw new ResourceAlreadyExistsException("Category", "name", request.name());
		}
		category.updateDetails(request.name(), newCode.value(), request.description(), request.type(),
				request.isActive());
		if (request.parentId() == null) {
			category.setParent(null);
		} else if (category.getParent() == null || !category.getParent().getPublicId().equals(request.parentId())) {
			Category parent = categoryRepository.findByPublicId(request.parentId())
					.orElseThrow(() -> new ResourceNotFoundException("Category", "parentId", request.parentId()));
			category.setParent(parent);
		}
		Category updatedCategory = categoryRepository.save(category);
		List<Category> subCategories = categoryRepository.findByParentId(updatedCategory.getId());
		return mapper.toDto(updatedCategory, subCategories);
	}

}
