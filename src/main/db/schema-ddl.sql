DROP DATABASE IF EXISTS desarrollo_api_eshop;

CREATE DATABASE desarrollo_api_eshop;

\c desarrollo_api_eshop;

-- Product: represente los artículos que están a la venta
-- ● id: PK
-- ● nombre: nombre del producto
-- ● price: precio del producto
-- ● stock: cantidad de productos en existencias

CREATE TABLE product (
    id_product SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    description VARCHAR(255) NOT NULL,
    brand VARCHAR(255) NOT NULL,
    image TEXT NOT NULL,
    stock INTEGER NOT NULL
);

-- Cliente: representa los clientes quienes compran los productos
-- ● id: PK
-- ● nombre: nombre completo del cliente
-- ● email: email del cliente
-- ● dirección: dirección física del cliente
-- ● password: contraseña del cliente
-- ● role: rol del cliente

CREATE TABLE customer (
    id_customer SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL CHECK (role IN ('USER', 'ADMIN')) DEFAULT 'USER'
);

-- Pedido: representa un pedido de compra realizado por un cliente.
-- ● id: PK
-- ● clienteId: FK apuntando a Cliente
-- ● fechaPedido: Fecha y hora cuando el pedido fue realizado
-- ● status: estado actual del pedido, los valores podrán ser, PENDIENTE,
-- ENVIADO y ENTREGADO

CREATE TABLE orders (
    id_order SERIAL PRIMARY KEY,
    customer_id INTEGER NOT NULL REFERENCES customer(id_customer),
    order_date TIMESTAMP NOT NULL CHECK (order_date > now()),
    status VARCHAR(255) NOT NULL CHECK (status IN ('PENDIENTE', 'ENVIADO', 'ENTREGADO'))
);

-- ItemPedido: Representa los artículos individuales dentro de un pedido.
-- ● id: PK
-- ● pedidoId: FK a Pedido
-- ● productoId: FK a Producto
-- ● cantidad: cantidad de productos solicitados
-- ● precioUnitario: precio del producto

CREATE TABLE order_item (
    id_order_item SERIAL PRIMARY KEY,
    order_id INTEGER NOT NULL REFERENCES orders(id_order),
    product_id INTEGER NOT NULL REFERENCES product(id_product),
    quantity INTEGER NOT NULL,
    unit_price NUMERIC(10, 2) NOT NULL
);

-- Pago: Representa los datos de pago de un pedido.
-- ● Id: PK
-- ● pedidoId: FK a pedido
-- ● totalPago: total cantidad a pagar
-- ● fechaPago: Fecha cuando se realiza el pago
-- ● metodoPago: método de pago, estos podrás ser EFECTIVO,
-- TARJETA_CREDITO, PAYPAL, NEQUI, DAVIPLATA, PSE

CREATE TABLE payment (
    id_payment SERIAL PRIMARY KEY,
    order_id INTEGER NOT NULL REFERENCES orders(id_order),
    amount NUMERIC(10, 2) NOT NULL,
    payment_date TIMESTAMP NOT NULL CHECK (payment_date > now()),
    payment_method VARCHAR(255) NOT NULL CHECK (payment_method IN ('EFECTIVO', 'TARJETA_CREDITO', 'PAYPAL', 'NEQUI', 'DAVIPLATA', 'PSE'))
);

-- DetalleEnvio: representa la información de envío de un pedido.
-- ● id: PK
-- ● pedidoId: FK a pedido
-- ● direccion: dirección de envío
-- ● trasnportadora: Servicio de transporte utilizado para el envío
-- ● numeroGuia: Número de seguimiento del envío

CREATE TABLE shipment (
    id_shipment SERIAL PRIMARY KEY,
    order_id INTEGER NOT NULL REFERENCES orders(id_order),
    address VARCHAR(255) NOT NULL,
    carrier VARCHAR(255) NOT NULL,
    tracking_number VARCHAR(255) NOT NULL
);