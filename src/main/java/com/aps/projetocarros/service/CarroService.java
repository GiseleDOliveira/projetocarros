package com.aps.projetocarros.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aps.projetocarros.model.Carro;
import com.aps.projetocarros.repository.CarroRepository;

@Service
public class CarroService {

    @Autowired
    private CarroRepository repositorioCarro;

    public Carro addCarro(Carro carro) {
        return repositorioCarro.save(carro);
    }

    public Carro atualizarCarro(Long id, Carro carro) {
        Carro carroExistente = repositorioCarro.findById(id)
                .orElseThrow(() -> new RuntimeException("Carro não encontrado"));
        carroExistente.setModelo(carro.getModelo());
        carroExistente.setMarca(carro.getMarca());
        carroExistente.setAno(carro.getAno());
        carroExistente.setPreco(carro.getPreco());
        return repositorioCarro.save(carroExistente);
    }

    public void removerCarro(Long id) {
        repositorioCarro.deleteById(id);
    }

    public List<Carro> listarCarros() {
        return repositorioCarro.findAll();
    }

    public Carro obterCarroPorId(Long id) {
        return repositorioCarro.findById(id)
                .orElseThrow();
    }
}
