package interfaz;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import control.Controladora;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class DetalleOrdenCompra extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	private JLabel lblNumOrden;
	private JLabel lblFechaOrden;
	private JLabel lblEstadoOrden;
	private JLabel lblClienteOrden;
	private JTable tablaLineasDetalle;
	private JButton btnAgregarLinea;
	private JButton btnEditarLinea;
	private JButton btnBorrarLinea;
	private JLabel lblSubtotalOrden;
	private JLabel lblImpuestoOrden;
	private JLabel lblTotalOrden;
	private JButton btnPonerPendiente;
	private JButton btnTerminarOrden;
	private int numeroOrdenActual;
	private VentanaInicial ventanaPrincipal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DetalleOrdenCompra dialog = new DetalleOrdenCompra(0, null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DetalleOrdenCompra(int numeroOrden, VentanaInicial ventanaPrincipal) {
		this.numeroOrdenActual = numeroOrden;
		this.ventanaPrincipal = ventanaPrincipal;
		
		setTitle("Detalle de Orden de Compra");
		setModal(true);
		setBounds(100, 100, 600, 438);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNumero = new JLabel("Número:");
		lblNumero.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNumero.setBounds(20, 20, 70, 14);
		contentPanel.add(lblNumero);
		
		lblNumOrden = new JLabel("---");
		lblNumOrden.setBounds(95, 20, 60, 14);
		contentPanel.add(lblNumOrden);
		
		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFecha.setBounds(180, 20, 50, 14);
		contentPanel.add(lblFecha);
		
		lblFechaOrden = new JLabel("---");
		lblFechaOrden.setBounds(235, 20, 90, 14);
		contentPanel.add(lblFechaOrden);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEstado.setBounds(350, 20, 50, 14);
		contentPanel.add(lblEstado);
		
		lblEstadoOrden = new JLabel("---");
		lblEstadoOrden.setBounds(410, 20, 100, 14);
		contentPanel.add(lblEstadoOrden);
		
		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCliente.setBounds(20, 55, 70, 14);
		contentPanel.add(lblCliente);
		
		lblClienteOrden = new JLabel("---------");
		lblClienteOrden.setBounds(95, 55, 300, 14);
		contentPanel.add(lblClienteOrden);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 90, 430, 220);
		contentPanel.add(scrollPane);
		
		tablaLineasDetalle = new JTable();
		tablaLineasDetalle.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {
				"Código Prod.", "Nombre Producto", "Cantidad", "Costo"
			}
		) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		});
		scrollPane.setViewportView(tablaLineasDetalle);
		
		btnAgregarLinea = new JButton("Agregar");
		btnAgregarLinea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregarLinea();
			}
		});
		btnAgregarLinea.setBounds(465, 141, 100, 25);
		contentPanel.add(btnAgregarLinea);
		
		btnEditarLinea = new JButton("Editar");
		btnEditarLinea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarLinea();
			}
		});
		btnEditarLinea.setBounds(465, 177, 100, 25);
		contentPanel.add(btnEditarLinea);
		
		btnBorrarLinea = new JButton("Borrar");
		btnBorrarLinea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarLinea();
			}
		});
		btnBorrarLinea.setBounds(465, 213, 100, 25);
		contentPanel.add(btnBorrarLinea);
		
		JLabel lblSubtotal = new JLabel("Costo:");
		lblSubtotal.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSubtotal.setBounds(340, 330, 60, 14);
		contentPanel.add(lblSubtotal);
		
		lblSubtotalOrden = new JLabel("---");
		lblSubtotalOrden.setBounds(410, 330, 80, 14);
		contentPanel.add(lblSubtotalOrden);
		
		JLabel lblImpuesto = new JLabel("Impuesto:");
		lblImpuesto.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblImpuesto.setBounds(340, 355, 60, 14);
		contentPanel.add(lblImpuesto);
		
		lblImpuestoOrden = new JLabel("---");
		lblImpuestoOrden.setBounds(410, 355, 80, 14);
		contentPanel.add(lblImpuestoOrden);
		
		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotal.setBounds(340, 380, 60, 14);
		contentPanel.add(lblTotal);
		
		lblTotalOrden = new JLabel("---");
		lblTotalOrden.setBounds(410, 380, 80, 14);
		contentPanel.add(lblTotalOrden);
		
		btnPonerPendiente = new JButton("Pendiente");
		btnPonerPendiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cambiarEstadoAPendiente();
			}
		});
		btnPonerPendiente.setBounds(20, 350, 150, 30);
		contentPanel.add(btnPonerPendiente);
		
		btnTerminarOrden = new JButton("Terminar");
		btnTerminarOrden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cambiarEstadoATerminar();
			}
		});
		btnTerminarOrden.setBounds(180, 350, 140, 30);
		contentPanel.add(btnTerminarOrden);
		
		cargarDatosOrden();
	}

	private void cargarDatosOrden() {
		try {
			Controladora control = Controladora.getInstance();
			logica.OrdenCompra orden = control.obtenerOrden(numeroOrdenActual);
			

			lblNumOrden.setText(String.valueOf(orden.getNumero()));
			lblFechaOrden.setText(orden.getFecha().toString());
			lblEstadoOrden.setText(orden.getEstado());
			lblClienteOrden.setText(orden.getCliente().getNombre() + " (ID: " + orden.getCliente().getId() + ")");
			

			lblSubtotalOrden.setText("¢" + orden.calcularMonto());        
			lblImpuestoOrden.setText("¢" + orden.calcularMontoImpuesto()); 
			lblTotalOrden.setText("¢" + orden.calcularMontoTotal());       
			
			
			DefaultTableModel model = (DefaultTableModel) tablaLineasDetalle.getModel();
			model.setRowCount(0);
			
			for (logica.Linea linea : orden.getLineas()) {
				Object[] fila = new Object[] {
					linea.getProducto().getCodigo(),
					linea.getProducto().getNombre(),
					linea.getCantidad(),
					"¢" + linea.calcularCosto() 
				};
				model.addRow(fila);
			}
			
			boolean esEditable = !orden.getEstado().equalsIgnoreCase("Terminada");
			btnAgregarLinea.setEnabled(esEditable);
			btnEditarLinea.setEnabled(esEditable);
			btnBorrarLinea.setEnabled(esEditable);
			btnPonerPendiente.setEnabled(esEditable);
			btnTerminarOrden.setEnabled(esEditable);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al cargar los datos de la orden: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void agregarLinea() {
		LineaOrdenCompra ventanaLinea = new LineaOrdenCompra(numeroOrdenActual, -1);
		ventanaLinea.setVisible(true);
		cargarDatosOrden();
	}

	private void editarLinea() {
		int filaSeleccionada = tablaLineasDetalle.getSelectedRow();
		if (filaSeleccionada == -1) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar un producto de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		LineaOrdenCompra ventanaLinea = new LineaOrdenCompra(numeroOrdenActual, filaSeleccionada);
		ventanaLinea.setVisible(true);
		cargarDatosOrden();
	}

	private void borrarLinea() {
		int filaSeleccionada = tablaLineasDetalle.getSelectedRow();
		if (filaSeleccionada == -1) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar la línea que desea eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		String nombreProd = tablaLineasDetalle.getValueAt(filaSeleccionada, 1).toString();
		
		int respuesta = JOptionPane.showConfirmDialog(this, 
				"¿Está seguro de eliminar " + nombreProd + " de la orden de compra?", "Confirmar", JOptionPane.YES_NO_OPTION);
				
		if (respuesta == JOptionPane.YES_OPTION) {
			try {
				Controladora.getInstance().borrarLinea(numeroOrdenActual, filaSeleccionada);
				cargarDatosOrden();
				JOptionPane.showMessageDialog(this, "Producto borrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void cambiarEstadoAPendiente() {
		int respuesta = JOptionPane.showConfirmDialog(this, 
				"¿Desea cambiar el estado de la orden a 'Pendiente'?", "Confirmar Estado", JOptionPane.YES_NO_OPTION);
				
		if (respuesta == JOptionPane.YES_OPTION) {
			try {
				Controladora.getInstance().establecerOrdenPendiente(numeroOrdenActual);
				cargarDatosOrden(); 
				
				if (ventanaPrincipal != null) {
					ventanaPrincipal.cargarOrdenes();
				}
				
				JOptionPane.showMessageDialog(this, "Estado actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void cambiarEstadoATerminar() {
		int respuesta = JOptionPane.showConfirmDialog(this, 
				"¿Desea marcar esta orden como 'Terminada'? Una vez finalizada no podrá modificarse.", "Confirmar Cierre", JOptionPane.YES_NO_OPTION);
				
		if (respuesta == JOptionPane.YES_OPTION) {
			try {
				Controladora.getInstance().establecerOrdenTerminada(numeroOrdenActual);
				cargarDatosOrden();
				
				if (ventanaPrincipal != null) {
					ventanaPrincipal.cargarOrdenes();
				}
				
				JOptionPane.showMessageDialog(this, "Orden finalizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}