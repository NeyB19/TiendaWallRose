package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import control.Controladora;
import logica.Cliente;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaInicial {

	private JFrame frmTiendaWallrose;
	private JTable tablaClientes;
	private JTable tablaOrdenes;
	private JTable tablaProductos;
	private JScrollPane scrollPane_Clientes;
	private JButton btnAgregarCliente;
	private JButton btnVerCliente;
	private JButton btnEditarCliente;
	private JButton btnBorrarCliente;

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
	public void cargarClientes() {
		Controladora control = Controladora.getInstance();
		DefaultTableModel model = (DefaultTableModel) tablaClientes.getModel();
		model.setRowCount(0);
		List<Cliente> listaClientes = control.obtenerListadoClientes();
		for (Cliente cliente : listaClientes) {
			Object[] fila = new Object[] {cliente.getId(), cliente.getNombre(), cliente.getEmail()};
			model.addRow(fila);
		}	
	}
	
	private void verCliente() {
		int numeroFila = tablaClientes.getSelectedRow();
		if (numeroFila == -1) {
			javax.swing.JOptionPane.showMessageDialog(
					frmTiendaWallrose, "Debe seleccionar un cliente.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
		} else {
			DefaultTableModel model = (DefaultTableModel) tablaClientes.getModel();
			String idCliente = (String) model.getValueAt(numeroFila, 0);
				
			InformacionCliente ventanaInformacion = new InformacionCliente(idCliente);
			ventanaInformacion.setVisible(true);
		}
	}
	private void borrarCliente() {
		int numeroFila = tablaClientes.getSelectedRow();
		if (numeroFila == -1) {
			JOptionPane.showMessageDialog(frmTiendaWallrose, "Debe seleccionar un cliente.", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			DefaultTableModel model = (DefaultTableModel)tablaClientes.getModel();
			String idCliente = (String)model.getValueAt(numeroFila, 0);
			String nombreCliente = (String)model.getValueAt(numeroFila, 1);
			
			int respuesta = JOptionPane.showConfirmDialog(frmTiendaWallrose,"Se eliminará la información del cliente " + nombreCliente + " (" + idCliente + ") y todas sus órdenes asociadas.",
					"Confirmar", JOptionPane.YES_NO_OPTION);
					
			if (respuesta == JOptionPane.YES_OPTION) {
				Controladora control = Controladora.getInstance();
				try {
					control.borrarCliente(idCliente);
					cargarClientes();
					
					JOptionPane.showMessageDialog(frmTiendaWallrose, "Cliente eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(frmTiendaWallrose, "Error al borrar cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	public void agregarCliente() {
		AgregarEditarCliente ventanaAgregar = new AgregarEditarCliente(this, true);
		ventanaAgregar.setTitle("Agregar Cliente");
		ventanaAgregar.setVisible(true);
	}

	public void editarCliente() {
		int numeroFila = tablaClientes.getSelectedRow();
		if (numeroFila == -1) {
			JOptionPane.showMessageDialog(frmTiendaWallrose, "Debe seleccionar un cliente.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		DefaultTableModel model = (DefaultTableModel) tablaClientes.getModel();
		String id = (String) model.getValueAt(numeroFila, 0);
		String nombre = (String) model.getValueAt(numeroFila, 1);
		String email = (String) model.getValueAt(numeroFila, 2);
		
		AgregarEditarCliente ventanaEditar = new AgregarEditarCliente(this, false);
		ventanaEditar.setTitle("Editar Cliente");
		
		ventanaEditar.getTextID().setText(id);
		ventanaEditar.getTextID().setEditable(false);
		ventanaEditar.getTextNombre().setText(nombre);
		ventanaEditar.getTextEmail().setText(email);
		
		ventanaEditar.setVisible(true);
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
		panelDeClientes.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				cargarClientes();
			}
		});
		tabbedPane.addTab("Clientes", null, panelDeClientes, null);
		panelDeClientes.setLayout(null);
		
		scrollPane_Clientes = new JScrollPane();
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
		
		btnAgregarCliente = new JButton("Agregar");
		btnAgregarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregarCliente();
			}
		});
		btnAgregarCliente.setBounds(478, 51, 94, 22);
		panelDeClientes.add(btnAgregarCliente);
		
		btnVerCliente = new JButton("Ver");
		btnVerCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verCliente();
			}
		});
		btnVerCliente.setBounds(478, 84, 94, 22);
		panelDeClientes.add(btnVerCliente);
		
		btnEditarCliente = new JButton("Editar");
		btnEditarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarCliente();
			}
		});
		btnEditarCliente.setBounds(478, 117, 94, 22);
		panelDeClientes.add(btnEditarCliente);
		
		btnBorrarCliente = new JButton("Borrar");
		btnBorrarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarCliente();
			}
		});
		btnBorrarCliente.setBounds(478, 150, 94, 22);
		panelDeClientes.add(btnBorrarCliente);
		
		JButton btnCargarDatos = new JButton("Cargar Datos");
		btnCargarDatos.setBounds(478, 215, 108, 22);
		panelDeClientes.add(btnCargarDatos);
		
		JButton btnGuardarDatos = new JButton("Guardar Datos");
		btnGuardarDatos.setBounds(478, 257, 108, 22);
		panelDeClientes.add(btnGuardarDatos);
		
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
				"Numero", "Fecha", "Estado"
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
