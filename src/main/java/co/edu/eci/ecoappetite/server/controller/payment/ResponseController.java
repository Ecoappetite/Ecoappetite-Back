package co.edu.eci.ecoappetite.server.controller.payment;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class ResponseController {

    @GetMapping("/success")
    public String handleSuccess() {
        // Lógica para pagos exitosos
        return "Pago exitoso";
    }

    @GetMapping("/failure")
    public String handleFailure() {
        // Lógica para pagos fallidos
        return "Pago fallido";
    }

    @GetMapping("/pending")
    public String handlePending() {
        // Lógica para pagos pendientes
        return "Pago pendiente";
    }

    @PostMapping("/notifications")
    public ResponseEntity<?> handleNotifications(@RequestBody String notification) {
        // Lógica para manejar notificaciones de Mercado Pago
        System.out.println("Notification received: " + notification);

        // Puedes procesar la notificación aquí según su tipo

        return ResponseEntity.ok().build();
    }
}
