package interfaz;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;

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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DetalleOrdenCompra dialog = new DetalleOrdenCompra();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DetalleOrdenCompra() {
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
		));
		scrollPane.setViewportView(tablaLineasDetalle);
		
		btnAgregarLinea = new JButton("Agregar");
		btnAgregarLinea.setBounds(465, 141, 100, 25);
		contentPanel.add(btnAgregarLinea);
		
		btnEditarLinea = new JButton("Editar");
		btnEditarLinea.setBounds(465, 177, 100, 25);
		contentPanel.add(btnEditarLinea);
		
		btnBorrarLinea = new JButton("Borrar");
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
		btnPonerPendiente.setBounds(20, 350, 150, 30);
		contentPanel.add(btnPonerPendiente);
		
		btnTerminarOrden = new JButton("Terminar");
		btnTerminarOrden.setBounds(180, 350, 140, 30);
		contentPanel.add(btnTerminarOrden);
	}
}