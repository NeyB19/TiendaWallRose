package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;

public class LineaOrdenCompra extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	private JTable tablaSeleccionProducto;
	private JTextField textCantidad;
	private JButton agregarButton;
	private JButton cancelButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LineaOrdenCompra dialog = new LineaOrdenCompra();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public LineaOrdenCompra() {
		setTitle("Linea de la Orden");
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
		));
		scrollPaneProductos.setViewportView(tablaSeleccionProducto);
		
		JLabel lblCantidad = new JLabel("Cantidad a comprar:");
		lblCantidad.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCantidad.setBounds(20, 245, 120, 14);
		contentPanel.add(lblCantidad);
		
		textCantidad = new JTextField();
		textCantidad.setBounds(150, 242, 120, 20);
		contentPanel.add(textCantidad);
		textCantidad.setColumns(10);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				agregarButton = new JButton("Agregar");
				agregarButton.setActionCommand("OK");
				buttonPane.add(agregarButton);
				getRootPane().setDefaultButton(agregarButton);
			}
			{
				cancelButton = new JButton("Cancelar");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}