package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import control.Controladora;
import logica.Producto;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class LineaOrdenCompra extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	private JTable tablaSeleccionProducto;
	private JTextField textCantidad;
	private JButton agregarButton;
	private JButton cancelButton;
	
	private int numeroOrdenActual;
	private int numeroLineaEdicion = -1; 
	private boolean esAgregar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LineaOrdenCompra dialog = new LineaOrdenCompra(0, -1);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructor 
	 */
	public LineaOrdenCompra(int numeroOrden, int numeroLinea) {
		this.numeroOrdenActual = numeroOrden;
		this.numeroLineaEdicion = numeroLinea;
		this.esAgregar = (numeroLinea == -1); 
		
		setTitle(esAgregar ? "Añadir Producto a la Orden" : "Editar Cantidad del Producto");
		setModal(true);
		setBounds(100, 100, 500, 380);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblSeleccione = new JLabel("Seleccione el producto de la lista:");
		lblSeleccione.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSeleccione.setBounds(20, 15, 250, 14);
		contentPanel.add(lblSeleccione);
		
		JScrollPane scrollPaneProductos = new JScrollPane();
		scrollPaneProductos.setBounds(20, 40, 440, 180);
		contentPanel.add(scrollPaneProductos);
		
		tablaSeleccionProducto = new JTable();
		tablaSeleccionProducto.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {
				"Código Prod.", "Nombre", "Existencias", "Precio"
			}
		) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		});
		scrollPaneProductos.setViewportView(tablaSeleccionProducto);
		
		JLabel lblCantidad = new JLabel("Cantidad a comprar:");
		lblCantidad.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCantidad.setBounds(20, 245, 120, 14);
		contentPanel.add(lblCantidad);
		
		textCantidad = new JTextField();
		textCantidad.setBounds(150, 242, 120, 20);
		contentPanel.add(textCantidad);
		textCantidad.setColumns(10);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				cargarProductosInventario();
			}
		});
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				agregarButton = new JButton(esAgregar ? "Agregar" : "Guardar");
				agregarButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						guardarLinea(); 
					}
				});
				agregarButton.setActionCommand("OK");
				buttonPane.add(agregarButton);
				getRootPane().setDefaultButton(agregarButton);
			}
			{
				cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	
	private void cargarProductosInventario() {
		Controladora control = Controladora.getInstance();
		DefaultTableModel model = (DefaultTableModel) tablaSeleccionProducto.getModel();
		model.setRowCount(0);
		
		List<Producto> lista = control.obtenerListadoProductos();
		for (Producto p : lista) {
			Object[] fila = new Object[] { p.getCodigo(), p.getNombre(), p.getExistencias(), "¢" + p.getPrecio() };
			model.addRow(fila);
		}
		
		if (!esAgregar) {
			try {
				logica.OrdenCompra orden = control.obtenerOrden(numeroOrdenActual);
				logica.Linea lineaActual = orden.getLineas().get(numeroLineaEdicion);
				
				textCantidad.setText(String.valueOf(lineaActual.getCantidad()));
				
				int codigoBuscado = lineaActual.getProducto().getCodigo();
				for (int i = 0; i < model.getRowCount(); i++) {
					int codigoFila = Integer.parseInt(model.getValueAt(i, 0).toString());
					if (codigoFila == codigoBuscado) {
						tablaSeleccionProducto.setRowSelectionInterval(i, i);
						break;
					}
				}
				
				tablaSeleccionProducto.setEnabled(false);
				
			} catch (Exception ex) {
				System.out.println("Error recuperando los datos de la línea: " + ex.toString());
			}
		}
	}
	
	private void guardarLinea() {
		int filaSeleccionada = tablaSeleccionProducto.getSelectedRow();
		if (filaSeleccionada == -1) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar un producto de la lista.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		String cantidadStr = textCantidad.getText().trim();
		if (cantidadStr.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Debe ingresar una cantidad a comprar.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		try {
			double cantidad = Double.parseDouble(cantidadStr);
			if (cantidad <= 0) {
				JOptionPane.showMessageDialog(this, "La cantidad debe ser un número mayor a cero.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			DefaultTableModel model = (DefaultTableModel) tablaSeleccionProducto.getModel();
			int codigoProd = Integer.parseInt(model.getValueAt(filaSeleccionada, 0).toString());
			float existenciasDisponibles = Float.parseFloat(model.getValueAt(filaSeleccionada, 2).toString());
			
			if (cantidad > existenciasDisponibles) {
				JOptionPane.showMessageDialog(this, 
					"Error: No puede solicitar " + cantidad + " unidades.\n" +
					"El inventario actual para este producto es de solo " + existenciasDisponibles + ".", 
					"Falta de Existencias", 
					JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			Controladora control = Controladora.getInstance();
			if (esAgregar) {
				control.agregarLinea(numeroOrdenActual, codigoProd, cantidad);
				JOptionPane.showMessageDialog(this, "Producto añadido a la orden con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
			} else {
				control.actualizarLinea(numeroOrdenActual, numeroLineaEdicion, codigoProd, cantidad);
				JOptionPane.showMessageDialog(this, "Cantidad modificada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
			}
			
			dispose(); 
			
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(this, "La cantidad ingresada debe ser un valor numérico válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}