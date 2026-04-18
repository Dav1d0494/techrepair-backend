<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel Técnico - TechRepair</title>
    <style>
        body{font-family:Arial,Helvetica,sans-serif;background:#f5f7fb;color:#333;margin:0;padding:0}
        .container{max-width:1000px;margin:32px auto;background:#fff;padding:20px;border-radius:8px;box-shadow:0 4px 12px rgba(0,0,0,0.06)}
        h1{margin:0 0 16px;font-size:22px;color:#0b5ed7}
        form{display:flex;flex-direction:column;gap:8px}
        input[type=text], textarea{padding:10px;border:1px solid #dfe3ea;border-radius:6px;font-size:14px}
        textarea{min-height:120px}
        .row{display:flex;gap:12px}
        .btn{background:#0b5ed7;color:#fff;padding:10px 14px;border:none;border-radius:6px;cursor:pointer}
        .btn:disabled{opacity:.6;cursor:not-allowed}
        table{width:100%;border-collapse:collapse;margin-top:18px}
        th,td{padding:10px;border-bottom:1px solid #eee;text-align:left}
        th{background:#f8fafc}
        .danger{background:#dc3545;color:#fff;border-radius:6px;padding:6px 10px;border:none;cursor:pointer}
        .msg{padding:10px;border-radius:6px;margin-bottom:12px}
        .error{background:#ffe7e7;color:#b00020}
        .success{background:#e7fff0;color:#0b7a3b}
    </style>
</head>
<body>
<div class="container">
    <h1>Panel Técnico - TechRepair</h1>

    <div id="messages"></div>

    <form id="newsForm">
        <input type="hidden" id="newsId" />
        <label>Título</label>
        <input type="text" id="title" required />
        <label>Contenido</label>
        <textarea id="content" required></textarea>
        <div class="row">
            <button class="btn" id="saveBtn" type="submit">Guardar noticia</button>
            <button type="button" id="clearBtn">Limpiar</button>
        </div>
    </form>

    <table id="newsTable">
        <thead>
        <tr><th>Título</th><th>Contenido</th><th>Acciones</th></tr>
        </thead>
        <tbody></tbody>
    </table>
</div>

<script>
    const apiBase = window.location.origin + '/api/news';

    function showMessage(text, type='error'){
        const messages = document.getElementById('messages');
        messages.innerHTML = `<div class="msg ${type==='error'?'error':'success'}">${text}</div>`;
        setTimeout(()=>messages.innerHTML='',5000);
    }

    async function loadNews(){
        try{
            const res = await fetch(apiBase + '/list');
            if(!res.ok) throw new Error('HTTP ' + res.status);
            const list = await res.json();
            const tbody = document.querySelector('#newsTable tbody');
            tbody.innerHTML = '';
            list.forEach(n=>{
                const tr = document.createElement('tr');
                const contentPreview = n.content.length>200 ? n.content.substring(0,200)+'...' : n.content;
                tr.innerHTML = `<td>${escapeHtml(n.title)}</td><td>${escapeHtml(contentPreview)}</td><td><button class="danger" data-id="${n.id}">Eliminar</button></td>`;
                tbody.appendChild(tr);
            });
        }catch(e){
            showMessage('No fue posible cargar las noticias técnicas desde el backend', 'error');
            console.error(e);
        }
    }

    function escapeHtml(unsafe){
        return unsafe
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#039;');
    }

    document.getElementById('newsForm').addEventListener('submit', async (e)=>{
        e.preventDefault();
        const id = document.getElementById('newsId').value || null;
        const title = document.getElementById('title').value.trim();
        const content = document.getElementById('content').value.trim();
        if(!title || !content){ showMessage('Completa título y contenido', 'error'); return; }
        const payload = { id: id?Number(id):null, title, content };
        const btn = document.getElementById('saveBtn');
        btn.disabled = true;
        try{
            const res = await fetch(apiBase + '/save', {
                method:'POST', headers:{'Content-Type':'application/json'}, body: JSON.stringify(payload)
            });
            if(!res.ok) throw new Error('HTTP ' + res.status);
            const saved = await res.json();
            showMessage('Noticia guardada', 'success');
            document.getElementById('newsForm').reset();
            document.getElementById('newsId').value = '';
            await loadNews();
        }catch(err){
            showMessage('Error guardando la noticia', 'error');
            console.error(err);
        }finally{ btn.disabled = false; }
    });

    document.getElementById('clearBtn').addEventListener('click', ()=>{
        document.getElementById('newsForm').reset();
        document.getElementById('newsId').value = '';
    });

    document.querySelector('#newsTable tbody').addEventListener('click', async (e)=>{
        if(e.target.matches('button.danger')){
            const id = e.target.getAttribute('data-id');
            if(!confirm('Eliminar noticia?')) return;
            try{
                const res = await fetch(apiBase + '/delete/' + id, {method:'DELETE'});
                if(!res.ok) throw new Error('HTTP ' + res.status);
                showMessage('Noticia eliminada', 'success');
                await loadNews();
            }catch(err){
                showMessage('Error eliminando la noticia', 'error');
                console.error(err);
            }
        }
    });

    // initial load
    loadNews();
</script>
</body>
</html>
