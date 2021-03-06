package br.com.jopaulofood.infrastructure.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.jopaulofood.application.service.RelatorioService;
import br.com.jopaulofood.application.service.RestauranteService;
import br.com.jopaulofood.application.service.ValidationException;
import br.com.jopaulofood.domain.pedido.Pedido;
import br.com.jopaulofood.domain.pedido.PedidoRepository;
import br.com.jopaulofood.domain.pedido.RelatorioItemFaturamento;
import br.com.jopaulofood.domain.pedido.RelatorioItemFilter;
import br.com.jopaulofood.domain.pedido.RelatorioPedidoFilter;
import br.com.jopaulofood.domain.restaurante.CategoriaRestauranteRepository;
import br.com.jopaulofood.domain.restaurante.ItemCardapio;
import br.com.jopaulofood.domain.restaurante.ItemCardapioRepository;
import br.com.jopaulofood.domain.restaurante.Restaurante;
import br.com.jopaulofood.domain.restaurante.RestauranteRepository;
import br.com.jopaulofood.util.SecurityUtils;

@Controller
@RequestMapping(path = "/restaurante")
public class RestauranteController {
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private RelatorioService relatorioService;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;
	
	@Autowired
	private ItemCardapioRepository itemCardapioRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
 
	@GetMapping(path = "/home")
	public String home(Model model) {
		Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
		List<Pedido> pedidos = pedidoRepository.findByRestaurante_IdOrderByDataDesc(restauranteId);
		model.addAttribute("pedidos", pedidos);
		return "restaurante-home";
	}
	
	@GetMapping(path = "/edit")
	public String edit(Model model) {
		Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
		Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow();
		model.addAttribute("restaurante", restaurante);
		ControllerHelper.setEditMode(model, true);
		ControllerHelper.addCategoriaToRequest(categoriaRestauranteRepository, model);
		return "restaurante-cadastro";
	}
	
	@PostMapping(path = "/save")
	public String save(@ModelAttribute("restaurante") @Valid Restaurante restaurante, Errors errors, Model model) {		
		if (!errors.hasErrors()) {
			try {
				restauranteService.saveRestaurante(restaurante);
				model.addAttribute("msg", "Restaurante salvo com sucesso!");
			} catch (ValidationException e) {
				errors.rejectValue("email", null, e.getMessage());
			}
		}
		ControllerHelper.setEditMode(model, true);
		ControllerHelper.addCategoriaToRequest(categoriaRestauranteRepository, model);
		return "restaurante-cadastro";
	}
	
	@GetMapping(path = "/comidas")
	public String viewComidas(Model model) {
		Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
		Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow();
		model.addAttribute("restaurante", restaurante);
		
		List<ItemCardapio> itensCardapio = itemCardapioRepository.findByRestaurante_IdOrderByNome(restauranteId);
		model.addAttribute("itensCardapio", itensCardapio);
		
		model.addAttribute("itemCardapio", new ItemCardapio());
		
		return "restaurante-comidas";
	}
	
	@GetMapping(path = "/comidas/remover")
	public String remover(@RequestParam("itemId") Integer itemId, Model model) {
		itemCardapioRepository.deleteById(itemId);	
		return "redirect:/restaurante/comidas";
	}
	
	@PostMapping(path = "/comidas/cadastrar")
	public String cadastrar(@Valid @ModelAttribute("itemCardapio") ItemCardapio itemCardapio, Errors errors, Model model) {
		if (errors.hasErrors()) {
			Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
			Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow();
			model.addAttribute("restaurante", restaurante);
			
			List<ItemCardapio> itensCardapio = itemCardapioRepository.findByRestaurante_IdOrderByNome(restauranteId);
			model.addAttribute("itensCardapio", itensCardapio);
			
			return "restaurante-comidas";
		}
		restauranteService.saveItemCardapio(itemCardapio);
		return "redirect:/restaurante/comidas";
	}
	
	@GetMapping(path = "/pedido")
	public String viewPedido(@RequestParam("pedidoId") Integer pedidoId, Model model) {
		Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow();
		model.addAttribute("pedido", pedido);
		return "restaurante-pedido";
	}
	
	@PostMapping(path = "/pedido/proximoStatus")
	public String proximoStatus(@RequestParam("pedidoId") Integer pedidoId, Model model) {
		Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow();
		pedido.definirProximoStatus();
		pedidoRepository.save(pedido);
		
		model.addAttribute("pedido", pedido);
		model.addAttribute("msg", "Status alterado com sucesso");
		
		return "restaurante-pedido";
	}
	
	@GetMapping(path = "/relatorio/pedidos")
	public String relatorioPedidos(@ModelAttribute("relatorioPedidoFilter") RelatorioPedidoFilter filter, Model model) {
		Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
		List<Pedido> pedidos = relatorioService.listPedidos(restauranteId, filter);
		model.addAttribute("pedidos", pedidos);
		
		model.addAttribute("filter", filter);
		
		return "restaurante-relatorio-pedidos";
	}
	
	@GetMapping(path = "/relatorio/itens")
	public String relatorioItens(@ModelAttribute("relatorioItemFilter") RelatorioItemFilter filter, Model model) {
		Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
		
		List<ItemCardapio> itensCardapio = itemCardapioRepository.findByRestaurante_IdOrderByNome(restauranteId);
		model.addAttribute("itensCardapio", itensCardapio);
		
		List<RelatorioItemFaturamento> itensCalculados = relatorioService.calcularFaturamentoItens(restauranteId, filter);
		model.addAttribute("itensCalculados", itensCalculados);
	
		model.addAttribute("relatorioItemFilter", filter);
		
		return "restaurante-relatorio-itens";
	}
}
