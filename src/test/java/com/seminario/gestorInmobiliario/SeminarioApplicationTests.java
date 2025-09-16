package com.seminario.gestorInmobiliario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.seminario.gestorInmobiliario.Entidades.FormaPago;
import com.seminario.gestorInmobiliario.Servicios.FormaPagoService;

@SpringBootTest(classes = com.seminario.gestorInmobiliario.SeminarioApplication.class)
class SeminarioApplicationTests {

	@Autowired
    private FormaPagoService formaPagoService;

    @Test
    void contextLoads() {
        // Este sigue verificando que el contexto arranca
    }

    @Test
    void testCrearFormaPago() {
        FormaPago fp = new FormaPago();
        fp.setNombre("Tarjeta");
        
        FormaPago guardada = formaPagoService.guardar(fp);
        assertNotNull(guardada.getIdFormaPago());
        assertEquals("Tarjeta", guardada.getNombre());
    }

}
