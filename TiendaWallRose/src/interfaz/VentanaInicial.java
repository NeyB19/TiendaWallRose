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

public class VentanaInicial {

	private JFrame frame;
	private JTable table;

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
		frame.setBounds(100, 100, 615, 369);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panelDeClientes = new JPanel();
		tabbedPane.addTab("Clientes", null, panelDeClientes, null);
		panelDeClientes.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 458, 282);
		panelDeClientes.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
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
		table.getColumnModel().getColumn(0).setPreferredWidth(99);
		table.getColumnModel().getColumn(1).setPreferredWidth(204);
		table.getColumnModel().getColumn(2).setPreferredWidth(165);
		scrollPane.setViewportView(table);
		
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
		tabbedPane.addTab("Órdenes", null, panelDeOrdenes, null);
		
		JPanel panelDeProductos = new JPanel();
		tabbedPane.addTab("Productos", null, panelDeProductos, null);
	}
}
