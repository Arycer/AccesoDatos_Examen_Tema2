DROP DATABASE IF EXISTS tienda;


-- Crear la base de datos
CREATE DATABASE tienda;
USE tienda;

-- Crear la tabla de clientes
CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    correo VARCHAR(255) UNIQUE NOT NULL
);

-- Crear la tabla de productos
CREATE TABLE productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL
);

-- Crear la tabla de pedidos
CREATE TABLE pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    id_cliente INT NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- Crear la tabla de detalles del pedido
CREATE TABLE detalles_pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES productos(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- Insertar datos en la tabla de clientes
INSERT INTO clientes (nombre, correo) VALUES
('Juan Pérez', 'juan.perez@gmail.com'),
('María López', 'maria.lopez@gmail.com'),
('Carlos García', 'carlos.garcia@hotmail.com'),
('Ana Fernández', 'ana.fernandez@yahoo.com');

-- Insertar datos en la tabla de productos
INSERT INTO productos (nombre, precio) VALUES
('Ordenador portátil', 1200.00),
('Teléfono móvil', 800.00),
('Teclado mecánico', 100.00),
('Ratón inalámbrico', 50.00),
('Monitor 4K', 300.00);

-- Insertar datos en la tabla de pedidos
INSERT INTO pedidos (fecha, id_cliente) VALUES
('2024-01-15', 1),
('2024-01-16', 2),
('2024-01-17', 3),
('2024-01-18', 4);

-- Insertar datos en la tabla de detalles_pedido
INSERT INTO detalles_pedido (id_pedido, id_producto, cantidad) VALUES
(1, 1, 1), -- Pedido 1, un ordenador portátil
(1, 4, 2), -- Pedido 1, dos ratones inalámbricos
(2, 2, 1), -- Pedido 2, un teléfono móvil
(3, 3, 1), -- Pedido 3, un teclado mecánico
(3, 5, 1), -- Pedido 3, un monitor 4K
(4, 1, 1), -- Pedido 4, un ordenador portátil
(4, 5, 2); -- Pedido 4, dos monitores 4K
