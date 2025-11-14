const API_URL = 'http://localhost:8080/api';

// Carregar clientes ao carregar a página
document.addEventListener('DOMContentLoaded', () => {
    carregarClientes();
    document.getElementById('formCliente').addEventListener('submit', cadastrarCliente);
});

async function carregarClientes() {
    const loading = document.getElementById('loading');
    const clientesList = document.getElementById('clientesList');
    
    try {
        loading.style.display = 'block';
        const response = await fetch(`${API_URL}/clientes`);
        const clientes = await response.json();
        
        loading.style.display = 'none';
        
        if (clientes.length === 0) {
            clientesList.innerHTML = '<p>Nenhum cliente cadastrado ainda.</p>';
            return;
        }
        
        let html = '<table><thead><tr><th>ID</th><th>Nome</th><th>E-mail</th><th>Telefone</th><th>Ações</th></tr></thead><tbody>';
        
        clientes.forEach(cliente => {
            html += `
                <tr>
                    <td>${cliente.id}</td>
                    <td>${cliente.nome}</td>
                    <td>${cliente.email}</td>
                    <td>${cliente.telefone}</td>
                    <td>
                        <button class="btn-secondary" onclick="editarCliente(${cliente.id})">Editar</button>
                        <button class="btn-remove" onclick="deletarCliente(${cliente.id})">Excluir</button>
                    </td>
                </tr>
            `;
        });
        
        html += '</tbody></table>';
        clientesList.innerHTML = html;
    } catch (error) {
        loading.style.display = 'none';
        clientesList.innerHTML = `<div class="error">Erro ao carregar clientes: ${error.message}</div>`;
    }
}

async function cadastrarCliente(e) {
    e.preventDefault();
    
    const formData = {
        nome: document.getElementById('nome').value,
        email: document.getElementById('email').value,
        telefone: document.getElementById('telefone').value
    };
    
    try {
        const response = await fetch(`${API_URL}/clientes`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        });
        
        if (response.ok) {
            document.getElementById('formCliente').reset();
            mostrarMensagem('Cliente cadastrado com sucesso!', 'success');
            carregarClientes();
        } else {
            const errors = await response.json();
            mostrarErros(errors);
        }
    } catch (error) {
        mostrarMensagem(`Erro ao cadastrar cliente: ${error.message}`, 'error');
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

async function deletarCliente(id) {
    if (!confirm('Tem certeza que deseja excluir este cliente?')) return;
    
    try {
        const response = await fetch(`${API_URL}/clientes/${id}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            mostrarMensagem('Cliente excluído com sucesso!', 'success');
            carregarClientes();
        }
    } catch (error) {
        mostrarMensagem(`Erro ao excluir cliente: ${error.message}`, 'error');
    }
}

function editarCliente(id) {
    // Implementar edição (pode abrir modal ou preencher formulário)
    alert(`Editar cliente ${id} - Funcionalidade a ser implementada`);
}

