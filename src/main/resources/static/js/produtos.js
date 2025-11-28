const API_URL =
    window.location.origin && window.location.origin !== 'null'
        ? `${window.location.origin}/api`
        : 'http://localhost:8081/api';

document.addEventListener('DOMContentLoaded', () => {
    carregarProdutos();
    document.getElementById('formProduto').addEventListener('submit', cadastrarProduto);
});

async function carregarProdutos() {
    const loading = document.getElementById('loading');
    const produtosList = document.getElementById('produtosList');
    
    try {
        loading.style.display = 'block';
        const response = await fetch(`${API_URL}/produtos`);
        const produtos = await response.json();
        
        loading.style.display = 'none';
        
        if (produtos.length === 0) {
            produtosList.innerHTML = '<p>Nenhum produto cadastrado ainda.</p>';
            return;
        }
        
        let html = '<table><thead><tr><th>ID</th><th>Nome</th><th>Descrição</th><th>Preço</th><th>Estoque</th><th>Ações</th></tr></thead><tbody>';
        
        produtos.forEach(produto => {
            html += `
                <tr>
                    <td>${produto.id}</td>
                    <td>${produto.nome}</td>
                    <td>${produto.descricao || '-'}</td>
                    <td>R$ ${parseFloat(produto.preco).toFixed(2)}</td>
                    <td>${produto.quantidadeEstoque}</td>
                    <td>
                        <button class="btn btn-outline-secondary btn-sm me-2" onclick="editarProduto(${produto.id})">Editar</button>
                        <button class="btn btn-outline-danger btn-sm" onclick="deletarProduto(${produto.id})">Excluir</button>
                    </td>
                </tr>
            `;
        });
        
        html += '</tbody></table>';
        produtosList.innerHTML = html;
    } catch (error) {
        loading.style.display = 'none';
        produtosList.innerHTML = `<div class="error">Erro ao carregar produtos: ${error.message}</div>`;
    }
}

async function cadastrarProduto(e) {
    e.preventDefault();
    
    const formData = {
        nome: document.getElementById('nome').value,
        descricao: document.getElementById('descricao').value,
        preco: parseFloat(document.getElementById('preco').value),
        quantidadeEstoque: parseInt(document.getElementById('quantidadeEstoque').value)
    };
    
    try {
        const response = await fetch(`${API_URL}/produtos`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        });
        
        if (response.ok) {
            document.getElementById('formProduto').reset();
            mostrarMensagem('Produto cadastrado com sucesso!', 'success');
            carregarProdutos();
        } else {
            const errors = await response.json();
            mostrarErros(errors);
        }
    } catch (error) {
        mostrarMensagem(`Erro ao cadastrar produto: ${error.message}`, 'error');
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

function mostrarErros(errors) {
    const formSection = document.querySelector('.form-section');
    let html = '<div class="error"><ul>';
    for (const [campo, mensagem] of Object.entries(errors)) {
        html += `<li>${campo}: ${mensagem}</li>`;
    }
    html += '</ul></div>';
    formSection.insertAdjacentHTML('afterbegin', html);
    
    setTimeout(() => {
        const errorDiv = formSection.querySelector('.error');
        if (errorDiv) errorDiv.remove();
    }, 5000);
}

async function deletarProduto(id) {
    if (!confirm('Tem certeza que deseja excluir este produto?')) return;
    
    try {
        const response = await fetch(`${API_URL}/produtos/${id}`, {
            method: 'DELETE'
        });
        
        if (response.ok || response.status === 204) {
            mostrarMensagem('Produto excluído com sucesso!', 'success');
            carregarProdutos();
        } else {
            const errorData = await response.json().catch(() => ({ message: 'Erro ao excluir produto' }));
            mostrarMensagem(`Erro ao excluir produto: ${errorData.message || 'Erro desconhecido'}`, 'error');
        }
    } catch (error) {
        mostrarMensagem(`Erro ao excluir produto: ${error.message}`, 'error');
    }
}

function editarProduto(id) {
    alert(`Editar produto ${id} - Funcionalidade a ser implementada`);
}

