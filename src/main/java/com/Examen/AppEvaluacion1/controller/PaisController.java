package com.Examen.AppEvaluacion1.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Examen.AppEvaluacion1.model.Producto;

@RestController
@RequestMapping("api/productos")
public class PaisController {

    private final List<Producto> productos = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong();

    public PaisController(){
        initData();
    }

    private void initData(){

        Producto gaseosa = new Producto(contador.incrementAndGet(), "Coca Cola", "Bebida", 3.50, 50);
        Producto galleta = new Producto(contador.incrementAndGet(), "Rellenitas", "snack", 1.00, 20);
        Producto caramelo = new Producto(contador.incrementAndGet(), "Halls", "golosina", 1.50, 40);
        Producto piqueos = new Producto(contador.incrementAndGet(), "Papas Lays", "snack", 4.50, 30);
        Producto postres = new Producto(contador.incrementAndGet(), "Tartaleta de Manzana", "postre", 7.50, 15);
        productos.add(gaseosa);
        productos.add(galleta);
        productos.add(caramelo);
        productos.add(piqueos);
        productos.add(postres);
    }
    
    @GetMapping("/")
    public ResponseEntity<List<Producto>>getProducto(){
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<Producto>obtener(@PathVariable Long id){
        Producto producto = productos.stream().filter(p -> p.getId().equals(id)).findAny().orElse(null);
        if(producto != null){
            return new ResponseEntity<>(producto, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Producto> registrar(@RequestBody Producto producto){
        Producto productoNuevo = new Producto(contador.incrementAndGet(), producto.getNombre(), producto.getDescripcion(), producto.getPrecio(), producto.getCantidad());
        productos.add(productoNuevo);
        return new ResponseEntity<>(productoNuevo, HttpStatus.CREATED);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto p){
        Producto temp = null;
        for(Producto producto : productos){
            if(producto.getId() == id){
                producto.setNombre(p.getNombre());
                producto.setDescripcion(p.getDescripcion());
                producto.setPrecio(p.getPrecio());
                producto.setCantidad(p.getCantidad());
                temp = producto;
                break;
            }
        }
        return new ResponseEntity<>(temp, HttpStatus.OK);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Producto> eliminar(@PathVariable Long id){
        Producto p = productos.stream().filter(x -> id == x.getId()).findAny().orElse(null);
        if(p !=null)
            productos.remove(p);

        return new ResponseEntity<Producto>(HttpStatus.NO_CONTENT);
    }


}
