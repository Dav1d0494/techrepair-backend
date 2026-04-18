<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro de evidencia</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f8;
            color: #1f2933;
            margin: 0;
            padding: 32px 16px;
        }

        .contenedor {
            max-width: 720px;
            margin: 0 auto;
            background-color: #ffffff;
            border-radius: 12px;
            box-shadow: 0 12px 30px rgba(15, 23, 42, 0.08);
            padding: 32px;
        }

        h1 {
            margin-top: 0;
            color: #0f172a;
        }

        p {
            line-height: 1.6;
        }

        .campo {
            margin-bottom: 18px;
        }

        label {
            display: block;
            font-weight: 600;
            margin-bottom: 8px;
        }

        input,
        textarea,
        select {
            width: 100%;
            padding: 12px;
            border: 1px solid #cbd5e1;
            border-radius: 8px;
            font-size: 1rem;
            box-sizing: border-box;
        }

        textarea {
            min-height: 140px;
            resize: vertical;
        }

        button {
            background-color: #0f766e;
            color: #ffffff;
            border: none;
            border-radius: 8px;
            padding: 12px 18px;
            font-size: 1rem;
            cursor: pointer;
        }

        .mensaje {
            padding: 14px 16px;
            border-radius: 8px;
            margin-bottom: 20px;
        }

        .exito {
            background-color: #dcfce7;
            color: #166534;
        }

        .error {
            background-color: #fee2e2;
            color: #991b1b;
        }
    </style>
</head>
<body>
<div class="contenedor">
    <h1>Registro de evidencia de notificación</h1>
    <p>Complete el siguiente formulario para registrar la evidencia académica de envío o simulación de una notificación.</p>

    <% String resultado = (String) request.getAttribute("resultado"); %>
    <% String error = (String) request.getAttribute("error"); %>

    <% if (resultado != null) { %>
        <div class="mensaje exito"><%= resultado %></div>
    <% } %>

    <% if (error != null) { %>
        <div class="mensaje error"><%= error %></div>
    <% } %>

    <form action="<%= request.getContextPath() %>/evidencia-notificacion" method="post">
        <div class="campo">
            <label for="usuario">Correo electrónico del usuario</label>
            <input
                type="email"
                id="usuario"
                name="usuario"
                value="<%= request.getAttribute("usuario") != null ? request.getAttribute("usuario") : "" %>"
                placeholder="usuario@techrepair.com"
                required
            >
        </div>

        <div class="campo">
            <label for="mensaje">Mensaje de la notificación</label>
            <textarea
                id="mensaje"
                name="mensaje"
                placeholder="Describa el contenido de la notificación."
                required
            ><%= request.getAttribute("mensaje") != null ? request.getAttribute("mensaje") : "" %></textarea>
        </div>

        <div class="campo">
            <label for="tipo">Tipo de notificación</label>
            <select id="tipo" name="tipo" required>
                <option value="">Seleccione una opción</option>
                <option value="INFO" <%= "INFO".equals(request.getAttribute("tipo")) ? "selected" : "" %>>Informativa</option>
                <option value="SUCCESS" <%= "SUCCESS".equals(request.getAttribute("tipo")) ? "selected" : "" %>>Éxito</option>
                <option value="WARNING" <%= "WARNING".equals(request.getAttribute("tipo")) ? "selected" : "" %>>Advertencia</option>
                <option value="ERROR" <%= "ERROR".equals(request.getAttribute("tipo")) ? "selected" : "" %>>Error</option>
            </select>
        </div>

        <button type="submit">Registrar evidencia</button>
    </form>
</div>
</body>
</html>
