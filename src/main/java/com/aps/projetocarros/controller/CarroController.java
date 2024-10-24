package com.aps.projetocarros.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aps.projetocarros.config.SpringContext;
import com.aps.projetocarros.model.Carro;
import com.aps.projetocarros.service.CarroService;
import com.aps.projetocarros.service.QRCodeService;
import com.itextpdf.html2pdf.HtmlConverter;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/carros")
public class CarroController {

    @Autowired
    private CarroService servicoCarro;

    @Autowired
    private QRCodeService servicoQRCode;

    @PostMapping
    public ResponseEntity<Carro> adicionarCarro(@RequestBody Carro carro) {
        return ResponseEntity.ok(servicoCarro.addCarro(carro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carro> atualizarCarro(@PathVariable Long id, @RequestBody Carro carro) {
        return ResponseEntity.ok(servicoCarro.atualizarCarro(id, carro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerCarro(@PathVariable Long id) {
        servicoCarro.removerCarro(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carro> obterCarroPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicoCarro.obterCarroPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Carro>> listarCarros() {
        return ResponseEntity.ok(servicoCarro.listarCarros());
    }

    @GetMapping("/relatorio")
    public void baixarRelatorioCarros(HttpServletResponse resposta, Model modelo) throws IOException {

        List<Carro> carros = servicoCarro.listarCarros();

        modelo.addAttribute("carros", carros);

        String conteudoHtml = gerarRelatorioHtml(modelo);

        ByteArrayOutputStream pdfSaida = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(new ByteArrayInputStream(conteudoHtml.getBytes()), pdfSaida);

        resposta.setContentType("application/pdf");
        resposta.setHeader("Content-Disposition", "attachment; filename=relatorio_carros.pdf");
        resposta.getOutputStream().write(pdfSaida.toByteArray());
    }

    private String gerarRelatorioHtml(Model modelo) {
        org.thymeleaf.context.Context contextoThymeleaf = new org.thymeleaf.context.Context();
        contextoThymeleaf.setVariables(modelo.asMap());
        return SpringContext.getThymeleafTemplateEngine().process("carReport", contextoThymeleaf);
    }

    @GetMapping("/{id}/qrcode")
    public ResponseEntity<byte[]> gerarQRCodeCarro(@PathVariable Long id) {
        Carro carro = servicoCarro.obterCarroPorId(id);
        String informacoesCarro = String.format("Nome: %s, Marca: %s, Preço: %.2f, Ano: %s", 
                carro.getModelo(), carro.getMarca(), carro.getPreco(), carro.getAno());

        byte[] imagemQRCode = servicoQRCode.generateQRCode(informacoesCarro, 300, 300);

        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imagemQRCode);
    }
}
