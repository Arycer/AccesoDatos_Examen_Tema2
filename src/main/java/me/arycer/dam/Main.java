package me.arycer.dam;

import me.arycer.dam.dao.ClientesDAO;
import me.arycer.dam.dao.DetallesPedidoDAO;
import me.arycer.dam.dao.PedidosDAO;
import me.arycer.dam.dao.ProductosDAO;
import me.arycer.dam.dao.impl.ClientesDAOImpl;
import me.arycer.dam.dao.impl.DetallesPedidoDAOImpl;
import me.arycer.dam.dao.impl.PedidosDAOImpl;
import me.arycer.dam.dao.impl.ProductosDAOImpl;
import me.arycer.dam.exception.ClienteNoExistenteException;
import me.arycer.dam.exception.ProductoNoExistenteException;
import me.arycer.dam.model.Cliente;
import me.arycer.dam.model.DetallesPedido;
import me.arycer.dam.model.Pedido;
import me.arycer.dam.model.Producto;

import java.util.*;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;

        do {
            System.out.println("--------------------");
            System.out.println("Menú de opciones:");
            System.out.println("- Gestión de clientes");
            System.out.println("1. Insertar un cliente");
            System.out.println("2. Listar todos los clientes");
            System.out.println("3. Actualizar el correo de un cliente");
            System.out.println("4. Eliminar un cliente");
            System.out.println("- Gestión de productos");
            System.out.println("5. Insertar un producto");
            System.out.println("6. Listar todos los productos");
            System.out.println("7. Actualizar el precio de un producto");
            System.out.println("8. Eliminar un producto según su ID");
            System.out.println("- Gestión de pedidos");
            System.out.println("9. Crear un nuevo pedido para un cliente");
            System.out.println("10. Listar todos los pedidos");
            System.out.println("11. Total gastado por los clientes ordenado");
            System.out.println("12. Eliminar un pedido");
            System.out.println("0. Salir.");
            System.out.println("--------------------");
            System.out.print("Seleccione una opción: ");
            opcion = SCANNER.nextInt();

            switch (opcion) {
                case 1 -> insertarCliente();
                case 2 -> listarClientes();
                case 3 -> actualizarCorreo();
                case 4 -> eliminarCliente();
                case 5 -> insertarProducto();
                case 6 -> listarProductos();
                case 7 -> actualizarPrecio();
                case 8 -> eliminarProducto();
                case 9 -> crearPedido();
                case 10 -> listarPedidos();
                case 11 -> totalGastado();
                case 12 -> eliminarPedido();
                case 0 -> System.out.println("Saliendo del programa...");
            }
        } while (opcion != 0);
    }

    private static void eliminarPedido() {
        PedidosDAO pedidosDAO = new PedidosDAOImpl();

        System.out.print("Introduce el ID del pedido: ");
        int id_pedido = SCANNER.nextInt();
        System.out.println();

        pedidosDAO.delete(id_pedido);
        System.out.println("Pedido borrado correctamente");
    }

    private static void totalGastado() {
        PedidosDAO pedidosDAO = new PedidosDAOImpl();
        DetallesPedidoDAO detallesPedidoDAO = new DetallesPedidoDAOImpl();
        ProductosDAO productosDAO = new ProductosDAOImpl();
        ClientesDAO clientesDAO = new ClientesDAOImpl();

        // Mapa para almacenar el gasto total por cliente
        final Map<Integer, Double> clientesGasto = new HashMap<>();

        // Leer todos los pedidos
        for (Pedido pedido : pedidosDAO.read()) {
            for (DetallesPedido detallesPedido : detallesPedidoDAO.getDetallesByIdPedido(pedido.getId())) {
                double precio = 0;

                try {
                    Producto producto = productosDAO.getProductoById(detallesPedido.getId_producto());
                    precio = producto.getPrecio();
                } catch (ProductoNoExistenteException e) {
                    // Ignorar
                }

                // Actualizar el gasto total del cliente en el mapa
                double importe = precio * detallesPedido.getCantidad();
                clientesGasto.merge(pedido.getId_cliente(), importe, Double::sum);
            }
        }

        // Convertir el mapa en una lista para ordenarlo
        List<Map.Entry<Integer, Double>> clientesGastoOrdenado = new ArrayList<>(clientesGasto.entrySet());
        clientesGastoOrdenado.sort((o1, o2) -> Double.compare(o2.getValue(), o1.getValue()));

        // Mostrar los resultados
        for (Map.Entry<Integer, Double> entry : clientesGastoOrdenado) {
            String displayCliente;

            try {
                Cliente cliente = clientesDAO.getClienteById(entry.getKey());
                displayCliente = "Nombre cliente: %s".formatted(cliente.getNombre());
            } catch (ClienteNoExistenteException e) {
                displayCliente = "ID Cliente: %s".formatted(entry.getKey());
            }

            System.out.printf("%s, Total gastado: %.2f\n", displayCliente, entry.getValue());
        }
    }

    private static void listarPedidos() {
        PedidosDAO pedidosDAO = new PedidosDAOImpl();
        DetallesPedidoDAO detallesPedidoDAO = new DetallesPedidoDAOImpl();
        ClientesDAO clientesDAO = new ClientesDAOImpl();
        ProductosDAO productosDAO = new ProductosDAOImpl();

        // Leer todos los pedidos
        for (Pedido pedido : pedidosDAO.read()) {
            String display_cliente;

            // Obtener el nombre del cliente
            try {
                Cliente cliente = clientesDAO.getClienteById(pedido.getId_cliente());
                display_cliente = "Nombre cliente: %s".formatted(cliente.getNombre());
            } catch (ClienteNoExistenteException e) {
                display_cliente = "ID Cliente: %d".formatted(pedido.getId_cliente());
            }

            double importe_total = 0;

            System.out.printf("ID: %d, Fecha: %s, %s\n", pedido.getId(), pedido.getFecha(), display_cliente);
            for (DetallesPedido detallesPedido : detallesPedidoDAO.getDetallesByIdPedido(pedido.getId())) {
                String display_producto;

                // Obtener el nombre del producto
                try {
                    Producto producto = productosDAO.getProductoById(detallesPedido.getId_producto());
                    display_producto = "Nombre producto: %s".formatted(producto.getNombre());
                    importe_total += producto.getPrecio() * detallesPedido.getCantidad();
                } catch (ClienteNoExistenteException e) {
                    display_producto = "ID Producto: %d".formatted(detallesPedido.getId_pedido());
                }

                System.out.printf(" - %s, Cantidad: %d\n", display_producto, detallesPedido.getCantidad());
            }

            System.out.printf("Importe total: %.2f\n", importe_total);
        }
    }

    private static void crearPedido() {
        ClientesDAO clientesDAO = new ClientesDAOImpl();
        ProductosDAO productosDAO = new ProductosDAOImpl();
        PedidosDAO pedidosDAO = new PedidosDAOImpl();

        System.out.print("Introduce el ID del cliente: ");
        int id_cliente = SCANNER.nextInt();
        System.out.println();

        // Validar si el cliente existe
        try {
            clientesDAO.getClienteById(id_cliente);
        } catch (ClienteNoExistenteException e) {
            System.err.println("Ha ocurrido un error: " + e.getMessage());
            return;
        }

        // Mapa para almacenar productos y cantidades
        final Map<Integer, Integer> PRODUCTOS_CANTIDAD = new HashMap<>();

        int id_producto;
        do {
            System.out.print("Introduce el ID del producto (-1 para parar): ");
            id_producto = SCANNER.nextInt();
            System.out.println();

            if (id_producto == -1) {
                continue;
            }

            // Validar si el producto existe
            try {
                productosDAO.getProductoById(id_producto);
            } catch (ProductoNoExistenteException e) {
                System.err.println("Ha ocurrido un error: " + e.getMessage());
                continue;
            }

            System.out.print("Introduce la cantidad a comprar: ");
            int cantidad = SCANNER.nextInt();
            System.out.println();

            // Actualizar la cantidad en el mapa
            PRODUCTOS_CANTIDAD.merge(id_producto, cantidad, Integer::sum);
        } while (id_producto != -1);

        // Pedir la fecha del pedido
        System.out.print("Introduce la fecha de compra: ");
        String fecha = SCANNER.next();
        System.out.println();

        // Crear el pedido en la base de datos
        pedidosDAO.crearPedidoCliente(id_cliente, fecha, PRODUCTOS_CANTIDAD);

        System.out.println("Pedido creado correctamente");
    }

    private static void eliminarProducto() {
        ProductosDAO productosDAO = new ProductosDAOImpl();
        PedidosDAO pedidosDAO = new PedidosDAOImpl();

        System.out.print("Introduce el ID del producto: ");
        int id_producto = SCANNER.nextInt();
        System.out.println();

        if (!pedidosDAO.getPedidosByIdProducto(id_producto).isEmpty()) {
            System.out.println("No puedes borrar el producto ya que aún tiene pedidos existentes");
            return;
        }

        productosDAO.delete(id_producto);
        System.out.println("Producto eliminado correctamente");
    }

    private static void actualizarPrecio() {
        ProductosDAO productosDAO = new ProductosDAOImpl();

        System.out.print("Introduce el ID del producto: ");
        int id_producto = SCANNER.nextInt();
        System.out.println();

        Producto producto;
        try {
            producto = productosDAO.getProductoById(id_producto);
        } catch (ClienteNoExistenteException e) {
            System.err.println("Ha ocurrido un error: " + e.getMessage());
            return;
        }

        System.out.print("Introduce el precio del producto: ");
        double precio = SCANNER.nextDouble();
        System.out.println();

        producto.setPrecio(precio);
        productosDAO.update(producto);

        System.out.println("Producto actualizado correctamente");
    }

    private static void listarProductos() {
        ProductosDAO productosDAO = new ProductosDAOImpl();

        System.out.println("Lista de productos: ");
        for (Producto producto : productosDAO.read()) {
            System.out.printf("ID: %d, Nombre: %s, Precio: %.2f\n", producto.getId(), producto.getNombre(), producto.getPrecio());
        }
    }

    private static void insertarProducto() {
        ProductosDAO productosDAO = new ProductosDAOImpl();

        System.out.print("Introduce el nombre del producto: ");
        String nombre = SCANNER.next();
        System.out.println();

        System.out.print("Introduce el precio del producto: ");
        double precio = SCANNER.nextDouble();
        System.out.println();

        productosDAO.create(new Producto(nombre, precio));
        System.out.println("Producto insertado correctamente.");
    }

    private static void eliminarCliente() {
        ClientesDAO clientesDAO = new ClientesDAOImpl();
        PedidosDAO pedidosDAO = new PedidosDAOImpl();

        System.out.print("Introduce el ID del cliente: ");
        int id_cliente = SCANNER.nextInt();
        System.out.println();

        if (!pedidosDAO.getPedidosByIdCliente(id_cliente).isEmpty()) {
            System.out.println("No puedes borrar al cliente ya que aún tiene pedidos existentes");
            return;
        }

        clientesDAO.delete(id_cliente);
        System.out.println("Cliente eliminado correctamente");
    }

    private static void actualizarCorreo() {
        ClientesDAO clientesDAO = new ClientesDAOImpl();

        System.out.print("Introduce el ID del cliente: ");
        int id_cliente = SCANNER.nextInt();
        System.out.println();

        Cliente cliente;
        try {
            cliente = clientesDAO.getClienteById(id_cliente);
        } catch (ClienteNoExistenteException e) {
            System.err.println("Ha ocurrido un error: " + e.getMessage());
            return;
        }

        System.out.print("Introduce el correo del cliente: ");
        String correo = SCANNER.next();
        System.out.println();

        cliente.setCorreo(correo);
        clientesDAO.update(cliente);

        System.out.println("Cliente actualizado correctamente");
    }

    private static void listarClientes() {
        ClientesDAO clientesDAO = new ClientesDAOImpl();

        System.out.println("Lista de clientes: ");
        for (Cliente cliente : clientesDAO.read()) {
            System.out.printf("ID: %d, Nombre: %s, Correo: %s\n", cliente.getId(), cliente.getNombre(), cliente.getCorreo());
        }
    }

    private static void insertarCliente() {
        ClientesDAO clientesDAO = new ClientesDAOImpl();

        System.out.print("Introduce el nombre del cliente: ");
        String nombre = SCANNER.next();
        System.out.println();

        System.out.print("Introduce el correo del cliente: ");
        String correo = SCANNER.next();
        System.out.println();

        clientesDAO.create(new Cliente(nombre, correo));
        System.out.println("Cliente insertado correctamente.");
    }
}
