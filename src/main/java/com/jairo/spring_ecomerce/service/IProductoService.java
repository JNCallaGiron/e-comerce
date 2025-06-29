package com.jairo.spring_ecomerce.service;

import com.jairo.spring_ecomerce.model.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {
    public Producto saveProdcuto(Producto producto);
    public List<Producto> listProductos();
    public Optional<Producto> getProducto(Long id);
    public void update(Producto producto);
    public void deleteProducto(Long id);
}
