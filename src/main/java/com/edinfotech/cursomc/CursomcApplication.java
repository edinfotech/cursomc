package com.edinfotech.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.edinfotech.cursomc.domain.Categoria;
import com.edinfotech.cursomc.domain.Cidade;
import com.edinfotech.cursomc.domain.Cliente;
import com.edinfotech.cursomc.domain.Endereco;
import com.edinfotech.cursomc.domain.Estado;
import com.edinfotech.cursomc.domain.Pagamento;
import com.edinfotech.cursomc.domain.PagamentoComBoleto;
import com.edinfotech.cursomc.domain.PagamentoComCartao;
import com.edinfotech.cursomc.domain.Pedido;
import com.edinfotech.cursomc.domain.Produto;
import com.edinfotech.cursomc.domain.enums.EstadoPagamento;
import com.edinfotech.cursomc.domain.enums.TipoCliente;
import com.edinfotech.cursomc.repositories.CategoriaRepository;
import com.edinfotech.cursomc.repositories.CidadeRepository;
import com.edinfotech.cursomc.repositories.ClienteRepository;
import com.edinfotech.cursomc.repositories.EnderecoRepository;
import com.edinfotech.cursomc.repositories.EstadoRepository;
import com.edinfotech.cursomc.repositories.PagamentoRepository;
import com.edinfotech.cursomc.repositories.PedidoRepository;
import com.edinfotech.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "informatica");
		Categoria cat2 = new Categoria(null, "escritorio");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Sao Paulo");
		Estado est2 = new Estado(null, "Parana");

		Cidade c1 = new Cidade(null, "Peruibe", est1);
		Cidade c2 = new Cidade(null, "Santos", est1);
		Cidade c3 = new Cidade(null, "Curitiba", est2);

		est1.getCidades().addAll(Arrays.asList(c1, c2));
		est2.getCidades().addAll(Arrays.asList(c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
	
		Cliente cli1 = new Cliente(null, "Cristiane", "Cris@mmmm", "222.222.222.-22",TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("138887777333", "33444324234"));
		
		Endereco e1 = new Endereco(null, "Rua Paraiso", "332", "fundos", "paradise", "1777555", c1, cli1);
		Endereco e2 = new Endereco(null, "Rua Paraiso", "332", "fundos", "paradise", "1777555", c2, cli1);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		
	}

}
