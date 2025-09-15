package com.proyectofinal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PedidoRequest {

    @JsonProperty("userId")      // Le decimos a Jackson que "userId" JSON mapea aquí
    private Long usuarioId;

    @JsonProperty("productId")   // Ojo: tu JSON usa "productId", no "productoId"
    private Long productoId;

    private Integer cantidad = 1;

    public PedidoRequest() {}

    public Long getUsuarioId()    { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Long getProductoId()   { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public Integer getCantidad()  { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}
