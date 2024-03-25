package br.com.christianpires.awpag.api.controller;

import br.com.christianpires.awpag.api.assembler.ParcelamentoAssembler;
import br.com.christianpires.awpag.api.model.ParcelamentoModel;
import br.com.christianpires.awpag.api.model.input.ParcelamentoInput;
import br.com.christianpires.awpag.domain.exception.NegocioException;
import br.com.christianpires.awpag.domain.model.Parcelamento;
import br.com.christianpires.awpag.domain.repository.ParcelamentoRepository;
import br.com.christianpires.awpag.domain.service.ParcelamentoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/parcelamentos")
public class ParcelamentoController {

    private final ParcelamentoRepository parcelamentoRepository;
    private final ParcelamentoService parcelamentoService;
    private final ParcelamentoAssembler parcelamentoAssembler;

    @GetMapping
    public List<ParcelamentoModel> listar() {
        return parcelamentoAssembler.toCollectionModel(parcelamentoRepository.findAll());
    }

    @GetMapping("/{parcelamentoId}")
    public ResponseEntity<ParcelamentoModel> buscar(@PathVariable Long parcelamentoId) {

        return parcelamentoRepository.findById(parcelamentoId)
                .map(parcelamentoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParcelamentoModel cadastrar(@Valid @RequestBody ParcelamentoInput parcelamentoInput) {
        Parcelamento novoParcelamento = parcelamentoAssembler.toEntity(parcelamentoInput);
        Parcelamento parcelamentoCadastro = parcelamentoService.cadastrar(novoParcelamento);
        return parcelamentoAssembler.toModel(parcelamentoCadastro);
    }


}
