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

public class InformacionCliente extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable tableOrdenesDelCliente;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			InformacionCliente dialog = new InformacionCliente();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public InformacionCliente() {
		setModal(true);
		setTitle("Información del Cliente");
		setBounds(100, 100, 496, 371);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblIDCliente = new JLabel("ID: ");
		lblIDCliente.setBounds(10, 11, 48, 14);
		contentPanel.add(lblIDCliente);
		
		JLabel lblIdentificacionCliente = new JLabel("New label");
		lblIdentificacionCliente.setBounds(38, 11, 122, 14);
		contentPanel.add(lblIdentificacionCliente);
		
		JLabel lblNombreCliente = new JLabel("Nombre:");
		lblNombreCliente.setBounds(10, 30, 48, 14);
		contentPanel.add(lblNombreCliente);
		
		JLabel lblEmailCliente = new JLabel("Email:");
		lblEmailCliente.setBounds(10, 49, 48, 14);
		contentPanel.add(lblEmailCliente);
		
		JLabel lblNombreDelCliente = new JLabel("New label");
		lblNombreDelCliente.setBounds(59, 30, 219, 14);
		contentPanel.add(lblNombreDelCliente);
		
		JLabel lblEmailDelCliente = new JLabel("New label");
		lblEmailDelCliente.setBounds(46, 49, 160, 14);
		contentPanel.add(lblEmailDelCliente);
		
		JLabel lblListaOrdenesCliente = new JLabel("Lista de órdenes:");
		lblListaOrdenesCliente.setBounds(10, 84, 111, 14);
		contentPanel.add(lblListaOrdenesCliente);
		
		JScrollPane scrollPane_OrdenesCliente = new JScrollPane();
		scrollPane_OrdenesCliente.setBounds(10, 109, 340, 154);
		contentPanel.add(scrollPane_OrdenesCliente);
		
		tableOrdenesDelCliente = new JTable();
		DefaultTableModel dataModel = new DefaultTableModel(
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
		};
		tableOrdenesDelCliente.setModel(dataModel);
		tableOrdenesDelCliente.getColumnModel().getColumn(0).setPreferredWidth(97);
		tableOrdenesDelCliente.getColumnModel().getColumn(1).setPreferredWidth(116);
		tableOrdenesDelCliente.getColumnModel().getColumn(2).setPreferredWidth(118);
		scrollPane_OrdenesCliente.setViewportView(tableOrdenesDelCliente);
		
		JButton btnTodasLasOrdenes = new JButton("Todas");
		btnTodasLasOrdenes.setBounds(373, 116, 99, 22);
		contentPanel.add(btnTodasLasOrdenes);
		
		JButton btnOrdenesIniciadas = new JButton("Iniciadas");
		btnOrdenesIniciadas.setBounds(373, 151, 99, 22);
		contentPanel.add(btnOrdenesIniciadas);
		
		JButton btnOrdenesPendientes = new JButton("Pendientes");
		btnOrdenesPendientes.setBounds(373, 184, 99, 22);
		contentPanel.add(btnOrdenesPendientes);
		
		JButton btnOrdenesTerminadas = new JButton("Terminadas");
		btnOrdenesTerminadas.setBounds(371, 217, 101, 22);
		contentPanel.add(btnOrdenesTerminadas);
		
		JLabel lblPendiente = new JLabel("Total pendiente: ");
		lblPendiente.setBounds(10, 276, 85, 14);
		contentPanel.add(lblPendiente);
		
		JLabel lblTotalPendiente = new JLabel("New label");
		lblTotalPendiente.setBounds(105, 276, 101, 14);
		contentPanel.add(lblTotalPendiente);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
