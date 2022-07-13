package com.dscatalog.services;

import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dscatalog.dto.CategoryDTO;
import com.dscatalog.entities.Category;
import com.dscatalog.exception.CategoryAtualizadaException;
import com.dscatalog.exception.CategoryCadastradaException;
import com.dscatalog.exception.DadosNaoLocalizadosException;
import com.dscatalog.exception.DeleteException;
import com.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {

	private static Logger logger = LoggerFactory.getLogger(CategoryService.class);

	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPage(PageRequest page) {
		logger.info("Buscando todas Categorias");
		Page<Category> list = repository.findAll(page);
		return list.map(this::convereterEntidadeParaDTO);
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
		verificarExistenciaNoBanco(dto);
		return repository.save(convereteDTOParaEntity(dto));

	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		validarParaAtualizar(dto);

		logger.info("Atualizando Categoria");

		Optional<Category> entity = repository.findById(id);

		entity.stream().map(newEntity -> {
			newEntity.setName(dto.getName());
			return convereterEntidadeParaDTO(repository.save(newEntity));

		}).collect(Collectors.toList());
		return dto;

	}

	public void delete(Long id) {
		verificarIdParaDeletar(id);
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

	// verificadores

	public void verificarExistenciaNoBanco(CategoryDTO dto) {

		logger.info("VERIFICANDO EXISTENCIA DE CATEGORIA SALVA NO BANCO");

		if (dto.getName().isEmpty() || dto.getName().isBlank()) {
			logger.error("NÃO PODE CADASTRAR CATEGORIA NULO OU BRANCO ", dto.getName());
			throw new CategoryCadastradaException("Nao pode cadastrar Categoria nulo ou branco " + dto.getName());
		}

		if (repository.findByName(dto.getName()).isPresent()) {
			logger.error("JÁ EXISTE CATEGORIA CADASTRADA COM O NOME: " + dto.getName() + "Com o id {} " + dto.getId());
			throw new CategoryCadastradaException("Já Existe Categoria cadastrada com o nome: " + dto.getName());
		}
	}

	private void validarParaAtualizar(CategoryDTO dto) {

		logger.info("VALIDANDO LOJA PARA ATUALIZAR ");

		if (dto.getId() == null) {
			logger.error("NÃO PODE ATUALIZAR CATEGORIA COM ID NULO ", dto.getId());
			throw new CategoryAtualizadaException("Nao pode atualizar categoria com id nulo: " + dto.getId());
		}

		if (dto.getId() <= 0) {
			logger.error("NÃO PODE ATUALIZAR CATEGORIA COM ID MENOR OU IGUAL A ZERO " + dto.getId());
			throw new CategoryAtualizadaException("Nao pode Atualizar categoria menor ou igual a zero " + dto.getId());
		}

		if (repository.findByName(dto.getName()).isPresent()) {
			logger.error("NÃO PODE ATUALIZAR CATEGORIA COM O MESMO NOME JÁ EXISTESNTE: " + dto.getName());
			throw new CategoryAtualizadaException(
					"Não pode atualizar categoria com o nome já existente: " + dto.getName());
		}

	}

	public void verificarIdParaDeletar(Long id) {
		if (!repository.findById(id).isPresent()) {
			logger.error(id + " : Não localizado para deletar");
			throw new DeleteException("ID: " + id + " não localizado para deletar");

		}

	}

}
