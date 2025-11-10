package com.gymapp.application.service.category;

import com.gymapp.api.dto.category.request.CategoryCreateRequest;
import com.gymapp.api.dto.category.request.CategoryFilterRequest;
import com.gymapp.api.dto.category.request.CategoryUpdateRequest;
import com.gymapp.api.dto.category.response.CategoryResponse;
import com.gymapp.application.mapper.category.CategoryMapper;
import com.gymapp.domain.entity.Category;
import com.gymapp.infrastructure.persistence.CategoryJpaRepository;
import com.gymapp.shared.dto.PageResponseDTO;
import com.gymapp.shared.error.exception.AppException;
import com.gymapp.shared.error.exception.BadRequestException;
import com.gymapp.shared.error.exception.ConflictException;
import com.gymapp.shared.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.gymapp.shared.error.ErrorConstants.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryJpaRepository repository;
    private final CategoryMapper categoryMapper;

    @Override
    public PageResponseDTO<CategoryResponse> search(CategoryFilterRequest filterRequest, Pageable pageable) throws BadRequestException {
        Page<Category> page = repository.findAll(pageable);

        List<CategoryResponse> content = page.map(categoryMapper::toResponse).getContent();

        return PageResponseDTO.<CategoryResponse>builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .empty(page.isEmpty())
                .content(content)
                .build();
    }

    @Override
    public CategoryResponse getById(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(()-> new AppException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, THE_CATEGORY_WITH_ID_D_DOES_NOT_EXIST.formatted(id)));
        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse createCategory(CategoryCreateRequest request) {
        if(repository.existsByNameIgnoreCase(request.getName().trim())){
            throw new ConflictException(THE_CATEGORY_ALREADY_EXISTS);
        }
        Category category = categoryMapper.toEntity(request);
        Category saved = repository.save(category);
        return categoryMapper.toResponse(saved);
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryUpdateRequest request) {
        Category existing = repository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, THE_CATEGORY_WITH_ID_D_DOES_NOT_EXIST.formatted(id)));

        existing.setName(request.getName());
        Category saved = repository.save(existing);
        return categoryMapper.toResponse(saved);
    }

    @Override
    public void deleteCategory(Long id) {
        if(!repository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND ,THE_CATEGORY_WITH_ID_D_DOES_NOT_EXIST.formatted(id));
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException ex){
            throw new ConflictException(CANNOT_DELETE_THE_CATEGORY_IS_REFERENCED, ex);
        }
    }
}
