package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import control.Controladora;
import logica.Cliente;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class SeleccionarCliente extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	private JTable tablaSeleccionCliente;
	private JButton btnConfirmar;
	private JButton btnCancelar;
	
	private VentanaInicial ventanaPrincipal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SeleccionarCliente dialog = new SeleccionarCliente(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SeleccionarCliente(VentanaInicial ventanaPrincipal) {
		this.ventanaPrincipal = ventanaPrincipal;
		
		setTitle("Seleccionar Cliente");
		setModal(true);
		setBounds(100, 100, 480, 320);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblInstruccion = new JLabel("Seleccione el cliente que realizará la orden de compra:");
		lblInstruccion.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblInstruccion.setBounds(20, 15, 350, 14);
		contentPanel.add(lblInstruccion);
		
		JScrollPane scrollPaneClientes = new JScrollPane();
		scrollPaneClientes.setBounds(20, 40, 424, 190);
		contentPanel.add(scrollPaneClientes);
		
		tablaSeleccionCliente = new JTable();
		tablaSeleccionCliente.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {
				"ID", "Nombre", "Email"
			}
		) {
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false; 
			}
		});
		scrollPaneClientes.setViewportView(tablaSeleccionCliente);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				cargarClientesDisponibles();
			}
		});
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnConfirmar = new JButton("Confirmar");
				btnConfirmar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						confirmarSeleccion();
					}
				});
				btnConfirmar.setActionCommand("OK");
				buttonPane.add(btnConfirmar);
				getRootPane().setDefaultButton(btnConfirmar);
			}
			{
				btnCancelar = new JButton("Cancelar");
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}
	}

	private void cargarClientesDisponibles() {
		Controladora control = Controladora.getInstance();
		DefaultTableModel model = (DefaultTableModel) tablaSeleccionCliente.getModel();
		model.setRowCount(0);
		
		List<Cliente> lista = control.obtenerListadoClientes();
		for (Cliente c : lista) {
			Object[] fila = new Object[] { c.getId(), c.getNombre(), c.getEmail() };
			model.addRow(fila);
		}
	}
	
	private void confirmarSeleccion() {
		int filaSeleccionada = tablaSeleccionCliente.getSelectedRow();
		if (filaSeleccionada == -1) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente de la lista para proceder.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		DefaultTableModel model = (DefaultTableModel) tablaSeleccionCliente.getModel();
		String idCliente = model.getValueAt(filaSeleccionada, 0).toString();
		
		try {
			Controladora control = Controladora.getInstance();
			control.crearOrdenVacia(idCliente); 
			
			List<logica.OrdenCompra> todas = control.obtenerListadoOrdenes();
			int numeroNuevaOrden = todas.get(todas.size() - 1).getNumero();
			dispose();
			
			DetalleOrdenCompra ventanaDetalle = new DetalleOrdenCompra(numeroNuevaOrden, ventanaPrincipal);
			ventanaDetalle.setTitle("Detalle de Órden N° " + numeroNuevaOrden);
			ventanaDetalle.setVisible(true);
			
			if (ventanaPrincipal != null) {
				ventanaPrincipal.cargarOrdenes();
			}
			
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error al crear la orden: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}