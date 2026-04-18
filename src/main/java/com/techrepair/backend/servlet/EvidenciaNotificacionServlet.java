package com.techrepair.backend.servlet;

import com.techrepair.backend.enums.NotificationType;
import com.techrepair.backend.model.Notification;
import com.techrepair.backend.model.User;
import com.techrepair.backend.repository.UserRepository;
import com.techrepair.backend.service.NotificationService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

@WebServlet("/evidencia-notificacion")
public class EvidenciaNotificacionServlet extends HttpServlet {

    private transient NotificationService notificationService;
    private transient UserRepository userRepository;

    @Override
    public void init() throws ServletException {
        WebApplicationContext context =
                WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        this.notificationService = context.getBean(NotificationService.class);
        this.userRepository = context.getBean(UserRepository.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        forwardToView(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String usuario = trim(request.getParameter("usuario"));
        String mensaje = trim(request.getParameter("mensaje"));
        String tipo = trim(request.getParameter("tipo"));

        request.setAttribute("usuario", usuario);
        request.setAttribute("mensaje", mensaje);
        request.setAttribute("tipo", tipo);

        if (usuario.isEmpty() || mensaje.isEmpty() || tipo.isEmpty()) {
            request.setAttribute("error", "Todos los campos son obligatorios para registrar la evidencia.");
            forwardToView(request, response);
            return;
        }

        Optional<User> optionalUser = userRepository.findByEmail(usuario);
        NotificationType notificationType = resolveType(tipo);

        if (optionalUser.isPresent()) {
            Notification notification = new Notification();
            notification.setUser(optionalUser.get());
            notification.setMessage(mensaje);
            notification.setType(notificationType);
            notificationService.send(notification);

            request.setAttribute(
                    "resultado",
                    "La notificación de evidencia fue registrada correctamente para el usuario indicado.");
        } else {
            request.setAttribute(
                    "resultado",
                    "La evidencia fue procesada como simulación. No se encontró un usuario registrado con el correo indicado.");
        }

        forwardToView(request, response);
    }

    private void forwardToView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/views/registro_evidencia.jsp");
        dispatcher.forward(request, response);
    }

    private NotificationType resolveType(String tipo) {
        try {
            return NotificationType.valueOf(tipo.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            return NotificationType.INFO;
        }
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }
}
