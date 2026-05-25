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

public class VentanaInicial {

	private JFrame frame;
	private JTable tablaClientes;
	private JTable tablaOrdenes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaInicial window = new VentanaInicial();
					window.frame.setVisible(true);
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
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 615, 398);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
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
		lblTotalPendiente.setBounds(10, 308, 94, 14);
		panelDeOrdenes.add(lblTotalPendiente);
		
		JPanel panelDeProductos = new JPanel();
		tabbedPane.addTab("Productos", null, panelDeProductos, null);
	}
}
