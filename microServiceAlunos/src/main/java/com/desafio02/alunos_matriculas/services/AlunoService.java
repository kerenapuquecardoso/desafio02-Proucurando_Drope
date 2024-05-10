package com.desafio02.alunos_matriculas.services;

import com.desafio02.alunos_matriculas.entities.Aluno;
import com.desafio02.alunos_matriculas.exceptions.CpfUniqueViolationException;
import com.desafio02.alunos_matriculas.exceptions.EntityNotFoundException;
import com.desafio02.alunos_matriculas.repositories.AlunoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AlunoService {
    private final AlunoRepository alunoRepository;

    @Transactional
    public Aluno salvar(Aluno aluno) {
        try {
            return alunoRepository.save(aluno);
        } catch (DataIntegrityViolationException ex) {
            throw new CpfUniqueViolationException("O cpf do aluno deve ser único");
        }
    }

    @Transactional
    public Aluno inabilitarAluno(Long id) {
        Aluno aluno = buscarPorId(id);
        aluno.setAtivo(false);
        return aluno;
    }

    @Transactional
    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Aluno id=%s não encontrado", id))
        );
    }

    @Transactional
    public List<Aluno> buscarTodos() {
        return alunoRepository.findAll();
    }

    @Transactional
    public Aluno mudarAluno(Long id, Aluno alunoEditado) {
        Aluno aluno = buscarPorId(id);
        aluno.setAtivo(alunoEditado.isAtivo());
        aluno.setCpf(alunoEditado.getCpf());
        aluno.setSexo(alunoEditado.getSexo());
        aluno.setDataNascimento(alunoEditado.getDataNascimento());
        aluno.setNome(alunoEditado.getNome());
        return salvar(aluno);
    }

    @Transactional
    public void apagarAluno(Long id) {
        Aluno aluno = buscarPorId(id);
        alunoRepository.delete(aluno);
    }
}
