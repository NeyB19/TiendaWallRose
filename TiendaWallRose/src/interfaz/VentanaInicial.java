package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;

public class VentanaInicial {

	private JFrame frmTiendaWallrose;
	private JTable tablaClientes;
	private JTable tablaOrdenes;
	private JTable tablaProductos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaInicial window = new VentanaInicial();
					window.frmTiendaWallrose.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaInicial() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTiendaWallrose = new JFrame();
		frmTiendaWallrose.setTitle("Tienda WallRose");
		frmTiendaWallrose.setResizable(false);
		frmTiendaWallrose.setBounds(100, 100, 615, 398);
		frmTiendaWallrose.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTiendaWallrose.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmTiendaWallrose.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panelDeClientes = new JPanel();
		tabbedPane.addTab("Clientes", null, panelDeClientes, null);
		panelDeClientes.setLayout(null);
		
		JScrollPane scrollPane_Clientes = new JScrollPane();
		scrollPane_Clientes.setBounds(10, 11, 458, 282);
		panelDeClientes.add(scrollPane_Clientes);
		
		tablaClientes = new JTable();
		tablaClientes.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Nombre", "Email"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tablaClientes.getColumnModel().getColumn(0).setPreferredWidth(99);
		tablaClientes.getColumnModel().getColumn(1).setPreferredWidth(204);
		tablaClientes.getColumnModel().getColumn(2).setPreferredWidth(165);
		scrollPane_Clientes.setViewportView(tablaClientes);
		
		JButton btnAgregarCliente = new JButton("Agregar");
		btnAgregarCliente.setBounds(478, 51, 94, 22);
		panelDeClientes.add(btnAgregarCliente);
		
		JButton btnVerCliente = new JButton("Ver");
		btnVerCliente.setBounds(478, 84, 94, 22);
		panelDeClientes.add(btnVerCliente);
		
		JButton btnEditarCliente = new JButton("Editar");
		btnEditarCliente.setBounds(478, 117, 94, 22);
		panelDeClientes.add(btnEditarCliente);
		
		JButton btnBorrarCliente = new JButton("Borrar");
		btnBorrarCliente.setBounds(478, 150, 94, 22);
		panelDeClientes.add(btnBorrarCliente);
		
		JPanel panelDeOrdenes = new JPanel();
		tabbedPane.addTab("Órdenes de Compra", null, panelDeOrdenes, null);
		panelDeOrdenes.setLayout(null);
		
		JScrollPane scrollPane_Ordenes = new JScrollPane();
		scrollPane_Ordenes.setBounds(10, 11, 458, 282);
		panelDeOrdenes.add(scrollPane_Ordenes);
		
		tablaOrdenes = new JTable();
		tablaOrdenes.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"N\u00FAmero", "Fecha", "Estado"
			}
		) {
			Class[] columnTypes = new Class[] {
				Object.class, Object.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tablaOrdenes.getColumnModel().getColumn(0).setPreferredWidth(99);
		tablaOrdenes.getColumnModel().getColumn(1).setPreferredWidth(127);
		tablaOrdenes.getColumnModel().getColumn(2).setPreferredWidth(110);
		scrollPane_Ordenes.setViewportView(tablaOrdenes);
		
		JButton btnNuevaOrden = new JButton("Nueva");
		btnNuevaOrden.setBounds(478, 51, 94, 22);
		panelDeOrdenes.add(btnNuevaOrden);
		
		JButton btnDetalleOrden = new JButton("Detalle");
		btnDetalleOrden.setBounds(478, 84, 94, 22);
		panelDeOrdenes.add(btnDetalleOrden);
		
		JButton btnBorrarOrden = new JButton("Borrar");
		btnBorrarOrden.setBounds(478, 117, 94, 22);
		panelDeOrdenes.add(btnBorrarOrden);
		
		JLabel lblTotalPendiente = new JLabel("Total pendiente:");
		lblTotalPendiente.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotalPendiente.setBounds(10, 308, 94, 14);
		panelDeOrdenes.add(lblTotalPendiente);
		
		JLabel lblTotalPendienteOrdenes = new JLabel("---");
		lblTotalPendienteOrdenes.setBounds(114, 308, 118, 14);
		panelDeOrdenes.add(lblTotalPendienteOrdenes);
		
		JPanel panelDeProductos = new JPanel();
		tabbedPane.addTab("Productos", null, panelDeProductos, null);
		panelDeProductos.setLayout(null);
		
		JScrollPane scrollPane_Productos = new JScrollPane();
		scrollPane_Productos.setBounds(10, 11, 460, 290);
		panelDeProductos.add(scrollPane_Productos);
		
		tablaProductos = new JTable();
		tablaProductos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"C\u00F3digo", "Nombre", "Existencias", "Unidad", "Precio"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, Float.class, String.class, Double.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(108);
		tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(154);
		tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(114);
		tablaProductos.getColumnModel().getColumn(3).setPreferredWidth(105);
		tablaProductos.getColumnModel().getColumn(4).setPreferredWidth(99);
		scrollPane_Productos.setViewportView(tablaProductos);
		
		JButton btnAgregarProducto = new JButton("Agregar");
		btnAgregarProducto.setBounds(480, 51, 94, 22);
		panelDeProductos.add(btnAgregarProducto);
		
		JButton btnEditarProducto = new JButton("Editar");
		btnEditarProducto.setBounds(480, 84, 94, 22);
		panelDeProductos.add(btnEditarProducto);
		
		JButton btnBorrarProducto = new JButton("Borrar");
		btnBorrarProducto.setBounds(480, 117, 94, 22);
		panelDeProductos.add(btnBorrarProducto);
	}
}
