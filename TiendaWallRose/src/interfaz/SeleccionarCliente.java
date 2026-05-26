package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;

public class SeleccionarCliente extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	private JTable tablaSeleccionCliente;
	private JButton btnConfirmar;
	private JButton btnCancelar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SeleccionarCliente dialog = new SeleccionarCliente();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SeleccionarCliente() {
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
		));
		scrollPaneClientes.setViewportView(tablaSeleccionCliente);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnConfirmar = new JButton("Confirmar");
				btnConfirmar.setActionCommand("OK");
				buttonPane.add(btnConfirmar);
				getRootPane().setDefaultButton(btnConfirmar);
			}
			{
				btnCancelar = new JButton("Cancelar");
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}
	}
}