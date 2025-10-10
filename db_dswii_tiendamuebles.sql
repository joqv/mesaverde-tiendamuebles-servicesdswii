CREATE DATABASE IF NOT EXISTS db_dswii_tiendamuebles;
USE db_dswii_tiendamuebles;

DROP TABLE IF EXISTS categorias;
CREATE TABLE categorias (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL
);

DROP TABLE IF EXISTS clientes;
CREATE TABLE clientes (
  id INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL,
  email VARCHAR(100) UNIQUE,
  telefono VARCHAR(20),
  direccion VARCHAR(255),
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS usuarios;
CREATE TABLE usuarios (
  id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  cliente_id INT UNIQUE,
  role VARCHAR(100) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

DROP TABLE IF EXISTS productos;
CREATE TABLE productos (
  id INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL,
  precio DECIMAL(10,2) NOT NULL,
  tipo ENUM('mueble','silla') NOT NULL,
  stock INT NOT NULL,
  -- stock_minimo INT NOT NULL DEFAULT 10,
  descripcion TEXT,
  imagen VARCHAR(150),
  categoria_id INT,
  PRIMARY KEY (id),
  FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

DROP TABLE IF EXISTS ventas;
CREATE TABLE ventas (
  id INT NOT NULL AUTO_INCREMENT,
  cliente_id INT,
  usuario_id INT,
  -- tipo_pago VARCHAR(50),
  fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  total DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (cliente_id) REFERENCES clientes(id),
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

DROP TABLE IF EXISTS detalles_venta;
CREATE TABLE detalles_venta (
  id INT NOT NULL AUTO_INCREMENT,
  venta_id INT NOT NULL,
  producto_id INT NOT NULL,
  cantidad INT NOT NULL,
  precio_unitario DECIMAL(10,2) NOT NULL,
  subtotal DECIMAL(10,2),
  PRIMARY KEY (id),
  FOREIGN KEY (venta_id) REFERENCES ventas(id),
  FOREIGN KEY (producto_id) REFERENCES productos(id)
);

-- datos

INSERT INTO categorias (nombre) VALUES
('Salas'),
('Comedores'),
('Dormitorios'),
('Oficinas'),
('Exteriores');

INSERT INTO clientes (nombre, email, telefono, direccion) VALUES
('Laura Méndez', 'laura@example.com', '555-1234', 'Av. Central 123'),
('Pedro Ramírez', 'pedro@example.com', '555-5678', 'Calle Norte 456'),
('Ana López', 'ana@example.com', '555-9012', 'Boulevard Sur 789'),
('Diego Torres', 'diego@example.com', '555-3456', 'Ruta 66 km 12'),
('Sofía Herrera', 'sofia@example.com', '555-7890', 'Camino Real 321');

INSERT INTO usuarios (username, password, cliente_id, role) VALUES
('laura', 'pass123', 1, 'ADMIN'),
('pedro', 'pass456', 2, 'USER'),
('ana', 'pass789', 3, 'USER'),
('diego', 'pass321', 4, 'USER'),
('sofia', 'pass654', 5, 'USER');

INSERT INTO productos (nombre, precio, tipo, stock, descripcion, imagen, categoria_id) VALUES
('Sofá 3 plazas', 1200.00, 'mueble', 10, 'Sofá cómodo de tela gris', 'sofa3.jpg', 1),
('Mesa comedor madera', 850.00, 'mueble', 5, 'Mesa de roble para 6 personas', 'mesa_comedor.jpg', 2),
('Silla ergonómica', 450.00, 'silla', 20, 'Silla de oficina con soporte lumbar', 'silla_ergonomica.jpg', 4),
('Cama matrimonial', 1500.00, 'mueble', 7, 'Cama con base de madera y colchón incluido', 'cama.jpg', 3),
('Banco de jardín', 300.00, 'mueble', 12, 'Banco de metal para exteriores', 'banco_jardin.jpg', 5);

INSERT INTO ventas (usuario_id, cliente_id, total) VALUES
(1, 1, 1650.00),
(2, 2, 450.00),
(3, 3, 300.00),
(4, 4, 850.00),
(5, 1, 1200.00);

INSERT INTO detalles_venta (venta_id, producto_id, cantidad, precio_unitario, subtotal) VALUES
(1, 1, 1, 1200.00, 1200.00),
(1, 3, 1, 450.00, 450.00),
(2, 3, 1, 450.00, 450.00),
(3, 5, 1, 300.00, 300.00),
(4, 2, 1, 850.00, 850.00);

--