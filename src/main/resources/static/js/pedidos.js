const API_URL = 'http://localhost:8080/api';

let clientes = [];
let produtos = [];

document.addEventListener('DOMContentLoaded', () => {
    carregarClientes();
    carregarProdutos();
    carregarPedidos();
    document.getElementById('formPedido').addEventListener('submit', criarPedido);
});

async function carregarClientes() {
    try {
        const response = await fetch(`${API_URL}/clientes`);
        clientes = await response.json();
        
        const select = document.getElementById('clienteId');
        select.innerHTML = '<option value="">Selecione um cliente</option>';
        
        clientes.forEach(cliente => {
            const option = document.createElement('option');
            option.value = cliente.id;
            option.textContent = `${cliente.nome} (${cliente.email})`;
            select.appendChild(option);
        });
        
        // Atualizar selects de produtos nos itens
        atualizarSelectsProdutos();
    } catch (error) {
        console.error('Erro ao carregar clientes:', error);
    }
}

async function carregarProdutos() {
    try {
        const response = await fetch(`${API_URL}/produtos`);
        produtos = await response.json();
        atualizarSelectsProdutos();
    } catch (error) {
        console.error('Erro ao carregar produtos:', error);
    }
}

function atualizarSelectsProdutos() {
    const selects = document.querySelectorAll('.produto-select');
    selects.forEach(select => {
        const currentValue = select.value;
        select.innerHTML = '<option value="">Selecione um produto</option>';
        
        produtos.forEach(produto => {
            const option = document.createElement('option');
            option.value = produto.id;
            option.textContent = `${produto.nome} - R$ ${parseFloat(produto.preco).toFixed(2)} (Estoque: ${produto.quantidadeEstoque})`;
            option.dataset.estoque = produto.quantidadeEstoque;
            select.appendChild(option);
        });
        
        if (currentValue) {
            select.value = currentValue;
        }
    });
}

function adicionarItem() {
    const container = document.getElementById('itensContainer');
    const itemDiv = document.createElement('div');
    itemDiv.className = 'item-pedido';
    
    itemDiv.innerHTML = `
        <div class="form-group">
            <label>Produto</label>
            <select class="produto-select" required>
                <option value="">Selecione um produto</option>
            </select>
        </div>
        <div class="form-group">
            <label>Quantidade</label>
            <input type="number" class="quantidade-input" min="1" required>
        </div>
        <button type="button" class="btn-remove" onclick="removerItem(this)">Remover</button>
    `;
    
    container.appendChild(itemDiv);
    atualizarSelectsProdutos();
}

function removerItem(button) {
    button.closest('.item-pedido').remove();
}

async function criarPedido(e) {
    e.preventDefault();
    
    const clienteId = parseInt(document.getElementById('clienteId').value);
    const itens = [];
    
    const itemDivs = document.querySelectorAll('.item-pedido');
    itemDivs.forEach(itemDiv => {
        const produtoId = parseInt(itemDiv.querySelector('.produto-select').value);
        const quantidade = parseInt(itemDiv.querySelector('.quantidade-input').value);
        
        if (produtoId && quantidade) {
            itens.push({ produtoId, quantidade });
        }
    });
    
    if (itens.length === 0) {
        mostrarMensagem('Adicione pelo menos um item ao pedido', 'error');
        return;
    }
    
    const pedidoData = {
        clienteId,
        itens
    };
    
    try {
        const response = await fetch(`${API_URL}/pedidos`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(pedidoData)
        });
        
        if (response.ok) {
            const pedido = await response.json();
            mostrarMensagem(`Pedido #${pedido.id} criado com sucesso!`, 'success');
            document.getElementById('formPedido').reset();
            document.getElementById('itensContainer').innerHTML = `
                <h3>Itens do Pedido</h3>
                <div class="item-pedido">
                    <div class="form-group">
                        <label>Produto</label>
                        <select class="produto-select" required>
                            <option value="">Selecione um produto</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Quantidade</label>
                        <input type="number" class="quantidade-input" min="1" required>
                    </div>
                    <button type="button" class="btn-remove" onclick="removerItem(this)">Remover</button>
                </div>
            `;
            atualizarSelectsProdutos();
            carregarPedidos();
        } else {
            const error = await response.text();
            mostrarMensagem(`Erro ao criar pedido: ${error}`, 'error');
        }
    } catch (error) {
        mostrarMensagem(`Erro ao criar pedido: ${error.message}`, 'error');
    }
}

async function carregarPedidos() {
    const loading = document.getElementById('loading');
    const pedidosList = document.getElementById('pedidosList');
    
    try {
        loading.style.display = 'block';
        const response = await fetch(`${API_URL}/pedidos`);
        const pedidos = await response.json();
        
        loading.style.display = 'none';
        
        if (pedidos.length === 0) {
            pedidosList.innerHTML = '<p>Nenhum pedido cadastrado ainda.</p>';
            return;
        }
        
        let html = '<table><thead><tr><th>ID</th><th>Cliente</th><th>Data</th><th>Status</th><th>Itens</th><th>Total</th></tr></thead><tbody>';
        
        pedidos.forEach(pedido => {
            const data = new Date(pedido.dataPedido).toLocaleString('pt-BR');
            const total = pedido.itens.reduce((sum, item) => {
                return sum + (parseFloat(item.precoUnitario) * item.quantidade);
            }, 0);
            
            html += `
                <tr>
                    <td>#${pedido.id}</td>
                    <td>${pedido.cliente.nome}</td>
                    <td>${data}</td>
                    <td>${pedido.status}</td>
                    <td>${pedido.itens.length} item(ns)</td>
                    <td>R$ ${total.toFixed(2)}</td>
                </tr>
            `;
        });
        
        html += '</tbody></table>';
        pedidosList.innerHTML = html;
    } catch (error) {
        loading.style.display = 'none';
        pedidosList.innerHTML = `<div class="error">Erro ao carregar pedidos: ${error.message}</div>`;
    }
}

function mostrarMensagem(mensagem, tipo) {
    const formSection = document.querySelector('.form-section');
    const div = document.createElement('div');
    div.className = tipo;
    div.textContent = mensagem;
    formSection.insertBefore(div, formSection.firstChild);
    
    setTimeout(() => div.remove(), 5000);
}

