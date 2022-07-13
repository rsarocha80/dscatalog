package com.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dscatalog.dto.CategoryDTO;
import com.dscatalog.entities.Category;
import com.dscatalog.exception.DadosNaoLocalizadosException;
import com.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {

	private static Logger logger = LoggerFactory.getLogger(CategoryService.class);

	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		logger.info("Buscando todas Categorias");
		List<Category> list = repository.findAll();
		return list.stream().map(this::convereterEntidadeParaDTO).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		logger.info("Buscando Categorias por id {} " + id);

		Category entity = repository.findById(id).orElseThrow(
				() -> new DadosNaoLocalizadosException("Não foi Localizado a Categoria com o id {} " + id));
		return convereterEntidadeParaDTO(entity);
	}

	@Transactional
	public Category insert(CategoryDTO dto) {
		return repository.save(convereteDTOParaEntity(dto));

	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {

		logger.info("Atualizando Categoria");

		Optional<Category> entity = repository.findById(id);

		entity.stream().map(newEntity -> {
			newEntity.setName(dto.getName());
			return convereterEntidadeParaDTO(repository.save(newEntity));

		}).collect(Collectors.toList());
		return dto;

	}

	public void delete(Long id) {

		repository.deleteById(id);

	}

	// conversores

	public CategoryDTO convereterEntidadeParaDTO(Category entity) {

		CategoryDTO dto = new CategoryDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;

	}

	public Category convereteDTOParaEntity(CategoryDTO dto) {

		Category entity = new Category();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		return entity;

	}

}
