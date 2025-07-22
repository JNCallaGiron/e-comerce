package com.jairo.spring_ecomerce.service;

import com.jairo.spring_ecomerce.model.Producto;
import com.jairo.spring_ecomerce.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService implements IProductoService {

   @Autowired
   private IProductoRepository productoRepository;

    @Override
    public Producto saveProdcuto(Producto producto) {
      return productoRepository.save(producto);
    }

    @Override
    public List<Producto> listProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> getProducto(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public void update(Producto producto) {
        this.saveProdcuto(producto);
    }

    @Override
    public void deleteProducto(Long id) {
        if (productoRepository.existsById(id)){
            productoRepository.deleteById(id);
        }
    }
}
