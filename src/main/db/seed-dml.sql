\c desarrollo_api_eshop;


-- CREATE TABLE product (
--     id_product SERIAL PRIMARY KEY,
--     name VARCHAR(255) NOT NULL,
--     price NUMERIC(10, 2) NOT NULL,
--     description VARCHAR(255) NOT NULL,
--     brand VARCHAR(255) NOT NULL,
--     image TEXT NOT NULL,
--     stock INTEGER NOT NULL
-- )

INSERT INTO product (name, price, description, brand, image, stock) VALUES
('T-Shirt', 10.99, 'A basic t-shirt', 'T-Shirt Company', 'https://www.filmyvastra.com/wp-content/uploads/2019/06/Black-Wide-tshirt-without-hanger-Recovered-scaled.jpg', 100),
('Jeans', 20.99, 'A basic pair of jeans', 'Jeans Company', 'https://hips.hearstapps.com/bpc.h-cdn.co/assets/16/50/1481757514-hm-shaping-skinny-jeans-black.jpg?crop=1.0xw:1xh;center,top&resize=768:*', 50);


-- CREATE TABLE customer (
--     id_customer SERIAL PRIMARY KEY,
--     name VARCHAR(255) NOT NULL,
--     email VARCHAR(255) NOT NULL,
--     address VARCHAR(255) NOT NULL,
--     password VARCHAR(255) NOT NULL,
--     role VARCHAR(255) NOT NULL
-- )

INSERT INTO customer (name, email, address, password) VALUES
('John Doe', 'G5z5Q@example.com', '123 Main St, Anytown, USA', '$2a$10$rV8hVjYTEkQgBonQr98uv.zopeqh009M/w0aZaCiJKhPTYgz0DUv.'), -- password: password
('Jane Smith', '4vXUJ@example.com', '456 Oak St, Anytown, USA', '$2a$10$rV8hVjYTEkQgBonQr98uv.zopeqh009M/w0aZaCiJKhPTYgz0DUv.'), -- password: password
('Bob Johnson', 'Jpj6A@example.com', '789 Pine St, Anytown, USA', '$2a$10$rV8hVjYTEkQgBonQr98uv.zopeqh009M/w0aZaCiJKhPTYgz0DUv.'); -- password: password


-- CREATE TABLE orders (
--     id_order SERIAL PRIMARY KEY,
--     customer_id INTEGER NOT NULL REFERENCES customer(id_customer),
--     order_date TIMESTAMP NOT NULL CHECK (order_date > now()),
--     status VARCHAR(255) NOT NULL CHECK (status IN ('PENDIENTE', 'ENVIADO', 'ENTREGADO'))
-- );

INSERT INTO orders (customer_id, order_date, status) VALUES
(1, '2025-01-01', 'PENDIENTE'),
(2, '2025-01-01', 'ENVIADO'),
(3, '2025-01-01', 'ENTREGADO');

-- CREATE TABLE order_item (
--     id_order_item SERIAL PRIMARY KEY,
--     order_id INTEGER NOT NULL REFERENCES orders(id_order),
--     product_id INTEGER NOT NULL REFERENCES product(id_product),
--     quantity INTEGER NOT NULL,
--     unit_price NUMERIC(10, 2) NOT NULL
-- );

INSERT INTO order_item (order_id, product_id, quantity, unit_price) VALUES
(1, 1, 2, 10.99),
(2, 2, 1, 20.99),
(3, 1, 1, 10.99);


-- CREATE TABLE payment (
--     id_payment SERIAL PRIMARY KEY,
--     order_id INTEGER NOT NULL REFERENCES orders(id_order),
--     amount NUMERIC(10, 2) NOT NULL,
--     payment_date TIMESTAMP NOT NULL CHECK (payment_date > now()),
--     payment_method VARCHAR(255) NOT NULL CHECK (payment_method IN ('EFECTIVO', 'TARJETA_CREDITO', 'PAYPAL', 'NEQUI', 'DAVIPLATA', 'PSE'))
-- )

INSERT INTO payment (order_id, amount, payment_date, payment_method) VALUES
(1, 21.98, '2025-01-01', 'EFECTIVO'),
(2, 20.99, '2025-01-01', 'TARJETA_CREDITO'),
(3, 10.99, '2025-01-01', 'EFECTIVO');



-- CREATE TABLE shipment (
--     id_shipment SERIAL PRIMARY KEY,
--     order_id INTEGER NOT NULL REFERENCES orders(id_order),
--     address VARCHAR(255) NOT NULL,
--     carrier VARCHAR(255) NOT NULL,
--     tracking_number VARCHAR(255) NOT NULL
-- )

INSERT INTO shipment (order_id, address, carrier, tracking_number) VALUES
(1, '123 Main St, Anytown, USA', 'Fedex', 'ABC123'),
(2, '456 Oak St, Anytown, USA', 'UPS', 'DEF456'),
(3, '789 Pine St, Anytown, USA', 'DHL', 'GHI789');