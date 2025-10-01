package com.aula.exception.handler.projetoexception.entity.cliente.service;

import com.aula.exception.handler.projetoexception.entity.cliente.Cliente;
import com.aula.exception.handler.projetoexception.entity.cliente.dto.ClienteRequestDto;
import com.aula.exception.handler.projetoexception.entity.cliente.dto.ClienteResponseDto;
import com.aula.exception.handler.projetoexception.entity.cliente.mapper.ClienteMapper;
import com.aula.exception.handler.projetoexception.entity.cliente.repository.ClienteRepository;
import com.aula.exception.handler.projetoexception.exception.business.ClienteNaoEncontradoException;
import com.aula.exception.handler.projetoexception.exception.business.CpfJaCadastradoException;
import com.aula.exception.handler.projetoexception.exception.business.EmailJaCadastradoException;
import com.aula.exception.handler.projetoexception.exception.business.NomeJaCadastradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public ClienteResponseDto salvarCliente(ClienteRequestDto dto) {
        if (clienteRepository.existsByNome(dto.nome())){
            throw new NomeJaCadastradoException(dto.nome());
        }
        if (clienteRepository.existsByEmail(dto.email())){
            throw new EmailJaCadastradoException(dto.email());
        }
        if (clienteRepository.existsByCpf(dto.cpf())){
            throw new CpfJaCadastradoException(dto.cpf());
        }
        Cliente cliente = ClienteMapper.toCliente(dto);
        var clienteSalvo = clienteRepository.save(cliente);
        return ClienteMapper.toDto(clienteSalvo);
    }

    @Override
    public ClienteResponseDto buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException(id));
        return ClienteMapper.toDto(cliente);
    }

    @Override
    public List<ClienteResponseDto> buscarTodos() {
        return clienteRepository.findAll() // retorna List<Cliente>
                .stream()               // cria um Stream<Cliente> pra poder fazer operações funcionais
                .map(ClienteMapper::toDto) // transforma cada Cliente em ClienteResponseDto
                .toList();              // coleta tudo de volta numa List<ClienteResponseDto>
    }

    @Override
    public ClienteResponseDto deletarCliente(Long id) {
        var respose = clienteRepository.findById(id)
                .orElseThrow(()-> new ClienteNaoEncontradoException(id));
        clienteRepository.deleteById(id);
        return ClienteMapper.toDto(respose);
    }
}
