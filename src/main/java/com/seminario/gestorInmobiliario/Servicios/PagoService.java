package com.seminario.gestorInmobiliario.Servicios;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seminario.gestorInmobiliario.Entidades.Pago;
import com.seminario.gestorInmobiliario.Entidades.Alquiler;
import com.seminario.gestorInmobiliario.Entidades.FormaPago;
import com.seminario.gestorInmobiliario.Entidades.Inquilino;
import com.seminario.gestorInmobiliario.Entidades.Localidad;
import com.seminario.gestorInmobiliario.Repositorios.PagoRepository;
import com.seminario.gestorInmobiliario.Repositorios.PagoRepository;
import com.seminario.gestorInmobiliario.Repositorios.FormaPagoRepository;
import com.seminario.gestorInmobiliario.Repositorios.FormaPagoRepository;


@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private FormaPagoRepository formaPagoRepository;


    @Transactional 
    public void crearPago(LocalDate fecha_limite, double interesMora)
            throws Exception {

        validar(fecha_limite, interesMora);

        Pago pago = new Pago();

        pago.setFecha_limite(fecha_limite);
        pago.setEstado();
        pago.setInteresMora(interesMora);

        pagoRepository.save(pago);

    }

    @Transactional(readOnly = true)
    public List<Pago> listarPagos() {

        List<Pago> pagos = new ArrayList<>();

        pagos = pagoRepository.findAll();
        return pagos;
    }

    @Transactional
    public void modificarPagos(int idPago, LocalDate fecha_limite, double interesMora)
        throws Exception {

        validar(fecha_limite, interesMora);

        Optional<Pago> pagoOpt = pagoRepository.findById(idPago);
        
        if (pagoOpt.isPresent()) {
            Pago pago = pagoOpt.get();

            pago.setFecha_limite(fecha_limite);
            pago.setInteresMora(interesMora);

            pagoRepository.save(pago);
        }
    }

    @Transactional
    public void eliminarPago(Integer idPago) throws Exception{
        Optional<Pago> pagoOpt = pagoRepository.findById(idPago);
        if (pagoOpt.isPresent()) {
            pagoRepository.delete(pagoOpt.get());
        } else {
            throw new Exception("el pago con el ID especificado no existe");
        }

    }

    @Transactional(readOnly = true)
    public Pago  getOne(Integer idPago){
        return pagoRepository.getReferenceById (idPago);
    }


    public double calcularPrecio(int idPago, LocalDate fechaPago) throws Exception{

        Optional<Pago> pagoOpt = pagoRepository.findById(idPago);
        double precio= 0;
        
        
        if (pagoOpt.isPresent()) {
            Pago pago = pagoOpt.get();
            
            precio= pago.getAlquiler().getPrecio(fechaPago);

            if(fechaPago.isAfter(pago.getFecha_limite())){
                precio= precio*pago.getInteresMora();
                long diasRetraso = ChronoUnit.DAYS.between(pago.getFecha_limite(), fechaPago);
                throw new Exception("los días de restraso del pago son: " + diasRetraso);
            }
            pago.setFechaPago(fechaPago);

        }
        return precio;
    }

    @Transactional
    public Pago registrarComoPagado (int idPago, int idFormaPago)throws Exception{
        Optional<Pago> pagoOpt = pagoRepository.findById(idPago);
        Pago pago = pagoOpt.get();

        if (pagoOpt.isPresent()) {
            
            FormaPago formaPago = formaPagoRepository.findById(idFormaPago).get();
            if (formaPago == null) {
                throw new Exception("La formaPago especificada no existe.");
            }
            pago.cambiarEstado();
            pago.setFormaPago(formaPago);
            pagoRepository.save(pago);
        }
        return pago;
    }
        

    private void validar(LocalDate fecha_limite, double interesMora)
            throws Exception {
        
        if (interesMora == 0) {
            throw new Exception("El interesMora no puede ser nulo o estar vacio");
        }
        if (fecha_limite.isAfter(LocalDate.now())){
            throw new Exception("la fecha debe ser mayor a la fecha actual");
        }
    }

    
}
