<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel Técnico - TechRepair</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background: white;
            border-radius: 15px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            overflow: hidden;
        }
        
        .header {
            background: linear-gradient(135deg, #2c3e50 0%, #3498db 100%);
            color: white;
            padding: 25px 30px;
        }
        
        .header h1 {
            font-size: 28px;
            margin-bottom: 5px;
        }
        
        .header p {
            opacity: 0.9;
            font-size: 14px;
        }
        
        .content {
            padding: 30px;
        }
        
        h2 {
            color: #2c3e50;
            margin-bottom: 10px;
            font-size: 24px;
        }
        
        .subtitle {
            color: #7f8c8d;
            margin-bottom: 25px;
            font-size: 14px;
        }
        
        .error-message {
            background: #fee;
            color: #c33;
            padding: 12px 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            border-left: 4px solid #c33;
            display: none;
        }
        
        .error-message.show {
            display: block;
        }
        
        .form-section {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 30px;
        }
        
        .form-group {
            margin-bottom: 15px;
        }
        
        label {
            display: block;
            margin-bottom: 5px;
            color: #2c3e50;
            font-weight: 500;
        }
        
        input[type="text"],
        textarea {
            width: 100%;
            padding: 12px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            font-size: 14px;
            transition: border-color 0.3s;
        }
        
        input[type="text"]:focus,
        textarea:focus {
            outline: none;
            border-color: #3498db;
        }
        
        .btn {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 12px 30px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: transform 0.2s, box-shadow 0.2s;
        }
        
        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(102, 126, 234, 0.4);
        }
        
        .btn:active {
            transform: translateY(0);
        }
        
        .btn-secondary {
            background: #95a5a6;
            margin-left: 10px;
        }
        
        .table-container {
            overflow-x: auto;
            margin-top: 20px;
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        th {
            background: #2c3e50;
            color: white;
            padding: 15px;
            text-align: left;
            font-weight: 600;
        }
        
        td {
            padding: 12px 15px;
            border-bottom: 1px solid #ecf0f1;
        }
        
        tr:hover {
            background: #f8f9fa;
        }
        
        .loading {
            text-align: center;
            padding: 40px;
            color: #7f8c8d;
        }
        
        .empty-message {
            text-align: center;
            padding: 40px;
            color: #95a5a6;
            font-style: italic;
        }
        
        .stats {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin: 30px 0;
        }
        
        .stat-card {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px;
            border-radius: 10px;
            text-align: center;
        }
        
        .stat-card h3 {
            font-size: 14px;
            opacity: 0.9;
            margin-bottom: 10px;
        }
        
        .stat-card .value {
            font-size: 32px;
            font-weight: bold;
        }
        
        .footer {
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid #ecf0f1;
            text-align: center;
            color: #7f8c8d;
        }
        
        .footer a {
            color: #3498db;
            text-decoration: none;
        }
        
        .footer a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>🔧 Panel Técnico</h1>
            <p>nain.zuñiga@itegperformance.com · Rol: Técnico</p>
        </div>
        
        <div class="content">
            <h2>📰 Noticias técnicas</h2>
            <p class="subtitle">Gestión de novedades técnicas del sistema.</p>
            
            <div id="errorMessage" class="error-message"></div>
            
            <div class="form-section">
                <h3 style="margin-bottom: 20px; color: #2c3e50;">➕ Nueva noticia</h3>
                
                <div class="form-group">
                    <label for="newsTitle">Título</label>
                    <input type="text" id="newsTitle" placeholder="Ej: Nueva actualización de seguridad" maxlength="200">
                </div>
                
                <div class="form-group">
                    <label for="newsContent">Contenido</label>
                    <textarea id="newsContent" rows="4" placeholder="Describe la noticia técnica..." maxlength="2000"></textarea>
                </div>
                
                <button class="btn" onclick="saveNews()">💾 Guardar noticia</button>
                <button class="btn btn-secondary" onclick="loadNews()">🔄 Actualizar</button>
            </div>
            
            <div class="stats">
                <div class="stat-card">
                    <h3>📋 Total noticias</h3>
                    <div class="value" id="totalNews">0</div>
                </div>
                <div class="stat-card">
                    <h3>🕐 Última publicación</h3>
                    <div class="value" id="lastPublication" style="font-size: 16px;">Pendiente</div>
                </div>
                <div class="stat-card">
                    <h3>🔒 Control de acceso</h3>
                    <div class="value" style="font-size: 20px;">Técnico</div>
                </div>
            </div>
            
            <h3>📋 Listado de noticias</h3>
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th style="width: 10%;">#</th>
                            <th style="width: 30%;">Título</th>
                            <th style="width: 60%;">Contenido</th>
                        </tr>
                    </thead>
                    <tbody id="newsTableBody">
                        <tr>
                            <td colspan="3" class="loading">⏳ Cargando noticias...</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            
            <div class="footer">
                <p>📱 Síguenos en Twitter: <a href="#" target="_blank">@itegperformance</a></p>
            </div>
        </div>
    </div>

    <script>
        // URL base de la API - Ajusta el puerto si es diferente
        const API_BASE_URL = 'http://localhost:8080/api/news';
        
        // Cargar noticias al iniciar la página
        document.addEventListener('DOMContentLoaded', function() {
            loadNews();
        });
        
        // Función para cargar noticias desde el backend
        async function loadNews() {
            const tbody = document.getElementById('newsTableBody');
            const errorDiv = document.getElementById('errorMessage');
            const totalSpan = document.getElementById('totalNews');
            const lastPubSpan = document.getElementById('lastPublication');
            
            try {
                console.log('🔄 Intentando cargar noticias desde:', API_BASE_URL + '/list');
                
                const response = await fetch(API_BASE_URL + '/list', {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json'
                    }
                });
                
                console.log('📡 Respuesta del servidor:', response.status, response.statusText);
                
                if (!response.ok) {
                    throw new Error(`Error HTTP ${response.status}: ${response.statusText}`);
                }
                
                const news = await response.json();
                console.log('✅ Noticias cargadas:', news);
                
                // Actualizar estadísticas
                totalSpan.textContent = news.length;
                
                if (news.length === 0) {
                    tbody.innerHTML = '<tr><td colspan="3" class="empty-message">📭 No hay noticias registradas.</td></tr>';
                    lastPubSpan.textContent = 'Pendiente';
                } else {
                    // Renderizar tabla
                    tbody.innerHTML = news.map((item, index) => `
                        <tr>
                            <td><strong>${index + 1}</strong></td>
                            <td>${escapeHtml(item.title)}</td>
                            <td>${escapeHtml(item.content)}</td>
                        </tr>
                    `).join('');
                    
                    // Mostrar última publicación (primera en el array)
                    lastPubSpan.textContent = news[0].title.substring(0, 20) + (news[0].title.length > 20 ? '...' : '');
                }
                
                // Ocultar mensaje de error
                errorDiv.classList.remove('show');
                
            } catch (error) {
                console.error('❌ Error al cargar noticias:', error);
                
                // Mostrar mensaje de error
                errorDiv.textContent = '❌ No fue posible cargar las noticias técnicas desde el backend.';
                errorDiv.classList.add('show');
                
                // Mostrar mensaje en la tabla
                tbody.innerHTML = '<tr><td colspan="3" style="text-align: center; color: #c33;">Error al cargar datos. Verifica la consola (F12).</td></tr>';
                
                // Resetear estadísticas
                totalSpan.textContent = '0';
                lastPubSpan.textContent = 'Error';
            }
        }
        
        // Función para guardar una nueva noticia
        async function saveNews() {
            const titleInput = document.getElementById('newsTitle');
            const contentInput = document.getElementById('newsContent');
            const errorDiv = document.getElementById('errorMessage');
            
            const title = titleInput.value.trim();
            const content = contentInput.value.trim();
            
            // Validación
            if (!title) {
                alert('⚠️ Por favor ingresa un título');
                titleInput.focus();
                return;
            }
            
            if (!content) {
                alert('⚠️ Por favor ingresa el contenido');
                contentInput.focus();
                return;
            }
            
            try {
                console.log('💾 Guardando noticia:', { title, content });
                
                const response = await fetch(API_BASE_URL + '/save', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json'
                    },
                    body: JSON.stringify({ 
                        title: title, 
                        content: content 
                    })
                });
                
                console.log('📡 Respuesta al guardar:', response.status);
                
                if (!response.ok) {
                    const errorText = await response.text();
                    throw new Error(`Error HTTP ${response.status}: ${errorText}`);
                }
                
                const savedNews = await response.json();
                console.log('✅ Noticia guardada:', savedNews);
                
                // Limpiar formulario
                titleInput.value = '';
                contentInput.value = '';
                
                // Ocultar mensaje de error
                errorDiv.classList.remove('show');
                
                // Recargar lista de noticias
                await loadNews();
                
                // Mostrar mensaje de éxito (opcional)
                alert('✅ Noticia guardada exitosamente');
                
            } catch (error) {
                console.error('❌ Error al guardar:', error);
                
                // Mostrar error
                errorDiv.textContent = '❌ Error al guardar la noticia: ' + error.message;
                errorDiv.classList.add('show');
                
                alert('❌ Error al guardar la noticia. Revisa la consola (F12) para más detalles.');
            }
        }
        
        // Función para prevenir XSS
        function escapeHtml(text) {
            if (!text) return '';
            
            const map = {
                '&': '&amp;',
                '<': '&lt;',
                '>': '&gt;',
                '"': '&quot;',
                "'": '&#039;'
            };
            
            return text.replace(/[&<>"']/g, function(m) { return map[m]; });
        }
        
        // Permitir guardar con Enter en el campo de título
        document.getElementById('newsTitle')?.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                document.getElementById('newsContent').focus();
            }
        });
        
        // Atajo Ctrl+Enter para guardar
        document.addEventListener('keydown', function(e) {
            if (e.ctrlKey && e.key === 'Enter') {
                e.preventDefault();
                saveNews();
            }
        });
        
        // Actualizar automáticamente cada 30 segundos (opcional)
        // setInterval(loadNews, 30000);
    </script>
</body>
</html>