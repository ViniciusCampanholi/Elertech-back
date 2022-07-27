package com.grupo1.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.grupo1.ecommerce.model.Carrinho;
import com.grupo1.ecommerce.model.CartaoCredito;
import com.grupo1.ecommerce.model.Endereco;
import com.grupo1.ecommerce.model.Item;
import com.grupo1.ecommerce.model.Pedido;
import com.grupo1.ecommerce.model.Usuario;
import com.grupo1.ecommerce.repository.CartaoCreditoRepository;
import com.grupo1.ecommerce.repository.EnderecoRepository;
import com.grupo1.ecommerce.repository.PedidoRepository;
import com.grupo1.ecommerce.repository.UsuarioRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private CartaoCreditoRepository cartaoRepository;

    public ResponseEntity<Pedido> fazerPedido(Long idUsuario, Long idEndereco, Long idCartao) {
        Usuario usuario = usuarioRepository.findById(idUsuario).get();
        Endereco endereco = enderecoRepository.findById(idEndereco).get();
        CartaoCredito cartao = cartaoRepository.findById(idCartao).get();

        Carrinho carrinho = usuario.getCarrinho();
        Pedido novoPedido = new Pedido();
        Pedido pedido = pedidoRepository.save(novoPedido);

        pedido.setEnderecoEntrega(endereco.getEndereco() + " - " + endereco.getCep());
        pedido.setFormaPagamento(cartao.getApelido() + " - final " + cartao.getNumeroCartao().substring(15, 19));
        pedido.setUsuario(usuario);

        for (Item item : carrinho.getItem()) {
            item.setCarrinho(null);
            item.setPedido(pedido);
            pedido.setQuantidadeItens(pedido.getQuantidadeItens() + item.getQuantidade());
            pedido.setValorTotalPedido(pedido.getValorTotalPedido() + item.getValorTotal());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoRepository.save(pedido));
    }

    public List<Pedido> pedidosDoUsuario(Long idUsuario) {
        return pedidoRepository.findAllPedidoUsuario(idUsuario);
    }
}