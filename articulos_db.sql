-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Servidor: mysql:3306
-- Tiempo de generación: 08-07-2025 a las 21:49:23
-- Versión del servidor: 8.0.42
-- Versión de PHP: 8.2.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `talento`
--
CREATE DATABASE IF NOT EXISTS `talento` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `talento`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `articulo`
--

CREATE TABLE `articulo` (
  `id` bigint NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `precio` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `articulo`
--

INSERT INTO `articulo` (`id`, `nombre`, `precio`) VALUES
(9, 'Lapicera', 800),
(10, 'Agua', 600),
(11, 'Cuaderno', 2200),
(13, 'Cuaderno', 5600);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedido`
--

CREATE TABLE `pedido` (
  `id` bigint NOT NULL,
  `cliente` varchar(255) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `cantidad` double DEFAULT NULL,
  `dni_cliente` varchar(255) DEFAULT NULL,
  `fecha_entrega` varchar(255) DEFAULT NULL,
  `fecha_pedido` varchar(255) DEFAULT NULL,
  `mail_cliente` varchar(255) DEFAULT NULL,
  `usuario_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `pedido`
--

INSERT INTO `pedido` (`id`, `cliente`, `total`, `cantidad`, `dni_cliente`, `fecha_entrega`, `fecha_pedido`, `mail_cliente`, `usuario_id`) VALUES
(15, NULL, NULL, 3, NULL, '2025-07-22', '2025-07-08', NULL, 19),
(17, NULL, NULL, 13, '1400', '2025-07-15', '2025-07-08', NULL, 18);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedido_articulo`
--

CREATE TABLE `pedido_articulo` (
  `pedido_id` bigint NOT NULL,
  `articulo_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `pedido_articulo`
--

INSERT INTO `pedido_articulo` (`pedido_id`, `articulo_id`) VALUES
(15, 9),
(17, 10),
(17, 13);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` bigint NOT NULL,
  `contraseña` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `rol` enum('ADMIN','CLIENTE') DEFAULT NULL,
  `dni` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `contraseña`, `email`, `nombre`, `rol`, `dni`) VALUES
(18, '$2a$10$7ZANMFsBxuDTu2q8qGfpOuAXmDjozSNk73pKN6avrN2AQQcqEej5m', 'admin@admin.com', 'Admin', 'ADMIN', '1400'),
(19, '$2a$10$mehG8kI4yr7TXRYXDCKCkOIg9SjJ94JU8t1ebJnAbvZUjdkMsnvYO', 'cliente@cliente.com', 'cliente', 'CLIENTE', '14000');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `articulo`
--
ALTER TABLE `articulo`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `pedido`
--
ALTER TABLE `pedido`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK6uxomgomm93vg965o8brugt00` (`usuario_id`);

--
-- Indices de la tabla `pedido_articulo`
--
ALTER TABLE `pedido_articulo`
  ADD KEY `FKkax26138toihkiww2akkpk73i` (`articulo_id`),
  ADD KEY `FK1gq0scwvbghp998v6ddxs17oj` (`pedido_id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `articulo`
--
ALTER TABLE `articulo`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `pedido`
--
ALTER TABLE `pedido`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `pedido`
--
ALTER TABLE `pedido`
  ADD CONSTRAINT `FK6uxomgomm93vg965o8brugt00` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`);

--
-- Filtros para la tabla `pedido_articulo`
--
ALTER TABLE `pedido_articulo`
  ADD CONSTRAINT `FK1gq0scwvbghp998v6ddxs17oj` FOREIGN KEY (`pedido_id`) REFERENCES `pedido` (`id`),
  ADD CONSTRAINT `FKkax26138toihkiww2akkpk73i` FOREIGN KEY (`articulo_id`) REFERENCES `articulo` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
